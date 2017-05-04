package c6h2cl2.YukariLib.Block

import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs

/**
 * @author C6H2Cl2
 */
open class SimpleBlock(
        modId: String, material: Material, name: String, textureName: String = name, creativeTab: CreativeTabs, hardness: Float = 1.5f, resistance: Float = 10f,
        lightLevel: Float = 0f, harvestTool: String? = null, harvestLevel: Int = 0, soundType: SoundType? = null
) : Block(material) {
    init {
        unlocalizedName = name
        setRegistryName(modId, textureName)
        setHardness(hardness)
        setResistance(resistance)
        setLightLevel(lightLevel)
        if (harvestTool != null) {
            setHarvestLevel(harvestTool, harvestLevel)
        }
        if (soundType != null) {
            setSoundType(soundType)
        }
        setCreativeTab(creativeTab)
    }

    override final fun setHardness(hardness: Float): Block {
        return super.setHardness(hardness)
    }

    override final fun setResistance(resistance: Float): Block {
        return super.setResistance(resistance)
    }

    override final fun setLightLevel(value: Float): Block {
        return super.setLightLevel(value)
    }

    override final fun setHarvestLevel(toolClass: String?, level: Int) {
        super.setHarvestLevel(toolClass, level)
    }

    override final fun setCreativeTab(tab: CreativeTabs?): Block {
        return super.setCreativeTab(tab)
    }

    override final fun setSoundType(sound: SoundType?): Block {
        return super.setSoundType(sound)
    }
}