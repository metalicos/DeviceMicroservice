package com.cyberdone.DeviceMicroservice.model.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;


@Slf4j
@Service
public class EncDecService {

    @Value("${security.device.key}")
    private byte[] securityKey;

    private SecretKeySpec generateKey() throws UnsupportedEncodingException {
        return new SecretKeySpec(securityKey, "AES");
    }

    public String encrypt(String message) throws GeneralSecurityException {
        try {
            byte[] cipherText = encrypt(generateKey(), message.getBytes(StandardCharsets.UTF_8));
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
