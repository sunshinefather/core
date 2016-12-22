package com.palmlink.core.xml;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static org.junit.Assert.assertEquals;

/**
 * @author Shihai.Fu
 */
public class DOMUtilsGetTextTest {
    @Test
    public void shouldReturnTextContentOfElement() {
        Document document = new XMLParser().parse("<xml><text>value</text></xml>");
        String value = DOMUtils.getText((Element) document.getDocumentElement().getFirstChild());
        assertEquals("value", value);
    }

    @Test
    public void shouldReturnEmptyForEmptyElement() {
        Document document = new XMLParser().parse("<xml><text></text></xml>");
        String value = DOMUtils.getText((Element) document.getDocumentElement().getFirstChild());
        assertEquals("", value);

        document = new XMLParser().parse("<xml><text/></xml>");
        value = DOMUtils.getText((Element) document.getDocumentElement().getFirstChild());
        assertEquals("", value);
    }

    @Test(expected = XMLException.class)
    public void shouldThrowExceptionIfNotTextElement() {
        Document document = new XMLParser().parse("<xml><text><value/></text></xml>");
        DOMUtils.getText((Element) document.getDocumentElement().getFirstChild());
    }
}
