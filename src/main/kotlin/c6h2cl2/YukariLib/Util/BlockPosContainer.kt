package c6h2cl2.YukariLib.Util

import net.minecraft.block.Block

/**
 * @author C6H2Cl2
 */
data class BlockPosContainer(val pos: BlockPos, val block: Block) {
    constructor(x: Int, y: Int, z: Int, block: Block) : this(BlockPos(x, y, z), block)
}