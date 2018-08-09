package mta.is.maiph.session;

import java.security.SecureRandom;

/**
 *
 * @author MaiPH
 */
public class TokenFactory {

    public static String generateRandomToken() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = bytes.toString();
        return token;
    }
}
