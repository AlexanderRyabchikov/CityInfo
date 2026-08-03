package cityinfo.io.core.uiKit.base

import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

private val RobotoDeviceFamily = DeviceFontFamilyName("Roboto")

private fun robotoFont(weight: FontWeight) = Font(
    familyName = RobotoDeviceFamily,
    weight = weight,
)

val Roboto = FontFamily(
    robotoFont(FontWeight.Thin),
    robotoFont(FontWeight.ExtraLight),
    robotoFont(FontWeight.Light),
    robotoFont(FontWeight.Normal),
    robotoFont(FontWeight.Medium),
    robotoFont(FontWeight.SemiBold),
    robotoFont(FontWeight.Bold),
    robotoFont(FontWeight.ExtraBold),
    robotoFont(FontWeight.Black),
)