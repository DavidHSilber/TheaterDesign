package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Created by dhs on 10/26/14.
 */
public class CheeseboroughTest {

    Element venueElement;
    Element elementOnTruss = null;
    final String type = "swivel";
    final String trussId = "cheeseboroughTestTruss";
    Integer hangPoint1X=50;
    Integer hangPointY=40;
    Integer hangPoint2X=110;
    Integer trussSize=12;
    Integer trussLength=120;
    String trussLocation = "c 45";

    @Test
    public void isA() throws Exception {
        Cheeseborough cheeseborough = new Cheeseborough(elementOnTruss);

        assert MinderDom.class.isInstance( cheeseborough );
    }

    @Test
    public void storesAttributes() throws Exception {
        Cheeseborough cheeseborough = new Cheeseborough(elementOnTruss);

        assertEquals( TestHelpers.accessString( cheeseborough, "on" ), trussId );
        assertEquals( TestHelpers.accessString( cheeseborough, "location" ), trussLocation );
//        assertEquals( TestHelpers.accessString( cheeseborough, "type" ), "" );
    }

//    @Test
//    public void storesOptionalAttributes() throws Exception {
//        elementOnTruss.setAttribute( "type", type );
//        Cheeseborough cheeseborough = new Cheeseborough(elementOnTruss);
//
//        assertEquals( TestHelpers.accessString( cheeseborough, "on" ), trussId );
//        assertEquals( TestHelpers.accessString( cheeseborough, "location" ), trussLocation );
//        assertEquals( TestHelpers.accessString( cheeseborough, "type" ), type );
//    }

//    @Test
//    public void registersLayer() throws Exception {
//        Cheeseborough cheeseborough = new Cheeseborough(elementOnTruss);
//
//        HashMap<String, Layer> layers = Layer.List();
//
//        assertTrue( layers.containsKey( Cheeseborough.LAYERTAG ) );
//        assertEquals( layers.get( Cheeseborough.LAYERTAG ).name(), Cheeseborough.LAYERNAME );
//    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFineTruss() throws Exception {
        new Cheeseborough(elementOnTruss);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Cheeseborough instance is missing required 'on' attribute.")
    public void noOn() throws Exception {
        elementOnTruss.removeAttribute("on");
        new Cheeseborough(elementOnTruss);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Cheeseborough instance is missing required 'location' attribute.")
    public void noLocation() throws Exception {
        elementOnTruss.removeAttribute("location");
        new Cheeseborough(elementOnTruss);
    }

//    @Test(expectedExceptions = AttributeMissingException.class,
//            expectedExceptionsMessageRegExp = "Cheeseborough instance is missing required 'type' attribute.")
//    public void noType() throws Exception {
//        elementOnTruss.removeAttribute("type");
//        new Cheeseborough(elementOnTruss);
//    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp = "Cheeseborough has unknown mounting: 'bloorglew'.")
    public void badOn() throws Exception {
        elementOnTruss.setAttribute("on", "bloorglew");
        Cheeseborough cheeseborough = new Cheeseborough(elementOnTruss);
        cheeseborough.verify();
    }

//    // TODO: commented out 2014-04-22 as it was hanging the whole test run.
//    @Test(expectedExceptions = MountingException.class,
//            expectedExceptionsMessageRegExp = "Cheeseborough of type 'floob' has unknown mounting: 'bloorglew'.")
//    public void badLocationOtherType() throws Exception {
//        elementOnTruss.setAttribute("type", "floob");
//        elementOnTruss.setAttribute("on", "bloorglew");
////        new Cheeseborough( elementOnTruss );
//        Cheeseborough cheeseborough = new Cheeseborough(elementOnTruss);
////        cheeseborough.location();
//    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp = "Cheeseborough has location '1'" +
                    " which caused: Truss \\(" + trussId + "\\) location does not include a valid vertex.")
    public void locationMissingVertex() throws Exception {
        elementOnTruss.setAttribute("location", "1");
        Cheeseborough cheeseborough = new Cheeseborough(elementOnTruss);
        cheeseborough.verify();
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp = "Cheeseborough has location 'b'" +
                    " which caused: Truss \\(" + trussId + "\\) location not correctly formatted.")
    public void badLocationMissingPosition() throws Exception {
        elementOnTruss.setAttribute("location", "b");
        Cheeseborough cheeseborough = new Cheeseborough(elementOnTruss);
        cheeseborough.verify();
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp = "Cheeseborough has location 'c -1'" +
                    " which caused: Truss \\(" + trussId + "\\) does not include location -1.")
    public void locateOffTrussNegative() throws Exception {
        elementOnTruss.setAttribute("location", "c -1");
        Cheeseborough cheeseborough = new Cheeseborough(elementOnTruss);
        cheeseborough.verify();
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp = "Cheeseborough has location 'd 121'" +
                    " which caused: Truss \\(" + trussId + "\\) does not include location 121.")
    public void locateOffTrussPositive() throws Exception {
        elementOnTruss.setAttribute("location", "d 121");
        Cheeseborough cheeseborough = new Cheeseborough(elementOnTruss);
        cheeseborough.verify();
    }

    @Test
    public void locate() throws Exception {
        Cheeseborough cheeseborough = new Cheeseborough(elementOnTruss);
        cheeseborough.verify();

        Point point = cheeseborough.place.location();
        Point origin = cheeseborough.place.origin();

        assertEquals( cheeseborough.place.rotation(), -0.0 );
        assertEquals( point.y(), 34.0 );
        assertEquals( point.x(), 65.0 );
        assertEquals( point.z(), 226.0 );

        assertEquals( origin.x(), 65.0 );
        assertEquals( origin.y(), 34.0 );
        assertEquals( origin.z(), 226.0 );
    }

    @Test
    public void verifyOKTruss() throws Exception {
        Cheeseborough cheeseborough = new Cheeseborough(elementOnTruss);

        cheeseborough.verify();
    }

//    @Test
//    public void verifyRecordsLocation() throws Exception {
//        Cheeseborough cheeseborough = new Cheeseborough(elementOnTruss);
//
//        cheeseborough.verify();
//
//        assertNotNull(TestHelpers.accessPoint(cheeseborough, "point"));
//    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.VenueReset();
        TestResets.MinderDomReset();
        TestResets.MountableReset();

        venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        Venue venue = new Venue( venueElement );
        venue.verify();

//        Element trussElement = new IIOMetadataNode( "truss" );
//        trussElement.setAttribute( "id", trussId );
//        trussElement.setAttribute( "length", "120" );
//        trussElement.setAttribute( "x", "12" );
//        trussElement.setAttribute( "y", "34" );
//        trussElement.setAttribute( "z", "56" );
//        Truss truss = new Truss( trussElement );
//        truss.verify();

        Element hangPoint1 = new IIOMetadataNode( "hangpoint" );
        hangPoint1.setAttribute( "id", "jim" );
        hangPoint1.setAttribute("x", hangPoint1X.toString());
        hangPoint1.setAttribute("y", hangPointY.toString());
        new HangPoint( hangPoint1 );

        Element hangPoint2 = new IIOMetadataNode( "hangpoint" );
        hangPoint2.setAttribute( "id", "joan" );
        hangPoint2.setAttribute("x", hangPoint2X.toString());
        hangPoint2.setAttribute( "y", hangPointY.toString());
        new HangPoint( hangPoint2 );

        Element suspendElement1 = new IIOMetadataNode( "suspend" );
        suspendElement1.setAttribute( "ref", "jim" );
        suspendElement1.setAttribute( "distance", "2" );
        new Suspend( suspendElement1 );

        Element suspendElement2 = new IIOMetadataNode( "suspend" );
        suspendElement2.setAttribute( "ref", "joan" );
        suspendElement2.setAttribute( "distance", "2" );
        new Suspend( suspendElement2 );

        Element trussElement = new IIOMetadataNode("truss");
        trussElement.setAttribute("id", trussId );
        trussElement.setAttribute("size", trussSize.toString());
        trussElement.setAttribute("length", trussLength.toString());
        trussElement.appendChild( suspendElement1 );
        trussElement.appendChild( suspendElement2 );
        Truss truss = new Truss( trussElement );
        truss.verify();

        elementOnTruss = new IIOMetadataNode( "cheeseborough" );
        elementOnTruss.setAttribute( "type", type );
        elementOnTruss.setAttribute("on", trussId);
        elementOnTruss.setAttribute("location", trussLocation );

        elementOnTruss = new IIOMetadataNode( "cheeseborough" );
        elementOnTruss.setAttribute( "type", type );
        elementOnTruss.setAttribute("on", trussId);
        elementOnTruss.setAttribute("location", trussLocation );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}