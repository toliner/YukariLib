@file:Suppress("UNUSED")

package c6h2cl2.YukariLib.Util

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.ChunkCoordIntPair
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraft.world.chunk.Chunk
import net.minecraftforge.common.util.ForgeDirection.*
import net.minecraftforge.common.util.ForgeDirection
import kotlin.comparisons.maxOf
import kotlin.comparisons.minOf

/**
 * @author C6H2Cl2
 */
data class BlockPos(private var x: Int, private var y: Int, private var z: Int) {
    companion object {
        @JvmStatic
        val Empty = BlockPos(0, 0, 0)
        @JvmStatic
        val Max_Pos = BlockPos(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)
        @JvmStatic
        val Min_Pos = BlockPos(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)
    }

    constructor(nbtTagCompound: NBTTagCompound, name: String = "blockPos") : this(0, 0, 0) {
        readFromNBT(nbtTagCompound, name)
    }

    // Directions
    val down: BlockPos
        get() = BlockPos(x, y - 1, z)

    val up: BlockPos
        get() = BlockPos(x, y + 1, z)

    val north: BlockPos
        get() = BlockPos(x, y, z - 1)

    val south: BlockPos
        get() = BlockPos(x, y, z + 1)

    val west: BlockPos
        get() = BlockPos(x - 1, y, z)

    val east: BlockPos
        get() = BlockPos(x + 1, y, z)

    infix fun down(value: Int) = BlockPos(x, y - value, z)

    infix fun up(value: Int) = BlockPos(x, y + value, z)

    infix fun north(value: Int) = BlockPos(x, y, z - value)

    infix fun south(value: Int) = BlockPos(x, y, z + value)

    infix fun west(value: Int) = BlockPos(x - value, y, z)

    infix fun east(value: Int) = BlockPos(x + value, y, z)

    //Utils
    fun getTileEntityFromPos(world: IBlockAccess): TileEntity? = world.getTileEntity(x, y, z)

    fun getBlockFromPos(world: World): Block = world.getBlock(x, y, z)

    fun getMetaFromPos(world: World): Int = world.getBlockMetadata(x, y, z)

    fun getChunk(world: World): Chunk = world.getChunkFromBlockCoords(x, z)

    fun getChunkCoordIntPair(world: World): ChunkCoordIntPair = getChunk(world).chunkCoordIntPair

    fun getDistance(posFrom: BlockPos, posTo: BlockPos): Double {
        return Math.sqrt(Math.pow((posFrom.x - posTo.x).toDouble(), 2.0) + Math.pow((posFrom.y - posTo.y).toDouble(), 2.0) + Math.pow((posFrom.z - posTo.z).toDouble(), 2.0))
    }

    fun getDistance(pos: BlockPos): Double = getDistance(this, pos)

    infix fun distance(pos: BlockPos): Double = getDistance(this, pos)

    fun getBlockDirection(tilePos: BlockPos): ForgeDirection {
        if (up == tilePos) {
            return ForgeDirection.UP
        } else if (down == tilePos) {
            return ForgeDirection.DOWN
        } else if (north == tilePos) {
            return ForgeDirection.NORTH
        } else if (south == tilePos) {
            return ForgeDirection.SOUTH
        } else if (east == tilePos) {
            return ForgeDirection.EAST
        } else if (west == tilePos) {
            return ForgeDirection.WEST
        } else {
            return ForgeDirection.UNKNOWN
        }
    }

    fun getPosForDirection(direction: ForgeDirection): BlockPos {
        return when (direction) {
            DOWN -> down
            UP -> up
            NORTH -> north
            SOUTH -> south
            EAST -> east
            WEST -> west
            UNKNOWN -> this
        }
    }

    fun getReversePos(direction: ForgeDirection, range: Int = 1): BlockPos {
        return when (direction) {
            DOWN -> up(range)
            UP -> down(range)
            NORTH -> south(range)
            SOUTH -> north(range)
            EAST -> west(range)
            WEST -> east(range)
            UNKNOWN -> this
        }
    }

    fun searchBlock(pos: BlockPos, block: Block, world: World): List<BlockPos> {
        return rangeTo(pos)
                .filter {
                    val b = it.getBlockFromPos(world)
                    b == block || b === block
                }
    }

    fun aroundPos(): List<BlockPos> {
        return listOf(this.down, this.up, this.north, this.south, this.west, this.east)
    }

    /**
     * directionに何も入れないかUNKNOWNだと立方体
     * @param radius 半径
     * @param direction DOWN,UP y座標固定 NORTH,SOUTH z座標固定 EAST,WEST x座標固定
     */
    fun frame(radius: Int, direction: ForgeDirection = UNKNOWN): List<BlockPos> {
        when (direction) {
            DOWN, UP -> {
                val list = this.west(radius).north(radius)..this.east(radius).south(radius)
                val list2 = this.west(radius - 1).north(radius - 1)..this.east(radius - 1).south(radius - 1)
                return list - list2
            }
            NORTH, SOUTH -> {
                val list = this.west(radius).down(radius)..this.east(radius).up(radius)
                val list2 = this.west(radius - 1).down(radius - 1)..this.east(radius - 1).up(radius - 1)
                return list - list2
            }
            EAST, WEST -> {
                val list = this.down(radius).north(radius)..this.up(radius).south(radius)
                val list2 = this.down(radius - 1).north(radius - 1)..this.up(radius - 1).south(radius - 1)
                return list - list2
            }
            else -> {
                val list = this.west(radius).north(radius).down(radius)..this.east(radius).south(radius).up(radius)

                val list2 = this.down(radius - 1).west(radius).north(radius)..this.up(radius - 1).east(radius).south(radius)
                val list3 = this.down(radius).west(radius - 1).north(radius - 1)..this.down(radius).east(radius - 1).south(radius - 1)
                val list4 = this.up(radius).west(radius - 1).north(radius - 1)..this.up(radius).east(radius - 1).south(radius - 1)
                return list - (list2 + list3 + list4)
            }
        }
    }

    fun getBox(radius: Int): List<BlockPos> {
        return (this.down(radius).north(radius).west(radius))..(this.up(radius).south(radius).east(radius))
    }

    fun getOutBox(radius: Int): List<BlockPos> {
        val list = this.down(radius).west(radius).north(radius)..this.up(radius).east(radius).south(radius)
        val list2 = this.down(radius - 1).west(radius - 1).north(radius - 1)..this.up(radius - 1).east(radius - 1).south(radius - 1)
        return list - list2
    }

    fun getCircle(radius: Int): List<BlockPos> = getCircle(radius + 0.5)

    fun getCircle(radius: Double): List<BlockPos> {
        return getBox(radius.toInt()).filter { it.distance(this) <= radius }
    }

    fun getCircleFrame(radius: Int): List<BlockPos> = getCircleFrame(radius + 0.5)

    fun getCircleFrame(radius: Double): List<BlockPos> {
        return getBox(radius.toInt()).filter { MathHelperEx.betweenOut(radius - 1, it distance this, radius) }
    }

    fun isBlockEqual(world: World, block: Block, meta: Int = 0): Boolean {
        return getBlockFromPos(world) == block && if (Item.getItemFromBlock(block).hasSubtypes) getMetaFromPos(world) == meta else true
    }

    fun canPlaceBlock(world: World, block: Block, meta: Int): Boolean {
        if (!block.canPlaceBlockAt(world, x, y, z)) return false
        if (!block.canBlockStay(world, x, y, z)) return false
        if (!block.canReplace(world, x, y, z, meta, ItemStack(block, 1, meta))) return false
        val axis = block.getCollisionBoundingBoxFromPool(world, x, y, z)
        return world.checkNoEntityCollision(axis)
    }

    fun setBlockFromPos(world: World, block: Block, meta: Int = 0, flag: Int = 1) {
        world.setBlock(x, y, z, block, meta, flag)
    }

    fun setBlockAndSound(world: World, block: Block, meta: Int = 0, flag: Int = 1) {
        setBlockFromPos(world, block, meta, flag)
        world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0f) / 2.0f, block.stepSound.pitch * 0.8f)
    }

    fun markDirty(world: World) {
        world.markBlockForUpdate(x, y, z)
        val tile = getTileEntityFromPos(world)
        tile?.markDirty()
    }

    //NBT
    @JvmOverloads fun writeToNBT(tagCompound: NBTTagCompound, tagName: String = "blockPos"): NBTTagCompound {
        val tag = NBTTagCompound()
        tag.setInteger("x", x)
        tag.setInteger("y", y)
        tag.setInteger("z", z)
        tagCompound.setTag(tagName, tag)
        return tagCompound
    }

    @JvmOverloads fun readFromNBT(tagCompound: NBTTagCompound, tagName: String = "blockPos"): BlockPos {
        val tag = tagCompound.getCompoundTag(tagName)
        x = tag.getInteger("x")
        y = tag.getInteger("y")
        z = tag.getInteger("z")
        return this
    }

    //Getter
    fun getX() = x
    fun getY() = y
    fun getZ() = z

    //Operators
    operator fun plus(pos: BlockPos) = BlockPos(x + pos.x, y + pos.y, z + pos.z)

    operator fun plusAssign(pos: BlockPos) {
        this.x += pos.x
        this.y += pos.y
        this.z += pos.z
    }

    operator fun minus(pos: BlockPos) = BlockPos(x - pos.x, y - pos.y, z - pos.z)

    operator fun minusAssign(pos: BlockPos) {
        this.x -= pos.x
        this.y -= pos.y
        this.z -= pos.z
    }

    operator fun times(value: Int) = BlockPos(x * value, y * value, z * value)

    operator fun timesAssign(value: Int) {
        x *= value
        y *= value
        z *= value
    }

    operator fun rangeTo(toPos: BlockPos): List<BlockPos> {
        val list = ArrayList<BlockPos>().toMutableList()
        for (i in minOf(x, toPos.x)..maxOf(x, toPos.x)) {
            for (j in minOf(y, toPos.y)..maxOf(y, toPos.y)) {
                (minOf(z, toPos.z)..maxOf(z, toPos.z)).mapTo(list) { BlockPos(i, j, it) }
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

    operator fun get(direction: ForgeDirection): BlockPos {
        return when (direction) {
            UP -> up
            DOWN -> down
            NORTH -> north
            SOUTH -> south
            EAST -> east
            WEST -> west
            else -> Empty
        }
    }

    operator fun get(direction: ForgeDirection, range: Int): BlockPos {
        return when (direction) {
            UP -> up(range)
            DOWN -> down(range)
            NORTH -> north(range)
            SOUTH -> south(range)
            EAST -> east(range)
            WEST -> west(range)
            else -> Empty
        }
    }
}