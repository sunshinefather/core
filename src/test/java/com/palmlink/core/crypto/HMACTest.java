package com.palmlink.core.crypto;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Shihai.Fu
 */
public class HMACTest {
    @Test
    public void authenticateByMD5() {
        HMAC hmac = new HMAC();
        hmac.setSecretKey("4VPDEtyUE".getBytes());
        byte[] bytes = hmac.digest("hello");
        Assert.assertNotNull(bytes);

        System.err.println(Arrays.toString(bytes));
    }

    @Test
    public void authenticateBySHA1() {
        HMAC hmac = new HMAC();
        hmac.setHash(HMAC.Hash.SHA1);
        hmac.setSecretKey("4VPDEtyUE".getBytes());
        byte[] bytes = hmac.digest("hello");
        Assert.assertNotNull(bytes);

        System.err.println(Arrays.toString(bytes));
    }

    @Test
    public void authenticateBySHA512() {
        HMAC hmac = new HMAC();
        hmac.setHash(HMAC.Hash.SHA512);
        hmac.setSecretKey("123456".getBytes());
        byte[] bytes = hmac.digest("hello");
        Assert.assertNotNull(bytes);

        System.err.println(Arrays.toString(bytes));
    }
}
