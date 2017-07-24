import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.security.SecureRandom;

import static java.lang.Math.abs;

class LongOps {
    private static Scanner stdin = new Scanner(System.in);
    private static SecureRandom csprng = new SecureRandom();

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
    static long getPublicKey() {
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
    static long getBaseLong() {
        long baseLong;
        System.out.println("Do you have a shared base? (Y/n)");
        char ans = stdin.next().charAt(0);  // make String answer a char
        baseLong = getLong(ans, "base");
        return baseLong;
    }
    static long getSecretLong() {
        long secretLong;
        System.out.println("Do you have a secret long? (Y/n)");
        char ans = stdin.next().charAt(0);  // make String answer a char
        secretLong = getLong(ans, "secret");
        return secretLong;
    }
    static long getModuloLong() {
        long moduloLong = 0;
        System.out.println("Do you have a modular long? (Y/n)");
        char ans = stdin.next().charAt(0);  // make String answer a char
        moduloLong = getLong(ans, "modulo");
        return moduloLong;
    }

    // call after getting longs
    public static BigInteger longToBigInt(long longValue) {
        BigInteger newBigInt;
        newBigInt = BigInteger.valueOf(longValue);
        return newBigInt;
    }
    public static boolean checkFailure(BigInteger... bigIntValues) {
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
