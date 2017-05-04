package c6h2cl2.YukariLib.ASM

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions

/**
 * @author C6H2Cl2
 */
@TransformerExclusions("com", "c6h2cl2", "org", "io", "it", "java", "jline")
class YukariLibLoadingPlugin :IFMLLoadingPlugin{
    override fun getModContainerClass(): String {
        return YukariLibDummyModContainer::class.java.canonicalName
    }

    override fun getASMTransformerClass(): Array<String> {
        return arrayOf(YukariLibTransformer::class.java.canonicalName)
    }

    override fun getSetupClass(): String? = null

    override fun injectData(data: MutableMap<String, Any>?) {
        //Do Nothing
    }

    override fun getAccessTransformerClass(): String? = null
}