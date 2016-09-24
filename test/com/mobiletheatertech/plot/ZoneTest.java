package com.mobiletheatertech.plot;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 10/6/13 Time: 8:53 PM To change this template use
 * File | Settings | File Templates.
 */

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.*;

/**
 * Test {@code Zone}.
 *
 * @author dhs
 * @since 0.0.15
 */
public class ZoneTest {

    Element element = null;
    Element prosceniumElement;

    final String id = "zone name";
    Double x = 12.0;
    Double y = 34.0;
    //    Integer z = 56;
    Double r = 78.0;
    Double xDrawn = 187.0;
    Double yDrawn = 116.0;
    //    Integer zDrawn = 56;
    Double rDrawn = r;
    String color = "magenta";
    String defaultColor = "teal";

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.MinderDomReset();
        Proscenium.Reset();
        TestResets.LayerReset();

        Element venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        prosceniumElement = new IIOMetadataNode( "proscenium" );
        prosceniumElement.setAttribute( "x", "175" );
        prosceniumElement.setAttribute( "y", "150" );
        prosceniumElement.setAttribute( "z", "12" );
        prosceniumElement.setAttribute( "width", "200" );
        prosceniumElement.setAttribute( "depth", "23" );
        prosceniumElement.setAttribute( "height", "144" );

        element = new IIOMetadataNode( "zone" );
        element.setAttribute( "id", id );
        element.setAttribute( "x", x.toString() );
        element.setAttribute( "y", y.toString() );
//        baseElement.setAttribute( "z", z.toString() );
        element.setAttribute( "r", r.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void isMinderDom() throws Exception {
        Zone zone = new Zone( element );

        assert MinderDom.class.isInstance( zone );
    }

    @Test
    public void storesAttributes() throws Exception {
        Zone zone = new Zone( element );

        assertEquals( TestHelpers.accessString( zone, "id" ), id );
        assertEquals( TestHelpers.accessDouble(zone, "x"), x );
        assertEquals( TestHelpers.accessDouble(zone, "y"), y );
//        assertEquals( TestHelpers.accessInteger( zone, "z" ), z );
        assertEquals( TestHelpers.accessDouble(zone, "r"), r );
        assertEquals( TestHelpers.accessString( zone, "color" ), defaultColor );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute( "color", color );

        Zone zone = new Zone( element );

        assertEquals( TestHelpers.accessString( zone, "id" ), id );
        assertEquals( TestHelpers.accessDouble(zone, "x"), x );
        assertEquals( TestHelpers.accessDouble(zone, "y"), y );
//        assertEquals( TestHelpers.accessInteger( zone, "z" ), z );
        assertEquals( TestHelpers.accessDouble(zone, "r"), r );
        assertEquals( TestHelpers.accessString( zone, "color" ), color );
    }

    @Test
    public void registersLayer() throws Exception {
        new Zone( element );

        HashMap<String, Layer> layers = Layer.List();

        assertTrue( layers.containsKey( Zone.LAYERTAG ) );
        assertEquals( layers.get( Zone.LAYERTAG ).name(), Zone.LAYERNAME );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Zone instance is missing required 'id' attribute.")
    public void noId() throws Exception {
        element.removeAttribute( "id" );
        new Zone( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Zone \\(" + id +
                  "\\) is missing required 'x' attribute.")
    public void noX() throws Exception {
        element.removeAttribute( "x" );
        new Zone( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Zone \\(" + id +
                  "\\) is missing required 'y' attribute.")
    public void noY() throws Exception {
        element.removeAttribute( "y" );
        new Zone( element );
    }

//    @Test( expectedExceptions = AttributeMissingException.class,
//           expectedExceptionsMessageRegExp = "Zone \\("+id+"\\) is missing required 'z' attribute." )
//    public void noZ() throws Exception {
//        baseElement.removeAttribute( "z" );
//        new Zone( baseElement );
//    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Zone \\(" + id +
                  "\\) is missing required 'r' attribute.")
    public void noR() throws Exception {
        element.removeAttribute( "r" );
        new Zone( element );
    }

    @Test
    public void storesSelf() throws Exception {
        Zone zone = new Zone( element );

        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains( zone );
    }

    @Test
    public void drawX() throws Exception {
        new Proscenium( prosceniumElement );
        Zone zone = new Zone( element );
        zone.verify();

        assertEquals( zone.drawX(), xDrawn );
    }

    @Test
    public void drawY() throws Exception {
        new Proscenium( prosceniumElement );
        Zone zone = new Zone( element );
        zone.verify();

        assertEquals( zone.drawY(), yDrawn );
    }

    @Test
    public void finds() throws Exception {
        Zone zone = new Zone( element );

        Zone found = Zone.Find( id );

        assertSame( found, zone );
    }

    @Test
    public void findsNothing() throws Exception {
        Zone found = Zone.Find( "Nothing" );

        assertNull( found );
    }

    @Test
    public void findIgnoresOther() throws Exception {
        Element pipeElement = new IIOMetadataNode( "pipe" );
        pipeElement.setAttribute( "length", "120" );
        pipeElement.setAttribute( "id", "pipe id" );
        pipeElement.setAttribute( "x", "2" );
        pipeElement.setAttribute( "y", "4" );
        pipeElement.setAttribute( "z", "6" );
        new Pipe( pipeElement );

        Zone found = Zone.Find( id );
        assertNull( found );

        Zone zone = new Zone( element );

        found = Zone.Find( id );
        assertSame( found, zone );
    }

    @Test
    public void findIgnoresOtherZone() throws Exception {
        Element otherZoneElement = new IIOMetadataNode( "zone" );
        otherZoneElement.setAttribute( "id", "Not Our Victim" );
        otherZoneElement.setAttribute( "x", "2" );
        otherZoneElement.setAttribute( "y", "4" );
        otherZoneElement.setAttribute( "r", "3" );
        new Zone( otherZoneElement );

        Zone found = Zone.Find( id );
        assertNull( found );

        Zone zone = new Zone( element );

        found = Zone.Find( id );
        assertSame( found, zone );
    }

    @Test
    public void verifyWithProscenium() throws Exception {
        new Proscenium( prosceniumElement );
        Zone zone = new Zone( element );

        assertEquals( TestHelpers.accessString( zone, "id" ), id );
        assertEquals( TestHelpers.accessDouble(zone, "x"), x );
        assertEquals( TestHelpers.accessDouble(zone, "y"), y );
//        assertEquals( TestHelpers.accessInteger( zone, "z" ), z );
        assertEquals( TestHelpers.accessDouble(zone, "r"), r );

        zone.verify();

        assertEquals( TestHelpers.accessString( zone, "id" ), id );
        assertEquals( TestHelpers.accessDouble(zone, "xDraw"), xDrawn );
        assertEquals( TestHelpers.accessDouble(zone, "yDraw"), yDrawn );
//        assertEquals( TestHelpers.accessInteger( zone, "z" ), zDrawn );
        assertEquals( TestHelpers.accessDouble(zone, "r"), rDrawn );
    }

    @Test
    public void verifyWithoutProscenium() throws Exception {
        Zone zone = new Zone( element );

        assertEquals( TestHelpers.accessString( zone, "id" ), id );
        assertEquals( TestHelpers.accessDouble(zone, "x"), x );
        assertEquals( TestHelpers.accessDouble(zone, "y"), y );
//        assertEquals( TestHelpers.accessInteger( zone, "z" ), z );
        assertEquals( TestHelpers.accessDouble(zone, "r"), r );

        zone.verify();

        assertEquals( TestHelpers.accessString( zone, "id" ), id );
        assertEquals( TestHelpers.accessDouble(zone, "xDraw"), x );
        assertEquals( TestHelpers.accessDouble(zone, "yDraw"), y );
//        assertEquals( TestHelpers.accessInteger( zone, "z" ), z );
        assertEquals( TestHelpers.accessDouble( zone, "r" ), r );
    }

    @Test
    public void domPlan() throws Exception {
        new Proscenium( prosceniumElement );
        Zone zone = new Zone( element );

        Draw draw = new Draw();
        draw.establishRoot();
        zone.verify();

        NodeList preGroupList = draw.root().getElementsByTagName( "g" );
        assertEquals( preGroupList.getLength(), 1 );
        NodeList preCircleList = draw.root().getElementsByTagName( "circle" );
        assertEquals( preCircleList.getLength(), 0 );
        NodeList preTextList = draw.root().getElementsByTagName( "text" );
        assertEquals( preTextList.getLength(), 0 );

        zone.dom( draw, View.PLAN );

        NodeList group = draw.root().getElementsByTagName( "g" );
        assertEquals( group.getLength(), 2 );
        Node groupNode = group.item( 1 );
        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
        Element groupElement = (Element) groupNode;
        assertEquals( groupElement.getAttribute( "class" ), Zone.LAYERTAG );

        NodeList circleList = draw.root().getElementsByTagName( "circle" );
        assertEquals( circleList.getLength(), 1 );
        Node node = circleList.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "cx" ), xDrawn.toString() );
        assertEquals( element.getAttribute( "cy" ), yDrawn.toString() );
        assertEquals( element.getAttribute( "r" ), rDrawn.toString() );

        assertEquals( element.getAttribute( "fill" ), "none" );
        assertEquals( element.getAttribute( "stroke" ), defaultColor );
        assertEquals( element.getAttribute( "stroke-opacity" ), "0.5" );
//        assertEquals( element.getAttribute( "stroke-width" ), "1" );


        NodeList textList = draw.root().getElementsByTagName( "text" );
        assertEquals( textList.getLength(), 1 );
        Node textNode = textList.item( 0 );
        assertEquals( textNode.getNodeType(), Node.ELEMENT_NODE );
        Element textElement = (Element) textNode;
        assertEquals( textElement.getAttribute( "fill" ), defaultColor );

    }

    @Test
    public void domPlanColor() throws Exception {
        element.setAttribute( "color", color );
        new Proscenium( prosceniumElement );
        Zone zone = new Zone( element );

        Draw draw = new Draw();
        draw.establishRoot();
        zone.verify();

        NodeList preGroupList = draw.root().getElementsByTagName( "g" );
        assertEquals( preGroupList.getLength(), 1 );
        NodeList preCircleList = draw.root().getElementsByTagName( "circle" );
        assertEquals( preCircleList.getLength(), 0 );
        NodeList preTextList = draw.root().getElementsByTagName( "text" );
        assertEquals( preTextList.getLength(), 0 );

        zone.dom( draw, View.PLAN );

        NodeList group = draw.root().getElementsByTagName( "g" );
        assertEquals( group.getLength(), 2 );
        Node groupNode = group.item( 1 );
        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
        Element groupElement = (Element) groupNode;
        assertEquals( groupElement.getAttribute( "class" ), Zone.LAYERTAG );

        NodeList circleList = groupElement.getElementsByTagName( "circle" );
        assertEquals( circleList.getLength(), 1 );
        Node node = circleList.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "cx" ), xDrawn.toString() );
        assertEquals( element.getAttribute( "cy" ), yDrawn.toString() );
        assertEquals( element.getAttribute( "r" ), rDrawn.toString() );

        assertEquals( element.getAttribute( "fill" ), "none" );
        assertEquals( element.getAttribute( "stroke" ), color );
        assertEquals( element.getAttribute( "stroke-opacity" ), "0.5" );
//        assertEquals( element.getAttribute( "stroke-width" ), "1" );


        NodeList textList = groupElement.getElementsByTagName( "text" );
        assertEquals( textList.getLength(), 1 );
        Node textNode = textList.item( 0 );
        assertEquals( textNode.getNodeType(), Node.ELEMENT_NODE );
        Element textElement = (Element) textNode;
        assertEquals( textElement.getAttribute( "fill" ), color );

    }

    @Test
    public void domSection() throws Exception {
        new Proscenium( prosceniumElement );
        Zone zone = new Zone( element );

        Draw draw = new Draw();
        draw.establishRoot();
        zone.verify();

        NodeList preCircleList = draw.root().getElementsByTagName( "circle" );
        assertEquals( preCircleList.getLength(), 0 );
        NodeList preTextList = draw.root().getElementsByTagName( "text" );
        assertEquals( preTextList.getLength(), 0 );

        zone.dom( draw, View.SECTION );

        NodeList circleList = draw.root().getElementsByTagName( "circle" );
        assertEquals( circleList.getLength(), 0 );
        NodeList textList = draw.root().getElementsByTagName( "text" );
        assertEquals( textList.getLength(), 0 );
    }

    @Test
    public void domFront() throws Exception {
        new Proscenium( prosceniumElement );
        Zone zone = new Zone( element );

        Draw draw = new Draw();
        draw.establishRoot();
        zone.verify();

        NodeList preCircleList = draw.root().getElementsByTagName( "circle" );
        assertEquals( preCircleList.getLength(), 0 );
        NodeList preTextList = draw.root().getElementsByTagName( "text" );
        assertEquals( preTextList.getLength(), 0 );

        zone.dom( draw, View.FRONT );

        NodeList circleList = draw.root().getElementsByTagName( "circle" );
        assertEquals( circleList.getLength(), 0 );
        NodeList textList = draw.root().getElementsByTagName( "text" );
        assertEquals( textList.getLength(), 0 );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

}
