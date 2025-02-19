package com.axier.poke.common

import java.util.*

fun String?.prettify(): String? {
    val locale = Locale.getDefault()
    return this?.lowercase(locale)
        ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}