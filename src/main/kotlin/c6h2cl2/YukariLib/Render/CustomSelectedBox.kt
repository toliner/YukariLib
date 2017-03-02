package c6h2cl2.YukariLib.Render

/**
 * @author kojin15.
 */
class CustomSelectedBox(var middleHeight: Double, var middleWidth: Double, var middleDepth: Double, var minX: Double, var minY: Double, var minZ: Double) {

    var drawSide = booleanArrayOf(false, false, false, false, false, false)
    var sideLength = doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

    /**
     * 指定した側面を描画するかどうか
     */
    fun drawSide(side: Int, draw: Boolean): CustomSelectedBox {
        if (drawSide.size > side) drawSide[side] = draw
        return this
    }

    /**
     * 指定した側面の方向に四角形を伸ばす
     */
    fun setSideLength(side: Int, length: Double): CustomSelectedBox {
        if (sideLength.size > side) sideLength[side] = length
        return this
    }

    /**
     * 指定した値の分だけ広げる
     */
    fun addExtraSpace(space: Double): CustomSelectedBox {
        this.minX -= space
        this.minY -= space
        this.minZ -= space
        this.middleDepth += space
        this.middleHeight += space
        this.middleWidth += space
        for (i in 0..5) {
            sideLength[i] += space
        }
        return this
    }

    fun offsetForDraw(x: Double, y: Double, z: Double): CustomSelectedBox {
        this.minX += x
        this.minY += y
        this.minZ += z
        return this
    }

}