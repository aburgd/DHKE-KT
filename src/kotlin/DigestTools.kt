package kotlin

import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

internal object DigestTools {
    fun getDigest(bigIntValue: BigInteger, instance: String?):
            ByteArray {
        var instanceAlgo: String? = instance
        if (instanceAlgo.isNullOrEmpty()) instanceAlgo = "SHA-256"
        val biString = bigIntValue.toString()
        var digest: MessageDigest? = null
        try {
            digest = MessageDigest.getInstance(instanceAlgo)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return digest!!.digest(biString.toByteArray(StandardCharsets
                .UTF_8))
    }

    fun digestPrinter(digestHex: String) {
        var digestHex = digestHex
        // left padding for odd-length strings
        if (digestHex.length % 2 != 0) digestHex = " " + digestHex
        var i = 0
        while (i < digestHex.length) {
            val currentChar = digestHex[i]
            val nextChar = digestHex[i + 1]
            System.out.printf("%c%c ", currentChar, nextChar)
            i += 2
        }
    }
}
