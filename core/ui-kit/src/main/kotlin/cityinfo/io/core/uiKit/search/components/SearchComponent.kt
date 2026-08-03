package cityinfo.io.core.uiKit.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cityinfo.io.core.uiKit.R
import cityinfo.io.core.uiKit.base.Colors
import cityinfo.io.core.uiKit.extensions.rippleClickable
import cityinfo.io.core.uiKit.search.SearchHandler
import cityinfo.io.core.uiKit.search.SearchStateInternal
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlin.time.Duration.Companion.milliseconds

private val FieldHeight = 56.dp
private val FieldHorizontalPadding = 16.dp
private val FieldBorderWidth = 1.5.dp
private val IconSize = 24.dp
private val IconSpacing = 8.dp

@Composable
internal fun SearchComponent(
    modifier: Modifier = Modifier,
    state: SearchStateInternal,
    handler: SearchHandler,
    placeholder: String,
    focusRequester: FocusRequester,
    search: MutableState<String>,
    backgroundColor: Color,
    focusedBackgroundColor: Color,
    focusedBorderColor: Color,
    shape: Shape,
    hintTextColor: Color,
    searchTextColor: Color,
    textStyle: TextStyle,
) {
    SearchField(
        modifier = modifier
            .focusRequester(focusRequester),
        search = search.value,
        state = state,
        handler = handler,
        placeholder = placeholder,
        backgroundColor = backgroundColor,
        focusedBackgroundColor = focusedBackgroundColor,
        focusedBorderColor = focusedBorderColor,
        shape = shape,
        hintTextColor = hintTextColor,
        searchTextColor = searchTextColor,
        textStyle = textStyle,
    )
}

@Composable
private fun SearchField(
    modifier: Modifier = Modifier,
    state: SearchStateInternal,
    handler: SearchHandler,
    placeholder: String,
    search: String,
    backgroundColor: Color,
    focusedBackgroundColor: Color,
    focusedBorderColor: Color,
    shape: Shape,
    hintTextColor: Color,
    searchTextColor: Color,
    textStyle: TextStyle,
) {
    var textFieldState by remember(search) {
        mutableStateOf(TextFieldValue(text = search, selection = TextRange(search.length)))
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(textFieldState) {
        snapshotFlow { textFieldState.text }
            .debounce(state.debounce.milliseconds)
            .filter { it.isNotEmpty() }
            .collect { text ->
                handler.onChangeText(text)
            }
    }

    BasicTextField(
        value = textFieldState,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        onValueChange = { textFieldState = it },
        textStyle = textStyle.copy(color = searchTextColor),
        cursorBrush = SolidColor(focusedBorderColor),
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                handler.onSearch(textFieldState.text)
            },
        ),
        singleLine = true,
        maxLines = 1,
        decorationBox = @Composable { innerTextField ->
            SearchFieldDecorationBox(
                isEmpty = textFieldState.text.isEmpty(),
                isFocused = isFocused,
                onClearText = {
                    textFieldState = TextFieldValue()
                    handler.onChangeText("")
                },
                placeholder = placeholder,
                textStyle = textStyle,
                backgroundColor = backgroundColor,
                focusedBackgroundColor = focusedBackgroundColor,
                focusedBorderColor = focusedBorderColor,
                shape = shape,
                hintTextColor = hintTextColor,
                innerTextField = innerTextField,
            )
        },
    )
}

@Composable
private fun SearchFieldDecorationBox(
    isEmpty: Boolean,
    isFocused: Boolean,
    onClearText: () -> Unit,
    placeholder: String,
    textStyle: TextStyle,
    backgroundColor: Color,
    focusedBackgroundColor: Color,
    focusedBorderColor: Color,
    shape: Shape,
    hintTextColor: Color,
    innerTextField: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(FieldHeight)
            .background(
                color = if (isFocused) focusedBackgroundColor else backgroundColor,
                shape = shape,
            )
            .border(
                width = if (isFocused) FieldBorderWidth else 0.dp,
                color = if (isFocused) focusedBorderColor else Color.Transparent,
                shape = shape,
            )
            .padding(horizontal = FieldHorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(IconSpacing),
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (isEmpty) {
                Text(
                    text = placeholder,
                    style = textStyle,
                    color = hintTextColor,
                    maxLines = 1,
                    textAlign = TextAlign.Start,
                )
            }
            innerTextField()
        }

        if (isEmpty) {
            Icon(
                modifier = Modifier.size(IconSize),
                painter = painterResource(R.drawable.ic_search),
                tint = Colors.IconPrimary,
                contentDescription = null,
            )
        } else {
            Icon(
                modifier = Modifier
                    .size(IconSize)
                    .rippleClickable(radius = IconSize, onClick = onClearText),
                painter = painterResource(R.drawable.ic_close),
                tint = Colors.BrandAccentColor,
                contentDescription = null,
            )
        }
    }
}