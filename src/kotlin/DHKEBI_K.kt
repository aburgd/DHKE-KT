import java.security.SecureRandom
import java.math.BigInteger
import java.util.Arrays
import java.util.Scanner
import java.lang.Math.*

object DHKEBI_K {
    private val stdin = Scanner(System.`in`)
    private val csprng = SecureRandom()

    @JvmStatic fun main(args: Array<String>) {
        println("Enter an option and press Return/Enter:")
        println("1) public key\n2) shared secret")

        val answer = stdin.nextInt()

        when (answer) {
            1 -> {
                val userPublicKey = publicKeyGeneration()
                System.out.printf("Your public key is:\n%d",
                        userPublicKey)
            }
            2 -> {
                val userSharedSecret = sharedSecretGeneration()
                System.out.printf("Your shared secret is:\n%d",
                        userSharedSecret)
            }
            else -> print("INVALID_ANS: Please enter 1 or " + "2")
        }
    }

    // generation methods
    private fun publicKeyGeneration(): BigInteger {
        val publicKey: BigInteger
        val moduloLong: BigInteger = longToBigInt(getModuloLong())
        val baseLong: BigInteger = longToBigInt(getBaseLong())
        val secretLong: BigInteger = longToBigInt(getSecretLong())
        val failOrSuccess = checkFailure(moduloLong, baseLong,
                secretLong)
        System.out.printf("failOrSuccess: %b\n", failOrSuccess)
        if (failOrSuccess) {
            println("BAD_LONG: Error getting long")
            System.exit(-1)
            publicKey = BigInteger.valueOf(-1)
            return publicKey
        } else {
            publicKey = baseLong.modPow(secretLong, moduloLong)
            return publicKey
        }
    }

    private fun sharedSecretGeneration(): BigInteger {
        val sharedSecret: BigInteger
        val secretLong: BigInteger = longToBigInt(getSecretLong())
        val moduloLong: BigInteger = longToBigInt(getModuloLong())
        val publicKey: BigInteger = longToBigInt(getPublicKey())
        val failOrSuccess = checkFailure(moduloLong, secretLong,
                publicKey)
        System.out.printf("failOrSuccess: %b\n", failOrSuccess)
        if (failOrSuccess) {
            println("BAD_LONG: Error getting one or more long" + " values")
            System.exit(-1)
            sharedSecret = BigInteger.valueOf(-1)
            return sharedSecret
        } else {
            sharedSecret = publicKey.modPow(secretLong, moduloLong)
            return sharedSecret
        }
    }

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

    private fun getPublicKey(): Long {
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

    private fun getBaseLong(): Long {
            val baseLong: Long
            println("Do you have a shared base? (Y/n)")
            val ans = stdin.next()[0]
            baseLong = getLong(ans, "base")
            return baseLong
    }
    private fun getSecretLong(): Long {
            val secretLong: Long
            println("Do you have a secret long? (Y/n)")
            val ans = stdin.next()[0]
            secretLong = getLong(ans, "secret")
            return secretLong
        }
    private fun getModuloLong(): Long {
            var moduloLong: Long = 0
            println("Do you have a modular long? (Y/n)")
            val ans = stdin.next()[0]
            moduloLong = getLong(ans, "modulo")
            return moduloLong
        }

    // call after getting longs
    private fun longToBigInt(longValue: Long): BigInteger {
        val newBigInt: BigInteger = BigInteger.valueOf(longValue)
        return newBigInt
    }

    private fun checkFailure(vararg bigIntValues: BigInteger): Boolean {
        // drop the values into an array
        val valueArray = arrayOfNulls<BigInteger>(bigIntValues.size)
        for (item in bigIntValues.indices) {
            valueArray[item] = bigIntValues[item]
        }
        // put the array into a list
        val valueList = Arrays.asList<BigInteger>(*valueArray)

        return valueList.contains(BigInteger.valueOf(1))
                || valueList.contains(BigInteger.valueOf(-1))
    }
}
