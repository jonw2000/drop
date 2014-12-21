package com.nine.drop

import javax.ws.rs.{Produces, GET, Path}

import scala.util.Random
import java.util.{List => JavaList}
import scala.collection.JavaConversions._

@Path("/scaladata")
class ScalaDataResource {

  @GET
  @Produces(Array("application/json"))
  def data: JavaList[Double] = randomDoubles.take(100).toList

  private def randomDoubles: Stream[Double] = Random.nextDouble() #:: randomDoubles
}
