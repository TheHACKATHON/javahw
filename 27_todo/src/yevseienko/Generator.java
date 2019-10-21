package yevseienko;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Generator{
    private static final char[] HEX_VALUES = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E', 'F' };
    private static final Random RANDOM = new SecureRandom();

    public static String randomHexValues(int nValues) {
        char[] result = new char[nValues];
        for (int i = 0; i < nValues; i++) {
            result[i] = HEX_VALUES[RANDOM.nextInt(HEX_VALUES.length)];
        }
        return new String(result);
    }

    public static int randomInt(int max){
        return RANDOM.nextInt(max);
    }

    public static String md5(String text){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
