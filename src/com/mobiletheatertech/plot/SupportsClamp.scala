package com.mobiletheatertech.plot


import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer


/**
  * Created by DHS on 7/27/16.
  */
trait SupportsClamp {

  val IsClampList = new ArrayBuffer[ IsClamp ]

  @throws[MountingException]
  def hang( luminaire: IsClamp, location: Double ): Unit = {
    if( null == luminaire )
      throw new DataException( "mounted element unexpectedly null!" )

    if ( location < minLocation || maxLocation < location ) {
      println( "minLocation: " + minLocation.toString )
      println( "location: " + location.toString )
      println( "maxLocation: " + maxLocation.toString )
      throw new MountingException( "location outside of permissible range." )
    }

    IsClampList += luminaire

    luminaire.position( new Point( 1.2, 3.4, 5.6 ) )
  }

  def loads: Array[ IsClamp ] = {
    return IsClampList.toArray
  }

  def contains( item: Luminaire ): Boolean = {
    IsClampList contains item
  }

  def origin(): Point = ???

  def orientation(): Double = ???

  def minLocation: Double = ???

  def maxLocation: Double = ???

  def rotatedLocation( location: String ): Place = ???

//  def weights( id: String ): String = {
//    val text: StringBuilder = new StringBuilder
//    text.append("Weights for ")
//    text.append(id)
//    text.append("\n\n")
////    text.append(suspensionPoints)
////    text.append("\n\n")
//    var totalWeight: Double = 0.0
//    for (lumi <- loads) {
////      text.append(lumi.unit)
////      text.append(": ")
////      text.append(lumi.`type`)
////      text.append(" at ")
////      text.append(lumi.locationValue)
////      text.append(" weighs ")
////      val weight: Double = lumi.weight
////      totalWeight += weight
////      text.append(weight.toString)
//      text.append(" pounds.")
//      text.append("\n")
//    }
//    text.append("\nTotal: ")
//    text.append(totalWeight.toString)
//    text.append(" pounds")
//    text.append("\n")
//    return text.toString
//  }
}

object SupportsClamp {

  @throws[MountingException]
  def Select(id: String): SupportsClamp = {
    for ( selection <- ElementalLister.List() ) {
      if ( null != selection.id )
        if ( selection.id.equals( id ) )
          if ( selection.isInstanceOf[SupportsClamp] )
            return selection.asInstanceOf[SupportsClamp]
    }
    null
  }

}