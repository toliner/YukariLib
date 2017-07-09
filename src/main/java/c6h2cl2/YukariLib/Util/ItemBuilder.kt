@file:Suppress("UNUSED")
package c6h2cl2.YukariLib.Util

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

/**
 * @author C6H2Cl2
 */
class ItemBuilder(val modId: String) {
    private val items = emptyList<Item>().toMutableList()

    fun create(name: String, textureName: String = name, creativeTabs: CreativeTabs? = null, stackSize: Int = 64, /*hasSubType : Boolean = false,
                       maxMeta : Int = 0,*/isFull3D: Boolean = false, containerItem: Item? = null): Item {
        val item = ItemUtil.CreateItem(name, textureName, modId, creativeTabs, stackSize, isFull3D, containerItem)
        items.add(item)
        return item
    }

    fun getAllItems(): Array<Item>{
        return items.toTypedArray()
    }

    fun registerItems(event: RegistryEvent.Register<Item>){
        items.forEach {
            event.registry.register(it)
        }
    }

    fun registerModels() {
        items.forEach {
            setCustomModelResourceLocation(it)
        }
    }
}