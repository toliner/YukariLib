package c6h2cl2.YukariLib.Event

import c6h2cl2.YukariLib.YukariLibCore
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ChatComponentText
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
}