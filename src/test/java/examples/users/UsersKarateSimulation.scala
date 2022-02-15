package examples.users

import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._
import scala.concurrent.duration._

class UsersKarateSimulation extends Simulation {

  val protocol = karateProtocol(
    "/users/{id}" -> Nil,
    "/users" -> pauseFor("get" -> 15, "post" -> 25)
  )

  protocol.nameResolver = (req, ctx) => req.getHeader("karate-name")
  protocol.runner.karateEnv("perf")

  val get = scenario("create").exec(karateFeature("classpath:examples/users/users.feature"))

  setUp(
    get.inject(rampUsers(10) during (5 seconds)).protocols(protocol),
  )

}