import akka.actor.ActorRef

/**
  * Created by 1 on 2/13/17.
  */
class MoonCar(_id: Int, _cur: Point, _end: Point, _v: Double, _angle: Double, _callback: ActorRef){
    def id = _id
    var cur = _cur
    def end = _end
    var v = _v
    var angle = _angle
    def callback = _callback
    sendOnCreate()

    def done() = {
        cur == end
    }
    def move() = {
        cur = cur.move(v, angle)
    }

    def rotate(r_angle: Double) = {
        angle += r_angle
        sendOnRotation(r_angle)

    }

    def accelerate(d_v: Double) = {
        v += d_v
    }

    def update(d_v: Double, r_angle: Double) = {
        rotate(r_angle)
        accelerate(d_v)
        move()
        sendOnStatusChange()
    }

    // callback: report initialization
    def sendOnCreate(): Unit = {
        callback ! onCreate(id, cur, end, v, angle)
    }

    // callback: report status change
    def sendOnStatusChange(): Unit = {
        callback ! onStatus(id, cur, end, v, angle)
    }

    // callback: report rotation
    def sendOnRotation(r_angle: Double): Unit = {
        callback ! onRotation(id, r_angle)
    }

}
