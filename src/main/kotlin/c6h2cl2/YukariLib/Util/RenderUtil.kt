package c6h2cl2.YukariLib.Util

import net.minecraft.client.renderer.Tessellator
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.ChunkPosition
import net.minecraft.world.World
import org.lwjgl.opengl.GL11

/**
 * @author kojin15.
 */
object RenderUtil {

    private fun drawOutlinedBoundingBox(tessellator: Tessellator, axis: AxisAlignedBB) {
        tessellator.startDrawing(3)
        tessellator.addVertex(axis.minX, axis.minY, axis.minZ)
        tessellator.addVertex(axis.maxX, axis.minY, axis.minZ)
        tessellator.addVertex(axis.maxX, axis.minY, axis.maxZ)
        tessellator.addVertex(axis.minX, axis.minY, axis.maxZ)
        tessellator.addVertex(axis.minX, axis.minY, axis.minZ)
        tessellator.draw()
        tessellator.startDrawing(3)
        tessellator.addVertex(axis.minX, axis.maxY, axis.minZ)
        tessellator.addVertex(axis.maxX, axis.maxY, axis.minZ)
        tessellator.addVertex(axis.maxX, axis.maxY, axis.maxZ)
        tessellator.addVertex(axis.minX, axis.maxY, axis.maxZ)
        tessellator.addVertex(axis.minX, axis.maxY, axis.minZ)
        tessellator.draw()
        tessellator.startDrawing(1)
        tessellator.addVertex(axis.minX, axis.minY, axis.minZ)
        tessellator.addVertex(axis.minX, axis.maxY, axis.minZ)
        tessellator.addVertex(axis.maxX, axis.minY, axis.minZ)
        tessellator.addVertex(axis.maxX, axis.maxY, axis.minZ)
        tessellator.addVertex(axis.maxX, axis.minY, axis.maxZ)
        tessellator.addVertex(axis.maxX, axis.maxY, axis.maxZ)
        tessellator.addVertex(axis.minX, axis.minY, axis.maxZ)
        tessellator.addVertex(axis.minX, axis.maxY, axis.maxZ)
        tessellator.draw()
    }

    fun renderBoundingBoxFromPos(list: List<BlockPos>,  world: World, player: EntityPlayer, partialTickItem: Float, color: List<Float>, lineWidth: Float) {
        val d3 = 0.002
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        // 線の色 (R, G, B, Alpha)
        GL11.glColor4f(color[0], color[1], color[2], color[3])
        //　線の太さ
        GL11.glLineWidth(lineWidth)
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glDepthMask(false)
        val d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTickItem
        val d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTickItem
        val d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTickItem

        list.forEach {
            val axis = AxisAlignedBB.getBoundingBox(it.getX().toDouble(), it.getY().toDouble(), it.getZ().toDouble(), it.getX().toDouble() + 1, it.getY().toDouble() + 1, it.getZ().toDouble() + 1).expand(d3, d3, d3).getOffsetBoundingBox(-d0, -d1, -d2)
            val tessellator = Tessellator.instance
            drawOutlinedBoundingBox(tessellator, axis)
        }

        GL11.glDepthMask(true)
        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_BLEND)
    }
}