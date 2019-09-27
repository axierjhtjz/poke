package com.axier.poke.common

import java.util.*

fun String?.prettify(): String? {
    val locale = Locale.getDefault()
    return this?.toLowerCase(locale)?.capitalize()
}