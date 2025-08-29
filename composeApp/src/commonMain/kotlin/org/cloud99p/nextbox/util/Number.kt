package org.cloud99p.nextbox.util

import kotlin.math.abs
import kotlin.math.floor
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

fun Float.formatAsMoney(decimals: Int = 2): String {
    val intPart = this.toLong()
    var n = abs(intPart)

    val intBuilder = StringBuilder()
    if (n == 0L) {
        intBuilder.append('0')
    } else {
        var count = 0
        while (n > 0) {
            if (count == 3) {
                intBuilder.append(',')
                count = 0
            }
            intBuilder.append((n % 10).toInt())
            n /= 10
            count++
        }
        if (intPart < 0) {
            intBuilder.append('-')
        }
        intBuilder.reverse()
    }

    return if (decimals > 0) {
        val factor = 10.0.pow(decimals).toFloat()
        val frac = abs(this - intPart)
        val truncated = floor(frac * factor)
        val fracStr = truncated
            .toInt()
            .toString()
            .padStart(decimals, '0')
        "$intBuilder.$fracStr"
    } else {
        intBuilder.toString()
    }
}
