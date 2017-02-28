package c6h2cl2.YukariLib.Event

import c6h2cl2.YukariLib.Render.ISpecialSelectedBox
import c6h2cl2.YukariLib.Render.RenderSpecialSelectedBox
import c6h2cl2.YukariLib.Util.BlockPos
import c6h2cl2.YukariLib.YukariLibCore
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent

/**
 * @author C6H2Cl2
 */
class YukariLibEventHandler {
    //プレイヤー死亡時に座標表示
    @SubscribeEvent
    fun onPlayerDeath(event: LivingDeathEvent){
        if (!YukariLibCore.isEnableDeathLog()) return
        val player = event.entityLiving as? EntityPlayer ?: return
        player.addChatComponentMessage(ChatComponentText("You died at ${player.posX}, ${player.posY}, ${player.posZ}"))
    }

    //セレクトボックスの表示
    @SubscribeEvent
    fun worldRenderEvent(event: RenderWorldLastEvent) {
        val player = Minecraft.getMinecraft().thePlayer
        val world = Minecraft.getMinecraft().theWorld
        val MOP = Minecraft.getMinecraft().objectMouseOver
        val pos = BlockPos(MOP.blockX, MOP.blockY, MOP.blockZ)

        if (player != null) {
            val tile = pos.getTileEntityFromPos(world)
            if (tile is ISpecialSelectedBox && tile.shouldRenderBox(MOP.subHit, player)) {
                RenderSpecialSelectedBox().drawSelectionBox(player, MOP, event.partialTicks, tile.getSpecialSelectedBox(MOP.subHit, player))
            }
        }
    }
}