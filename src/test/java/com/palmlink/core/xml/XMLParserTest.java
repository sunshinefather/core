package com.palmlink.core.xml;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * @author Shihai.Fu
 */
public class XMLParserTest {
    @Test
    public void parseXMLContent() {
        Document document = new XMLParser().parse("<xml>value</xml>");

        Element root = document.getDocumentElement();
        assertEquals("xml", root.getTagName());
        assertEquals(1, root.getChildNodes().getLength());
        assertEquals("value", root.getChildNodes().item(0).getTextContent());
    }

    @Test
    public void parseXMLWithNamespaceAware() {
        String xml = "<parent xmlns=\"http://www.ehealth.com/\""
                + " xmlns:app=\"http://www.ehealth.com/app\">"
                + "<app:child>value</app:child>"
                + "</parent>";

        Document document = new XMLParser()
                .setNamespaceAware(true)
                .parse(xml);

        String value = XPathUtils.selectText(document, "/:parent/app:child");
        Assert.assertEquals("value", value);
    }

    @Test
    public void parseXMLWithoutNamespaceAware() {
        String xml = "<parent xmlns=\"http://www.ehealth.com/\""
                + " xmlns:app=\"http://www.ehealth.com/app\">"
                + "<app:child>value</app:child>"
                + "</parent>";

        Document document = new XMLParser()
                .parse(xml);

        String value = XPathUtils.selectText(document, "/parent/child");
        Assert.assertEquals("value", value);
    }

    @Test(timeout = 5000)
    public void parseShouldNotFetchDTDAndValidate() {
        new XMLParser().parse("<?xml version=\"1.0\"?><!DOCTYPE xml SYSTEM \"http://tempuri.org/not-exsit-dtd.dtd\"><xml/>");
    }

    @Test
    public void xmlDeclarationWithEncodingUTF16() throws UnsupportedEncodingException {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-16\"?><envelope></envelope>";

        new XMLParser().parse(xml.getBytes("UTF-16"));
    }
}
