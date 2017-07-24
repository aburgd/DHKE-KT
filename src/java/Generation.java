package java;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

class Generation {
    private static Scanner stdin = new Scanner(System.in);
    private static SecureRandom csprng = new SecureRandom();

    // generation methods
    static BigInteger publicKeyGeneration() {
        BigInteger publicKey, moduloLong, baseLong, secretLong;
        moduloLong = LongOps.longToBigInt(LongOps.getModuloLong());
        baseLong = LongOps.longToBigInt(LongOps.getBaseLong());
        secretLong = LongOps.longToBigInt(LongOps.getSecretLong());
        boolean failOrSuccess = LongOps.checkFailure(moduloLong, baseLong,
                secretLong);
        System.out.printf("failOrSuccess: %b\n", failOrSuccess);
        if (failOrSuccess) {
            System.out.println("BAD_LONG: Error getting long");
            System.exit(-1);
            publicKey = BigInteger.valueOf(-1);
            return publicKey;
        } else {
            publicKey = baseLong.modPow(secretLong, moduloLong);
            return publicKey;
        }
    }
    static BigInteger sharedSecretGeneration() {
        BigInteger sharedSecret, secretLong, moduloLong, publicKey;
        publicKey = LongOps.longToBigInt(LongOps.getPublicKey());
        secretLong = LongOps.longToBigInt(LongOps.getSecretLong());
        moduloLong = LongOps.longToBigInt(LongOps.getModuloLong());
        boolean failOrSuccess = LongOps.checkFailure(moduloLong, secretLong,
                publicKey);
        System.out.printf("failOrSuccess: %b\n", failOrSuccess);
        if (failOrSuccess) {
            System.out.println("BAD_LONG: Error getting one or more long" +
                    " values");
            System.exit(-1);
            sharedSecret = BigInteger.valueOf(-1);
            return sharedSecret;
        } else {
            sharedSecret = publicKey.modPow(secretLong, moduloLong);
            return sharedSecret;
        }
    }
}
