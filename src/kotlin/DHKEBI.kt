package kotlin

import org.apache.commons.codec.binary.Hex

import java.math.BigInteger
import java.util.Scanner

object DHKEBI {

    private val stdin = Scanner(System.`in`)

    @JvmStatic fun main(args: Array<String>) {
        println("Enter an option and press Return/Enter:")
        println("1) public key\n2) shared secret")

        val answer = stdin.nextInt()

        when (answer) {
            1 -> {
                val userPublicKey = Generation.publicKeyGeneration()
                val userKeyDigest = DigestTools.getDigest(userPublicKey, "SHA")
                val userKeyHex = Hex.encodeHexString(userKeyDigest)
                System.out.printf("Your public key is:\n%d\n",
                        userPublicKey)
                print("Your public key's digest is:\n")
                DigestTools.digestPrinter(userKeyHex)
            }
            2 -> {
                val userSharedSecret = Generation.sharedSecretGeneration()
                val userSecretDigest = DigestTools.getDigest(userSharedSecret, "SHA")
                val userSecretHex = Hex.encodeHexString(userSecretDigest)
                System.out.printf("Your shared secret is:\n%d\n",
                        userSharedSecret)
                print("Your shared secret's digest is:\n")
                DigestTools.digestPrinter(userSecretHex)
            }
            else -> print("INVALID_ANS: Please enter 1 or " + "2")
        }
    }
}
