@file:Suppress("UNUSED")
package c6h2cl2.yukarilib.util

import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockPos.*
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side.*
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * @author C6H2Cl2
 */

fun BlockPos.toBlockPosKt() = this as? BlockPosKt ?: if (this is MutableBlockPos) MutableBlockPosKt(x, y, z) else BlockPosKt(this.x, this.y, this.z)

fun BlockPosRange.stepX(step: Int) {
    checkStepIsPositive(step > 0, step)
    BlockPosRange(this.first, this.last, stepX = step)
}

fun BlockPosRange.stepY(step: Int) {
    checkStepIsPositive(step > 0, step)
    BlockPosRange(this.first, this.last, stepY = step)
}

fun BlockPosRange.stepZ(step: Int) {
    checkStepIsPositive(step > 0, step)
    BlockPosRange(this.first, this.last, stepZ = step)
}

fun checkStepIsPositive(isPositive: Boolean, step: Number) {
    if (!isPositive) throw IllegalArgumentException("Step must be positive, was: $step.")
}

private fun register(item: Item) {
    GameRegistry.register(item)
}

private fun register(block: Block) {
    GameRegistry.register(block)
}

private fun Block.getItemBlock(): Item {
    return Item.getItemFromBlock(this)!!
}

private fun addRecipe(output: ItemStack, vararg components: Any) {
    GameRegistry.addRecipe(output, *components)
}

private fun Block.initItemBlock(registryName: ResourceLocation = this.registryName!!): Item {
    val name = if (!registryName.resourcePath.startsWith("block")) {
        ResourceLocation(registryName.resourceDomain, "block/${registryName.resourcePath}")
    } else {
        registryName
    }
    return ItemBlock(this).setRegistryName(name)
}

@SideOnly(CLIENT)
private fun getModelResourceLocation(item: Item): ModelResourceLocation {
    return ModelResourceLocation(item.registryName!!, "inventory")
}

@SideOnly(CLIENT)
private fun getModelResourceLocation(block: Block): ModelResourceLocation {
    return getModelResourceLocation(block.getItemBlock())
}
@SideOnly(CLIENT)
private fun setTexture(item: Item) {
    ModelLoader.setCustomModelResourceLocation(item, 0, getModelResourceLocation(item))
}

@SideOnly(CLIENT)
private fun setTexture(block: Block) {
    setTexture(block.getItemBlock())
}

@SideOnly(CLIENT)
private fun setTexture(item: Item, range: IntRange, otherTexture: Boolean, customFolder: String = "") {
    if (otherTexture) {
        range.forEach {
            ModelLoader.setCustomModelResourceLocation(item, it, ModelResourceLocation(ResourceLocation("${item.registryName?.resourceDomain}", "$customFolder/${item.registryName?.resourcePath}_$it"), "inventory"))
        }
    } else {
        range.forEach {
            ModelLoader.setCustomModelResourceLocation(item, it, getModelResourceLocation(item))
        }
    }
}