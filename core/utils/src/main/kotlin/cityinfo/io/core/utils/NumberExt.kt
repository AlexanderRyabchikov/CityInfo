package cityinfo.io.core.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

private const val GROUPING_SEPARATOR = ' '
private const val GROUPING_PATTERN = "#,##0"

fun Long.toGroupedNumber(locale: Locale = Locale.getDefault()): String {
    val symbols = DecimalFormatSymbols(locale).apply { groupingSeparator = GROUPING_SEPARATOR }

    return DecimalFormat(GROUPING_PATTERN, symbols).format(this)
}