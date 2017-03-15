package c6h2cl2.YukariLib.Util

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

/**
 * @author kojin15.
 */
class PlayerOperation {
    val player: EntityPlayer

    constructor(player: EntityPlayer) {
        this.player = player
    }

    fun getInventory() = player.inventory

    fun getHaveNumber(item: Item, meta: Int = 0): Int = getHaveNumber(ItemStack(item, 1, meta))
    fun getHaveNumber(block: Block, meta: Int = 0): Int = getHaveNumber(ItemStack(block, 1, if (Item.getItemFromBlock(block).hasSubtypes) meta else 0))

    fun getHaveNumber(stack: ItemStack): Int {
        var number = 0
        getInventory().mainInventory.filterNotNull().forEach {
            if (it.isItemEqual(stack)) number += it.stackSize
        }
        return number
    }

    fun getHaveHotbar(item: Item, meta: Int = 0): Int = getHaveHotbar(ItemStack(item, 1, meta))
    fun getHaveHotbar(block: Block, meta: Int = 0): Int = getHaveHotbar(ItemStack(block, 1, if (Item.getItemFromBlock(block).hasSubtypes) meta else 0))

    fun getHaveHotbar(stack: ItemStack): Int {
        var number = 0
        val hotbar = getInventory().mainInventory.filterIndexed { i, it -> MathHelperEx.betweenIn(27, i, 35) }
        hotbar.filterNotNull().forEach {
            if (it.isItemEqual(stack)) number += it.stackSize
        }
        return number
    }

    fun useItem(item: Item, meta: Int = 0, number: Int): Boolean = useItem(ItemStack(item, 1, meta), number)
    fun useItem(block: Block, meta: Int = 0, number: Int): Boolean = useItem(ItemStack(block, 1, if (Item.getItemFromBlock(block).hasSubtypes) meta else 0), number)
    fun useItem(stack: ItemStack): Boolean = useItem(stack, stack.stackSize)

    fun useItem(stack: ItemStack, number: Int): Boolean {
        if (getHaveNumber(stack) < number) return false
        var toUse = number
        getInventory().mainInventory.forEachIndexed { i, it ->
            if (it != null && it.isItemEqual(stack)) {
                if (it.stackSize < toUse) {
                    toUse -= it.stackSize
                    it.stackSize = 0
                } else {
                    it.stackSize -= toUse
                    toUse = 0
                }
                if (it.stackSize == 0) getInventory().setInventorySlotContents(i, null)
                if (toUse <= 0) return@forEachIndexed
            }
        }
        player.inventoryContainer.detectAndSendChanges()
        return true
    }

    fun isHaveItem(item: Item, meta: Int = 0): Boolean = isHaveItem(ItemStack(item, 1, meta))
    fun isHaveItem(block: Block, meta: Int = 0): Boolean = isHaveItem(ItemStack(block, 1, if (Item.getItemFromBlock(block).hasSubtypes) meta else 0))
    fun isHaveItem(stack: ItemStack): Boolean = getHaveNumber(stack) > 0

    fun isHaveHotbar(item: Item, meta: Int = 0): Boolean = isHaveHotbar(ItemStack(item, 1, meta))
    fun isHaveHotbar(block: Block, meta: Int = 0): Boolean = isHaveHotbar(ItemStack(block, 1, if (Item.getItemFromBlock(block).hasSubtypes) meta else 0))
    fun isHaveHotbar(stack: ItemStack): Boolean = getHaveHotbar(stack) > 0

    fun isCreative(): Boolean = player.capabilities.isCreativeMode

}