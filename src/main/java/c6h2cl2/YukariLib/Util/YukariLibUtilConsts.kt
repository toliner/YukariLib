@file:JvmName("YukariLibUtils")

package c6h2cl2.YukariLib.Util

import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.translation.I18n
import net.minecraftforge.client.model.ModelLoader
import java.util.*

/**
 * @author C6H2Cl2
 */

fun EntityPlayer.sendMessageOnlyServer(component: ITextComponent) {
    if (!world.isRemote) sendMessage(component)
}

fun Block.getItemBlock(): Item {
    return Item.getItemFromBlock(this)
}

fun Block.initItemBlock(registryName: ResourceLocation = this.registryName!!): Item {
    val name = if (!registryName.resourcePath.startsWith("block")) {
        ResourceLocation(registryName.resourceDomain, "block/${registryName.resourcePath}")
    } else {
        registryName
    }
    return ItemBlock(this).setRegistryName(name)
}

fun translateToLocal(key: String): String {
    if (I18n.canTranslate(key)) {
        return I18n.translateToLocal(key)
    } else {
        return I18n.translateToFallback(key)
    }
}

fun translateToLocalFormatted(key: String, vararg format: Any): String {
    val s = translateToLocal(key)
    try {
        return String.format(s, *format)
    } catch (e: IllegalFormatException) {
        val errorMessage = "Format error: " + s
        println(errorMessage)
        return errorMessage
    }
}

fun getModelResourceLocation(item: Item): ModelResourceLocation {
    return ModelResourceLocation(item.registryName!!, "inventory")
}

fun getModelResourceLocation(block: Block): ModelResourceLocation {
    return getModelResourceLocation(block.getItemBlock())
}

fun setCustomModelResourceLocation(item: Item) {
    ModelLoader.setCustomModelResourceLocation(item, 0, getModelResourceLocation(item))
}

fun setCustomModelResourceLocation(block: Block) {
    setCustomModelResourceLocation(block.getItemBlock())
}
