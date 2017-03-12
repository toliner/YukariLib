@file:Suppress("UNUSED")
package c6h2cl2.YukariLib.Util

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntity
import java.util.*
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility.PUBLIC
import kotlin.reflect.full.declaredMemberProperties

/**
 * @author C6H2Cl2
 */
class RegisterHandler {
    private val items = LinkedList<Item>().toMutableList()
    private val blocks = LinkedList<Block>().toMutableList()

    fun handle() {
        items.sortBy { it.unlocalizedName }
        blocks.sortBy { it.unlocalizedName }
        blocks.forEach {
            GameRegistry.registerBlock(it, it.unlocalizedName)
        }
        items.forEach {
            GameRegistry.registerItem(it, it.unlocalizedName)
        }
        blocks.filter { it is ITileEntityContainer<*> }
                .forEach {
                    @Suppress("UNCHECKED_CAST")
                    GameRegistry.registerTileEntity(((it as ITileEntityContainer<*>).tileClass.java as Class<out TileEntity>),it.getTileId())
                }
    }

    fun setCreativeTab(tab: CreativeTabs): RegisterHandler{
        items.forEach { it.creativeTab = tab }
        blocks.forEach { it.setCreativeTab(tab) }
        return this
    }

    fun build(target: Any): RegisterHandler {
        //全フィールドの取得
        val members = target::class.declaredMemberProperties
        //名前でソート→KPropertyから値取得
        val fields = members
                .map { @Suppress("UNCHECKED_CAST") (it as KProperty1<Any, Any?>) }
                .filter { !it.isAbstract && it.visibility == PUBLIC }
                .map { it.get(target) }
        //仕分けして各Listに追加
        fields.filter { it is Item }
                .mapTo(items) { it as Item }
        fields.filter { it is Block }
                .mapTo(blocks) { it as Block }
        fields.filter { it is Array<*> || it is Collection<*> }
                .forEach {
                    val array = it as? Array<*> ?: (it as Collection<*>).toTypedArray()
                    array.filter { it is Item }
                            .mapTo(items) { it as Item }
                    array.filter { it is Block }
                            .mapTo(blocks) { it as Block }
                }
        return this
    }
}
//TODO: devJarにもKotlinのreflect入れる