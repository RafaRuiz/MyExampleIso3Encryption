package com.example.iso3.encryption.data.encriptors

import java.util.*
import kotlin.random.Random

class Iso3Encryptor {

    /**
     * Generates a random HEX char between 10 and 15
     *
     */
    private fun generateRandomHexadecimalChar(): String {
        return Integer.toHexString(Random.nextInt(10,16))
    }

    /**
     * It takes the right most 12 pan digits (without the check digit) needed to do the calculations.
     * Example, for:
     * 11114444888822227, it returns 444488882222
     *
     * and append it to a prefix of four zeros "0000"
     *
     * Example, for:
     * 1111444488882222
     *
     * it returns
     * 0000444488882222
     */
    fun adjustPanWithRightMost12Digits(pan: String): String {
        val panWithoutCheckDigit = pan.subSequence(0, pan.length - 1)
        return "0000" + panWithoutCheckDigit.subSequence(
            panWithoutCheckDigit.length - 12,
            panWithoutCheckDigit.length
        )
    }

    /**
     * We need to create the first PIN block which is:
     * [F L P P P P P/R P/R P/R P/R P/R P/R P/R P/R P/R P/R] where:
     *
     * F - is the block format
     * L - is the length of the pin (4 for '1234', 6 for '177421')
     * P - is the PIN digit at position
     * R - is a random hex value between 0 (0x0) and F (0xF)
     *
     * accepts a:
     * @param pin that has previously checked if it has between 4 and 12 characters
     *
     * @return the pin block that we'll need to calculate the Clear PIN Block at the end
     */
    fun createPinBlock(pin: String): String {
        return StringBuilder()
            .append(BLOCK_FORMAT)
            .append(Integer.toHexString(pin.length))
            .append(pin)
            .apply {
                while(length < PIN_BLOCK_DIGITS) {
                    append(generateRandomHexadecimalChar())
                }
            }
            .toString()
    }

    /**
     * This method takes the PIN Block generated with [createPinBlock], the parameter [pan] and
     * executes the operation of (XOR) described in the calculation steps.
     *
     * @param pinBlock is the String generated previously with [createPinBlock]
     * @param pan is a static String, usually the Personal Account Number
     */
    fun createClearPinBlock(pinBlock: String, pan: String): String {
        val stringBuilder = StringBuilder()
        (pinBlock.indices)
            .forEach {
                val pinBlockChar = pinBlock[it]
                val panChar = pan[it]
                val xor = pinBlockChar.toString().toLong(16)
                    .xor(panChar.toString().toLong(16))
                    .let { long ->
                        return@let Integer.toHexString(long.toInt())
                            .toUpperCase(Locale.getDefault())
                    }
                stringBuilder.append(xor)
            }

        return stringBuilder.toString()
    }

    companion object {
        private const val BLOCK_FORMAT = "3"
        private const val PIN_BLOCK_DIGITS = 16
    }

}