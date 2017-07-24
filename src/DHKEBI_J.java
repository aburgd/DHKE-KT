import java.math.BigInteger;
import java.util.Scanner;
import org.apache.commons.codec.binary.Hex;

public class DHKEBI_J {

    private static Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter an option and press Return/Enter:");
        System.out.println("1) public key\n2) shared secret");

        int answer = stdin.nextInt();

        switch (answer) {
            case 1: {
                BigInteger userPublicKey = Generation.publicKeyGeneration();
                byte[] userKeyDigest = DigestTools.getDigest
                        (userPublicKey, "SHA");
                String userKeyHex = Hex.encodeHexString(userKeyDigest);
                System.out.printf("Your public key is:\n%d\n",
                        userPublicKey);
                System.out.print("Your public key's digest is:\n");
                DigestTools.digestPrinter(userKeyHex);
                break;
            }
            case 2: {
                BigInteger userSharedSecret = Generation.sharedSecretGeneration();
                byte[] userSecretDigest = DigestTools.getDigest
                        (userSharedSecret, "SHA");
                String userSecretHex = Hex.encodeHexString
                        (userSecretDigest);
                System.out.printf("Your shared secret is:\n%d\n",
                        userSharedSecret);
                System.out.print("Your shared secret's digest is:\n");
                DigestTools.digestPrinter(userSecretHex);
                break;
            }
            default: System.out.print("INVALID_ANS: Please enter 1 or " +
                    "2"); break;
        }
    }
}
