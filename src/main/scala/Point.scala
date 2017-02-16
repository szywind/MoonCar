/**
  * Created by 1 on 2/14/17.
  */
class Point(_x: Double, _y: Double) {
    def x = _x
    def y = _y

    def this(l: List[Double]) = this(l(0), l(1))

    def move(v: Double, angle: Double) =
        new Point(x + v*math.cos(angle.toRadians), y + v*math.sin(angle.toRadians))

    override def toString: String = {
        "(" + x.toString() + "," + y.toString() + ")"
    }

    def == (that: Point): Boolean = {
        if(math.abs(this.x - that.x) < 1e-6) math.abs(this.y-that.y) < 1e-6 else false
    }

    def * (scalar: Double): Point = {
        new Point(x*scalar, y*scalar)
    }
}
