package kotlin

import java.math.BigInteger
import java.security.SecureRandom
import java.util.Arrays
import java.util.Scanner

import java.lang.Math.abs

internal object LongOps {
    private val stdin = Scanner(System.`in`)
    private val csprng = SecureRandom()

    // get methods
    private fun newRandomBytes(): ByteArray {
        val byteArray = ByteArray(8)
        csprng.nextBytes(byteArray)
        return byteArray
    }

    private fun bytesToLong(byteArray: ByteArray): Long {
        var longForm: Long = 0
        for (i in byteArray.indices) {
            longForm += byteArray[i].toLong() and 0xffL shl 8 * i
        }
        return abs(longForm)
    }

    private fun getLong(ans: Char, longType: String): Long {
        val get: Long
        if (ans == 'Y' || ans == 'y') {
            System.out.printf("Please enter your %s long:\n", longType)
            get = stdin.nextLong()
            return get
        } else if (ans == 'n' || ans == 'N') {
            get = bytesToLong(newRandomBytes())
            System.out.printf("Your %s long is %d\n", longType, get)
            return get
        } else {
            return -1
        }
    }

    val publicKey: Long
        get() {
            var publicKey: Long = 0
            println("Do you have a public key? (Y/n)")
            val ans = stdin.next()[0]
            if (ans == 'Y' || ans == 'y') {
                println("Please enter your public key:")
                publicKey = stdin.nextLong()
            } else if (ans == 'n' || ans == 'N') {
                print("BAD_ANS: Please re-run the program after " + "generating a public key")
                publicKey = -1
                System.exit(publicKey.toInt())
            }
            return publicKey
        }
    // make String answer a char
    val baseLong: Long
        get() {
            val baseLong: Long
            println("Do you have a shared base? (Y/n)")
            val ans = stdin.next()[0]
            baseLong = getLong(ans, "base")
            return baseLong
        }
    // make String answer a char
    val secretLong: Long
        get() {
            val secretLong: Long
            println("Do you have a secret long? (Y/n)")
            val ans = stdin.next()[0]
            secretLong = getLong(ans, "secret")
            return secretLong
        }
    // make String answer a char
    val moduloLong: Long
        get() {
            var moduloLong: Long = 0
            println("Do you have a modular long? (Y/n)")
            val ans = stdin.next()[0]
            moduloLong = getLong(ans, "modulo")
            return moduloLong
        }

    // call after getting longs
    fun longToBigInt(longValue: Long): BigInteger {
        val newBigInt: BigInteger
        newBigInt = BigInteger.valueOf(longValue)
        return newBigInt
    }

    fun checkFailure(vararg bigIntValues: BigInteger): Boolean {
        // drop the values into an array
        val valueArray = arrayOfNulls<BigInteger>(bigIntValues.size)
        for (item in bigIntValues.indices) {
            valueArray[item] = bigIntValues[item]
        }
        // put the array into a list
        val valueList = Arrays.asList<BigInteger>(*valueArray)

        // failure
        // not failure
        return valueList.contains(BigInteger.valueOf(1)) || valueList.contains(BigInteger.valueOf(-1))
    }
}
