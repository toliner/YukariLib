package c6h2cl2.YukariLib.Common

import java.io.File

/**
 * @author C6H2Cl2
 */
open class CommonProxy {
    open fun getDir(): File = File(".")
}