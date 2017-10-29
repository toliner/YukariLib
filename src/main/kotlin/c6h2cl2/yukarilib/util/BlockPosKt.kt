package c6h2cl2.yukarilib.util

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos

/**
 * @author C6H2Cl2
 */

@Suppress("UNUSED")
open class BlockPosKt(x: Int, y: Int, z: Int) : BlockPos(x, y, z) {
    fun writeToNBT(tagCompound: NBTTagCompound): NBTTagCompound = writeToNBT(tagCompound, "BlockPos")

    fun writeToNBT(tagCompound: NBTTagCompound, name: String): NBTTagCompound {
        val tag: NBTTagCompound = NBTTagCompound()
        tag.setInteger("x", x)
        tag.setInteger("y", y)
        tag.setInteger("z", z)
        tagCompound.setTag(name, tag)
        return tagCompound
    }

    fun readFromNBT(tagCompound: NBTTagCompound): BlockPosKt = readFromNBT(tagCompound, "BlockPos")

    fun readFromNBT(tagCompound: NBTTagCompound, name: String): BlockPosKt {
        val tag = tagCompound.getCompoundTag(name)
        return BlockPosKt(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"))
    }

    operator fun plus(other: BlockPos) = BlockPosKt(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: BlockPos) = BlockPosKt(x - other.x, y - other.y, z - other.z)

    operator fun rangeTo(other: BlockPosKt): BlockPosRange {
        val flag = this <= other
        (0..5 step 4)
        return BlockPosRange(if (flag) this else other, if (flag) other else this)
    }

    override infix fun up(n: Int) = BlockPosKt(x, y + n, z)

    override infix fun down(n: Int) = BlockPosKt(x, y - n, z)

    override infix fun north(n: Int) = BlockPosKt(x, y, z - n)

    override infix fun south(n: Int) = BlockPosKt(x, y, z + n)

    override infix fun east(n: Int) = BlockPosKt(x + n, y, z)

    override infix fun west(n: Int) = BlockPosKt(x - n, y, z)

    open fun toMutable() = MutableBlockPosKt(x, y, z)
}