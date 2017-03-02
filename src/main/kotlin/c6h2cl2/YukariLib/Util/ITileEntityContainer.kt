package c6h2cl2.YukariLib.Util

import net.minecraft.tileentity.TileEntity
import kotlin.reflect.KClass

/**
 * @author C6H2Cl2
 */
interface ITileEntityContainer<in T:TileEntity> {
    val tileClass: KClass<in T>
    fun setTileId(id: String)
    fun getTileId(): String
}