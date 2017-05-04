package c6h2cl2.YukariLib.Client

import net.minecraft.client.resources.IResourcePack
import net.minecraft.client.resources.data.IMetadataSection
import net.minecraft.client.resources.data.MetadataSerializer
import net.minecraft.util.ResourceLocation
import java.awt.image.BufferedImage
import java.io.InputStream

/**
 * @author C6H2Cl2
 */
class TestResourcePack: IResourcePack {
    override fun resourceExists(location: ResourceLocation?): Boolean {
        println("\n\n\n\n\n\n")
        println(location?.toString())
        return false
    }

    override fun getPackImage(): BufferedImage? {
        println("\n\n\n\n\n\n")
        return null
    }

    override fun <T : IMetadataSection?> getPackMetadata(metadataSerializer: MetadataSerializer?, metadataSectionName: String?): T? {
        println("\n\n\n\n\n\n")
        return null
    }

    override fun getInputStream(location: ResourceLocation?): InputStream {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPackName(): String {
        println("\n\n\n\n\n\n")
        return "TestPack"
    }

    override fun getResourceDomains(): MutableSet<String> {
        println("\n\n\n\n\n\n")
        return setOf("test").toMutableSet()
    }
}