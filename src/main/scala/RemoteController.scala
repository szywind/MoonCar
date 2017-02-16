import java.util
import scala.concurrent.duration._

import akka.actor.Actor

/**
  * Created by 1 on 2/13/17.
  */

case class onCreate(id: Int, cur: Point, end: Point, velocity: Double, angle: Double)
case class onRotation(id: Int, r_angle: Double)
case class onStatus(id: Int, cur: Point, end: Point, velocity: Double, angle: Double)
case class onShow()

class RemoteController extends Actor {
    import context._
    private val status = scala.collection.mutable.Map[Int, Tuple3[Point, Double, Double]]()
    private val timestamp: Long = System.currentTimeMillis()

    def receive = {
        case onCreate(id: Int, cur: Point, end: Point, velocity: Double, angle: Double) => {
            status(id) = (cur, velocity, angle)
        }
        case onRotation(id: Int, r_angle: Double) => {
            // handle the event after 2s delay
            context.system.scheduler.scheduleOnce(2000 milliseconds) {
                val time = System.currentTimeMillis() - timestamp
                if (math.abs(r_angle) > 1e-6) {
                    println("\n[" + time.toString() + "ms]" + "月球车" + id.toString() + "遇到障碍物旋转" + r_angle.toString())
                }
            }
        }
        case onStatus(id: Int, cur: Point, end: Point, velocity: Double, angle: Double) => {
            // handle the event after 2s delay
            context.system.scheduler.scheduleOnce(2000 milliseconds) {
                status(id) = (cur, velocity, angle)
            }
        }
        case onShow() => {
            showStatus()
        }

        case _ => println("null")
    }

    private def showStatus(): Unit = {
        val time = System.currentTimeMillis() - timestamp

        println("\n[" + time.toString() + "ms]")
        for ((id, state) <- status) {
            println("月球车" + id.toString() + "状态：")
            println("\t当前位置" + state._1.toString() + ", 预测位置" + state._1.move(state._2, state._3).toString() +
                ", 方向" + state._3.toString())
        }
    }
}
