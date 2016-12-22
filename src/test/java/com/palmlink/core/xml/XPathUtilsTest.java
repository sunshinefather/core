package com.palmlink.core.xml;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Shihai.Fu
 */
public class XPathUtilsTest {
    @Test
    public void getTextOfAttribute() {
        Document xml = new XMLParser().parse("<xml><node attr=\"value\"/></xml>");
        String text = XPathUtils.selectText(xml, "/xml/node/@attr");

        assertEquals("value", text);
    }

    @Test
    public void getTextOfElement() {
        Document xml = new XMLParser().parse("<xml><node>value</node></xml>");
        String text = XPathUtils.selectText(xml, "/xml/node");

        assertEquals("value", text);
    }

    @Test
    public void getTextOfEmptyElement() {
        Document xml = new XMLParser().parse("<xml><node/></xml>");
        String text = XPathUtils.selectText(xml, "/xml/node");

        assertEquals("", text);
    }

    @Test
    public void getTextOfText() {
        Document xml = new XMLParser().parse("<xml><node>value</node></xml>");
        String text = XPathUtils.selectText(xml, "/xml/node/text()");

        assertEquals("value", text);
    }

    @Test(expected = XMLException.class)
    public void getTextShouldThrowExceptionIfElementIsNotTextElement() {
        Document xml = new XMLParser().parse("<xml><node><child/></node></xml>");
        XPathUtils.selectText(xml, "/xml/node");
    }

    @Test
    public void selectElements() {
        Document xml = new XMLParser().parse("<xml><node>value1</node><node>value2</node></xml>");
        List<Element> elements = XPathUtils.selectElements(xml, "/xml/node");

        assertEquals(2, elements.size());
        assertEquals("node", elements.get(0).getTagName());
        assertEquals("value2", elements.get(1).getTextContent());
    }

    @Test
    public void selectElementShouldReturnNullIfNotExisted() {
        Document xml = new XMLParser().parse("<xml/>");
        Element element = XPathUtils.selectElement(xml, "none");

        assertNull(element);
    }

    @Test
    public void countNumberOfNode() {
        Document xml = new XMLParser().parse("<xml><child/><child/><child/></xml>");
        int count = XPathUtils.selectInt(xml, "count(/xml/child)");

        assertEquals(3, count);

        xml = new XMLParser().parse("<xml></xml>");
        count = XPathUtils.selectInt(xml, "count(/xml/child)");

        assertEquals(0, count);
    }

    @Test
    public void selectElementByRelativeXPath() {
        Document xml = new XMLParser().parse("<xml><context><child></child></context></xml>");
        Element context = XPathUtils.selectElement(xml, "/xml/context");

        Assert.assertNotNull(context);

        Element child = XPathUtils.selectElement(context, "child");

        Assert.assertNotNull(child);
    }
}
