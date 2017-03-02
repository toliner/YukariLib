package c6h2cl2.YukariLib.Block

import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import kotlin.reflect.KClass

/**
 * @author C6H2Cl2
 */
open class BlockWithTileEntity<in T : TileEntity>(material: Material, val tileClass: KClass<in T>, val createTile: (World, Int) -> TileEntity, private var tileID: String = "") : CSBoxBlockContainer(material) {
    override fun createNewTileEntity(world: World, meta: Int): TileEntity {
        return createTile(world, meta)
    }

    fun setTileId(id: String) {
        if (tileID == "") tileID = id
    }

    fun getTileId(): String {
        return if (tileID == "") {
            unlocalizedName
        } else {
            tileID
        }
    }
}