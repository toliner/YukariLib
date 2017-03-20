package c6h2cl2.YukariLib.Util

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos.MutableBlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

/**
 * @author C6H2Cl2
 */

class BlockPosEx(x: Int, y: Int, z: Int) : MutableBlockPos(x, y, z) {
    companion object{
        @JvmStatic
        val Empty = BlockPosEx(0,0,0)
        @JvmStatic
        val Max_Pos = BlockPosEx(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)
        @JvmStatic
        val Min_Pos = BlockPosEx(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)
    }

    //Utils
    fun getTileEntityFromPos(world: IBlockAccess) = world.getTileEntity(this)

    fun getBlockFromPos(world: World): Block = world.getBlockState(this).block

    fun getBlockState(world: World): IBlockState = world.getBlockState(this)

    fun writeToNBT(tagCompound: NBTTagCompound): NBTTagCompound = writeToNBT(tagCompound, "BlockPos")

    fun writeToNBT(tagCompound: NBTTagCompound, name: String): NBTTagCompound {
        val tag: NBTTagCompound = NBTTagCompound()
        tag.setInteger("x", x)
        tag.setInteger("y", y)
        tag.setInteger("z", z)
        tagCompound.setTag(name, tag)
        return tagCompound
    }

    fun readFromNBT(tagCompound: NBTTagCompound): BlockPosEx = readFromNBT(tagCompound, "BlockPos")

    fun readFromNBT(tagCompound: NBTTagCompound, name: String): BlockPosEx {
        val tag = tagCompound.getCompoundTag(name)
        return BlockPosEx(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"))
    }

    fun searchBlock(pos: BlockPosEx, block: Block, world: World): List<BlockPosEx> {
        val targets = arrayListOf<BlockPosEx>()
        return rangeTo(pos)
                .filter {
                    val b = it.getBlockFromPos(world)
                    b == block || b === block
                }
    }

    //Operators
    operator fun plus(pos: BlockPosEx) = BlockPosEx(x + pos.x, y + pos.y, z + pos.z)

    operator fun plusAssign(pos: BlockPosEx) {
        add(pos.x, pos.y, pos.z)
    }

    operator fun minus(pos: BlockPosEx) = BlockPosEx(x - pos.x, y - pos.y, z - pos.z)

    operator fun minusAssign(pos: BlockPosEx) {
        add(-pos.x, -pos.y, -pos.z)
    }

    operator fun times(value: Int) = BlockPosEx(x * value, y * value, z * value)

    operator fun rangeTo(toPos: BlockPosEx): List<BlockPosEx> {
        val list = ArrayList<BlockPosEx>().toMutableList()
        for (i in minOf(x, toPos.x)..maxOf(x, toPos.x)) {
            for (j in minOf(y, toPos.y)..maxOf(y, toPos.y)) {
                (minOf(z, toPos.z)..maxOf(z, toPos.z)).mapTo(list) { BlockPosEx(i, j, it) }
            }
        }
        return list.toList()
    }

    operator fun get(type: Char): Int {
        return when (type) {
            'x' -> x
            'y' -> y
            'z' -> z
            else -> 0
        }
    }
}