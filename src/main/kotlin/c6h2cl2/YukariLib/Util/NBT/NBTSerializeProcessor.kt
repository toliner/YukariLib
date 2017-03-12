@file:Suppress("UNUSED")

package c6h2cl2.YukariLib.Util.NBT

import c6h2cl2.YukariLib.Util.NBT.NBTSerializeType.*
import c6h2cl2.YukariLib.YukariLibCore
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import java.util.concurrent.*
import java.util.concurrent.TimeUnit.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.*

/**
 * @author C6H2Cl2
 */
object NBTSerializeProcessor {
    //Object → NBT
    fun serialize(target: Any): NBTTagCompound {
        // @NBTSerializableが付いているクラスである必要がある
        val annotation = target::class.annotations.find { it is NBTSerializable } as NBTSerializable? ?: throw IllegalArgumentException("argument \"target\" must be annotated with c6h2cl2.YukariLib.Util.NBT.NBTSerializable")
        //ブラックリストかホワイトリストか
        val members = if (annotation.type == WHITELIST) {
            target::class.declaredMemberProperties.filter {
                it.annotations.find { it is NBTSerializeInclude } != null
            }
        } else {
            target::class.declaredMemberProperties.filter {
                it.annotations.find { it is NBTSerializeExclude } == null
            }
        }
        //マルチスレッドのタスク
        val service = Executors.newFixedThreadPool(YukariLibCore.maxThreadPerObject)!!
        val tag = NBTTagCompound()
        members.forEach {
            service.submit({
                //privateなものもアクセス可能にする
                if (it.javaField == null){
                    it.getter.javaMethod?.isAccessible = true
                }else{
                    it.javaField!!.isAccessible = true
                }
                //値の確保
                val value = it.call(target)
                // NBTTagNameアノテーションがついていたらその値を使う。なければ変数名を使う。
                val name = ((it.annotations.find { it is NBTTagName } as NBTTagName?)?.name ) ?: it.name
                when(value){
                //プリミティブ
                    is Number -> tag.setNumber(name, value)
                    is Boolean -> tag.setBoolean(name, value)
                    is String -> tag.setString(name, value)
                //Tag
                    is NBTBase -> tag.setTag(name, value)
                //NBTに読み書きできるやつら
                    is INBTDataSaver -> value.writeToNBT(name, tag)
                    is ItemStack -> {
                        val nbt = NBTTagCompound()
                        value.writeToNBT(nbt)
                        tag.setTag(name, nbt)
                    }
                    is TileEntity -> {
                        val nbt = NBTTagCompound()
                        value.writeToNBT(nbt)
                        tag.setTag(name, nbt)
                    }
                    is Entity -> {
                        val nbt = NBTTagCompound()
                        value.writeToNBT(nbt)
                        tag.setTag(name, nbt)
                    }
                //再帰呼び出し
                    it.annotations.find { it is NBTSerializable } != null -> serialize(value)
                //どうしようこれ
                    else -> println("You are an idiot!! ☺")
                }
            })
        }
        service.awaitTermination(50, MILLISECONDS)
        return tag
    }

    fun deserialize(tagCompound: NBTTagCompound, target: Any) {
        TODO()
    }
}