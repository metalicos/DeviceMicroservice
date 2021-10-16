package com.cyberdone.DeviceMicroservice.model.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;


@Slf4j
@Service
public class EncDecService {

    private static SecretKeySpec generateKey() throws UnsupportedEncodingException {
        return new SecretKeySpec(new byte[]{-1, -2, -3, 100, 100, -88, 100, -95, 100, -69, 95, 48, 77, -78, 100, 62},
                "AES");
    }

    public String encrypt(String message)
            throws GeneralSecurityException {
        try {
            final SecretKeySpec key = generateKey();
            byte[] cipherText = encrypt(key, message.getBytes(StandardCharsets.UTF_8));
            return String.valueOf(Base64.encodeBase64String(cipherText));
        } catch (UnsupportedEncodingException e) {
            throw new GeneralSecurityException(e);
        }
    }

    public String decrypt(String b64Text) throws GeneralSecurityException {
        try {
            String decrypted = new String(decrypt(generateKey(), Base64.decodeBase64(b64Text)), StandardCharsets.UTF_8);
            return decrypted.replaceAll("%%%.+$", "");
        } catch (UnsupportedEncodingException e) {
            throw new GeneralSecurityException(e);
        }
    }

    private byte[] encrypt(SecretKeySpec key, byte[] message) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(message);
    }

    private byte[] decrypt(SecretKeySpec key, byte[] decodedCipherText) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(decodedCipherText);
    }

}
