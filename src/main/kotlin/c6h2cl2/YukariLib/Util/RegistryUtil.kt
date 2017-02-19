package c6h2cl2.YukariLib.Util

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import java.util.*
import kotlin.reflect.KParameter
import kotlin.reflect.full.declaredMembers

/**
 * @author C6H2Cl2
 */
class RegistryUtil {
    private val items = LinkedList<Item>()
    private val blocks = LinkedList<Block>()

    fun handle() {
        blocks.forEach {
            GameRegistry.registerBlock(it, it.unlocalizedName)
        }
        items.forEach {
            GameRegistry.registerItem(it, it.unlocalizedName)
        }
    }

    fun build(target: Any): RegistryUtil {
        val members = target::class.declaredMembers
        members.sortedBy { s -> s.name }
                .filter { it.parameters.filter { it.kind == KParameter.Kind.INSTANCE }.isNotEmpty() }
                .forEach {
                    val entry = it.call(target)
                    if (entry is Item) {
                        items.add(entry)
                    } else if (entry is Block) {
                        blocks.add(entry)
                    }
                }

        return this
    }
}
//TODO: devJarにもKotlinのreflect入れる