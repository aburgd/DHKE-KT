import java.math.BigInteger;
import java.util.Scanner;

public class DHKEBI_J {

    private static Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter an option and press Return/Enter:");
        System.out.println("1) public key\n2) shared secret");

        int answer = stdin.nextInt();

        switch (answer) {
            case 1: {
                BigInteger userPublicKey = Generation.publicKeyGeneration();
                String userKeyDigest = DigestTools.getDigest
                        (userPublicKey, "SHA").toString();

                System.out.printf("Your public key is:\n%d",
                        userPublicKey);
                System.out.print("Your public key's digest is:\n");
                DigestTools.digestPrinter(userKeyDigest);
                break;
            }
            case 2: {
                BigInteger userSharedSecret = Generation.sharedSecretGeneration();
                String userSecretDigest = DigestTools.getDigest
                        (userSharedSecret, "SHA").toString();
                System.out.printf("Your shared secret is:\n%d",
                        userSharedSecret);
                System.out.print("Your shared secret's digest is:\n");
                DigestTools.digestPrinter(userSecretDigest);
                break;
            }
            default: System.out.print("INVALID_ANS: Please enter 1 or " +
                    "2"); break;
        }
    }
}
