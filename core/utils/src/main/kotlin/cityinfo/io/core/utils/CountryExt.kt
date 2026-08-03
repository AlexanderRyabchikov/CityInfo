package cityinfo.io.core.utils

import java.util.Locale

fun String?.toCountryName(locale: Locale = Locale.getDefault()): String {
    val code = this?.trim().orEmpty()
    if (code.length != 2) return code

    val displayName = Locale("", code.uppercase(Locale.ROOT)).getDisplayCountry(locale)

    return displayName.takeIf { it.isNotBlank() && !it.equals(code, ignoreCase = true) } ?: code
}