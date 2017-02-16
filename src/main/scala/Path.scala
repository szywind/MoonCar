/**
  * Created by 1 on 2/14/17.
  */
class Path(_start: Point, _end: Point, _v: List[Double], _angle: List[Double]) {
    // 起点
    def start: Point = _start
    // 终点
    def end: Point = _end
    // 每一步的速度增量
    def d_v: List[Double] = _v
    // 每一步的角度增量
    def d_angel: List[Double] = _angle

    def this(_start: List[Double], _end: List[Double], _v: List[Double], _angle: List[Double]) = {
        this(new Point(_start), new Point(_end), _v, _angle)
    }
}
