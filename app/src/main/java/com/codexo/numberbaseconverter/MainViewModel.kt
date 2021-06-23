package com.codexo.numberbaseconverter

import androidx.lifecycle.ViewModel
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

class MainViewModel : ViewModel() {


    fun toDec(num: String, source: Int): BigDecimal {
        var valueInTen: Int
        var decSum = BigDecimal.ZERO
        var dot = num.lastIndex + 1
        for (i in num.indices) if (num[i] == '.') dot = i
        for (j in num.indices) {
            val pow = if (j < dot) dot - j - 1 else j - dot
            valueInTen = when {
                num[j].isLetter() -> {
                    num[j].code - 87
                }
                num[j].isDigit() -> {
                    num[j].toString().toInt()
                }
                else -> 0
            }
            val digitValue = if (j < dot) BigDecimal(valueInTen) * BigDecimal(source).pow(pow)
            else BigDecimal(valueInTen).setScale(7, RoundingMode.DOWN) / BigDecimal(source).pow(pow)
            decSum += digitValue
        }
        return decSum
    }

    fun fromDec(transDec: BigDecimal, target: BigInteger) {
        var remain: BigInteger
        var str = """"""
        val intPart: BigInteger = transDec.setScale(0, RoundingMode.DOWN).toBigInteger()

        var div = intPart
        while (div > BigInteger.ZERO) {
            remain = div % target
            div /= target
            val toStr = if (remain.toInt() in 10..35) {
                (remain.toInt() + 87).toChar().toString()
            } else remain.toInt().toString()
            str += toStr
        }
        print("Conversion result: ")
        print(str.reversed())

        val checkDot = transDec.toString()
        if ('.' !in checkDot) return

        print(".")
        var fraction = transDec - BigDecimal(intPart)
        var count = 0
        while (count < 5) {
            val multiply = fraction * BigDecimal(target)
            val line = multiply.setScale(0, RoundingMode.DOWN)
            fraction = multiply - line
            val fractionStr = if (line.toInt() in 10..35) {
                (line.toInt() + 87).toChar().toString()
            } else line.toInt().toString()
            print(fractionStr)
            count++
        }
    }
}