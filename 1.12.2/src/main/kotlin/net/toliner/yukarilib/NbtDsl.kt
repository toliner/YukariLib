/*
 * Copyright 2019 toliner
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
@file:Suppress("UNUSED")

package net.toliner.yukarilib

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import java.util.*

fun buildNbt(buildAction: NBTTagCompound.() -> Unit): NBTTagCompound = NBTTagCompound().apply(buildAction)

val NBTTagCompound.byte
    get() = NBTChildByteManager(this)

val NBTTagCompound.short
    get() = NBTChildShortManager(this)

val NBTTagCompound.int
    get() = NBTChildIntManager(this)

val NBTTagCompound.long
    get() = NBTChildLongManager(this)

val NBTTagCompound.float
    get() = NBTChildFloatManager(this)

val NBTTagCompound.double
    get() = NBTChildDoubleManager(this)

val NBTTagCompound.boolean
    get() = NBTChildBooleanManager(this)

/**
 * Because the name "string" conflicts with [NBTBase.getString], this property is named to "str"
 */
val NBTTagCompound.str
    get() = NBTChildStringManager(this)

val NBTTagCompound.uuid
    get() = NBTChildUuidManager(this)

val NBTTagCompound.byteArray
    get() = NBTChildByteArrayManager(this)

val NBTTagCompound.intArray
    get() = NBTChildIntArrayManager(this)

val NBTTagCompound.tag
    get() = NBTChildTagManager(this)

val NBTTagCompound.compoundTag
    get() = NBTChildCompoundTagManager(this)

class NBTChildByteManager internal constructor(private val tag: NBTTagCompound) {
    operator fun set(key: String, value: Byte) = tag.setByte(key, value)
    operator fun get(key: String): Byte? = if (tag.hasKey(key)) tag.getByte(key) else null
}

class NBTChildShortManager internal constructor(private val tag: NBTTagCompound) {
    operator fun set(key: String, value: Short) = tag.setShort(key, value)
    operator fun get(key: String): Short? = if (tag.hasKey(key)) tag.getShort(key) else null
}

class NBTChildIntManager internal constructor(private val tag: NBTTagCompound) {
    operator fun set(key: String, value: Int) = tag.setInteger(key, value)
    operator fun get(key: String): Int? = if (tag.hasKey(key)) tag.getInteger(key) else null
}

class NBTChildLongManager internal constructor(private val tag: NBTTagCompound) {
    operator fun set(key: String, value: Long) = tag.setLong(key, value)
    operator fun get(key: String): Long? = if (tag.hasKey(key)) tag.getLong(key) else null
}

class NBTChildFloatManager internal constructor(private val tag: NBTTagCompound) {
    operator fun set(key: String, value: Float) = tag.setFloat(key, value)
    operator fun get(key: String): Float? = if (tag.hasKey(key)) tag.getFloat(key) else null
}

class NBTChildDoubleManager internal constructor(private val tag: NBTTagCompound) {
    operator fun set(key: String, value: Double) = tag.setDouble(key, value)
    operator fun get(key: String): Double? = if (tag.hasKey(key)) tag.getDouble(key) else null
}

class NBTChildBooleanManager internal constructor(private val tag: NBTTagCompound) {
    operator fun set(key: String, value: Boolean) = tag.setBoolean(key, value)
    operator fun get(key: String): Boolean? = if (tag.hasKey(key)) tag.getBoolean(key) else null
}

class NBTChildStringManager internal constructor(private val tag: NBTTagCompound) {
    operator fun set(key: String, value: String) = tag.setString(key, value)
    operator fun get(key: String): String? = if (tag.hasKey(key)) tag.getString(key) else null
}

class NBTChildUuidManager internal constructor(private val tag: NBTTagCompound) {
    operator fun set(key: String, value: UUID) = tag.setUniqueId(key, value)
    operator fun get(key: String): UUID? = if (tag.hasUniqueId(key)) tag.getUniqueId(key) else null
}

class NBTChildByteArrayManager internal constructor(private val tag: NBTTagCompound) {
    operator fun set(key: String, value: ByteArray) = tag.setByteArray(key, value)
    operator fun set(key: String, value: List<Byte>) = tag.setByteArray(key, value.toByteArray())
    operator fun get(key: String): ByteArray? = if (tag.hasKey(key)) tag.getByteArray(key) else null
}

class NBTChildIntArrayManager internal constructor(private val tag: NBTTagCompound) {
    operator fun set(key: String, value: IntArray) = tag.setIntArray(key, value)
    operator fun set(key: String, value: List<Int>) = tag.setIntArray(key, value.toIntArray())
    operator fun get(key: String): IntArray? = if (tag.hasKey(key)) tag.getIntArray(key) else null
}

class NBTChildTagManager internal constructor(private val tag: NBTTagCompound) {
    operator fun set(key: String, value: NBTBase) = tag.setTag(key, value)
    operator fun get(key: String): NBTBase? = if (tag.hasKey(key)) tag.getTag(key) else null
}

class NBTChildCompoundTagManager internal constructor(private val tag: NBTTagCompound) {
    operator fun set(key: String, value: NBTTagCompound) = tag.setTag(key, value)
    operator fun get(key: String): NBTTagCompound? = if (tag.hasKey(key)) tag.getTag(key) as? NBTTagCompound else null
    operator fun invoke(key: String, buildAction: NBTTagCompound.() -> Unit) = set(key, NBTTagCompound().apply(buildAction))
}
