import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import static java.lang.Math.*;

public class DHKEBI_J {
    private static Scanner stdin = new Scanner(System.in);
    private static SecureRandom csprng = new SecureRandom();

    public static void main(String[] args) {
        System.out.println("Enter an option and press Return/Enter:");
        System.out.println("1) public key\n2) shared secret");

        int answer = stdin.nextInt();

        switch (answer) {
            case 1: {
                BigInteger userPublicKey = publicKeyGeneration();
                System.out.printf("Your public key is:\n%d",
                        userPublicKey);
                break;
            }
            case 2: {
                BigInteger userSharedSecret = sharedSecretGeneration();
                System.out.printf("Your shared secret is:\n%d",
                        userSharedSecret);
                break;
            }
            default: System.out.print("INVALID_ANS: Please enter 1 or " +
                    "2"); break;
        }
    }

    // generation methods
    private static BigInteger publicKeyGeneration() {
        BigInteger publicKey, moduloLong, baseLong, secretLong;
        moduloLong = longToBigInt(getModuloLong());
        baseLong = longToBigInt(getBaseLong());
        secretLong = longToBigInt(getSecretLong());
        boolean failOrSuccess = checkFailure(moduloLong, baseLong,
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
    private static BigInteger sharedSecretGeneration() {
        BigInteger sharedSecret, secretLong, moduloLong, publicKey;
        publicKey = longToBigInt(getPublicKey());
        secretLong = longToBigInt(getSecretLong());
        moduloLong = longToBigInt(getModuloLong());
        boolean failOrSuccess = checkFailure(moduloLong, secretLong,
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

    // get methods
    private static byte[] newRandomBytes() {
        byte[] byteArray = new byte[8];
        csprng.nextBytes(byteArray);
        return byteArray;
    }
    private static long bytesToLong(byte[] byteArray) {
        long longForm = 0;
        for (int i = 0; i < byteArray.length; i++) {
            longForm += ((long) byteArray[i] & 0xffL) << (8 * i);
        }
        return abs(longForm);
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
    private static long getModuloLong() {
        long moduloLong = 0;
        System.out.println("Do you have a modular long? (Y/n)");
        char ans = stdin.next().charAt(0);  // make String answer a char
        moduloLong = getLong(ans, "modulo");
        return moduloLong;
    }

    // call after getting longs
    private static BigInteger longToBigInt(long longValue) {
        BigInteger newBigInt;
        newBigInt = BigInteger.valueOf(longValue);
        return newBigInt;
    }
    private static boolean checkFailure(BigInteger... bigIntValues) {
        // drop the values into an array
        BigInteger[] valueArray = new BigInteger[bigIntValues.length];
        for (int item = 0; item < bigIntValues.length; item++) {
            valueArray[item] = bigIntValues[item];
        }
        // put the array into a list
        List<BigInteger> valueList = Arrays.asList(valueArray);

        // failure
        // not failure
        return valueList.contains(BigInteger.valueOf(1))
                || valueList.contains(BigInteger.valueOf(-1));
    }
}
