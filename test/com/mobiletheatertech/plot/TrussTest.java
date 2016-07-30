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
import java.util.TreeMap;

import static org.testng.Assert.*;

/**
 * @author dhs
 * @since 0.0.5
 */
public class TrussTest {

    Element element = null;
    Element positionedTrussElement = null;
    HangPoint hanger1 = null;
    HangPoint hanger2 = null;
    HangPoint hanger3 = null;
    Element trussOnBaseElement = null;
    Element baseElement = null;
    Element suspendElement1a = null;
    Element suspendElement1b = null;
    Element suspendElement2 = null;
    Element suspendElement3 = null;
    Element elementTrussDiagonal = null;
    Element trussPositionedElement = null;
//    Element baseElement = null;
    final String trussId = "trussID";
    final String trussDiagonalId = "trusDiagonalId";
    final String trussOnBaseId = "trussOnBaseId";
    final String trussPositionedId = "trussPositionedId";
    Double size = 12.0;
    String sizeIntegerString = "12";
    Double length = 160.0;
    Double x1 = 100.0;
    Double y1and2 = 200.0;
    Double x2 = 180.0;
    Double suspend3X = 150.0;
    Double suspend3Y = 150.0;
    Double x = 73.7;
    Double y = 12.5;
    Double z = 22.4;

    Double baseSize = 24.0;
    Double baseX = 17.0;
    Double baseY = 32.8;

    Double venueHeight = 240.0;
    Double suspendDistance = 1.0;

    Double negativeX = -21.9;
    final String unit = "unit";


    private Element prosceniumElement = null;
    private Integer prosceniumX = 200;
    private Integer prosceniumY = 144;
    private Integer prosceniumZ = 12;


    Element luminaireElement = null;
    final String luminaireUnit = "unit";
    final String luminaireType = "Altman 6x9";
    String luminaireLocation = "12";



    @Test
    public void isA() throws Exception {
        Truss instance = new Truss( element );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );
        assert UniqueId.class.isInstance( instance );
        assertFalse( Yokeable.class.isInstance( instance ) );

        assert SupportsClamp.class.isInstance( instance );
        assert Populate.class.isInstance( instance );
        assert Legendable.class.isInstance( instance );
//        assert Schematicable.class.isInstance( instance );
    }

    @Test
    public void constantLayerName() {
        assertEquals(Truss$.MODULE$.LayerName(), "Trusses");
    }

    @Test
    public void constantLayerTag() {
        assertEquals(Truss$.MODULE$.LayerTag(), "truss");
    }

    @Test
    public void constantColor() {
        assertEquals(Truss$.MODULE$.Color(), "dark blue");
    }

    @Test
    public void constantLegendRegistered() {
        assertEquals(Truss$.MODULE$.LegendRegistered(), false );
    }

    @Test
    public void baseCountIncrement() {
        assertEquals( Truss$.MODULE$.BaseCount(), 0 );

        Truss$.MODULE$.BaseCountIncrement();

        assertEquals( Truss$.MODULE$.BaseCount(), 1 );
    }

    @Test
    public void storesAttributes() throws Exception {
        Truss truss = new Truss( element );

        assertEquals( TestHelpers.accessDouble( truss, "size" ), size );
        assertEquals( TestHelpers.accessDouble( truss, "length" ), length );
        assertNull( TestHelpers.accessDouble( truss, "x" ) );
        assertNull( TestHelpers.accessDouble( truss, "y" ) );
        assertNull( TestHelpers.accessDouble( truss, "z" ) );
    }

    @Test
    public void storeOptionalsAttributes() throws Exception {
        element.setAttribute( "x", x.toString());
        element.setAttribute( "y", y.toString());
        element.setAttribute( "z", z.toString() );
        Truss truss = new Truss( element );

        assertEquals( TestHelpers.accessDouble( truss, "size" ), size );
        assertEquals( TestHelpers.accessDouble( truss, "length" ), length );
        assertEquals( TestHelpers.accessDouble( truss, "x" ), x );
        assertEquals( TestHelpers.accessDouble( truss, "y" ), y );
        assertEquals( TestHelpers.accessDouble( truss, "z" ), z );
    }

    @Test
    public void positionedNot() throws Exception {
        Truss truss = new Truss( element );

        assertEquals( TestHelpers.accessBoolean( truss, "positioned" ), Boolean.FALSE );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Truss \\(" + trussPositionedId + "\\) must have position, one base, or two suspend children.")
    public void positionedNoX() throws Exception {
        trussPositionedElement.removeAttribute( "x" );
        new Truss( trussPositionedElement );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Truss \\(" + trussPositionedId + "\\) must have position, one base, or two suspend children.")
    public void positionedNoY() throws Exception {
        trussPositionedElement.removeAttribute( "y" );
        new Truss( trussPositionedElement );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Truss \\(" + trussPositionedId + "\\) must have position, one base, or two suspend children.")
    public void positionedNoZ() throws Exception {
        trussPositionedElement.removeAttribute( "z" );
        new Truss( trussPositionedElement );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Truss \\(" + trussPositionedId + "\\) must have position, one base, or two suspend children.")
    public void positionedNoXY() throws Exception {
        trussPositionedElement.removeAttribute( "x" );
        trussPositionedElement.removeAttribute( "y" );
        new Truss( trussPositionedElement );
    }

    @Test
    public void positioned() throws Exception {
        Truss truss = new Truss( trussPositionedElement );

        assertEquals( TestHelpers.accessBoolean( truss, "positioned" ), Boolean.TRUE );
    }

    @Test
    public void Values() throws Exception {
        Truss truss = new Truss( trussPositionedElement );
//        truss.verify();

        Point start = (Point) TestHelpers.accessObject( truss, "start" );
        assertEquals( start.x(), x );
        assertEquals( start.y(), y );
        assertEquals( start.z(), z );
    }


//    @Test
//    public void marksProcessed() throws Exception {
//        String emptyMark = diversionElement.attribute( "processedMark" );
//        assertEquals( emptyMark, "", "Should be unset" );
//
//        Truss truss = new Truss( diversionElement );
//
//        String trussMark = TestHelpers.accessString( truss, "processedMark" );
//        String elementMark = diversionElement.attribute( "processedMark" );
//        assertNotNull( trussMark );
//        assertNotEquals( trussMark, "", "Should be set in Truss object" );
//        assertNotEquals( elementMark, "", "Should be set in Element" );
//        assertEquals( trussMark, elementMark, "should match" );
//    }
//
//    @Test
//    public void findNull() throws Exception {
//        new Truss( diversionElement );
//
//        Truss found = Truss.Find( null );
//
//        assertNull( found );
//    }
//
//    @Test
//    public void findsMarked() throws Exception {
//        Truss truss = new Truss( diversionElement );
//
//        Truss found = Truss.Find( diversionElement.attribute( "processedMark" ) );
//
//        assertSame( found, truss );
//    }

    @Test
    public void storesElement() throws Exception {
        Truss truss = new Truss( element );

        Field elementField = TestHelpers.accessField( truss, "element" );
        Element elementReference = (Element) elementField.get( truss );

        assertSame( elementReference, element );
    }

//    @Test
//    public void layer() throws Exception {
//        assertNull( Category.Select( Truss.CATEGORY ) );
//
//        new Truss( element );
//
//        assertNotNull( Category.Select( Truss.CATEGORY ) );
//    }

    @Test
    public void baseChild() throws Exception {
        Truss truss = new Truss(trussOnBaseElement);
        new Base( baseElement );

        truss.verify();

        Field baseField = TestHelpers.accessField( truss, "base" );
        Base base = (Base) baseField.get( truss );

        assert Base.class.isInstance( base );

        Field suspendField1 = TestHelpers.accessField( truss, "suspend1" );
        Object suspend1 = suspendField1.get( truss );
        assertNull(suspend1);

        Field suspendField2 = TestHelpers.accessField( truss, "suspend2" );
        Object suspend2 = suspendField2.get( truss );
        assertNull(suspend2);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Truss \\(" + trussOnBaseId + "\\) must have position, one base, or two suspend children.")
    public void noBase() throws Exception {
        trussOnBaseElement.removeChild( baseElement );
        new Truss( trussOnBaseElement );

//        truss.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Truss \\(" + trussOnBaseId + "\\) must have position, one base, or two suspend children.")
    public void tooManyBases() throws Exception {
        Element baseElementExtra = new IIOMetadataNode( "base" );
        baseElementExtra.setAttribute( "size", baseSize.toString() );
        baseElementExtra.setAttribute("x", "12" );
        baseElementExtra.setAttribute("y", "2009" );

        trussOnBaseElement.appendChild( baseElement );
        trussOnBaseElement.appendChild( baseElementExtra );
        new Truss( trussOnBaseElement );

//        truss.verify();
    }

    /*
            Make a couple of suspend objects that are children of this truss
            and confirm that they are properly associated
     */
    @Test
    public void suspendChildren() throws Exception {
        Truss truss = new Truss( element );
        new Suspend(suspendElement1a);
        new Suspend( suspendElement2 );

//        truss.verify();

        Field suspendField1 = TestHelpers.accessField( truss, "suspend1" );
        Suspend suspend1 = (Suspend) suspendField1.get( truss );

        Field suspendField2 = TestHelpers.accessField( truss, "suspend2" );
        Suspend suspend2 = (Suspend) suspendField2.get( truss );

        assertTrue( Suspend.class.isInstance( suspend1 ) );
        assertTrue( Suspend.class.isInstance( suspend2 ) );

        Field baseField = TestHelpers.accessField( truss, "base" );
        Object baseReference = baseField.get(truss);
        assertNull(baseReference);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
          expectedExceptionsMessageRegExp =
                  "Truss \\(" + trussId + "\\) must have position, one base, or two suspend children.")
    public void noSuspends() throws Exception {
        element.removeChild(suspendElement1a);
        element.removeChild( suspendElement2 );
        new Truss( element );

//        truss.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class,
          expectedExceptionsMessageRegExp =
                  "Truss \\(" + trussId + "\\) must have position, one base, or two suspend children.")
    public void tooFewSuspends() throws Exception {
        element.removeChild(suspendElement1a);
        new Truss( element );

//        truss.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class,
          expectedExceptionsMessageRegExp = "Truss \\(" + trussId + "\\) must have position, one base, or two suspend children.")
    public void tooManySuspends() throws Exception {
        // This is just broken.
        // A new 'element' should have been constructed in setUpMethod() that has two
        //   suspendElement# in it, but apparently the removal in the above test is not
        //   being overwritten.
        element.appendChild(suspendElement1a);
        element.appendChild( suspendElement2 );
        element.appendChild( suspendElement3 );

        new Truss( element );

//        truss.verify();
    }

//    @Test
//    public void verifyBase() throws Exception {
//        baseElement.removeChild( suspendElement1a );
//        baseElement.removeChild( suspendElement2 );
//        baseElement.appendChild( baseElement );
//        Truss truss = new Truss( baseElement );
//
//        truss.verify();
//
//        Field suspendField1 = TestHelpers.accessField( truss, "suspend1" );
//        Field suspendField2 = TestHelpers.accessField( truss, "suspend2" );
//
//        assertNull( suspendField1 );
//        assertNull( suspendField2 );
//
//        Field baseField = TestHelpers.accessField( truss, "base" );
//
//        assertNotNull( baseField );
//    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFineSize12() throws Exception {
        new Truss( element );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFineSize12Integer() throws Exception {
        element.setAttribute( "size", sizeIntegerString );
        new Truss( element );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFineSize20() throws Exception {
        element.setAttribute( "size", "20.5" );
        new Truss( element );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFinePositioned() throws Exception {
        element.removeChild(suspendElement1a);
        element.removeChild( suspendElement2 );
        element.removeChild( suspendElement3 );
        element.setAttribute( "x", x.toString() );
        element.setAttribute( "y", y.toString() );
        element.setAttribute( "z", z.toString() );

        new Truss( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Truss \\(" + trussId + "\\) is missing required 'size' attribute.")
    public void noSize() throws Exception {
        element.removeAttribute( "size" );
        new Truss( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Truss \\(" + trussId + "\\) is missing required 'length' attribute.")
    public void noLength() throws Exception {
        element.removeAttribute( "length" );
        new Truss( element );
    }

    @Test(expectedExceptions = KindException.class,
          expectedExceptionsMessageRegExp =
                  "Truss of size 302.0 not supported. Try 12.0 or 20.5.")
    public void unsupportedSize() throws Exception {
        element.setAttribute( "size", "302" );
        new Truss( element );
    }

//    @Test( expectedExceptions = StructureException.class,
//        expectedExceptionsMessageRegExp = 
//        "Truss should not extend beyond the boundaries of the venue")
//    public void noSuspend() throws Exception {
//        fail( "Need to test this in concert with suspend");
//        baseElement.setAttribute( "depth", "401");
//        Truss truss = new Truss( baseElement );
//    }
//    
//    @Test( expectedExceptions = StructureException.class,
//        expectedExceptionsMessageRegExp = 
//        "Truss should not extend beyond the boundaries of the venue")
//    public void tooFewSuspend() throws Exception {
//        fail( "Need to test this in concert with suspend");
//        baseElement.setAttribute( "depth", "401");
//        Truss truss = new Truss( baseElement );
//    }

    @Test
    public void location() throws Exception {
        Truss truss = new Truss( element );
//        truss.verify();
        assertNotNull( TestHelpers.accessObject(truss, "suspend1"));
        assertEquals( truss.suspend1().locate(),
                new Point( x1, y1and2, venueHeight - suspendDistance ) );
        assertNotNull( TestHelpers.accessObject(truss, "suspend2"));
        assertEquals( truss.suspend2().locate(),
                new Point( x2, y1and2, venueHeight - suspendDistance ) );

        Point point = truss.mountableLocation("a 17");
        assertEquals( point, new Point(77.0, 194.0, 239.0));
    }

    @Test
    public void locationVertexC() throws Exception {
        Truss truss = new Truss( element );
        new Suspend(suspendElement1a);
        new Suspend( suspendElement2 );
        truss.verify();
        assertNotNull( TestHelpers.accessObject(truss, "suspend1"));
        assertNotNull( TestHelpers.accessObject(truss, "suspend2"));

        Point point = truss.mountableLocation("c 17");
        assertEquals( point, new Point(77.0, 194.0, 227.0));
    }

    @Test(expectedExceptions=InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Truss \\(" + trussId + "\\) location must include vertex and distance.")
    public void locationFormatNoDistance() throws Exception{
        Truss truss = new Truss( element );

        truss.mountableLocation("a");
    }

    @Test(expectedExceptions=InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Truss \\(" + trussId + "\\) location does not include a valid vertex.")
    public void locationFormatNoVertex() throws Exception{
        Truss truss = new Truss( element );

        truss.mountableLocation("17");
    }

    @Test(expectedExceptions=MountingException.class,
            expectedExceptionsMessageRegExp = "Truss \\(" + trussId + "\\) does not include location 161.")
    public void locationOffTruss() throws Exception{
        Truss truss = new Truss( element );

        truss.mountableLocation("a 161");
    }

    @Test(expectedExceptions=MountingException.class,
            expectedExceptionsMessageRegExp = "Truss \\(" + trussId + "\\) does not include location -1.")
    public void locationNegativeOffTruss() throws Exception{
        Truss truss = new Truss( element );

        truss.mountableLocation("a -1");
    }

    @Test(expectedExceptions=InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Truss \\(" + trussId + "\\) location does not include a valid vertex.")
    public void locationVertexOffTruss() throws Exception{
        Truss truss = new Truss( element );

        truss.mountableLocation("e 16");
    }

    @Test
    public void locationPositionedVertexA() throws Exception {
        Truss truss = new Truss( trussPositionedElement );

        Point point = truss.mountableLocation("a 17");
        assertEquals(point, new Point(x - length / 2 + 17, y - size / 2, z + size / 2));
    }

    @Test
    public void locationPositionedVertexB() throws Exception {
        Truss truss = new Truss( trussPositionedElement );

        Point point = truss.mountableLocation("b 97");
        assertEquals( point, new Point(x - length / 2 + 97, y + size / 2, z + size / 2));
    }

    @Test
    public void locationPositionedVertexC() throws Exception {
        Truss truss = new Truss( trussPositionedElement );

        Point point = truss.mountableLocation("c 15");
        assertEquals( point, new Point( x - length / 2 + 15, y - size / 2, z - size / 2 ));
    }

    @Test
    public void locationPositionedVertexD() throws Exception {
        Truss truss = new Truss( trussPositionedElement );

        Point point = truss.mountableLocation("d 150");
        assertEquals( point, new Point( x - length / 2 + 150, y + size / 2, z - size / 2 ));
    }

    @Test(expectedExceptions=InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Truss \\(" + trussPositionedId + "\\) location must include vertex and distance.")
    public void locationPositionedFormatNoDistance() throws Exception{
        Truss truss = new Truss( trussPositionedElement );

        truss.mountableLocation("a");
    }

    @Test(expectedExceptions=InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Truss \\(" + trussPositionedId + "\\) location does not include a valid vertex.")
    public void locationPositionedFormatNoVertex() throws Exception{
        Truss truss = new Truss( trussPositionedElement );

        truss.mountableLocation("17");
    }

    @Test(expectedExceptions=MountingException.class,
            expectedExceptionsMessageRegExp = "Truss \\(" + trussPositionedId + "\\) does not include location 161.")
    public void locationPositionedOffTruss() throws Exception{
        Truss truss = new Truss( trussPositionedElement );

        truss.mountableLocation("a 161");
    }

    @Test(expectedExceptions=MountingException.class,
            expectedExceptionsMessageRegExp = "Truss \\(" + trussPositionedId + "\\) does not include location -1.")
    public void locationPositionedNegativeOffTruss() throws Exception{
        Truss truss = new Truss( trussPositionedElement );

        truss.mountableLocation("a -1");
    }

    @Test(expectedExceptions=InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Truss \\(" + trussPositionedId +  "\\) location does not include a valid vertex.")
    public void locationPositionedVertexOffTruss() throws Exception{
        Truss truss = new Truss( trussPositionedElement );

        truss.mountableLocation("e 16");
    }

    @Test(expectedExceptions=InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Truss not yet supported with Proscenium.")
    public void trussWithProscenium() throws Exception {
        Element prosceniumElement = new IIOMetadataNode("proscenium");
        prosceniumElement.setAttribute("width", "260");
        prosceniumElement.setAttribute("height", "200");
        prosceniumElement.setAttribute("depth", "22");
        prosceniumElement.setAttribute("x", "160");
        prosceniumElement.setAttribute("y", "150");
        prosceniumElement.setAttribute("z", "14");
        new Proscenium(prosceniumElement);
        assertTrue( Proscenium.Active());
        assertTrue( (Boolean)TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Proscenium","ACTIVE"));

        new Truss( element );
    }

    @Test
    public void rotatedLocation() throws Exception {
        Truss truss = new Truss( element );
        new Suspend(suspendElement1a);
        new Suspend( suspendElement2 );

        truss.verify();

        Place place = truss.rotatedLocation( "b 23");

        assertEquals(place.origin(), TestHelpers.accessPoint(truss, "point1"));
        assertNotNull(TestHelpers.accessDouble(truss, "rotation"));
        assertEquals( place.rotation(), -0.0);
        assertEquals( place.location(), new Point(83.0, 206.0, 239.0));
    }
 
    @Test
    public void rotatedLocationBeforeVerify() throws Exception {
        Truss truss = new Truss( element );
        new Suspend(suspendElement1a);
        new Suspend( suspendElement2 );

        Place place = truss.rotatedLocation( "b 23");

        assertEquals( place.origin(), TestHelpers.accessPoint( truss, "point1"));
        assertNotNull( TestHelpers.accessDouble(truss, "rotation"));
        assertEquals( place.rotation(), -0.0);
        assertEquals( place.location(), new Point(83.0, 206.0, 239.0));
    }

    @Test
    public void rotatedLocationDiagonal() throws Exception {
        new Suspend(suspendElement1b);
        new Suspend( suspendElement3 );
        Truss truss = new Truss( elementTrussDiagonal );

        Place place = truss.rotatedLocation( "b 23");

        assertEquals( place.origin(), TestHelpers.accessPoint( truss, "point1"));
        assertNotNull( TestHelpers.accessDouble(truss, "rotation"));
        assertEquals( place.rotation(), -45.0);
        assertEquals( place.location(), new Point(83.0, 206.0, 239.0));

        fail( "Does rotatedLocation give correct results for truss at a diagonal to the room coordinates?");
    }

    @Test
    public void minLocation() {
        Truss truss = new Truss( positionedTrussElement );

        assertEquals( truss.minLocation(), 0.0 );
    }

    @Test
    public void maxLocation() {
        Truss truss = new Truss( positionedTrussElement );

        assertEquals( truss.maxLocation(), length );
    }

//    @Test
//    public void minLocationProscenium() throws Exception {
//        new Proscenium(prosceniumElement);
//
//        Truss truss = new Truss( positionedTrussElement );
//
//        assertEquals( truss.minLocation(), negativeX );
//    }
//
//    @Test
//    public void maxLocationProscenium() throws Exception {
//        new Proscenium(prosceniumElement);
//
//        Truss truss = new Truss( positionedTrussElement );
//
//        assertEquals( truss.maxLocation(), negativeX + length );
//    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "Pipe \\(" + trussId + "\\) unit '" + unit +"' has location outside of permissible range.")
    public void hangLocationOutOfRange() throws Exception {
        final String type = "Altman 6x9";

        Element elementOnPipe = new IIOMetadataNode( "luminaire" );
        elementOnPipe.setAttribute("unit", unit);
        elementOnPipe.setAttribute( "type", type );
        elementOnPipe.setAttribute("location", "162" );

        positionedTrussElement.appendChild( elementOnPipe );
        new Truss( positionedTrussElement );
    }

    @Test
    public void tagCallbackRegistered() {
        positionedTrussElement.appendChild( luminaireElement );
        Truss truss = new Truss( positionedTrussElement );

        assertEquals( truss.tags().size(), 1 );
    }

    @Test
    public void populateChildren() {
        positionedTrussElement.appendChild( luminaireElement );
        Truss truss = new Truss( positionedTrussElement );

        scala.collection.mutable.ArrayBuffer<IsClamp> list = truss.IsClampList();
        assertEquals( list.size(), 1 );
    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Truss truss = new Truss( element );
        new Suspend(suspendElement1a);
        new Suspend(suspendElement2);
        truss.verify();

        truss.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Truss$.MODULE$.LayerTag() );

        NodeList list = groupElement.getElementsByTagName("rect");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("width"), length.toString());
        assertEquals(element.getAttribute("height"), size.toString());
        assertEquals(element.getAttribute("fill"), "none");
        assertEquals(element.getAttribute("stroke"), "dark blue");
        Double x = x1 - (length - (x2 - x1)) / 2;
        assertEquals(element.getAttribute("x"), x.toString());
        Double y = y1and2 - size / 2;
        assertEquals(element.getAttribute("y"), y.toString());
        assertEquals(element.getAttribute("transform"), "rotate(-0.0," + x1.toString() + "," + y1and2.toString() + ")");
    }

    @Test
    public void legendRegistered() throws Exception {
        TestResets.LegendReset();
        new Base( baseElement );
        new Truss(trussOnBaseElement);

        TreeMap<Integer, Legendable> legendList =
                (TreeMap<Integer, Legendable>)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Legend", "LEGENDLIST" );
        assertEquals( legendList.size(), 1 );
        Integer order = legendList.lastKey();
        assert( order >= LegendOrder.Structure.ordinal() );
        assert( order < LegendOrder.Luminaire.ordinal() );
    }

    @Test
    public void legendRegisteredOnce() throws Exception {
        TestResets.LegendReset();
        new Base( baseElement );
        new Truss(trussOnBaseElement);
        trussOnBaseElement.setAttribute( "id", "differentBaseTruss");
        new Truss(trussOnBaseElement);

        TreeMap<Integer, Legendable> legendList = (TreeMap<Integer, Legendable>)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Legend", "LEGENDLIST" );
        assertEquals( legendList.size(), 1 );
    }

    @Test
    public void domLegendItem() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Truss truss = new Truss(trussOnBaseElement);
        new Base( baseElement );
        truss.verify();
//        truss.dom(draw, View.PLAN);
        Truss$.MODULE$.BaseCountIncrement();
        PagePoint startPoint = new PagePoint( 20.0, 1/0.0 );

        NodeList preGroup = draw.root().getElementsByTagName( "g" );
        assertEquals( preGroup.getLength(), 1 );

        PagePoint endPoint = truss.domLegendItem( draw, startPoint );

//        NodeList group = draw.root().getElementsByTagName( "g" );
//        assertEquals( group.getLength(), 1 );
//        Node groupNod = group.item(0);
//        Element groupElem = (Element) groupNod;

        NodeList groupList = draw.root().getElementsByTagName( "g" );
        assertEquals( groupList.getLength(), 2 );
        Node groupNode = groupList.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;

        NodeList rectList = groupElement.getElementsByTagName( "rect" );
        assertEquals( rectList.getLength(), 2 );

        Node rectNode = rectList.item(0);
        assertEquals(rectNode.getNodeType(), Node.ELEMENT_NODE);
        Element rectElement = (Element) rectNode;
        assertEquals(rectElement.getAttribute("x"), "0.0" );
        assertEquals(rectElement.getAttribute("y"), "0.0" );
        assertEquals(rectElement.getAttribute("width"), "12.0");
        assertEquals(rectElement.getAttribute("height"), "12.0");

        rectNode = rectList.item(1);
        assertEquals(rectNode.getNodeType(), Node.ELEMENT_NODE);
        rectElement = (Element) rectNode;
        Double innerX = startPoint.x() + 3;
        Double innerY = startPoint.y() + 3;
        assertEquals(rectElement.getAttribute("x"), "3.0" );
        assertEquals(rectElement.getAttribute("y"), "3.0" );
        assertEquals(rectElement.getAttribute("width"), "6.0");
        assertEquals(rectElement.getAttribute("height"), "6.0");

        NodeList textList = groupElement.getElementsByTagName( "text" );
        assertEquals( textList.getLength(), 2 );

        Node textNode = textList.item(0);
        assertEquals(textNode.getNodeType(), Node.ELEMENT_NODE);
        Element textElement = (Element) textNode;
        Double x = Legend.TEXTOFFSET;
        Double y = 8.0;
        assertEquals(textElement.getAttribute("x"), x.toString() );
        assertEquals(textElement.getAttribute("y"), y.toString() );
        assertEquals(textElement.getAttribute("fill"), "black" );

        textNode = textList.item(1);
        assertEquals(textNode.getNodeType(), Node.ELEMENT_NODE);
        textElement = (Element) textNode;
        x = Legend.QUANTITYOFFSET;
        assertEquals(textElement.getAttribute("x"), x.toString() );
        assertEquals(textElement.getAttribute("y"), y.toString() );
        assertEquals(textElement.getAttribute("fill"), "black" );

        // TODO Check for text here

        assertEquals( endPoint, new PagePoint( startPoint.x(), startPoint.y() + 9 ));
    }


//    @Test
//    public void domPlanProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        new Proscenium( prosceniumElement );
//        Pipe pipe = new Pipe( baseElement );
//        pipe.verify();
//
//        pipe.dom( draw, View.PLAN );
//
//        NodeList list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element baseElement = (Element) node;
//        Integer ex = prosceniumX + x;
//        Integer wy = prosceniumY - (y - 1);
//        assertEquals( baseElement.attribute( "x" ), ex.toString() );
//        assertEquals( baseElement.attribute( "y" ), wy.toString() );
//    }
//
//    @Test
//    public void domPlanNoProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Pipe pipe = new Pipe( baseElement );
//        pipe.verify();
//
//        pipe.dom( draw, View.PLAN );
//
//        NodeList list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element baseElement = (Element) node;
//        assertEquals( baseElement.attribute( "x" ), x.toString() );
//        assertEquals( baseElement.attribute( "y" ), ((Integer) (y - 1)).toString() );
//    }

//    @Test
//    public void domSection() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Pipe pipe = new Pipe( baseElement );
//        pipe.verify();
//
//        pipe.dom( draw, View.SECTION );
//
//        NodeList group = draw.root().getElementsByTagName( "g" );
//        assertEquals( group.getLength(), 2 );
//        Node groupNode = group.item( 1 );
//        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
//        Element groupElement = (Element) groupNode;
//        assertEquals( groupElement.attribute( "class" ), Pipe.LAYERTAG );
//
//        NodeList list = groupElement.getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element baseElement = (Element) node;
//        assertEquals( baseElement.attribute( "width" ), Pipe.DIAMETER.toString() );
//        assertEquals( baseElement.attribute( "height" ), Pipe.DIAMETER.toString() );
//        assertEquals( baseElement.attribute( "fill" ), "none" );
//    }
//
//    @Test
//    public void domSectionProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        new Proscenium( prosceniumElement );
//        Pipe pipe = new Pipe( baseElement );
//        pipe.verify();
//
//        pipe.dom( draw, View.SECTION );
//
//        NodeList list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element baseElement = (Element) node;
//        Integer wye = prosceniumY - (y - 1);
//        Integer zee = Venue.Height() - (prosceniumZ + z - 1);
//        assertEquals( baseElement.attribute( "x" ), wye.toString() );
//        assertEquals( baseElement.attribute("y"), zee.toString() );
//    }
//
//    @Test
//    public void domSectionNoProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Pipe pipe = new Pipe( baseElement );
//        pipe.verify();
//
//        pipe.dom( draw, View.SECTION );
//
//        NodeList list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element baseElement = (Element) node;
//        Integer wye = y - 1;
//        Integer zee = Venue.Height() - (z - 1);
//        assertEquals( baseElement.attribute( "x" ), wye.toString() );
//        assertEquals( baseElement.attribute( "y" ), zee.toString() );
//    }

    @Test
    public void parseWithSuspends() throws Exception {
        String xml = "<plot>" +
                "<hangpoint id=\"bill\" x=\"7\" y=\"8\" />" +
                "<hangpoint id=\"betty\" x=\"7\" y=\"8\" />" +
                "<truss id=\"id\" size=\"12\" length=\"1\" >" +
                "<suspend ref=\"bill\" distance=\"3\" />" +
                "<suspend ref=\"betty\" distance=\"3\" />" +
                "</truss>" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        new Parse( stream );

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 5 );
    }

//    @Test
//    public void parseWithBase() throws Exception {
//        String xml = "<plot>" +
//                "<truss size=\"12\" length=\"1\" >" +
//                "<base x=\"55\" y=\"27\" />" +
//                "</truss>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestHelpers.MinderDomReset();
//
//        new Parse( stream );
//
//        ArrayList<Minder> list = Drawable.List();
//        assertEquals( list.size(), 2 );
//    }

    @Test
    public void parseMultiple() throws Exception {
        String xml = "<plot>" +
                "<hangpoint id=\"roger\" x=\"7\" y=\"8\" />" +
                "<hangpoint id=\"renee\" x=\"7\" y=\"8\" />" +
                "<truss id=\"id\" size=\"12\" length=\"1\" >" +
                "<suspend ref=\"roger\" distance=\"3\" />" +
                "<suspend ref=\"renee\" distance=\"3\" />" +
                "</truss>" +
                "<truss id=\"id2\" size=\"12\" length=\"1\" >" +
                "<suspend ref=\"roger\" distance=\"3\" />" +
                "<suspend ref=\"renee\" distance=\"3\" />" +
                "</truss>" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        new Parse( stream );

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 8 );
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
        TestResets.ElementalListerReset();
        TestResets.MountableReset();
        TestResets.MinderDomReset();
        TestResets.LuminaireReset();
        Truss.Reset();
        UniqueId.Reset();

        assertTrue( ElementalLister.List().isEmpty() );
        assertTrue( Layer.List().isEmpty() );

        Element venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", venueHeight.toString() );
        new Venue( venueElement );

        prosceniumElement = new IIOMetadataNode("proscenium");
        prosceniumElement.setAttribute("width", "260");
        prosceniumElement.setAttribute("height", "200");
        prosceniumElement.setAttribute("depth", "22");
        prosceniumElement.setAttribute("x", prosceniumX.toString());
        prosceniumElement.setAttribute("y", prosceniumY.toString());
        prosceniumElement.setAttribute("z", prosceniumZ.toString());

        Element hangPoint1 = new IIOMetadataNode( "hangpoint" );
        hangPoint1.setAttribute( "id", "jim" );
        hangPoint1.setAttribute("x", x1.toString());
        hangPoint1.setAttribute("y", y1and2.toString());
        hanger1 = new HangPoint( hangPoint1 );

        Element hangPoint2 = new IIOMetadataNode( "hangpoint" );
        hangPoint2.setAttribute( "id", "joan" );
        hangPoint2.setAttribute("x", x2.toString());
        hangPoint2.setAttribute("y", y1and2.toString());
        hanger2 = new HangPoint( hangPoint2 );

        Element hangPoint3 = new IIOMetadataNode( "hangpoint" );
        hangPoint3.setAttribute( "id", "fred" );
        hangPoint3.setAttribute("x", suspend3X.toString());
        hangPoint3.setAttribute("y", suspend3Y.toString());
        hanger3 = new HangPoint( hangPoint3 );

        suspendElement1a = new IIOMetadataNode( "suspend" );
        suspendElement1a.setAttribute( "ref", "jim" );
        suspendElement1a.setAttribute( "distance", suspendDistance.toString() );

        suspendElement1b = new IIOMetadataNode( "suspend" );
        suspendElement1b.setAttribute( "ref", "jim" );
        suspendElement1b.setAttribute( "distance", suspendDistance.toString() );

        suspendElement2 = new IIOMetadataNode( "suspend" );
        suspendElement2.setAttribute( "ref", "joan" );
        suspendElement2.setAttribute( "distance", suspendDistance.toString() );

        suspendElement3 = new IIOMetadataNode( "suspend" );
        suspendElement3.setAttribute( "ref", "fred" );
        suspendElement3.setAttribute( "distance", suspendDistance.toString() );

//        baseElement = new IIOMetadataNode( "base" );
//        baseElement.setAttribute( "x", "1" );
//        baseElement.setAttribute( "y", "2" );

        element = new IIOMetadataNode("truss");
        element.setAttribute("id", trussId );
        element.setAttribute("size", size.toString());
        element.setAttribute("length", length.toString());
        element.appendChild(suspendElement1a);
        element.appendChild( suspendElement2 );
        
        positionedTrussElement = new IIOMetadataNode("truss");
        positionedTrussElement.setAttribute("id", trussId );
        positionedTrussElement.setAttribute("size", size.toString());
        positionedTrussElement.setAttribute("length", length.toString());
        positionedTrussElement.setAttribute("x", x.toString());
        positionedTrussElement.setAttribute("y", y.toString());
        positionedTrussElement.setAttribute("z", z.toString());

        elementTrussDiagonal = new IIOMetadataNode("truss");
        elementTrussDiagonal.setAttribute("id", trussDiagonalId );
        elementTrussDiagonal.setAttribute("size", size.toString());
        elementTrussDiagonal.setAttribute("length", length.toString());
        elementTrussDiagonal.appendChild(suspendElement1b);
        elementTrussDiagonal.appendChild( suspendElement3 );

        baseElement = new IIOMetadataNode( "base" );
        baseElement.setAttribute( "size", baseSize.toString() );
        baseElement.setAttribute("x", baseX.toString());
        baseElement.setAttribute("y", baseY.toString());

        trussOnBaseElement = new IIOMetadataNode("truss");
        trussOnBaseElement.setAttribute("id", trussOnBaseId );
        trussOnBaseElement.setAttribute("size", size.toString());
        trussOnBaseElement.setAttribute("length", length.toString());
        trussOnBaseElement.appendChild(baseElement);

        trussPositionedElement = new IIOMetadataNode("truss");
        trussPositionedElement.setAttribute("id", trussPositionedId );
        trussPositionedElement.setAttribute("size", size.toString());
        trussPositionedElement.setAttribute("length", length.toString());
        trussPositionedElement.setAttribute( "x", x.toString() );
        trussPositionedElement.setAttribute( "y", y.toString() );
        trussPositionedElement.setAttribute( "z", z.toString() );


        luminaireElement = new IIOMetadataNode( "luminaire" );
        luminaireElement.setAttribute("unit", luminaireUnit);
        luminaireElement.setAttribute( "type", luminaireType );
        luminaireElement.setAttribute("location", luminaireLocation );

    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}