package c6h2cl2.YukariLib.Event

import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.entity.player.EntityItemPickupEvent
import net.minecraftforge.event.entity.player.PlayerEvent.LoadFromFile
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

/**
 * @author C6H2Cl2
 */
class PlayerEventHandler {

    /*
    @SubscribeEvent
    fun onPlayerJoinInWorld(event: EntityJoinWorldEvent) {

    }*/

    @SubscribeEvent
    fun onPlayerLoaded(event: LoadFromFile) {
        val player = event.entityPlayer
    }

    @SubscribeEvent
    fun onBreakBlock(event: EntityItemPickupEvent){
        val player = event.entityPlayer
    }
}