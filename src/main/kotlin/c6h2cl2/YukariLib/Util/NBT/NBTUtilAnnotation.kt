@file:Suppress("UNUSED")

package c6h2cl2.YukariLib.Util.NBT

import net.minecraft.nbt.NBTTagCompound
import kotlin.annotation.AnnotationTarget.*

/**
 * @author C6H2Cl2
 */

@Target(CLASS)
annotation class NBTSerializable(val type: NBTSerializeType)

@Target(CLASS, FIELD, PROPERTY)
annotation class NBTSerializeInclude

@Target(CLASS, FIELD, PROPERTY)
annotation class NBTSerializeExclude

annotation class NBTTagName(val name: String)

enum class NBTSerializeType {
    WHITELIST, BLACKLIST
}

fun NBTTagCompound.setNumber(name: String, number: Number){
    when(number){
        is Int -> this.setInteger(name, number)
        is Float -> this.setFloat(name, number)
        is Double -> this.setDouble(name, number)
        is Long -> this.setLong(name, number)
        is Short -> this.setShort(name, number)
        is Byte -> this.setByte(name, number)
    }
}