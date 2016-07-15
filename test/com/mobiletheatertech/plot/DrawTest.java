package com.mobiletheatertech.plot;

import org.apache.batik.svggen.SVGGraphics2D;
import org.testng.annotations.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static org.testng.Assert.*;

/**
 * @author dhs
 * @since 0.0.1
 */
public class DrawTest {

    public DrawTest() {
    }

//    @Mocked
//    SVGGraphics2D mockGraphics;

    @Test
    public void draw() {
//        new Expectations() {
//            {
//                new SVGGraphics2D( (Document) any );
//            }
//        };
        new Draw();
    }

    @Test
    public void storesNamespace() throws Exception {
        Draw draw = new Draw();

        String namespace = TestHelpers.accessString( draw, "namespace" );
        assertEquals( namespace, "http://www.w3.org/2000/svg" );
    }

    @Test
    public void storesDocument() throws Exception {
        Draw draw = new Draw();

        Object doc = TestHelpers.accessObject(draw, "document");
        assert Document.class.isInstance( doc );
    }

    @Test
    public void storesSvgGenerator() throws Exception {
        Draw draw = new Draw();

        Object generator = TestHelpers.accessObject( draw, "svgGenerator" );
        assert SVGGraphics2D.class.isInstance( generator );
    }

    @Test
    public void canvas() throws Exception {
        Draw draw = new Draw();
        Object generator = TestHelpers.accessObject( draw, "svgGenerator" );
        assertSame( generator, draw.canvas() );
    }

    @Test
    public void document() throws Exception {
        Draw draw = new Draw();

        Object doc = TestHelpers.accessObject( draw, "document" );
        assertSame( draw.document(), doc );
    }

    @Test
    public void storesRootBeforeGetRoot() throws Exception {
        Draw draw = new Draw();

        Object root = TestHelpers.accessObject( draw, "root" );
        assertNull(root);
    }

    @Test
    public void storesRootAfterGetRoot() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();

        Object root = TestHelpers.accessObject( draw, "root" );
        assertNotNull( root );
        assert Element.class.isInstance( root );
    }

    @Test
    public void rootBeforeGetRoot() throws Exception {
        Draw draw = new Draw();

        Object root = TestHelpers.accessObject( draw, "root" );
        assertSame( draw.root(), root );
        assertNull(draw.root());
    }

    @Test
    public void rootAfterGetRoot() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();

        Object root = TestHelpers.accessObject( draw, "root" );
        assertSame( draw.root(), root );
        assertNotNull(draw.root());
    }

    @Test
    public void createsSvgElement() throws Exception {
        Draw draw = new Draw();

        String namespace = TestHelpers.accessString( draw, "namespace" );
        String tag = "defs";
        SvgElement element = draw.element(tag);
        assert SvgElement.class.isInstance( element );
        assertEquals( element.element().getTagName(), tag );
        assertEquals(element.element().getNamespaceURI(), namespace);
        assertEquals(element.element().getOwnerDocument(), draw.document());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void setDocumentTitleBeforeGetRoot() {
        Draw draw = new Draw();
        String title = "Follow The Rabbit";
        draw.setDocumentTitle(title);
    }

    @Test
    public void setDocumentTitleAfterGetRoot() {
        Draw draw = new Draw();
        draw.establishRoot();
        String title = "Follow The Rabbit";
        draw.setDocumentTitle( title );

        NodeList list = draw.root().getElementsByTagName( "title" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals(element.getTextContent(), title);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void setScriptBeforeGetRoot() {
        Draw draw = new Draw();
        String title = "Follow The Rabbit";
        draw.setScript(title);
    }

    @Test
    public void setScriptAfterGetRoot() {
        Draw draw = new Draw();
        draw.establishRoot();
        String script = "Follow The Rabbit";
        draw.setScript( script );

        NodeList list = draw.root().getElementsByTagName( "script" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals(element.getTextContent(), script);
    }

    @Test
    public void scriptBeforeTitle() {
        Draw draw = new Draw();
        draw.establishRoot();
        String script = "script code here";
        draw.setScript( script );
        String title = "Follow The Rabbit";
        draw.setDocumentTitle( title );

        scriptAlwaysBeforeTitle(draw);
    }

    @Test
    public void titleBeforeScript() {
        Draw draw = new Draw();
        draw.establishRoot();
        String title = "Follow The Rabbit";
        draw.setDocumentTitle( title );
        String script = "script code here";
        draw.setScript( script );

        scriptAlwaysBeforeTitle(draw);
    }

    void scriptAlwaysBeforeTitle(Draw draw) {
        NodeList scriptList = draw.root().getElementsByTagName( "script" );
        assertEquals( scriptList.getLength(), 1 );
        Node scriptNode = scriptList.item( 0 );
//        assertEquals( scriptNode.getNodeType(), Node.ELEMENT_NODE );
//        Element scriptElement = (Element) scriptNode;
//        assertEquals(scriptElement.getTextContent(), script);

        NodeList titleList = draw.root().getElementsByTagName( "title" );
        assertEquals( titleList.getLength(), 1 );
        Node titleNode = titleList.item( 0 );
//        assertEquals( titleNode.getNodeType(), Node.ELEMENT_NODE );
//        Element titleElement = (Element) titleNode;
//        assertEquals(titleElement.getTextContent(), title);

        Element root = draw.root();
        Node first = root.getFirstChild();
        assertSame( first, scriptNode );
        Node second = first.getNextSibling();
        assertSame( second, titleNode );
    }

    @Test
    public void svgGroupOverride() throws Exception {
        fail();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void insertRootChildBeforeGetRoot() {
        Draw draw = new Draw();
        draw.insertRootChild( draw.element( "type" ) );
    }

    @Test
    public void insertRootChildAfterGetRoot() {
        Draw draw = new Draw();
        draw.establishRoot();
        SvgElement element = draw.element("type");
        draw.insertRootChild( element );
        Element root = draw.root();
        Node first = root.getFirstChild();
//        assertSame( first, element );
        Node second = first.getNextSibling();
        assertSame( second, element );
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void appendRootChildBeforeGetRoot() {
        Draw draw = new Draw();
        draw.appendRootChild( draw.element( "type" ) );
    }

    @Test
    public void appendRootChildAfterGetRoot() {
        Draw draw = new Draw();
        draw.establishRoot();
        SvgElement element = draw.element("type");
        draw.appendRootChild( element );
        Element root = draw.root();
        Node last = root.getLastChild();
        assertSame( last, element );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}