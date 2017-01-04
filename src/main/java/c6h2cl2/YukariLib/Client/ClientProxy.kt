package c6h2cl2.YukariLib.Client

import c6h2cl2.YukariLib.Common.CommonProxy
import net.minecraft.client.Minecraft
import java.io.File

/**
 * @author C6H2Cl2
 */
class ClientProxy :CommonProxy(){
    override fun getDir(): File = Minecraft.getMinecraft().mcDataDir
}