@file:Suppress("UNUSED")

package c6h2cl2.YukariLib.Util.NBT

import net.minecraft.nbt.NBTTagCompound
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClassifier

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

fun NBTTagCompound.setNumber(name: String, number: Number) {
    when (number) {
        is Int -> this.setInteger(name, number)
        is Float -> this.setFloat(name, number)
        is Double -> this.setDouble(name, number)
        is Long -> this.setLong(name, number)
        is Short -> this.setShort(name, number)
        is Byte -> this.setByte(name, number)
    }
}

fun NBTTagCompound.getNumber(name: String, type: KClassifier): Number {
     return when(type) {
         Int::class.java -> this.getInteger(name)
         Float::class.java -> this.getFloat(name)
         Double::class.java -> this.getDouble(name)
         Long::class.java -> this.getLong(name)
         Short::class.java -> this.getShort(name)
         Byte::class.java -> this.getByte(name)
         else -> throw IllegalArgumentException()
    }
}