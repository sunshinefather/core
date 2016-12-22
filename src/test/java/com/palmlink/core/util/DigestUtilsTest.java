package com.palmlink.core.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Shihai.Fu
 */
public class DigestUtilsTest {
    @Test
    public void encodeBase64WithEmptyString() {
        assertEquals("", DigestUtils.base64(""));
    }

    @Test
    public void encodeBase64() {
        // from http://en.wikipedia.org/wiki/Base64
        assertEquals("bGVhc3VyZS4=", DigestUtils.base64("leasure."));
    }

    @Test
    public void decodeBase64() {
        // from http://en.wikipedia.org/wiki/Base64
        assertEquals("leasure.", new String(DigestUtils.decodeBase64("bGVhc3VyZS4=")));
    }

    @Test
    public void md5WithEmptyString() {
        // from http://en.wikipedia.org/wiki/MD5
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", DigestUtils.md5(""));
    }

    @Test
    public void md5() {
        // from http://en.wikipedia.org/wiki/MD5
        assertEquals("e4d909c290d0fb1ca068ffaddf22cbd0", DigestUtils.md5("The quick brown fox jumps over the lazy dog."));
    }

    @Test
    public void hex() {
        assertEquals("74657374206d657373616765", DigestUtils.hex("test message".getBytes()));
    }

    @Test
    public void decodeBase64URLSafe() {
        String message = "leasure.";
        String encodedMessage = DigestUtils.base64URLSafe(message.getBytes());
        System.err.println(encodedMessage);
        assertEquals(message, new String(DigestUtils.decodeBase64(encodedMessage)));
    }
}
