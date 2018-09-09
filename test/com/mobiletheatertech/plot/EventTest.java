package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/13/13 Time: 8:00 PM To change this template use
 * File | Settings | File Templates.
 * <p/>
 * Test {@code Event}.
 *
 * @author dhs
 * @since 0.0.2
 */
public class EventTest {

    Element element = null;
    String name = "Event Name";

    private Element baseForPipeElement = null;
    private Element baseForTrussElement = null;
    private Double baseX = 42.1;
    private Double baseY = 57.9;
    private Double baseSize = 12.5;

    Element flatElement = null;
    Double x1 = 1.1;
    Double y1 = 2.2;
    Double x2 = 3.3;
    Double y2 = 4.4;

    Element setPieceElement = null;
    Double x = 5.5;
    Double y = 6.6;

    Element venueElement = null;
    Element luminaireElement = null;
    Element eventLuminaireElement = null;


    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.ElementalListerReset();
//        TestResets.EventReset();
        UniqueId.Reset();
        Event.Reset();

        baseForPipeElement = new IIOMetadataNode( PipeBase.Tag() );
        baseForPipeElement.setAttribute( "x", baseX.toString() );
        baseForPipeElement.setAttribute( "y", baseY.toString() );

        baseForTrussElement = new IIOMetadataNode( TrussBase.Tag() );
        baseForTrussElement.setAttribute( "x", baseX.toString() );
        baseForTrussElement.setAttribute( "y", baseY.toString() );
        baseForTrussElement.setAttribute( "size", baseSize.toString() );

        setPieceElement = new IIOMetadataNode( SetPiece.Tag() );
        setPieceElement.setAttribute( "x", x.toString() );
        setPieceElement.setAttribute( "y", y.toString() );

        flatElement = new IIOMetadataNode( Flat.Tag() );
        flatElement.setAttribute( "x1", x1.toString() );
        flatElement.setAttribute( "y1", y1.toString() );
        flatElement.setAttribute( "x2", x2.toString() );
        flatElement.setAttribute( "y2", y2.toString() );

        element = new IIOMetadataNode( Event.Tag() );
        element.setAttribute( "id", name );


        venueElement = new IIOMetadataNode( Venue.Tag() );
        venueElement.setAttribute( "room", "venue room" );
        venueElement.setAttribute( "width", "200" );
        venueElement.setAttribute( "depth", "250" );
        venueElement.setAttribute( "height", "100" );
//        Venue venue = new Venue( venueElement );

        luminaireElement = new IIOMetadataNode( Luminaire.LAYERTAG );
        luminaireElement.setAttribute( "on", "luminairePipe" );
        luminaireElement.setAttribute( "type", "luminaireType" );
        luminaireElement.setAttribute( "location", "78" );
        luminaireElement.setAttribute( "unit", "luminaireUnit" );
        luminaireElement.setAttribute( "owner", "luminaireOwner" );
//        eventLuminaireElement = new IIOMetadataNode( Event.)
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    public EventTest() {
    }


    @Test
    public void constantTag() {
        assertEquals( Event.Tag(), "event" );
    }

    @Test
    public void isA() throws Exception {
        Event instance = new Event( element );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );
        assertTrue( UniqueId.class.isInstance( instance ) );
        assertFalse( Yokeable.class.isInstance( instance ) );

        assertFalse( LinearSupportsClamp.class.isInstance( instance ) );
        assertTrue( Populate.class.isInstance( instance ) );
        assertFalse( Legendable.class.isInstance( instance ) );
//        assert Schematicable.class.isInstance( instance );
    }

    @Test
    public void storesAttributes() throws Exception {
        Event event = new Event( element );

        assertEquals( TestHelpers.accessString( event, "id" ), name );
    }

    @Test
    public void idUsed() throws Exception {
        Event event = new Event( element );

        assertNotNull( TestHelpers.accessString( event, "id" ) );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
           expectedExceptionsMessageRegExp = "Event is not defined." )
    public void noEventName() throws Exception {
        Event.Name();
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Event instance is missing required 'id' attribute." )
    public void noName() throws Exception {
        element.removeAttribute( "id" );
        new Event( element );
    }

    @Test
    public void name() throws Exception {
        Element eventElement = new IIOMetadataNode();
        eventElement.setAttribute( "id", "Thingy Name" );

        new Event( eventElement );

        assertEquals( Event.Name(), "Thingy Name" );

        new Event( element );

        assertEquals( Event.Name(), name );
    }

//    @Test( expectedExceptions = InvalidXMLException.class,
//            expectedExceptionsMessageRegExp = "Event requires that the Venue be defined." )
//    public void noVenue() throws Exception {
//        new Event( element );
//    }

    @Test
    public void countPopulateTags() {
        Event event = new Event( element );
        assertEquals( event.populateTags().size(), 5 );
    }

    @Test
    public void tagCallbackRegisteredPipeBase() {
        Event event = new Event( element );

        assertTrue( event.populateTags().contains( PipeBase.Tag() ) );
    }

    @Test
    public void tagCallbackRegisteredTrussBase() {
        Event event = new Event( element );

        assertTrue( event.populateTags().contains( TrussBase.Tag() ) );
    }

    @Test
    public void tagCallbackRegisteredTruss() {
        Event event = new Event( element );

        assertTrue( event.populateTags().contains( Truss.Tag() ) );
    }

    @Test
    public void tagCallbackRegisteredSetPiece() {
        Event event = new Event( element );

        assertTrue( event.populateTags().contains( SetPiece.Tag() ) );
    }

    @Test
    public void tagCallbackRegisteredLuminaire() {
        Event event = new Event( element );

        assertTrue( event.populateTags().contains( Luminaire.LAYERTAG ) );
    }

    @Test
    public void populateChildrenPipeBase() {
        element.appendChild( baseForPipeElement );
        new Event( element );

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister event = list.get( 0 );
        assert MinderDom.class.isInstance( event );
        assert Event.class.isInstance( event );

        ElementalLister pipebase = list.get( 1 );
        assert MinderDom.class.isInstance( pipebase );
        assert PipeBase.class.isInstance( pipebase );

        assertEquals( list.size(), 2 );
    }

    @Test
    public void populateChildrenTrussBase() {
        element.appendChild( baseForTrussElement );
        new Event( element );

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister event = list.get( 0 );
        assert MinderDom.class.isInstance( event );
        assert Event.class.isInstance( event );

        ElementalLister trussbase = list.get( 1 );
        assert MinderDom.class.isInstance( trussbase );
        assert TrussBase.class.isInstance( trussbase );

        assertEquals( list.size(), 2 );
    }

    @Test
    public void populateChildrenSetPiece() {
        element.appendChild( setPieceElement );
        setPieceElement.appendChild( flatElement );
        new Event( element );

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister event = list.get( 0 );
        assert MinderDom.class.isInstance( event );
        assert Event.class.isInstance( event );

        ElementalLister setPiece = list.get( 1 );
//        assert MinderDom.class.isInstance( setPiece );
        assert SetPiece.class.isInstance( setPiece );

        assertEquals( list.size(), 3 );
    }

    @Test
    public void populateChildrenLuminaire() {
        new Venue( venueElement );
        element.appendChild( luminaireElement );
        new Event( element );

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister venue = list.get( 0 );
        assert MinderDom.class.isInstance( venue );
        assert Venue.class.isInstance( venue );

        ElementalLister event = list.get( 1 );
        assert MinderDom.class.isInstance( event );
        assert Event.class.isInstance( event );

        ElementalLister luminaire = list.get( 2 );
        assert MinderDom.class.isInstance( luminaire );
        assert Luminaire.class.isInstance( luminaire );

        ElementalLister luminaireInformation = list.get( 3 );
        assert MinderDom.class.isInstance( luminaireInformation );
//        System.err.println( "Class: " + dunno.getClass().toString() );
        assert LuminaireInformation.class.isInstance( luminaireInformation );

        assertEquals( list.size(), 4 );
    }

    @Test
    public void parseXML() throws Exception {
        String name = "Name of event";
        String xml = "<plot>" +
                "<event id=\"" + name + "\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

//        TestResets.MinderDomReset();

        new Parse( stream );

        assertEquals( Event.Name(), name );
    }

    @Test
    public void domSetsTitlePlan() throws Exception {
        Draw draw = new Draw();
        Event event = new Event( element );
        draw.establishRoot();

        event.dom( draw, View.PLAN );

        String titleActual = TestHelpers.getDomElementText( draw, "title" );
        assertEquals( titleActual, name + " - Plan view" );
    }

    @Test
    public void domSetsTitleSection() throws Exception {
        Draw draw = new Draw();
        Event event = new Event( element );
        draw.establishRoot();

        event.dom( draw, View.SECTION );

        String titleActual = TestHelpers.getDomElementText( draw, "title" );
        assertEquals( titleActual, name );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
}