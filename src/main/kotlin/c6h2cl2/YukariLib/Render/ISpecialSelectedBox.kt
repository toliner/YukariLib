package c6h2cl2.YukariLib.Render

import net.minecraft.entity.player.EntityPlayer

/**
 * @author kojin15.
 */
interface ISpecialSelectedBox {
    /**
     * @param subHit Cubeと対応した値
     */
    fun getSpecialSelectedBox(subHit: Int, player: EntityPlayer): SpecialSelectedBox

    /**
     * @param subHit Cubeと対応した値
     * @return セレクトボックスを描画するかどうか
     */
    fun shouldRenderBox(subHit: Int, player: EntityPlayer): Boolean
}