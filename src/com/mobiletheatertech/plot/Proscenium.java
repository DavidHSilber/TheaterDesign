package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 7/30/13 Time: 1:59 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * Represents a proscenium arch.
 * <p/>
 * XML tag is 'proscenium'.
 * <p/>
 * Required attributes are:<dl> <dt>x</dt><dd>coordinate of center of proscenium with respect to
 * venue</dd> <dt>y</dt><dd>coordinate of center of proscenium with respect to venue</dd>
 * <dt>z</dt><dd>coordinate of floor level of proscenium stage with respect to venue</dd>
 * <dt>width</dt><dd>width of proscenium opening</dd> <dt>depth</dt><dd>thickness of proscenium
 * wall</dd> <dt>height</dt><dd>height of proscenium opening</dd>
 *
 * @author dhs
 * @since 0.0.7
 */
public class Proscenium extends MinderDom {

    private static boolean ACTIVE = false;
    private static Point ORIGIN = new Point( 0.0, 0.0, 0.0 );

    private Double width = null;
    private Double depth = null;
    private Double height = null;
    private Double x = null;
    private Double y = null;
    private Double z = null;

    final static String Tag = "proscenium";
//    private static final String COLOR = "black";
    static final String Color = "black";
    static final String StageColor = "black";
    static final String FadedColor = "gray";

    /**
     * True if a {@code Proscenium} has been defined. False otherwise.
     *
     * @return true or false
     */
    public static boolean Active() {
        return ACTIVE;
    }

    /**
     * Provide the origin of the coordinates used for a lighting plot.
     *
     * @return center of proscenium at the upstage side of the openng
     */
    public static Point Origin() {
        return ORIGIN;
    }

    /**
     * Provide the drawing point, given a point relative to the origin established by the {@code
     * Proscenium}.
     *
     * @param unfixed point relative to the {@code Proscenium}'s origin
     * @return the drawing point
     */
    public static Point LocateIfActive( Point unfixed ) {
        if ( Active() ) {
            return new Point(
                    ORIGIN.x() + unfixed.x(),
                    ORIGIN.y() - unfixed.y(),
                    ORIGIN.z() + unfixed.z()
            );
        }
        else {
            return unfixed;
        }

    }

    public static String LocateIfActivePathString( Double ex, Double wy ) {
        Double localX = ex;
        Double localY = wy;

        if ( Active() ) {
            localX = ORIGIN.x() + ex;
            localY = ORIGIN.y() - wy;
        }

        return localX.toString() + " " + localY.toString() + " ";
    }

    /**
     * Construct a {@code Proscenium} from an XML Element.
     * <p/>
     * Keep track of the defined origin.
     *
     * @param element DOM Element defining a proscenium
     * @throws AttributeMissingException if any attribute is missing
     * @throws InvalidXMLException       if more than one 'proscenium' element is found or if null
     *                                   element is somehow presented to constructor
     * @throws LocationException         if the proscenium would not fit in the venue
     * @throws SizeException             if any dimension is less than zero
     */
    public Proscenium(Element element)
            throws AttributeMissingException, DataException, InvalidXMLException,
            LocationException, ReferenceException, SizeException
    {
        super(element);

        if (Active()) {
            throw new InvalidXMLException("Multiple Prosceniums are not currently supported.");
        }

        width  = getDoubleAttribute( "width" );
        depth  = getDoubleAttribute( "depth" );
        height = getDoubleAttribute( "height" );
        x = getDoubleAttribute( "x" );
        y = getDoubleAttribute( "y" );
        z = getDoubleAttribute( "z" );

        if (0 >= width) throw new SizeException("Proscenium", "width");
        if (0 >= depth) throw new SizeException("Proscenium", "depth");
        if (0 >= height) throw new SizeException("Proscenium", "height");


        if (!Venue.Contains2D(new Rectangle(
                x.intValue() - width.intValue() / 2, y.intValue(),
                width.intValue(), depth.intValue()))) {
            throw new LocationException(
                    "Proscenium should not extend beyond the boundaries of the venue.");
        }
        if (0 > z) {
            throw new LocationException(
                    "Proscenium should not extend beyond the boundaries of the venue.");
        }
        if (z + height > Venue.Height()) {
            throw new LocationException(
                    "Proscenium should not extend beyond the boundaries of the venue.");
        }

        ACTIVE = true;
        ORIGIN = new Point(x, y, z);
        new Point( x, y, - z );
    }

    @Override
    public void verify() throws InvalidXMLException {
    }

//    /**
//     * @param canvas drawing media
//     */
//    @Override
//    public void drawPlan(Graphics2D canvas) throws ReferenceException {
////        canvas.setPaint( Color.BLACK );
////        canvas.draw( new Rectangle( x - width / 2, y, width, depth ) );
//
//        // Draw CenterLine
//        float[] dashPattern = {6, 2, 3, 2};
//        canvas.setStroke(
//                new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, dashPattern,
//                        0));
//        canvas.draw(new Line2D.Float(x, 0, x, Venue.Depth()));
//        canvas.setStroke(new BasicStroke(1));
//    }
//
//    /**
//     * @param canvas drawing media
//     */
//    @Override
//    public void drawSection(Graphics2D canvas) throws ReferenceException {
//        int bottom = Venue.Height();
//        canvas.setPaint(Color.BLACK);
//        canvas.draw(new Rectangle(y, bottom - z - height, depth, height));
//    }
//
//    /**
//     * @param canvas drawing media
//     */
//    @Override
//    public void drawFront(Graphics2D canvas) throws ReferenceException {
//        int bottom = Venue.Height();
//        canvas.setPaint(Color.BLACK);
//        canvas.draw(new Rectangle(x - width / 2, bottom - z - height, width, height));
//
//        // Draw CenterLine
//        float[] dashPattern = {6, 2, 3, 2};
//        canvas.setStroke(
//                new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, dashPattern,
//                        0));
//        canvas.draw(new Line2D.Float(x, 0, x, Venue.Height()));
//        canvas.setStroke(new BasicStroke(1));
//    }

    /**
     * Draw the proscenium.
     * <p/>
     * Currently only drawing the plan view.
     *
     * @param draw canvas/DOM manager
     * @param mode drawing mode
     */
    @Override
    public void dom(Draw draw, View mode) throws ReferenceException {
        Element element = null;
//
//        switch (mode) {
//            case PLAN:
//                element = svgRectangle( draw, x, y, width, depth, COLOR );
//                draw.appendRootChild( element );
//                break;
//            case SECTION:
//                element = svgLine( draw, y, bottom, y, bottom - z, COLOR );
//                draw.appendRootChild( element );
//                element = svgLine( draw, y, bottom - z, y + depth, bottom - z, COLOR );
//                draw.appendRootChild( element );
//                element = svgLine( draw, y + depth, bottom - z, y + depth, bottom, COLOR );
//                draw.appendRootChild( element );
//                break;
//            case FRONT:
//                element = svgLine( draw, x, bottom, x, bottom - z, COLOR );
//                draw.appendRootChild( element );
//                element = svgLine( draw, x, bottom - z, x + width, bottom - z, COLOR );
//                draw.appendRootChild( element );
//                element = svgLine( draw, x + width, bottom - z, x + width, bottom, COLOR );
//                draw.appendRootChild( element );
//                break;
//            case TRUSS:
//                break;
//        }

        switch (mode) {
            case TRUSS:
                return;
            case PLAN:
                domPlan(draw);
                return;
            case SECTION:
                domSection(draw);
                return;
        }
    }

    private void domPlan(Draw draw) {
        Double startX = x - width / 2;
        Double startY = y;
        Double endX = x + width / 2;
        Double endY = y + depth;

        // SR end of proscenium arch
        draw.line( draw, startX, startY, startX, endY, Color );

        // SL end of proscenium arch
        draw.line( draw, endX, startY, endX, endY, Color );

        // US side of proscenium arch
        SvgElement line = draw.line( draw, startX, startY, endX, startY, FadedColor );
        line.attribute("stroke-opacity", "0.3");

        // DS side of proscenium arch
        line = draw.line( draw, startX, endY, endX, endY, FadedColor );
        line.attribute("stroke-opacity", "0.1");
    }

    void domSection( Draw draw ) throws ReferenceException {
        Double zee = Venue.Height();
        SvgElement prosceniumWall = draw.line( draw, y, zee, y, zee - Venue.Height(), FadedColor );
        Double front = y + depth;
        SvgElement frontWall = draw.line( draw, front, zee, front, zee - Venue.Height(), FadedColor );
        Double top = zee - height;
        SvgElement archTop = draw.line( draw, y, top, front, top, FadedColor );
        SvgElement stage = draw.line( draw, 0.0, zee, y, zee, StageColor );
        stage.attribute( "stroke-width", "2" );
    }

    /**
     * Describe this {@code Proscenium}.
     *
     * @return textual description
     */
    @Override
    public String toString() {
        return "Proscenium: " + width + " by " + height + " by " + depth + " at (" + x + "," + y +
                "," + z + ")";
    }
}

