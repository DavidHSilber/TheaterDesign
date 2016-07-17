/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author dhs
 * @since 0.0.5
 */

// Should extend ElementalLister rather than MinderDom

public class Suspend extends MinderDom {

    private String refId = null;
    private Integer distance = null;
    private HangPoint hangPoint = null;
    //    private Truss truss = null;        // Should reference Truss which is suspended by this.
    private String processedMark = null;
    private Double load = 0.0;
    private Double location = 0.0;


    public Suspend( Element element )
            throws AttributeMissingException, DataException, InvalidXMLException,
            ReferenceException
    {
        super( element );

        refId = getStringAttribute(element, "ref");
        distance = getIntegerAttribute(element, "distance");

        hangPoint = HangPoint.Find( refId );
        if (null == hangPoint) {
            throw new ReferenceException(
                    "Cannot suspend from unknown hangpoint ref " + refId + "." );
        }

        processedMark = Mark.Generate();
        element.setAttribute( "processedMark", processedMark );
    }

    /**
     * Find a {@code Suspend}
     *
     * @param mark string to match while searching for a {@code Suspend}
     * @return {@code Suspend} whose mark matches specified string
     */
    // Copied from Truss - refactor to Minder?
    public static Suspend Find( String mark ) {
        for (ElementalLister thingy : ElementalLister.List()) {
            if (Suspend.class.isInstance( thingy )) {
                if (((Suspend) thingy).processedMark.equals( mark )) {
                    return (Suspend) thingy;
                }
            }
        }
        return null;
    }

    public Point locate() throws ReferenceException {
        Point location = hangPoint.locate();
        return new Point(
                location.x(),
                location.y(),
                location.z() - distance );
    }

    public String ref() {
        return refId;
    }

    public void location( Double location ) {
        this.location = location;
    }

    public Double location() {
        return location;
    }

    @Override
    public void verify() {
    }

    public void load( Double increment ) {
        load += increment;
    }

    public Double load() {
        return load;
    }

    public String refId() {
        return refId;
    }

    @Override
    public void dom( Draw draw, View mode ) {
//        switch (mode) {
//            case TRUSS:
//                return;
//        }
    }

    public String toString() {
      return "Suspend - refId: "+refId+" distance: "+distance+" processedMark: "+processedMark+" hangpoint: "+hangPoint+".";
    }

}
