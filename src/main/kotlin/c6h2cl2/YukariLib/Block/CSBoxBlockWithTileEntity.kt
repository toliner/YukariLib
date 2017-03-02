package c6h2cl2.YukariLib.Block

import c6h2cl2.YukariLib.Util.ITileEntityContainer
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import kotlin.reflect.KClass

/**
 * @author C6H2Cl2
 */
class CSBoxBlockWithTileEntity<in T : TileEntity>(material: Material, override val tileClass: KClass<in T>, val createTile: (World, Int) -> TileEntity, private var tileID: String = "")
    : CSBoxBlockContainer(material), ITileEntityContainer<T> {
    override fun createNewTileEntity(world: World, meta: Int): TileEntity {
        return createTile(world, meta)
    }

    override fun setTileId(id: String) {
        if (tileID == "") tileID = id
    }

    override fun getTileId(): String {
        return if (tileID == "") {
            unlocalizedName
        } else {
            tileID
        }
    }
}