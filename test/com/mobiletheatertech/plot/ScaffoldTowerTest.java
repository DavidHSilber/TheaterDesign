package com.mobiletheatertech.plot;

import mockit.Mocked;
import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/28/13 Time: 5:32 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * Test {@code ScaffoldTower}.
 *
 * @author dhs
 * @since 0.0.3
 */
public class ScaffoldTowerTest {

    Element element = null;
    Element elementP = null;

    public ScaffoldTowerTest() {
    }

    @Test
    public void isMinderDom() throws Exception {
        Table table = new Table(element);

        assert MinderDom.class.isInstance(table);
    }

    @Test
    public void storesAttributes() throws Exception {
        Table table = new Table(element);

        assertEquals(TestHelpers.accessDouble(table, "width"), 288.0);
        assertEquals(TestHelpers.accessDouble(table, "depth"), 144.0);
        assertEquals(TestHelpers.accessDouble(table, "x"), 56.0);
        assertEquals(TestHelpers.accessDouble(table, "y"), 16.0);
        assertEquals(TestHelpers.accessDouble(table, "z"), 16.0);
        assertEquals(TestHelpers.accessDouble(table, "height"), 12.0);
    }

    // Until such time as I properly implement this class' use of id.
    @Test
    public void idUnused() throws Exception {
        Table table = new Table(element);

        assertEquals(TestHelpers.accessString(table, "id"), "");
    }

    @Test
    public void storesSelf() throws Exception {
        Table table = new Table(element);

        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains(table);
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new Table(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Table instance is missing required 'width' attribute.")
    public void noWidth() throws Exception {
        element.removeAttribute("width");
        new Table(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Table instance is missing required 'depth' attribute.")
    public void noDepth() throws Exception {
        element.removeAttribute("depth");
        new Table(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Table instance is missing required 'x' attribute.")
    public void noX() throws Exception {
        element.removeAttribute("x");
        new Table(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Table instance is missing required 'y' attribute.")
    public void noY() throws Exception {
        element.removeAttribute("y");
        new Table(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Table instance is missing required 'z' attribute.")
    public void noZ() throws Exception {
        element.removeAttribute("z");
        new Table(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Table instance is missing required 'height' attribute.")
    public void noHeight() throws Exception {
        element.removeAttribute("height");
        new Table(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Table should not extend beyond the boundaries of the venue.")
    public void tooLargeWidth() throws Exception {
        element.setAttribute("width", "495");
        new Table(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Table should not extend beyond the boundaries of the venue.")
    public void tooLargeDepth() throws Exception {
        element.setAttribute("depth", "401");
        new Table(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Table should not extend beyond the boundaries of the venue.")
    public void tooLargeX() throws Exception {
        element.setAttribute("x", "263");
        new Table(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Table should not extend beyond the boundaries of the venue.")
    public void tooLargeY() throws Exception {
        element.setAttribute("y", "401");
        new Table(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Table should not extend beyond the boundaries of the venue.")
    public void tooLargeZ() throws Exception {
        element.setAttribute("z", "229");
        new Table(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Table should not extend beyond the boundaries of the venue.")
    public void tooLargeHeight() throws Exception {
        element.setAttribute("height", "225");
        new Table(element);
    }

    @Test(expectedExceptions = SizeException.class,
            expectedExceptionsMessageRegExp =
                    "Table should have a positive width.")
    public void tooSmallWidth() throws Exception {
        element.setAttribute("width", "-1");
        new Table(element);
    }

    @Test(expectedExceptions = SizeException.class,
            expectedExceptionsMessageRegExp =
                    "Table should have a positive depth.")
    public void tooSmallDepth() throws Exception {
        element.setAttribute("depth", "-1");
        new Table(element);
    }

    @Test(expectedExceptions = SizeException.class,
            expectedExceptionsMessageRegExp =
                    "Table should have a positive height.")
    public void tooSmallHeight() throws Exception {
        element.setAttribute("height", "-1");
        new Table(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Table should not extend beyond the boundaries of the venue.")
    public void tooSmallX() throws Exception {
        element.setAttribute("x", "-1");
        new Table(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Table should not extend beyond the boundaries of the venue.")
    public void tooSmallY() throws Exception {
        element.setAttribute("y", "-1");
        new Table(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Table should not extend beyond the boundaries of the venue.")
    public void tooSmallZ() throws Exception {
        element.setAttribute("z", "-1");
        new Table(element);
    }

    @Test
    public void parse() throws Exception {
        String xml = "<plot>" +
                "<table width=\"72\" depth=\"30\" x=\"3\" y=\"6\" z=\"0\" height=\"36\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());

        TestResets.MinderDomReset();

        // TODO Takes too long
//        new Parse(stream);

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals(list.size(), 1);
    }

    @Test
    public void parseMultiple() throws Exception {
        String xml = "<plot>" +
                "<table width=\"72\" depth=\"30\" x=\"3\" y=\"6\" z=\"0\" height=\"36\" />" +
                "<table width=\"72\" depth=\"30\" x=\"3\" y=\"6\" z=\"0\" height=\"36\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());

        TestResets.MinderDomReset();

        // TODO Takes too long
//        new Parse(stream);

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals(list.size(), 2);
    }

    @Mocked
    Graphics2D mockCanvas;

//    @Test
//    public void draw() throws Exception {
//        Table table = new Table( baseElement );
//
//        new Expectations() {
//            {
//                mockCanvas.setPaint( Color.orange );
//                mockCanvas.draw( new Rectangle( 56, 16, 288, 144 ) );
//            }
//        };
//        table.drawPlan( mockCanvas );
//    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();

        draw.establishRoot();
        Table table = new Table(element);

        NodeList existingGroups = draw.root().getElementsByTagName("rect");
        assertEquals(existingGroups.getLength(), 0);

        table.dom(draw, View.PLAN);


//        NodeList createdGroups = draw.root().getElementsByTagName( "g" );
//        assertEquals( createdGroups.getLength(), 2 );

        NodeList rectangles = draw.root().getElementsByTagName("rect");
        assertEquals(rectangles.getLength(), 1);
        Node groupNode = rectangles.item(0);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element tableElement = (Element) groupNode;
        assertEquals(tableElement.getAttribute("class"), Table.LAYERTAG);
        assertEquals(tableElement.getAttribute("x"), "56");
        assertEquals(tableElement.getAttribute("y"), "16");
        assertEquals(tableElement.getAttribute("width"), "288");
        assertEquals(tableElement.getAttribute("height"), "144");
        assertEquals(tableElement.getAttribute("fill"), "none");
        assertEquals(tableElement.getAttribute("stroke"), "brown");

//        int CHAIRCOUNT = (WIDTH / CHAIRWIDTH) * (DEPTH / (CHAIRDEPTH + FOOTSPACE));
//        String expectedX = Integer.toString( X + CHAIRWIDTH / 2 + 2 );
//        String expectedY = Integer.toString( Y + CHAIRDEPTH / 2 + FOOTSPACE );
//        assert (CHAIRCOUNT > 0);
//
//        NodeList list = groupElement.getElementsByTagName( "use" );
//        assertEquals( list.getLength(), CHAIRCOUNT );
//
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element baseElement = (Element) node;
//        assertEquals( baseElement.attribute( "xlink:href" ), "#chair" );
//        assertEquals( baseElement.attribute( "x" ), expectedX );
//        assertEquals( baseElement.attribute( "y" ), expectedY );
    }

    @Test
    public void domSectionUndrawn() throws Exception {
        Draw draw = new Draw();

        draw.establishRoot();
        Table table = new Table(element);

        NodeList existingGroups = draw.root().getElementsByTagName("rect");
        assertEquals(existingGroups.getLength(), 0);

        table.dom(draw, View.SECTION);

        NodeList rectangles = draw.root().getElementsByTagName("rect");
        assertEquals(rectangles.getLength(), 0);
    }

    @Test
    public void domFrontUndrawn() throws Exception {
        Draw draw = new Draw();

        draw.establishRoot();
        Table table = new Table(element);

        NodeList existingGroups = draw.root().getElementsByTagName("rect");
        assertEquals(existingGroups.getLength(), 0);

        table.dom(draw, View.FRONT);

        NodeList rectangles = draw.root().getElementsByTagName("rect");
        assertEquals(rectangles.getLength(), 0);
    }

//    @Test
//    public void tablesCreatesMultiple() {
////        fail( "Table does not yet support multiples" );
//        throw new SkipException( "Table does not yet support multiples" );
//    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute("room", "Test Name");
        venueElement.setAttribute("width", "550");
        venueElement.setAttribute("depth", "400");
        venueElement.setAttribute("height", "240");
        new Venue(venueElement);

        element = new IIOMetadataNode("table");
        element.setAttribute("width", "288");
        element.setAttribute("depth", "144");
        element.setAttribute("x", "56");
        element.setAttribute("y", "16");
        element.setAttribute("z", "16");
        element.setAttribute("height", "12");

//        elementP = new IIOMetadataNode( "table" );
//        elementP.setAttribute( "proscenium-width", "330" );
//        elementP.setAttribute( "proscenium-height", "250" );
//        elementP.setAttribute( "proscenium-depth", "22" );
//        elementP.setAttribute( "apron-depth", "56" );
//        elementP.setAttribute( "apron-width", "440" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}