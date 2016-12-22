package com.palmlink.core.crypto;

import com.palmlink.core.util.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 * @author Shihai.Fu
 */
public class AESTest {
    @Test
    public void encryptAndDecrypt() throws NoSuchAlgorithmException {
        AES aes = new AES();

        byte[] key = aes.generateKey();
        System.err.println(DigestUtils.base64(key));

        aes.setKey(key);
        String message = "test-message";

        byte[] cipherText = aes.encrypt(message.getBytes());

        byte[] plainBytes = aes.decrypt(cipherText);

        String plainText = new String(plainBytes);

        Assert.assertEquals(message, plainText);
    }
}
