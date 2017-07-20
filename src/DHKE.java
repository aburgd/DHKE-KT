// Only use Random for example purposes!
// Use security.SecureRandom for production!
// Actually, this entire thing is for example purposes
// You've been warned

import java.security.SecureRandom;
import java.util.Scanner;
import java.util.stream.LongStream;
import java.math.BigInteger;
import static java.lang.Math.*;

public class DHKE {

    private static Scanner stdin = new Scanner(System.in);
    private static SecureRandom csprng = new SecureRandom();

    public static void main(String[] args) {

        System.out.println("Enter an option and press Return/Enter:");
        System.out.println("1) public key\n2) shared secret");

        int answer = stdin.nextInt();

        switch (answer) {
            case 1: {
                long userPublicKey = publicKeyGeneration();
                System.out.printf("Your public key is:\n %d",
                        userPublicKey);
                break;
            }
            case 2: {
                long userSharedSecret = sharedSecretGeneration();
                System.out.printf("Your shared secret is:\n%d",
                        userSharedSecret);
                break;
            }
        }
    }

    private static long publicKeyGeneration() {
        long publicKey, moduloLong, baseLong, secretLong;
        moduloLong = getModuloLong();
        baseLong = getBaseLong();
        secretLong = getSecretLong();
        boolean failOrSuccess = checkFailure(moduloLong, baseLong,
                secretLong);
        System.out.printf("failOrSuccess: %b\n", failOrSuccess);
        if (failOrSuccess) {
            System.out.println("BAD_LONG: Error getting long");
            System.exit(-1);
            publicKey = -1;
            return publicKey;
        } else {
            publicKey = (long)(pow((float)baseLong, (float)secretLong)) %
                    moduloLong;
            return publicKey;
        }
    }

    private static long sharedSecretGeneration() {
        long sharedSecret, secretLong, moduloLong, publicKey;
        publicKey = getPublicKey();
        secretLong = getSecretLong();
        moduloLong = getModuloLong();
        boolean failOrSuccess = checkFailure(moduloLong, secretLong,
                publicKey);
        System.out.printf("failOrSuccess: %b\n", failOrSuccess);
        if (failOrSuccess) {
            System.out.println("BAD_LONG: Error getting one or more long" +
                    " values");
            System.exit(-1);
            sharedSecret = -1;
            return sharedSecret;
        } else {
            publicKey = (long)(pow((float)secretLong, (float)secretLong)
            ) % moduloLong;
            return publicKey;
        }
    }

    private static byte[] newRandomBytes() {
        byte[] byteArray = new byte[4];  // make some bytes
        csprng.nextBytes(byteArray);  // get some new secure bytes
        return byteArray;
    }

    private static long bytesToLong(byte[] byteArray) {
        long newLong = 0;
        for (int i = 0; i < byteArray.length; i++) {
            newLong += ((long) byteArray[i] & 0xffL) << (8 * i);
        }
        newLong = abs(newLong);
        return newLong;  // in case of negative
    }

    private static long getPublicKey() {
        long publicKey = 0;
        System.out.println("Do you have a public key? (Y/n)");
        char ans = stdin.next().charAt(0);
        if (ans == 'Y' || ans == 'y') {
            System.out.println("Please enter your public key:");
            publicKey = stdin.nextLong();
        } else if (ans == 'n' || ans == 'N'){
            System.out.print("BAD_ANS: Please re-run the program after " +
                    "generating a public key");
            publicKey = -1;
            System.exit((int)publicKey);
        }
        return publicKey;
    }

    private static long getModuloLong() {
        long moduloLong = 0;
        System.out.println("Do you have a modular long? (Y/n)");
        char ans = stdin.next().charAt(0);  // make String answer a char
        moduloLong = getLong(ans, "modulo");
        return moduloLong;
    }

    private static long getBaseLong() {
        long baseLong;
        System.out.println("Do you have a shared base? (Y/n)");
        char ans = stdin.next().charAt(0);  // make String answer a char
        baseLong = getLong(ans, "base");
        return baseLong;
    }

    private static long getSecretLong() {
        long secretLong;
        System.out.println("Do you have a secret long? (Y/n)");
        char ans = stdin.next().charAt(0);  // make String answer a char
        secretLong = getLong(ans, "secret");
        return secretLong;
    }

    private static long getLong(char ans, String longType) {
        long get;
        if (ans == 'Y' || ans == 'y') {
            System.out.printf("Please enter your %s long:\n", longType);
            get = stdin.nextLong();
            return get;
        } else if (ans == 'n' || ans == 'N'){
            get = bytesToLong(newRandomBytes());
            System.out.printf("Your %s long is %d\n", longType, get);
            return get;
        } else {
            return -1;
        }
    }

    private static boolean checkFailure(long... longValues) {
        return LongStream.of(longValues).anyMatch(item ->
                item < 0);
    }
}
