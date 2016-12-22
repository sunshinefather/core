package com.palmlink.core.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * @author Shihai.Fu
 */
public class ByteArrayPostTest {
    @Test
    public void useOctetStreamAsContentType() throws UnsupportedEncodingException {
        ByteArrayPost post = new ByteArrayPost("http://url");
        post.setBytes(new byte[10]);

        HttpEntity entity = ((HttpPost) post.createRequest()).getEntity();

        assertEquals("binary/octet-stream", entity.getContentType().getValue());
    }
}
