@file:Suppress("UNUSED")

package c6h2cl2.YukariLib.Util.NBT

import net.minecraft.nbt.NBTTagCompound

/**
 * @author C6H2Cl2
 */
interface INBTDataSaver {
    fun readFromNBT(tagCompound: NBTTagCompound)
    fun readFromNBT(tagName: String, tagCompound: NBTTagCompound): INBTDataSaver {
        readFromNBT(tagCompound.getCompoundTag(tagName))
        return this
    }

    fun writeToNBT(tagCompound: NBTTagCompound)
    fun writeToNBT(tagName: String, tagCompound: NBTTagCompound): NBTTagCompound {
        val tag = NBTTagCompound()
        writeToNBT(tag)
        tagCompound.setTag(tagName, tag)
        return tagCompound
    }
}