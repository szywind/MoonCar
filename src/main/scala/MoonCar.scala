import akka.actor.ActorRef

/**
  * Created by 1 on 2/13/17.
  */
class MoonCar(_id: Int, _cur: Point, _end: Point, _v: Double, _angle: Double, _callback: ActorRef){
    // 车辆编号
    def id = _id
    // 当前位置
    var cur = _cur
    // 终点位置
    def end = _end
    // 速度
    var v = _v
    // 方向角（相对于+x轴，逆时针为正值）
    var angle = _angle
    // event-driven handler
    def callback = _callback

    sendOnCreate()

    def done() = {
        cur == end
    }

    // 平移
    def move() = {
        cur = cur.move(v, angle)
    }

    // 旋转
    def rotate(r_angle: Double) = {
        angle += r_angle
        sendOnRotation(r_angle)
    }

    // 变速
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
