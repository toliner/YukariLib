package c6h2cl2.YukariLib.Render

import c6h2cl2.YukariLib.Util.BlockPos
import c6h2cl2.YukariLib.Util.Client.Pointer3D
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

/**
 * @author kojin15.
 * Item用
 */
interface ICustomBoundingBox {
    fun getBoundingBoxPos(itemStack: ItemStack, player: EntityPlayer, world: World, pos: BlockPos, side: Int, hitPos: Pointer3D): List<BlockPos>
    /**
     * @return listOf( R: Float, G: Float, B: Float, A: Float )
     */
    fun getRGBA(): List<Float>

    /**
     * 太さ
     */
    fun getLineWidth(): Float
}