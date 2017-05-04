@file:Suppress("UNUSED")
package c6h2cl2.YukariLib.Util

import c6h2cl2.YukariLib.Block.SimpleBlock
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs

/**
 * @author C6H2Cl2
 */

class BlockBuilder(val modId: String) {
    private val blocks = emptyList<Block>().toMutableList()

    fun create(material: Material, name: String, textureName: String = name, creativeTab: CreativeTabs, hardness: Float = 1.5f, resistance: Float = 10f,
               lightLevel: Float = 0f, harvestTool: String? = null, harvestLevel: Int = 0, soundType: SoundType? = null
    ): Block {
        val block = SimpleBlock(modId, material, name, textureName, creativeTab, hardness, resistance, lightLevel, harvestTool, harvestLevel, soundType)
        blocks.add(block)
        return block
    }

    fun register(){
        registerBlocks()
        registerModels()
    }

    fun registerBlocks(){
        registerAll(blocks)
    }

    fun registerModels(){
        blocks.forEach {
            setCustomModelResourceLocation(it)
        }
    }
}