package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

/**
 * Test {@code Pipe}.
 *
 * @author dhs
 * @since 0.0.6
 */
public class PipeTest {

    Element element = null;
    final String unit = "unit";
    private Element prosceniumElement = null;
    private Element pipeCrossesProsceniumCenterElement = null;

    //    Element baseElement = null;
    private Element trussBaseElement = null;
    private Element trussElement = null;
    private Element cheeseborough1Element = null;
    private Element cheeseborough2Element = null;
    private Element pipeOnCheeseboroughsElement = null;
    private Element baseForPipeElement = null;
    private Element pipeOnBaseElement = null;

    private Double baseSize = 36.0;
    private Double baseX = 40.0;
    private Double baseY = 50.0;
    private String trussID = "trussID";
    private Double trussSize = 12.0;
    private Double trussLength = 120.0;
    private String cheeseborough1Id = "chedder";
    private String cheeseborough2Id = "brie";
    private String cheeseborough1Location = "a 112";
    private String cheeseborough2Location = "b 112";
    final String pipeOnCheeseboroughsId = "pipeId";
    private Double pipeOnCheeseboroughsLength = 100.0;
    
    
    Double x = 12.0;
    Double negativeX = -12.0;
    Double y = 23.0;
    Double z = 34.0;
    Double length = 120.0;   // 10' pipe.

    private Integer prosceniumX = 200;
    private Integer prosceniumY = 144;
    private Integer prosceniumZ = 12;
    private final String pipeId = "balconyId";

    Element luminaireElement = null;
    final String luminaireUnit = "unit";
    final String luminaireType = "Altman 6x9";
    String luminaireLocation = "12";



    @Test
    public void isA() throws Exception {
        Pipe instance = new Pipe(element);

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );
        assert UniqueId.class.isInstance( instance );
        assertFalse( Yokeable.class.isInstance( instance ) );

        assert LinearSupportsClamp.class.isInstance( instance );
        assert Populate.class.isInstance( instance );
//        assert Schematicable.class.isInstance( instance );
    }

    @Test
    public void constantColor() {
        assertEquals( Pipe$.MODULE$.Color(), "black" );
    }

    @Test
    public void constantDiameter() {
        assertEquals( Pipe$.MODULE$.Diameter(), 2.0 );
    }

    @Test
    public void constantLayerName() {
        assertEquals(Pipe$.MODULE$.LayerName(), "Pipes");
    }

    @Test
    public void constantLayerTag() {
        assertEquals(Pipe$.MODULE$.LayerTag(), "pipe");
    }

    @Test
    public void constantCheeseborough() {
        assertEquals(Pipe$.MODULE$.Cheeseborough(), "cheeseborough");
    }

    @Test
    public void storesAttributesWithPosition() throws Exception {
        Pipe pipe = new Pipe(element);

        assertEquals( TestHelpers.accessString( pipe, "id" ), pipeId );
        assertEquals( TestHelpers.accessDouble( pipe, "length" ), length );
        assertEquals( TestHelpers.accessPoint( pipe, "start" ), new Point( x, y, z ) );
        assertEquals( TestHelpers.accessDouble( pipe, "orientationValue" ), 0.0 );
        assertEquals( TestHelpers.accessDouble( pipe, "offsetX" ), 0.0 );
    }

    @Test
    public void storesAttributesWithBase() throws Exception {
        Pipe pipe = new Pipe(pipeOnBaseElement);

        assertEquals( TestHelpers.accessString( pipe, "id" ), pipeId );
        assertEquals( TestHelpers.accessDouble( pipe, "length" ), length );
        assertEquals( TestHelpers.accessPoint( pipe, "start" ), new Point( baseX, baseY, 2.0 ) );
        assertEquals( TestHelpers.accessDouble( pipe, "orientationValue" ), 0.0 );
        assertEquals( TestHelpers.accessDouble( pipe, "offsetX" ), 0.0 );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute("orientation", "90");
        element.setAttribute("offsetx", "-50");

        Pipe pipe = new Pipe(element);

        assertEquals( TestHelpers.accessString( pipe, "id" ), pipeId );
        assertEquals( TestHelpers.accessDouble( pipe, "length" ), length );
        assertEquals( TestHelpers.accessPoint( pipe, "start" ), new Point( x, y, z ) );
        assertEquals( TestHelpers.accessDouble( pipe, "orientationValue" ), 90.0 );
        assertEquals( TestHelpers.accessDouble( pipe, "offsetX" ), -50.0 );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeId + "\\) orientation may only be set to 90.")
    public void attributeOrientationNot90() throws Exception {
        element.setAttribute("orientation", "45");

        new Pipe(element);
    }

    // Tested in YokeableTest
//    @Test
//    public void stores() throws Exception {
//        ArrayList<Yokeable> list1 = (ArrayList<Yokeable>)
//                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Yokeable", "MOUNTABLELIST");
//        assertEquals(list1.size(), 0);
//
//        Pipe pipe = new Pipe(element);
//
//        ArrayList<Yokeable> list2 = (ArrayList<Yokeable>)
//                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Yokeable", "MOUNTABLELIST");
//        assert list2.contains(pipe);
//    }

    @Test
    public void stores() throws Exception {
        LinearSupportsClamp nothing = LinearSupportsClamp$.MODULE$.Select( pipeId );
        assertNull( nothing );

        element.setAttribute("length", "0");

        Pipe pipe = null;
        try {
            pipe = new Pipe(element);
        } catch (Exception e) {
        }

        LinearSupportsClamp thingy = LinearSupportsClamp$.MODULE$.Select( pipeId );
        assertSame( thingy, pipe );
    }

    @Test
    public void storesOnlyWhenGood() throws Exception {
        LinearSupportsClamp nothing = LinearSupportsClamp$.MODULE$.Select( pipeId );
        assertNull( nothing );

        element.setAttribute("length", "0");

        try {
            new Pipe(element);
        } catch (Exception e) {
        }

        LinearSupportsClamp notFound = LinearSupportsClamp$.MODULE$.Select( pipeId );
        assertNull( notFound );
    }

    @Test
    public void unstoresWhenBad() throws Exception {
        LinearSupportsClamp nothing = LinearSupportsClamp$.MODULE$.Select( pipeId );
        assertNull( nothing );

        element.setAttribute("length", "339");

        try {
            new Pipe(element);
        } catch (Exception e) {
        }

        LinearSupportsClamp notFound = LinearSupportsClamp$.MODULE$.Select( pipeId );
        assertNull( notFound );
    }

    @Test
    public void unstoresWhenBadWithProscenium() throws Exception {
        LinearSupportsClamp nothing = LinearSupportsClamp$.MODULE$.Select( pipeId );
        assertNull( nothing );

        new Proscenium(prosceniumElement);
        element.setAttribute("length", "339");

        try {
            new Pipe(element);
        } catch (Exception e) {
        }

        LinearSupportsClamp notFound = LinearSupportsClamp$.MODULE$.Select( pipeId );
        assertNull( notFound );
    }

    @Test
    public void registersLayer() throws Exception {
        Pipe pipe = new Pipe(element);

        HashMap<String, Layer> layers = Layer.List();

        assertTrue(layers.containsKey(Pipe$.MODULE$.LayerTag()));
        assertEquals(layers.get(Pipe$.MODULE$.LayerTag()).name(), Pipe$.MODULE$.LayerName());
    }
//
//    @Test
//    public void select() throws Exception {
//        element.setAttribute("id", "friendly");
//        Pipe pipe = new Pipe(element);
//        assertTrue( ElementalLister.List().contains( pipe ) );
//    }
//
//    @Test
//    public void selectCoexistsWithTruss() throws Exception {
//        Element hangPoint1 = new IIOMetadataNode("hangpoint");
//        hangPoint1.setAttribute("id", "jim");
//        hangPoint1.setAttribute("x", "100");
//        hangPoint1.setAttribute("y", "102");
//        new HangPoint(hangPoint1);
//
//        Element hangPoint2 = new IIOMetadataNode("hangpoint");
//        hangPoint2.setAttribute("id", "joan");
//        hangPoint2.setAttribute("x", "200");
//        hangPoint2.setAttribute("y", "202");
//        new HangPoint(hangPoint2);
//
//        Element suspendElement1 = new IIOMetadataNode("suspend");
//        suspendElement1.setAttribute("ref", "jim");
//        suspendElement1.setAttribute("distance", "1");
//
//        Element suspendElement2 = new IIOMetadataNode("suspend");
//        suspendElement2.setAttribute("ref", "joan");
//        suspendElement2.setAttribute("distance", "2");
//
//        Element trussElement = new IIOMetadataNode("truss");
//        trussElement.setAttribute("id", "fred");
//        trussElement.setAttribute("size", "12");
//        trussElement.setAttribute("length", "120");
//        trussElement.appendChild(suspendElement1);
//        trussElement.appendChild(suspendElement2);
//        Truss truss = new Truss(trussElement);
//
//        element.setAttribute("id", "friendly");
//        Pipe pipe = new Pipe(element);
//        assertSame(Yokeable.Select("friendly"), pipe);
//        assertSame(Yokeable.Select("fred"), truss);
//    }

    @Test
    public void storesSelf() throws Exception {
        Pipe pipe = new Pipe(element);

        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains(pipe);
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new Pipe(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Pipe instance is missing required 'id' attribute.")
    public void noId() throws Exception {
        element.removeAttribute("id");
        new Pipe(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeId + "\\) is missing required 'length' attribute.")
    public void noLength() throws Exception {
        element.removeAttribute("length");
        new Pipe(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeId + "\\) must be on base or explicitly positioned.")
    public void positionedNoX() throws Exception {
        element.removeAttribute("x");
        new Pipe(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeId + "\\) must be on base or explicitly positioned.")
    public void positionedNoY() throws Exception {
        element.removeAttribute("y");
        new Pipe(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeId + "\\) must be on base or explicitly positioned.")
    public void positionedNoZ() throws Exception {
        element.removeAttribute("z");
        new Pipe(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeId + "\\) must be on base or explicitly positioned.")
    public void noLocation() throws Exception {
        element.removeAttribute( "x" );
        element.removeAttribute( "y" );
        element.removeAttribute( "z" );
        new Pipe(element);
    }

    @Test(expectedExceptions = SizeException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\{ origin=Point \\{ x=12.0, y=23.0, z=34.0 \\}, length=0.0 \\} should have a positive length.")
    public void tooSmallLengthZero() throws Exception {
        element.setAttribute("length", "0");
        new Pipe(element);
    }

    @Test(expectedExceptions = SizeException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\{ origin=Point \\{ x=12.0, y=23.0, z=34.0 \\}, length=-1.0 \\} should have a positive length.")
    public void tooSmallLength() throws Exception {
        element.setAttribute("length", "-1");
        new Pipe(element);
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooLargeLength() throws Exception {
        element.setAttribute("length", "339");
        Pipe pipe = new Pipe(element);
//        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooLargeLengthProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        element.setAttribute("length", "339");
        Pipe pipe = new Pipe(element);
//        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooSmallX() throws Exception {
        element.setAttribute("x", "-1");
        Pipe pipe = new Pipe(element);
//        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooLargeX() throws Exception {
        element.setAttribute("x", "351");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooSmallY() throws Exception {
        element.setAttribute("y", "-1");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooLargeY() throws Exception {
        element.setAttribute("y", "401");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooSmallZ() throws Exception {
        element.setAttribute("z", "-1");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooLargeZ() throws Exception {
        element.setAttribute("z", "241");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooSmallXProscenium() throws Exception {
        new Proscenium( prosceniumElement );
        element.setAttribute("x", "-" + (prosceniumX + 1) );
        Pipe pipe = new Pipe(element);
//        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooLargeXProscenium() throws Exception {
        new Proscenium( prosceniumElement );
        element.setAttribute("x", "351");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooSmallYProscenium() throws Exception {
        new Proscenium( prosceniumElement );
        element.setAttribute("y", "-" + (prosceniumY + 1) );
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooLargeYProscenium() throws Exception {
        new Proscenium( prosceniumElement );
        element.setAttribute("y", "401");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooSmallZProscenium() throws Exception {
        new Proscenium( prosceniumElement );
        element.setAttribute("z", "-" + (prosceniumZ + 1) );
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test(expectedExceptions = LocationException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not extend beyond the boundaries of the venue.")
    public void tooLargeZProscenium() throws Exception {
        new Proscenium( prosceniumElement );
        element.setAttribute("z", "241");
        Pipe pipe = new Pipe(element);
        pipe.verify();
    }

    @Test
    public void baseReference() throws Exception {
        Pipe pipe = new Pipe( pipeOnBaseElement );
//        new PipeBase( baseForPipeElement );

//        pipe.verify();

        Field baseField = TestHelpers.accessField( pipe, "base" );
        PipeBase base = (PipeBase) baseField.get( pipe );
        assert PipeBase.class.isInstance( base );

        Field boxOriginField = TestHelpers.accessField( pipe, "boxOrigin" );
        Point boxOrigin = (Point) boxOriginField.get( pipe );
        assertNotNull( boxOrigin );
        assertEquals( boxOrigin.z(), 2.0 );
        assertEquals( boxOrigin.y(), 49.0 );
        assertEquals( boxOrigin.x(), 39.0 );
    }

    @Test
    public void baseReferenceProscenium() throws Exception {
        new Proscenium( prosceniumElement );
        Pipe pipe = new Pipe( pipeOnBaseElement );
        baseForPipeElement.setAttribute( "z", "-12" );
        new PipeBase( baseForPipeElement );

        pipe.verify();

        PipeBase base = pipe.base();
//        Field baseField = TestHelpers.accessField( pipe, "base" );
//        PipeBase base = (PipeBase) baseField.get( pipe );
        assertNotNull( base );
        assert PipeBase.class.isInstance( base );

        Point boxOrigin = pipe.boxOrigin();
//        Field boxOriginField = TestHelpers.accessField( pipe, "boxOrigin" );
//        Point boxOrigin = (Point) boxOriginField.get( pipe );
        assertNotNull( boxOrigin );
        assertEquals( boxOrigin.z(), 14.0 );
        assertEquals( boxOrigin.y(), 93.0 );
        assertEquals( boxOrigin.x(), 239.0 );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) should not have more than one base.")
    public void tooManyBases() throws Exception {
        Element secondBaseElement = new IIOMetadataNode( "pipebase" );
//        secondBaseElement.setAttribute( "size", baseSize.toString() );
        secondBaseElement.setAttribute( "x", baseX.toString() );
        secondBaseElement.setAttribute( "y", baseY.toString() );
        new PipeBase( secondBaseElement );
        pipeOnBaseElement.appendChild( secondBaseElement );

        new PipeBase( baseForPipeElement );
        Pipe pipe = new Pipe( pipeOnBaseElement );

        pipe.verify();
    }

    /*
            Make a couple of suspend objects that are children of this truss
            and confirm that they are properly associated
     */
    @Test
    public void verifyCheeseboroughReferences() throws Exception {
        TrussBase trussBase = new TrussBase( trussBaseElement );
        trussBase.verify();
        Truss truss = new Truss( trussElement );
        truss.verify();
        Cheeseborough c1 = new Cheeseborough( cheeseborough1Element );
        c1.verify();
        Cheeseborough c2 = new Cheeseborough( cheeseborough2Element );
        c2.verify();
        Pipe pipe = new Pipe( pipeOnCheeseboroughsElement );
        pipe.verify();

//        Field cheeseborough1Field = TestHelpers.accessField( pipe, "cheeseborough1" );
//        Cheeseborough cheeseborough1 = (Cheeseborough) cheeseborough1Field.get( pipe );
        assertTrue(Cheeseborough.class.isInstance( pipe.support1() ));

//        Field cheeseborough2Field = TestHelpers.accessField( pipe, "cheeseborough2" );
//        Cheeseborough cheeseborough2 = (Cheeseborough) cheeseborough2Field.get( pipe );
        assertTrue( Cheeseborough.class.isInstance( pipe.support2() ) );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeOnCheeseboroughsId + "\\) should have zero or two cheeseboroughs.")
    public void verifyTooManyCheeseboroughReferences() throws Exception {

        String cheeseborough3Id = "brie";
        String cheeseborough3Location = "b 112";

        Element cheeseborough3Element = new IIOMetadataNode( "cheeseborough" );
        cheeseborough3Element.setAttribute( "id", cheeseborough3Id );
        cheeseborough3Element.setAttribute( "on", trussID );
        cheeseborough3Element.setAttribute( "location", cheeseborough3Location );

        pipeOnCheeseboroughsElement.appendChild( cheeseborough3Element );

        TrussBase trussBase = new TrussBase( trussBaseElement );
        trussBase.verify();
        Truss truss = new Truss( trussElement );
        truss.verify();
        Cheeseborough c1 = new Cheeseborough( cheeseborough1Element );
        c1.verify();
        Cheeseborough c2 = new Cheeseborough( cheeseborough2Element );
        c2.verify();
        Cheeseborough c3 = new Cheeseborough( cheeseborough3Element );
        c3.verify();
        Pipe pipe = new Pipe( pipeOnCheeseboroughsElement );
        pipe.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeOnCheeseboroughsId + "\\) should have zero or two cheeseboroughs.")
    public void verifyOnlyOneCheeseboroughReference() throws Exception {
        pipeOnCheeseboroughsElement.removeChild( cheeseborough2Element );

        TrussBase trussBase = new TrussBase( trussBaseElement );
        trussBase.verify();
        Truss truss = new Truss( trussElement );
        truss.verify();
        Cheeseborough c1 = new Cheeseborough( cheeseborough1Element );
        c1.verify();
        Pipe pipe = new Pipe( pipeOnCheeseboroughsElement );
        pipe.verify();
    }

    @Test
    public void location() throws Exception {
        Pipe pipe = new Pipe(element);

        Point place = pipe.mountableLocation("15");
        assertEquals( place, new Point( 27, 23, 34 ) );
        assert place.equals(new Point(27, 23, 34));
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Pipe \\(" + pipeId + "\\) location must be a number.")
    public void locationNotNumber() throws Exception {
        Pipe pipe = new Pipe(element);

        pipe.mountableLocation("a");
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp = "beyond the end of pipe")
    public void locationOffPipe() throws Exception {
        Pipe pipe = new Pipe(element);

        pipe.mountableLocation("121");
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "beyond the end of pipe")
    public void locationNegativeOffPipe() throws Exception {
        Pipe pipe = new Pipe(element);

        pipe.mountableLocation("-1");
    }

    @Test
    public void locationWithProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        Pipe pipe = new Pipe(element);

        Point place = pipe.mountableLocation("15");
        assertEquals(place.x, 227.0 );
        assertEquals(place.y, 122.0 );
        assertEquals(place.z, 45.0 );
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "beyond the end of pipe")
    public void locationOffPipeWithProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        Pipe pipe = new Pipe(element);

        pipe.mountableLocation("121");
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "beyond the end of pipe")
    public void locationNegativeOffPipeWithProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        Pipe pipe = new Pipe(element);

        pipe.mountableLocation("-1");
    }

    @Test
    public void locationPipeCrossesCenterlineWithProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        element.setAttribute("x", "-12");
        Pipe pipe = new Pipe(element);

        Point place = pipe.mountableLocation("15");
        assertEquals(place.x, 215.0 );
        assertEquals(place.y, 122.0 );
        assertEquals(place.z, 45.0 );
    }

    @Test
    public void locationNegativePipeCrossesCenterlineWithProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        element.setAttribute("x", "-12");
        Pipe pipe = new Pipe(element);

        Point place = pipe.mountableLocation("-5");
        assertEquals(place.x, 195.0 );
        assertEquals(place.y, 122.0 );
        assertEquals(place.z, 45.0 );
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "beyond the end of pipe")
    public void locationOffPipeCrossingCenterlineOfProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        element.setAttribute("x", "-12");
        Pipe pipe = new Pipe(element);

        pipe.mountableLocation("120");
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "beyond the end of pipe")
    public void locationNegativeOffPipeCrossingCenterlineOfProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        Pipe pipe = new Pipe(pipeCrossesProsceniumCenterElement);

        pipe.mountableLocation("-65");
    }

    @Test
    public void locationDistance() throws Exception {
        Pipe pipe = new Pipe(element);

        assertEquals( pipe.locationDistance( "15" ), (Integer) 15 );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) location must be a number.")
    public void locationDistanceNotNumber() throws Exception {
        Pipe pipe = new Pipe(element);

        pipe.locationDistance("15a");
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) location must be in the range of 0.0 to 120.0.")
    public void locationDistanceOffPipePlus() throws Exception {
        Pipe pipe = new Pipe(element);

        pipe.locationDistance( "121" );
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) location must be in the range of 0.0 to 120.0.")
    public void locationDistanceOffPipeMinus() throws Exception {
        Pipe pipe = new Pipe(element);

        pipe.locationDistance( "-1" );
    }

    @Test
    public void locationDistanceProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        Pipe pipe = new Pipe(element);

        assertEquals( pipe.locationDistance( "15" ), (Integer) 15 );
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) location must be in the range of -12.0 to 108.0.")
    public void locationDistanceOffProsceniumPipePlus() throws Exception {
        new Proscenium(prosceniumElement);

        Pipe pipe = new Pipe(pipeCrossesProsceniumCenterElement);

        pipe.locationDistance( "109" );
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) location must be in the range of -12.0 to 108.0.")
    public void locationDistanceOffProsceniumPipeMinus() throws Exception {
        new Proscenium(prosceniumElement);

        Pipe pipe = new Pipe(pipeCrossesProsceniumCenterElement);

        pipe.locationDistance( "-13" );
    }

    @Test
    public void locationDistanceProsceniumOrientation90() throws Exception {
        new Proscenium(prosceniumElement);

        element.setAttribute( "orientation", "90" );
        Pipe pipe = new Pipe(element);

        assertEquals( pipe.locationDistance( "15" ), (Integer) 15 );
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) location must be in the range of 0.0 to 120.0.")
    public void locationDistanceOffProsceniumOrientation90PipePlus() throws Exception {
        new Proscenium(prosceniumElement);

        element.setAttribute( "orientation", "90" );
        Pipe pipe = new Pipe(element);

        pipe.locationDistance( "121" );
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) location must be in the range of 0.0 to 120.0.")
    public void locationDistanceOffProsceniumOrientation90PipeMinus() throws Exception {
        new Proscenium(prosceniumElement);

        element.setAttribute( "orientation", "90" );
        Pipe pipe = new Pipe(element);

        pipe.locationDistance( "-61" );
    }

    @Test
    public void rotatedLocationPositioned() throws Exception {
        Pipe pipe = new Pipe(element);

        Place place = pipe.rotatedLocation( "23" );

        assertNotNull( place );
        assertNotNull( pipe.start() );
        assertNotNull( place.location() );

        Point start = pipe.start();
        assertEquals( place.origin(), start );

        Point location = place.location();
        assertEquals( location.x(), start.x() + 23 );
        assertEquals( location.y(), start.y() );
        assertEquals( location.z(), start.z() );

        assertEquals( place.rotation(), 0.0 );
    }

    @Test
    public void rotatedLocationPositioned90() throws Exception {
        element.setAttribute( "orientation", "90" );
        Pipe pipe = new Pipe( element );

        Place place = pipe.rotatedLocation( "23" );

        assertNotNull( place );
        assertNotNull( pipe.start() );
        assertNotNull( place.location() );

        Point start = pipe.start();
        assertEquals( place.origin(), start );

        Point location = place.location();
        assertEquals( location.y(), start.y() - 23 );
        assertEquals( location.z(), start.z() - 1 );
        assertEquals( location.x(), start.x() - 1 );

        assertEquals( place.rotation(), 90.0 );
    }

    @Test
    public void rotatedLocationProsceniumd90() throws Exception {
        pipeCrossesProsceniumCenterElement.setAttribute( "orientation", "90" );
        new Proscenium( prosceniumElement );
        Pipe pipe = new Pipe( pipeCrossesProsceniumCenterElement );

        Place place = pipe.rotatedLocation( "23" );

        assertNotNull( place );
        assertNotNull( pipe.start() );
        assertNotNull( place.location() );

        Point start = pipe.start();
        assertEquals( place.origin(), start );

        Point location = place.location();
        assertEquals( location.x(), prosceniumX + start.x() - 1 );
        assertEquals( location.y(), prosceniumY + start.y() + 23 );
        assertEquals( location.z(), prosceniumZ + start.z() - 1 );

        assertEquals( place.rotation(), 90.0 );
    }

    @Test
    public void rotatedLocationBase() throws Exception {
        Pipe pipe = new Pipe( pipeOnBaseElement );
        PipeBase base = pipe.base();
        assertNotNull( base );

        Place place = pipe.rotatedLocation( "56");

        assertNotNull( pipe.start() );
        assertNotNull( place );
        assertNotNull( place.location() );

        Point start = pipe.start();
        assertEquals( place.origin(), start );

        Point location = place.location();
        assertEquals( location.x(), base.x() );
        assertEquals( location.y(), base.y() );
        assertEquals( location.z(), base.z() + Pipe.baseOffsetZ() + 56 );

        assertEquals( place.rotation(), 0.0 );
    }

    @Test
    public void rotatedLocationProsceniumBase() throws Exception {
        new Proscenium( prosceniumElement );
        Pipe pipe = new Pipe( pipeOnBaseElement );
        PipeBase base = pipe.base();
        assertNotNull( base );

        Place place = pipe.rotatedLocation( "56");

        assertNotNull( pipe.start() );
        assertNotNull( place );
        assertNotNull( place.location() );

        Point start = pipe.start();
        assertEquals( place.origin(), start );

        Point location = place.location();
        assertEquals( location.x(), prosceniumX + base.x() );
        assertEquals( location.y(), prosceniumY - base.y() );
        assertEquals( location.z(), prosceniumZ + base.z() + Pipe.baseOffsetZ() + 56 );

        assertEquals( place.rotation(), 0.0 );
    }

    @Test
    public void rotatedLocationSRofProsceniumCenterline() throws Exception {
        new Proscenium( prosceniumElement );
        Pipe pipe = new Pipe( pipeCrossesProsceniumCenterElement );

        Place place = pipe.rotatedLocation( "-9");

        assertNotNull( place );
        assertNotNull( pipe.start() );
        assertNotNull( place.location() );

        Point start = pipe.start();
        assertEquals( place.origin(), start );

        Point location = place.location();
        assertEquals( location.x(), prosceniumX - 9.0 );
        assertEquals( location.y(), prosceniumY - start.y() + 1 );
        assertEquals( location.z(), prosceniumZ + start.z() - 1 );

        assertEquals( place.rotation(), 0.0 );
    }

    @Test
    public void minLocation() {
        Pipe pipe = new Pipe( element );

        assertEquals( pipe.minLocation(), 0.0 );
    }

    @Test
    public void maxLocation() {
        Pipe pipe = new Pipe( element );

        assertEquals( pipe.maxLocation(), length );
    }

    @Test
    public void minLocationProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        Pipe pipe = new Pipe( pipeCrossesProsceniumCenterElement );

        assertEquals( pipe.minLocation(), negativeX );
    }

    @Test
    public void maxLocationProscenium() throws Exception {
        new Proscenium(prosceniumElement);

        Pipe pipe = new Pipe( pipeCrossesProsceniumCenterElement );

        assertEquals( pipe.maxLocation(), negativeX + length );
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + pipeId + "\\) unit '" + unit +"' does not include location 122.0.")
    public void hangLocationOutOfRange() throws Exception {
        final String type = "Altman 6x9";

        Element elementOnPipe = new IIOMetadataNode( "luminaire" );
        elementOnPipe.setAttribute("unit", unit);
        elementOnPipe.setAttribute( "type", type );
        elementOnPipe.setAttribute("location", "122" );

        element.appendChild( elementOnPipe );
        new Pipe( element );
    }

//    void callBack( Element element ) {
//    }

    @Test
    public void tagCallbackRegistered() {
        element.appendChild( luminaireElement );
        Pipe pipe = new Pipe( element );

        assertEquals( pipe.tags().size(), 1 );
    }

    @Test
    public void populateChildren() {
        element.appendChild( luminaireElement );
        Pipe pipe = new Pipe( element );

        scala.collection.mutable.ArrayBuffer<IsClamp> list = pipe.IsClampList();
        assertEquals( list.size(), 1 );
    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Pipe pipe = new Pipe(element);
        pipe.verify();

        pipe.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Pipe$.MODULE$.LayerTag());

        NodeList list = groupElement.getElementsByTagName("rect");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("width"), length.toString());

        assertEquals(element.getAttribute("height"), Pipe$.MODULE$.DiameterString() );
        assertEquals(element.getAttribute("fill"), "none");
    }

    @Test
    public void domPlanProscenium() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        new Proscenium(prosceniumElement);
        Pipe pipe = new Pipe(element);
//        pipe.verify();

        pipe.dom(draw, View.PLAN);

        NodeList list = draw.root().getElementsByTagName("rect");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        Double ex = prosceniumX + x;
        Double wy = prosceniumY - (y + 1);
        assertEquals( element.getAttribute("x"), ex.toString() );
        assertEquals( element.getAttribute("y"), wy.toString() );
    }

//    @Test
//    public void domPlanNoProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Pipe pipe = new Pipe(element);
//        pipe.verify();
//
//        pipe.dom(draw, View.PLAN);
//
//        NodeList list = draw.root().getElementsByTagName("rect");
//        assertEquals(list.getLength(), 1);
//        Node node = list.item(0);
//        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
//        Element element = (Element) node;
//        assertEquals(element.getAttribute("x"), x.toString());
//        assertEquals(element.getAttribute("y"), ((Double) (y - 1)).toString());
//    }
//
//    @Test
//    public void domSection() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Pipe pipe = new Pipe(element);
//        pipe.verify();
//
//        pipe.dom(draw, View.SECTION);
//
//        NodeList group = draw.root().getElementsByTagName("g");
//        assertEquals(group.getLength(), 2);
//        Node groupNode = group.item(1);
//        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
//        Element groupElement = (Element) groupNode;
//        assertEquals(groupElement.getAttribute("class"), Pipe$.MODULE$.LayerTag() );
//
//        NodeList list = groupElement.getElementsByTagName("rect");
//        assertEquals(list.getLength(), 1);
//        Node node = list.item(0);
//        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
//        Element element = (Element) node;
//        assertEquals(element.getAttribute("width"), Pipe$.MODULE$.DiameterString() );
//        assertEquals(element.getAttribute("height"), Pipe$.MODULE$.DiameterString() );
//        assertEquals(element.getAttribute("fill"), "none");
//    }
//
//    @Test
//    public void domSectionProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        new Proscenium(prosceniumElement);
//        Pipe pipe = new Pipe(element);
//        pipe.verify();
//
//        pipe.dom(draw, View.SECTION);
//
//        NodeList list = draw.root().getElementsByTagName("rect");
//        assertEquals(list.getLength(), 1);
//        Node node = list.item(0);
//        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
//        Element element = (Element) node;
//        Double wye = prosceniumY - (y - 1);
//        Double zee = Venue.Height() - (prosceniumZ + z - 1);
//        assertEquals(element.getAttribute("x"), wye.toString());
//        assertEquals(element.getAttribute("y"), zee.toString());
//    }
//
//    @Test
//    public void domSectionNoProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Pipe pipe = new Pipe(element);
//        pipe.verify();
//
//        pipe.dom(draw, View.SECTION);
//
//        NodeList list = draw.root().getElementsByTagName("rect");
//        assertEquals(list.getLength(), 1);
//        Node node = list.item(0);
//        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
//        Element element = (Element) node;
//        Double wye = y - 1;
//        Double zee = Venue.Height() - (z - 1);
//        assertEquals(element.getAttribute("x"), wye.toString());
//        assertEquals(element.getAttribute("y"), zee.toString());
//    }
//
//    @Test
//    public void domFront() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Pipe pipe = new Pipe(element);
//        pipe.verify();
//
//        pipe.dom(draw, View.FRONT);
//
//        NodeList group = draw.root().getElementsByTagName("g");
//        assertEquals(group.getLength(), 2);
//        Node groupNode = group.item(1);
//        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
//        Element groupElement = (Element) groupNode;
//        assertEquals(groupElement.getAttribute("class"), Pipe$.MODULE$.LayerTag() );
//
//        NodeList list = groupElement.getElementsByTagName("rect");
//        assertEquals(list.getLength(), 1);
//        Node node = list.item(0);
//        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
//        Element element = (Element) node;
//        assertEquals(element.getAttribute("width"), length.toString());
//        assertEquals(element.getAttribute("height"), Pipe$.MODULE$.DiameterString() );
//        assertEquals(element.getAttribute("fill"), "none");
//    }
//
//    @Test
//    public void domFrontProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        new Proscenium(prosceniumElement);
//        Pipe pipe = new Pipe(element);
//        pipe.verify();
//
//        pipe.dom(draw, View.FRONT);
//
//        NodeList list = draw.root().getElementsByTagName("rect");
//        assertEquals(list.getLength(), 1);
//        Node node = list.item(0);
//        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
//        Element element = (Element) node;
//        Double exe = prosceniumX + x;
//        Double zee = Venue.Height() - (prosceniumZ + z - 1);
//        assertEquals(element.getAttribute("x"), exe.toString());
//        assertEquals(element.getAttribute("y"), zee.toString());
//    }
//
//    @Test
//    public void domFrontNoProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Pipe pipe = new Pipe(element);
//        pipe.verify();
//
//        pipe.dom(draw, View.FRONT);
//
//        NodeList list = draw.root().getElementsByTagName("rect");
//        assertEquals(list.getLength(), 1);
//        Node node = list.item(0);
//        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
//        Element element = (Element) node;
//        Double zee = Venue.Height() - (z - 1);
//        assertEquals(element.getAttribute("x"), x.toString());
//        assertEquals(element.getAttribute("y"), zee.toString());
//    }

    @Test
    public void parseWithCoordinates() throws Exception {
        String xml = "<plot>" +
                "<pipe id=\"Identifier\" length=\"71\" x=\"4\" y=\"14\" z=\"2\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        new Parse( stream );

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 1 );
    }

    @Test(enabled = false)
    public void parseWithAnchors() throws Exception {
        String xml = "<plot>" +
//                "<hangpoint id=\"bill\" x=\"7\" y=\"8\" />" +
//                "<hangpoint id=\"betty\" x=\"7\" y=\"8\" />" +
                "<pipe id=\"fineMe\" length=\"17\" >" +
                "<anchor ref=\"bill\" />" +
                "<anchor ref=\"betty\" />" +
                "</pipe>" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

//        new Parse( stream );

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 5 );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.ProsceniumReset();
        TestResets.MountableReset();
        TestResets.LayerReset();
        TestResets.ElementalListerReset();
        TestResets.LuminaireReset();
        element = null;
        UniqueId.Reset();


        Element venueElement = new IIOMetadataNode("venue");
        venueElement.setAttribute("room", "Test Name");
        venueElement.setAttribute("width", "350");
        venueElement.setAttribute("depth", "400");
        venueElement.setAttribute("height", "240");
        new Venue(venueElement);

        prosceniumElement = new IIOMetadataNode("proscenium");
        prosceniumElement.setAttribute("width", "260");
        prosceniumElement.setAttribute("height", "200");
        prosceniumElement.setAttribute("depth", "22");
        prosceniumElement.setAttribute("x", prosceniumX.toString());
        prosceniumElement.setAttribute("y", prosceniumY.toString());
        prosceniumElement.setAttribute("z", prosceniumZ.toString());

        element = new IIOMetadataNode("pipe");
        element.setAttribute("id", pipeId);
        element.setAttribute("length", length.toString());
        element.setAttribute("x", x.toString());
        element.setAttribute("y", y.toString());
        element.setAttribute("z", z.toString());

        pipeCrossesProsceniumCenterElement = new IIOMetadataNode("pipe");
        pipeCrossesProsceniumCenterElement.setAttribute("id", pipeId);
        pipeCrossesProsceniumCenterElement.setAttribute("length", length.toString());
        pipeCrossesProsceniumCenterElement.setAttribute("x", negativeX.toString());
        pipeCrossesProsceniumCenterElement.setAttribute("y", y.toString());
        pipeCrossesProsceniumCenterElement.setAttribute("z", z.toString());

//        anchorElementA = new IIOMetadataNode( "anchor" );
//        Make an anchor element, which is anything that can hold a pipe.
//                Perhaps it should just be called "pipeclamp" and a cheeseborough is
//                made up outof a pair of them
//                and a half-borough or an lighting hanger is a pipeClamp with a bolt
//
//        anchoredElement = new IIOMetadataNode( "pipe" );
//        anchoredElement.setAttribute("id", pipeId);
//        anchoredElement.setAttribute("length", length.toString());
//        anchoredElement.setAttribute("");


//        baseElement = new IIOMetadataNode( "pipebase" );
////        baseElement.setAttribute( "size", baseSize.toString() );
//        baseElement.setAttribute( "x", baseX.toString() );
//        baseElement.setAttribute( "y", baseY.toString() );

        trussBaseElement = new IIOMetadataNode( "base" );
        trussBaseElement.setAttribute( "size", baseSize.toString() );
        trussBaseElement.setAttribute( "x", baseX.toString() );
        trussBaseElement.setAttribute( "y", baseY.toString() );
        
        trussElement = new IIOMetadataNode( "truss" );
        trussElement.setAttribute( "id", trussID );
        trussElement.setAttribute( "size", trussSize.toString() );
        trussElement.setAttribute( "length", trussLength.toString() );
        trussElement.appendChild( trussBaseElement );

        cheeseborough1Element = new IIOMetadataNode( "cheeseborough" );
        cheeseborough1Element.setAttribute( "id", cheeseborough1Id );
        cheeseborough1Element.setAttribute( "on", trussID );
        cheeseborough1Element.setAttribute( "location", cheeseborough1Location );

        cheeseborough2Element = new IIOMetadataNode( "cheeseborough" );
        cheeseborough2Element.setAttribute( "id", cheeseborough2Id );
        cheeseborough2Element.setAttribute( "on", trussID );
        cheeseborough2Element.setAttribute( "location", cheeseborough2Location );

        pipeOnCheeseboroughsElement = new IIOMetadataNode( "pipe" );
        pipeOnCheeseboroughsElement.setAttribute( "id", pipeOnCheeseboroughsId );
        pipeOnCheeseboroughsElement.setAttribute( "length", pipeOnCheeseboroughsLength.toString() );
        pipeOnCheeseboroughsElement.appendChild( cheeseborough1Element );
        pipeOnCheeseboroughsElement.appendChild( cheeseborough2Element );

        baseForPipeElement = new IIOMetadataNode( "pipebase" );
//        baseForPipeElement.setAttribute( "size", baseSize.toString() );
        baseForPipeElement.setAttribute( "x", baseX.toString() );
        baseForPipeElement.setAttribute( "y", baseY.toString() );

        pipeOnBaseElement = new IIOMetadataNode( "pipe" );
        pipeOnBaseElement.setAttribute( "id", pipeId );
        pipeOnBaseElement.setAttribute( "length", length.toString() );
        pipeOnBaseElement.appendChild( baseForPipeElement );

        luminaireElement = new IIOMetadataNode( "luminaire" );
        luminaireElement.setAttribute("unit", luminaireUnit);
        luminaireElement.setAttribute( "type", luminaireType );
        luminaireElement.setAttribute("location", luminaireLocation );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
