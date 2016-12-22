package com.palmlink.core.http;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.easymock.EasyMockSupport;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

/**
 * @author Shihai.Fu
 */
public class HTTPHeadersTest extends EasyMockSupport {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void createHeadersFromHTTPResponse() {
        exception.expect(UnsupportedOperationException.class);

        HttpResponse response = createNiceMock(HttpResponse.class);
        expect(response.getAllHeaders()).andReturn(new Header[0]);
        replayAll();

        HTTPHeaders headers = HTTPHeaders.loadHeaders(response);
        
        headers.add("head", "value");

        verifyAll();
    }

    @Test
    public void addShouldCreateHeadersList() {
        HTTPHeaders headers = new HTTPHeaders();
        headers.add("head", "value");

        assertEquals(1, headers.headers.size());
        assertEquals("head", headers.headers.get(0).getName());
        assertEquals("value", headers.headers.get(0).getValue());
    }

    @Test
    public void getFirstHeaderValue() {
        HTTPHeaders headers = new HTTPHeaders();
        headers.headers = new ArrayList<HTTPHeader>();
        headers.headers.add(new HTTPHeader("head1", "value1"));
        headers.headers.add(new HTTPHeader("head2", "value2"));
        headers.headers.add(new HTTPHeader("head2", "value3"));

        String value = headers.getValue("head2");

        assertEquals("value2", value);
    }
}
