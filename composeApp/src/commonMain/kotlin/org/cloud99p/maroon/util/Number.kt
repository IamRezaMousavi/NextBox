package org.cloud99p.maroon.util

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sign

fun Float.round(decimals: Int = 0): Float {
    val pow = 10.0f.pow(decimals)
    return round(abs(this) * pow) / pow * sign(this)
}

fun Double.round(decimals: Int = 0): Double {
    val pow = 10.0.pow(decimals)
    return round(abs(this) * pow) / pow * sign(this)
}
