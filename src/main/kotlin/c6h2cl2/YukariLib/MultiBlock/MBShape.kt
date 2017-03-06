/*package c6h2cl2.YukariLib.MultiBlock

/**
 * @author C6H2Cl2
 */
class MBShape(vararg shape: Array<LongArray>) {
    private val shapeArray: Array<LongArray> = Array(shape[0].size, { i -> LongArray(shape[0][0].size) })

    init {
        val size = shape[0].size
        val size_ = shape[0][0].size
        if (!shape.all { it.size == size && it.all { it.size == size_ } }){
            throw IllegalArgumentException("Shape arrays must same size")
        }
        shape.forEach { it.forEach { if (it.filter { it != 0L && it != 1L }.isNotEmpty()) throw IllegalArgumentException("All values must 0 or 1") } }
    }
}*/