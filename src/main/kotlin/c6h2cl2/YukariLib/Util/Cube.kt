package c6h2cl2.YukariLib.Util

import c6h2cl2.YukariLib.Util.BlockPos
import net.minecraft.block.Block
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.AxisAlignedBB

/**
 * @author kojin15.
 */
open class Cube(private var minPosD: Pointer3D, private var maxPosD: Pointer3D) {
    companion object {
        @JvmStatic
        val empty = Cube(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        @JvmStatic
        val fullBlock = Cube(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
    }

    fun getMinPos(): Pointer3D = minPosD
    fun getMaxPos(): Pointer3D = maxPosD

    fun getMinX(): Double = minPosD.getX()
    fun getMinY(): Double = minPosD.getY()
    fun getMinZ(): Double = minPosD.getZ()
    fun getMaxX(): Double = maxPosD.getX()
    fun getMaxY(): Double = maxPosD.getY()
    fun getMaxZ(): Double = maxPosD.getZ()

    constructor(minX: Double, minY: Double, minZ: Double, maxX: Double, maxY: Double, maxZ: Double) :
            this(Pointer3D(minX, minY, minZ), Pointer3D(maxX, maxY, maxZ))

    constructor(axis: AxisAlignedBB) : this(axis.minX, axis.minY, axis.minZ, axis.maxX, axis.maxY, axis.maxZ)

    constructor(cube: Cube) : this(cube.minPosD, cube.maxPosD)

    constructor(tagCompound: NBTTagCompound, tagName: String = "Cube") : this(Pointer3D.empty, Pointer3D.empty) {
        this.readFromNBT(tagCompound, tagName)
    }

    fun copy(): Cube = Cube(minPosD, maxPosD)

    fun axisBB(): AxisAlignedBB = AxisAlignedBB.getBoundingBox(minPosD.getX(), minPosD.getY(), minPosD.getZ(), maxPosD.getX(), maxPosD.getY(), maxPosD.getZ())

    fun add(pos: Pointer3D): Cube {
        minPosD.add(pos)
        maxPosD.add(pos)
        return this
    }

    fun add(pos: BlockPos): Cube {
        minPosD.add(Pointer3D(pos))
        maxPosD.add(Pointer3D(pos))
        return this
    }

    fun delete(pos: Pointer3D): Cube {
        minPosD.delete(pos)
        maxPosD.delete(pos)
        return this
    }

    fun delete(pos: BlockPos): Cube {
        minPosD.delete(Pointer3D(pos))
        maxPosD.delete(Pointer3D(pos))
        return this
    }

    fun set(pos: Pointer3D): Cube {
        minPosD.set(pos)
        maxPosD.set(pos)
        return this
    }

    fun set(pos: BlockPos): Cube {
        minPosD.set(Pointer3D(pos))
        maxPosD.set(Pointer3D(pos))
        return this
    }

    @JvmOverloads fun readFromNBT(tagCompound: NBTTagCompound, tagName: String = "Cube"): Cube {
        val tag: NBTTagCompound = tagCompound.getCompoundTag(tagName)
        minPosD.readFromNBT(tagCompound, "minPosD")
        maxPosD.readFromNBT(tagCompound, "maxPosD")
        return this
    }

    @JvmOverloads fun writeToNBT(tagCompound: NBTTagCompound, tagName: String = "Cube"): NBTTagCompound {
        val tag = NBTTagCompound()
        minPosD.writeToNBT(tagCompound, "minPosD")
        maxPosD.writeToNBT(tagCompound, "maxPosD")
        tagCompound.setTag(tagName, tag)
        return tagCompound
    }

    fun setBlockBounds(block: Block) {
        block.setBlockBounds(minPosD.getX().toFloat(), minPosD.getY().toFloat(), minPosD.getZ().toFloat(), maxPosD.getX().toFloat(), maxPosD.getY().toFloat(), maxPosD.getZ().toFloat())
    }

}