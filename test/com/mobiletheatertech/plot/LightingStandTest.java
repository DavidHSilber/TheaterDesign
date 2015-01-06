package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

/**
 * Created by dhs on 12/12/14.
 */
public class LightingStandTest {

    Element element = null;
    Element element2 = null;
    final String id = "Lighitng Stand ID";
    String id2 = "Other Lighitng Stand ID";
    Draw draw;
    Double x = 6.7;
    Double y = 3.6;
    Double orientation = -45.0;

    @Test
    public void isA() throws Exception {
        LightingStand instance = new LightingStand( element );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );
        assert Mountable.class.isInstance( instance );

        assert Schematicable.class.isInstance( instance );
        assert Legendable.class.isInstance( instance );
    }

    @Test
    public void storesAttributes() throws Exception {
        LightingStand instance = new LightingStand( element );

        assertEquals(TestHelpers.accessDouble(instance, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(instance, "y"), y.doubleValue());
        assertEquals(TestHelpers.accessDouble(instance, "orientation"), 0.0 );
    }

    @Test
    public void storesOptionalOrientationAttribute() throws Exception {
        element.setAttribute("orientation", orientation.toString());
        LightingStand instance = new LightingStand( element );

        assertEquals(TestHelpers.accessDouble(instance, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(instance, "y"), y.doubleValue());
        assertEquals(TestHelpers.accessDouble(instance, "orientation"), orientation.doubleValue() );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "LightingStand \\("+id+"\\) location must specify a letter in the range of 'a' to 'd'.")
    public void noLocation() throws Exception {
        LightingStand instance = new LightingStand( element );
        instance.verify();

        instance.location("");
    }

    @Test
    public void schematicLocation() throws Exception {
        LightingStand instance = new LightingStand( element );
        instance.verify();
        draw.establishRoot();

        instance.dom(draw, View.SCHEMATIC);

        PagePoint point = instance.schematicLocation("a");
//        assertEquals(point,
//                new PagePoint(Schematic.FirstX - LightingStand.Space * 1.5,
//                        Schematic.FirstY));
        fail();
    }

    @Test
    public void location() throws Exception {
        LightingStand instance = new LightingStand( element );
        instance.verify();

        Point point = instance.location( "a" );
        assertEquals(point, new Point(x + 18, y, 144.0));
    }

    @Test
    public void rotatedLocation() throws Exception {
        SvgElement.Offset( 9.9, 1.1 );
        element.setAttribute("orientation", orientation.toString());
        LightingStand instance = new LightingStand( element );
        instance.verify();

        Place place = instance.rotatedLocation("d");
        assertEquals( place.rotation(), orientation );
        assertEquals( place.location(), new Point( x - 18, y, 144.0 ));
        assertEquals( place.origin(), new Point( x + SvgElement.OffsetX(), y + SvgElement.OffsetY(), 144.0 ));
    }

    @Test
    public void domCounts() throws Exception {
        LightingStand instance = new LightingStand( element );
        instance.verify();
        draw.establishRoot();

        assertEquals(
                (Integer) TestHelpers.accessStaticObject("com.mobiletheatertech.plot.LightingStand", "Count"),
                (Integer) 0);

        instance.dom(draw, View.PLAN);

        assertEquals(
                (Integer) TestHelpers.accessStaticObject("com.mobiletheatertech.plot.LightingStand", "Count" ),
                (Integer) 1 );
    }

    private void domCheckPlanSymbolGeneration() throws Exception {
        NodeList defsList = draw.root().getElementsByTagName("defs");
        assertEquals(defsList.getLength(), 2);
        /* 0th baseElement is the empty defs tag generated by Batik - why? */
        Node defsNode = defsList.item(1);
        assertEquals(defsNode.getNodeType(), Node.ELEMENT_NODE);
        Element defsElement = (Element) defsNode;
        assertEquals( defsElement.getAttribute("id"), "" );

        NodeList symbolList = draw.root().getElementsByTagName("symbol");
        assertEquals(symbolList.getLength(), 1);

        Node symbolNode = symbolList.item(0);
        assertEquals(symbolNode.getNodeType(), Node.ELEMENT_NODE);
        Element symbolElement = (Element) symbolNode;
        assertEquals(symbolElement.getAttribute("id"), LightingStand.TAG );

        NodeList rectangleList = symbolElement.getElementsByTagName( "rect" );
        assertEquals(rectangleList.getLength(), 4);

        Node rectangleNode = rectangleList.item(0);
        assertEquals(rectangleNode.getNodeType(), Node.ELEMENT_NODE );
        Element rectangleElement = (Element) rectangleNode;
        assertEquals(rectangleElement.getAttribute("x"), "-1.0");
        assertEquals(rectangleElement.getAttribute("y"), "-1.0");
        assertEquals(rectangleElement.getAttribute("width"), "2.0");
        assertEquals(rectangleElement.getAttribute("height"), "12.0");

        rectangleNode = rectangleList.item(1);
        assertEquals(rectangleNode.getNodeType(), Node.ELEMENT_NODE );
        rectangleElement = (Element) rectangleNode;
        assertEquals(rectangleElement.getAttribute("x"), "-11.0");
        assertEquals(rectangleElement.getAttribute("y"), "-1.0");
        assertEquals(rectangleElement.getAttribute("width"), "12.0");
        assertEquals(rectangleElement.getAttribute("height"), "2.0");
        assertEquals(rectangleElement.getAttribute("transform"), "rotate(30.0,0.0,0.0)");

        rectangleNode = rectangleList.item(2);
        assertEquals(rectangleNode.getNodeType(), Node.ELEMENT_NODE );
        rectangleElement = (Element) rectangleNode;
        assertEquals(rectangleElement.getAttribute("x"), "-1.0");
        assertEquals(rectangleElement.getAttribute("y"), "-1.0");
        assertEquals(rectangleElement.getAttribute("width"), "12.0");
        assertEquals(rectangleElement.getAttribute("height"), "2.0");
        assertEquals(rectangleElement.getAttribute("transform"), "rotate(-30.0,0.0,0.0)");

        rectangleNode = rectangleList.item(3);
        assertEquals(rectangleNode.getNodeType(), Node.ELEMENT_NODE );
        rectangleElement = (Element) rectangleNode;
        assertEquals(rectangleElement.getAttribute("x"), "-24.0");
        assertEquals(rectangleElement.getAttribute("y"), "-1.0");
        assertEquals(rectangleElement.getAttribute("width"), "48.0");
        assertEquals(rectangleElement.getAttribute("height"), "2.0");
        assertEquals(rectangleElement.getAttribute("fill"), "white");
    }

    private void domCheckSchematicSymbolGeneration() throws Exception {
        NodeList defsList = draw.root().getElementsByTagName("defs");
        assertEquals(defsList.getLength(), 2);
        /* 0th baseElement is the empty defs tag generated by Batik - why? */
        Node defsNode = defsList.item(1);
        assertEquals(defsNode.getNodeType(), Node.ELEMENT_NODE);
        Element defsElement = (Element) defsNode;
        assertEquals( defsElement.getAttribute("id"), "" );

        NodeList symbolList = draw.root().getElementsByTagName("symbol");
        assertEquals(symbolList.getLength(), 1);

        Node symbolNode = symbolList.item(0);
        assertEquals(symbolNode.getNodeType(), Node.ELEMENT_NODE);
        Element symbolElement = (Element) symbolNode;
        assertEquals(symbolElement.getAttribute("id"), LightingStand.TAG );

        NodeList rectangleList = symbolElement.getElementsByTagName( "rect" );
        assertEquals(rectangleList.getLength(), 2);

        Node rectangleNode = rectangleList.item(0);
        assertEquals(rectangleNode.getNodeType(), Node.ELEMENT_NODE );
        Element rectangleElement = (Element) rectangleNode;
        assertEquals(rectangleElement.getAttribute("x"), "-1.0");
        assertEquals(rectangleElement.getAttribute("y"), "-1.0");
        assertEquals(rectangleElement.getAttribute("width"), "2.0");
        assertEquals(rectangleElement.getAttribute("height"), "24.0");

        rectangleNode = rectangleList.item(1);
        assertEquals(rectangleNode.getNodeType(), Node.ELEMENT_NODE );
        rectangleElement = (Element) rectangleNode;
        assertEquals(rectangleElement.getAttribute("x"), "-24.0");
        assertEquals(rectangleElement.getAttribute("y"), "-1.0");
        assertEquals(rectangleElement.getAttribute("width"), "48.0");
        assertEquals(rectangleElement.getAttribute("height"), "2.0");
        assertEquals(rectangleElement.getAttribute("fill"), "white");
    }

//    Then test the legend stuff - register and uses symbol to draw

    @Test
    public void domFirstGeneratesPlanSymbol() throws Exception {
        draw.establishRoot();
        LightingStand instance = new LightingStand( element );

        instance.dom(draw, View.PLAN);

        domCheckPlanSymbolGeneration();
    }

    @Test
    public void domFirstGeneratesSchematicSymbol() throws Exception {
        draw.establishRoot();
        LightingStand instance = new LightingStand( element );

        instance.dom(draw, View.SCHEMATIC);

        domCheckSchematicSymbolGeneration();
    }

    @Test
    public void domSecondPlanDoesNothingMore() throws Exception {
        draw.establishRoot();
        LightingStand instance1 = new LightingStand( element );
        LightingStand instance2 = new LightingStand( element2 );

        instance1.dom(draw, View.PLAN);
        instance2.dom(draw, View.PLAN);

        domCheckPlanSymbolGeneration();
    }

    @Test
    public void domSecondSchematicDoesNothingMore() throws Exception {
        draw.establishRoot();
        LightingStand instance1 = new LightingStand( element );
        LightingStand instance2 = new LightingStand( element2 );

        instance1.dom(draw, View.SCHEMATIC);
        instance2.dom(draw, View.SCHEMATIC);

        domCheckSchematicSymbolGeneration();
    }

    @Test
    public void domPlan() throws Exception {
        draw.establishRoot();
        LightingStand instance = new LightingStand( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        instance.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), LightingStand.TAG);

        NodeList list = groupElement.getElementsByTagName("use");
        assertEquals(list.getLength(), 1);

        Node node = list.item( 0 );
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("xlink:href"), "#"+LightingStand.TAG);
        Double thisX = new Double( element.getAttribute("x") );
        assertEquals( thisX, x );
        Double thisY = new Double( element.getAttribute("y") );
        assertEquals( thisY, y );
    }

    @Test
    public void domSchematicTwiceSetsPostion() throws Exception {
        draw.establishRoot();
        LightingStand instance1 = new LightingStand( element );
        LightingStand instance2 = new LightingStand( element2 );

        instance1.dom(draw, View.SCHEMATIC);
        instance2.dom(draw, View.SCHEMATIC);

//        assertEquals( instance1.schematicPosition(),
//                new PagePoint( Schematic.FirstX, Schematic.FirstY ));
//        assertEquals( instance2.schematicPosition(),
//                new PagePoint( Schematic.FirstX * 3, Schematic.FirstY ));
        fail();
    }

    @Test
    public void domSchematicTwice() throws Exception {
        draw.establishRoot();
        LightingStand instance1 = new LightingStand( element );
        LightingStand instance2 = new LightingStand( element2 );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        instance1.dom(draw, View.SCHEMATIC);
        instance2.dom(draw, View.SCHEMATIC);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 3);

        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), LightingStand.TAG);
        NodeList list = groupElement.getElementsByTagName("use");
        assertEquals(list.getLength(), 1);
        Node node = list.item( 0 );
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("xlink:href"), "#"+LightingStand.TAG);
        Double thisX = new Double( element.getAttribute("x") );
//        assertEquals( thisX, Schematic.FirstX );
        fail();

//
//        Double thisY = new Double( element.getAttribute("y") );
//        assertEquals( thisY, Schematic.FirstY );
//
//        groupNode = group.item(2);
//        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
//        groupElement = (Element) groupNode;
//        assertEquals(groupElement.getAttribute("class"), LightingStand.TAG);
//        list = groupElement.getElementsByTagName("use");
//        assertEquals(list.getLength(), 1);
//        node = list.item( 0 );
//        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
//        element = (Element) node;
//        assertEquals(element.getAttribute("xlink:href"), "#"+LightingStand.TAG);
//        thisX = new Double( element.getAttribute("x") );
//        assertEquals( thisX, Schematic.FirstX * 3 );
//        thisY = new Double( element.getAttribute("y") );
//        assertEquals( thisY, Schematic.FirstY );
    }

    @Test
    public void domPlanRotated() throws Exception {
        SvgElement.Offset(3.8, 9.2);
        element.setAttribute("orientation", orientation.toString());
        draw.establishRoot();
        LightingStand instance = new LightingStand( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        instance.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), LightingStand.TAG);

        NodeList list = groupElement.getElementsByTagName("use");
        assertEquals(list.getLength(), 1);

        Node node = list.item( 0 );
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("xlink:href"), "#"+LightingStand.TAG);
        Double thisX = new Double( element.getAttribute("x") );
        assertEquals( thisX, x + SvgElement.OffsetX() );
        Double thisY = new Double( element.getAttribute("y") );
        assertEquals( thisY, y + SvgElement.OffsetY() );
        thisX = x+SvgElement.OffsetX();
        thisY = y+SvgElement.OffsetY();
        assertEquals(element.getAttribute("transform"), "rotate(-45.0,"+thisX+","+thisY+")");
    }

    @Test
    public void domTruss() throws Exception {
        draw.establishRoot();
        LightingStand instance = new LightingStand( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        instance.dom(draw, View.TRUSS);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 1);
    }

    @Test
    public void domSection() throws Exception {
        draw.establishRoot();
        LightingStand instance = new LightingStand( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        instance.dom(draw, View.SECTION);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 1);
    }

    @Test
    public void domFront() throws Exception {
        draw.establishRoot();
        LightingStand instance = new LightingStand( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        instance.dom(draw, View.FRONT);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 1);
    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.MountableReset();
        TestResets.LightingStandReset();
        SvgElement.Offset(0.0, 0.0);
        TestResets.LightingStandReset();
//        Schematic.CountX = 0;
//        Schematic.CountY = 1;

        element = new IIOMetadataNode("lighting-stand");
        element.setAttribute("id", id);
        element.setAttribute("x", x.toString() );
        element.setAttribute("y", y.toString() );

        element2 = new IIOMetadataNode("lighting-stand");
        element2.setAttribute("id", id2 );
        element2.setAttribute("x", x.toString() );
        element2.setAttribute("y", y.toString() );

        draw = new Draw();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
