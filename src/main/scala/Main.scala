import java.io.File
import java.util

import akka.actor.{ActorSystem, Props}

import scala.concurrent.duration._
import com.google.gson.Gson
import java.util.{LinkedHashMap, Map => JMap}
import scala.collection.JavaConverters._

import scala.io.Source
/**
  * Created by 1 on 2/14/17.
  */
object Main extends App{
    override def main(args: Array[String]): Unit ={
        val system = ActorSystem("MoonCar")
        import system.dispatcher

        // default actor constructor
        val remoteController = system.actorOf(Props[RemoteController], name = "RemoteController")

        var done = false

        // read data
        val jsonString = Source.fromFile(Main.getClass.getClassLoader.getResource("paths.json").getPath).getLines().mkString
        val gson = new Gson()
        val raw = gson.fromJson(jsonString, (new util.HashMap[String, Object]()).getClass)
        val ncar = raw.get("ncar").toString.toDouble.toInt
        val T = raw.get("steps").toString.toDouble.toInt
        val pathArray = gson.fromJson(raw.get("path").toString, (new Array[util.HashMap[String, Object]](ncar)).getClass)

        val paths = scala.collection.mutable.Map[Int, Path]()
        for(path <- pathArray){
            val id = path.get("id").toString.toDouble.toInt
            val start: List[Double] = path.get("start").asInstanceOf[util.ArrayList[Double]].asScala.toList
            val end: List[Double] = path.get("end").asInstanceOf[util.ArrayList[Double]].asScala.toList
            val d_v: List[Double] = path.get("d_velocity").asInstanceOf[util.ArrayList[Double]].asScala.toList
            val d_angle: List[Double] = path.get("d_angle").asInstanceOf[util.ArrayList[Double]].asScala.toList
            paths += (id -> new Path(start, end, d_v, d_angle))
        }


        // initialize car position,
        var mooncars = scala.collection.mutable.MutableList[MoonCar]()
        for(path <- paths) {
            mooncars += new MoonCar(path._1, path._2.start, path._2.end, 0, 0, remoteController)
        }

        // dump status every 500 ms
        val cancellable =
            system.scheduler.schedule(0 milliseconds,
                500 milliseconds,
                remoteController,
                onShow())

//        system.scheduler.scheduleOnce(500 milliseconds){
//            remoteController ! onShow()
//        }
//        // define a scala runnable
//        class MyThread extends Runnable {
//            def run {
//                while(true) {
//                    // your custom behavior here
//                    remoteController ! onShow()
//                }
//            }
//        }
//
//        // start your runnable thread somewhere later in the code
//        var thread = new Thread(new MyThread)
//        thread.start

        // simulate car transition
        var t = 0
        while(!done && t < T) {
            done = true
            mooncars.foreach(mooncar => {
                mooncar.update(paths(mooncar.id).d_v(t), paths(mooncar.id).d_angel(t))
                done &&= mooncar.done()
            })
            Thread.sleep(1000)
            t += 1
        }

        // waiting for the info of the last step
        Thread.sleep(10000)
        cancellable.cancel()
//        thread.interrupt()
    }
}
