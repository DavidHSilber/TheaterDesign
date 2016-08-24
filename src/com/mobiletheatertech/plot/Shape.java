package com.mobiletheatertech.plot;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 12/6/13 Time: 3:40 PM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.21
 */


import org.w3c.dom.Element;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Represents an area in space.
 * <p/>
 * Provides a method to determine if a rectangle fits entirely withing the {@code Shape}.
 */
public class Shape extends Elemental {
    final static String Tag = "shape";

    private Polygon polygon = null;
    private int[] xPoints;
    private int[] yPoints;
    private Double radius = null;

    private ArrayList<Point> vertices = new ArrayList<>();

    public Shape( Element element )  throws DataException, InvalidXMLException {
        super( element );

        String prototype = getOptionalStringAttributeOrNull( "polygon" );
        String circle = getOptionalStringAttributeOrNull( "circle" );

        if( null != prototype ) {
            String[] numbers = prototype.split("\\s+");
            int size = numbers.length;
            int pointCount = size / 2;

            xPoints = new int[pointCount];
            yPoints = new int[pointCount];

            // Need at least three points to make a shape.
            if (6 > size) {
                throw new DataException("Invalid Shape specification.");
            }

            for (int index = 0, pointIndex = 0; index < size; ) {
                if (index + 1 >= size) {
                    throw new DataException("Invalid Shape specification.");
                }

                Integer x = Integer.valueOf(numbers[index]);
                xPoints[pointIndex] = x;
                index++;

                Integer y = Integer.valueOf(numbers[index]);
                yPoints[pointIndex] = y;
                index++;

                pointIndex++;
            }
            polygon = new Polygon(xPoints, yPoints, pointCount);
        }
        else if ( null != circle ) {
            radius = new Double( circle );
        }
        else {
            throw new InvalidXMLException("Empty Shape specification.");
        }

    }

    double x() {
        Rectangle2D rectangle = polygon.getBounds2D();

        return rectangle.getX();
    }

    double y() {
        Rectangle2D rectangle = polygon.getBounds2D();

        return rectangle.getY();
    }

    double width() {
        Rectangle2D rectangle = polygon.getBounds2D();

        return rectangle.getWidth();
    }

    double depth() {
        Rectangle2D rectangle = polygon.getBounds2D();

        return rectangle.getHeight();
    }

    SvgElement toSvg( SvgElement parent, Draw draw, Double centerX, Double centerY )
    {
        String color = parent.attribute( "stroke" );
        if( null != radius )
        {
            if( Proscenium.Active() ) {
                centerX = Proscenium.Origin().x() + centerX;
                centerY = Proscenium.Origin().y() - centerY;
            }
            return parent.circle( draw, centerX, centerY, radius, color );
        }
        else
        {
            StringBuilder path = new StringBuilder("M ");

            for (int index = 0; index < xPoints.length; index++) {
                path.append( Proscenium.LocateIfActivePathString(
                        centerX + xPoints[index], centerY + yPoints[index] ) );
            }

            path.append("Z");

            return parent.path( draw, path.toString(), color );
        }
    }

    Boolean fits(double x, double y, double width, double depth) {
        return polygon.contains(x, y, width, depth );
    }
}
