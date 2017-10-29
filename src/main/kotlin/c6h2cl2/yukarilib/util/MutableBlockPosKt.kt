package c6h2cl2.yukarilib.util

/**
 * @author C6H2Cl2
 */
@Suppress("UNUSED")
class MutableBlockPosKt(@JvmField var x: Int = 0, @JvmField var y: Int = 0,@JvmField var z: Int = 0): BlockPosKt(x, y, z) {
    override fun getX() = x
    override fun getY() = y
    override fun getZ() = z

    override fun toMutable(): MutableBlockPosKt {
        return this
    }

    override fun toImmutable(): BlockPosKt {
        return BlockPosKt(x, y, z)
    }

    fun setPos(x: Int, y: Int, z: Int): MutableBlockPosKt {
        this.x = x
        this.y = y
        this.z = z
        return this
    }

    infix fun moveUp(n: Int): MutableBlockPosKt {
        this.y += n
        return this
    }

    infix fun moveDown(n: Int): MutableBlockPosKt {
        this.y -= n
        return this
    }

    infix fun moveNorth(n: Int): MutableBlockPosKt {
        this.z -= n
        return this
    }

    infix fun moveSouth(n: Int): MutableBlockPosKt {
        this.z += n
        return this
    }

    infix fun moveEast(n: Int): MutableBlockPosKt {
        this.x += n
        return this
    }

    infix fun moveWest(n: Int): MutableBlockPosKt {
        this.x -= n
        return this
    }
}