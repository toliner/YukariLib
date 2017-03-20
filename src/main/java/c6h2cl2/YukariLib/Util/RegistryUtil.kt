package c6h2cl2.YukariLib.Util

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.fml.common.registry.GameRegistry
import java.util.*
import kotlin.reflect.KParameter
import kotlin.reflect.full.declaredMembers

/**
 * @author C6H2Cl2
 */
class RegistryUtil {
    private val items = LinkedList<Item>()

    fun handle(){
    }

    fun build(target: Any): RegistryUtil{
        val members = target::class.declaredMembers
        val blocks = LinkedList<Block>()
        val items = LinkedList<Item>()
        //After
        members.sortedBy { s -> s.name }
                .filter { it.parameters.filter { it.kind == KParameter.Kind.INSTANCE }.isNotEmpty() }
                .forEach { val entry = it.call(target)
                    if (entry is Item){
                        items.add(entry)
                    }else if (entry is Block){
                        blocks.add(entry)
                    }}
        blocks.forEach {
            GameRegistry.register<Block>(it)
            //items.add(ItemBlock(it))
        }
        items.forEach {
            GameRegistry.register<Item>(it)
        }
        return this
    }
}
//TODO: devJarにもKotlinのreflect入れる