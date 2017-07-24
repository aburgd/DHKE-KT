package kotlin

import java.math.BigInteger
import java.security.SecureRandom
import java.util.Scanner

internal object Generation {
    private val stdin = Scanner(System.`in`)
    private val csprng = SecureRandom()

    // generation methods
    fun publicKeyGeneration(): BigInteger {
        val publicKey: BigInteger
        val moduloLong: BigInteger
        val baseLong: BigInteger
        val secretLong: BigInteger
        moduloLong = LongOps.longToBigInt(LongOps.moduloLong)
        baseLong = LongOps.longToBigInt(LongOps.baseLong)
        secretLong = LongOps.longToBigInt(LongOps.secretLong)
        val failOrSuccess = LongOps.checkFailure(moduloLong, baseLong,
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

    fun sharedSecretGeneration(): BigInteger {
        val sharedSecret: BigInteger
        val secretLong: BigInteger
        val moduloLong: BigInteger
        val publicKey: BigInteger
        publicKey = LongOps.longToBigInt(LongOps.publicKey)
        secretLong = LongOps.longToBigInt(LongOps.secretLong)
        moduloLong = LongOps.longToBigInt(LongOps.moduloLong)
        val failOrSuccess = LongOps.checkFailure(moduloLong, secretLong,
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
}
