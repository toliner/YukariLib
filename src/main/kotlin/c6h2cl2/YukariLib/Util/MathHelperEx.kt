package c6h2cl2.YukariLib.Util

/**
 * @author kojin15.
 */
object MathHelperEx {

    fun betweenIn(min: Int, x: Int, max: Int): Boolean {
        return min <= x && x <= max
    }

    fun betweenIn(min: Double, x: Double, max: Double): Boolean {
        return min <= x && x <= max
    }

    fun betweenOut(min: Int, x: Int, max: Int): Boolean {
        return min < x && x < max
    }

    fun betweenOut(min: Double, x: Double, max: Double): Boolean {
        return min < x && x < max
    }
}