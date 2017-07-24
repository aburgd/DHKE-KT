import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestTools {
    public static byte[] getDigest(BigInteger bigIntValue, String...
            instanceAlgo) {
        if (instanceAlgo[1].isEmpty()) instanceAlgo[1] = "SHA-256";
        String biString = bigIntValue.toString();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(instanceAlgo[1]);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digest.digest(biString.getBytes(StandardCharsets
                .UTF_8));
    }

    public static void digestPrinter(String digestHex) {
        // left padding for odd-length strings
        if (digestHex.length() % 2 != 0) digestHex = " " + digestHex;
        for (int i = 0; i < digestHex.length(); i += 2) {
            char currentChar = digestHex.charAt(i);
            char nextChar = digestHex.charAt(i+1);
            System.out.printf("%c%c ", currentChar, nextChar);
        }
    }
}
