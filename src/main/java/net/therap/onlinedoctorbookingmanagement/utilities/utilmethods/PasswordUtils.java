package net.therap.onlinedoctorbookingmanagement.utilities.utilmethods;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;

import static net.therap.onlinedoctorbookingmanagement.utilities.utilmethods.StringUtils.checkIfAParamIsNullOrEmpty;

/**
 * @author anwar
 * @since 2/9/18
 */
public class PasswordUtils {

    private static final int iterationsForHashing = 20 * 1000;
    private static final int saltLength = 32;
    private static final int desiredKeyLength = 256;

    public static String getSaltedHash(String password) throws Exception {
        byte[] salt;
        String saltedHash;

        try {
            salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength);
            saltedHash = Base64.encodeBase64String(salt) + "$" + hash(password, salt);
        } catch (Exception e) {
            return null;
        }

        return saltedHash;
    }

    public static boolean isPasswordMatched(String password, String stored) throws Exception {
        if (!checkIfAParamIsNullOrEmpty(password, stored)) {
            String[] saltAndPass = stored.split("\\$");

            if (saltAndPass.length != 2) {
                throw new IllegalStateException(
                        "The stored password should have the form 'salt$hash'");
            }

            String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));

            return hashOfInput.equals(saltAndPass[1]);
        }
        return false;
    }

    private static String hash(String password, byte[] salt) throws Exception {

        if (password.length() == 0) {
            throw new IllegalArgumentException("Empty passwords are not supported.");
        }

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey secretKey = secretKeyFactory.generateSecret(new PBEKeySpec(
                        password.toCharArray(), salt, iterationsForHashing, desiredKeyLength)
        );

        return Base64.encodeBase64String(secretKey.getEncoded());
    }
}