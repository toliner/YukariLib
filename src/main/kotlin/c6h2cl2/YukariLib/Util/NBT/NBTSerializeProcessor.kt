@file:Suppress("UNUSED")

package c6h2cl2.YukariLib.Util.NBT

import c6h2cl2.YukariLib.Util.NBT.NBTSerializeType.*
import c6h2cl2.YukariLib.YukariLibCore
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import java.util.concurrent.TimeUnit.*
import kotlin.reflect.KProperty1
import kotlin.reflect.full.*
import kotlin.reflect.jvm.*

/**
 * @author C6H2Cl2
 */
@Deprecated("This is experimental code")
object NBTSerializeProcessor {
    //Object → NBT
    fun serialize(target: Any): NBTTagCompound {
        val members = getAllMember(target)
        //マルチスレッドのタスク
        val service = YukariLibCore.getExecutorService()
        val tag = NBTTagCompound()
        members.forEach {
            service.submit(SerializeRunner(it, target, tag))
        }
        service.awaitTermination(50, MILLISECONDS)
        return tag
    }

    //NBT→Object
    fun deserialize(target: Any, tagCompound: NBTTagCompound) {
        val members = getAllMember(target)
        //マルチスレッドのタスク
        val service = YukariLibCore.getExecutorService()
        members.forEach {
            service.submit(DeserializeRunner(it, target, tagCompound))
        }
        service.awaitTermination(50, MILLISECONDS)
    }

    //全メンバー変数を取得する
    private fun getAllMember(target: Any): List<KProperty1<out Any, Any?>> {
        // @NBTSerializableが付いているクラスである必要がある
        val annotation = target::class.annotations.find { it is NBTSerializable } as NBTSerializable? ?: throw IllegalArgumentException("argument \"target\" must be annotated with c6h2cl2.YukariLib.Util.NBT.NBTSerializable")
        //ブラックリストかホワイトリストか
        return if (annotation.type == WHITELIST) {
            target::class.declaredMemberProperties.filter {
                it.annotations.find { it is NBTSerializeInclude } != null
            }
        } else {
            target::class.declaredMemberProperties.filter {
                it.annotations.find { it is NBTSerializeExclude } == null
            }
        }
    }

    //マルチスレッド用に分割したRunnableクラス、Object→NBT
    private class SerializeRunner(private val member: KProperty1<out Any, Any?>, private val target: Any, private val tag: NBTTagCompound) : Runnable {
        override fun run() {
            //privateなものもアクセス可能にする
            setAccessible(member)
            //値の確保
            val value = member.call(target)
            // NBTTagNameアノテーションがついていたらその値を使う。なければ変数名を使う。
            val name = ((member.annotations.find { it is NBTTagName } as NBTTagName?)?.name) ?: member.name
            if (value == null) throw IllegalStateException()
            when (value) {
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

            //どうしようこれ
                else -> {
                    //再帰呼び出し
                    if (member.annotations.find { it is NBTSerializable } != null) {
                        serialize(value)
                    } else {
                        println("You are an idiot!! ☺")
                        throw IllegalStateException()
                    }
                }
            }
        }
    }

    //マルチスレッド用に分割したRunnableクラス、NBT→Object
    private class DeserializeRunner(private val member: KProperty1<out Any, Any?>, private val target: Any, private val tag: NBTTagCompound) : Runnable {
        override fun run() {
            setAccessible(member)
            val type = member.returnType.classifier
            val name = getMemberName(member)
            val memberClass = member::class
            /*val value = when {
                memberClass.isSubclassOf(Number::class) -> tag.getNumber(name, type!!)
                memberClass == Boolean::class -> tag.getBoolean(name)
                memberClass == String::class -> tag.getString(name)

            }*/

            when (type) {
            //プリミティブ
                Number::class.java -> tag.getNumber(name, type)
                Boolean::class.java -> tag.getBoolean(name)
                String::class.java -> tag.getString(name)

                else -> {
                    //NBTタグ
                    if (member::class.isSubclassOf(NBTBase::class)) {
                        tag.getTag(name)
                    } else if (member::class.isSubclassOf(ItemStack::class)) {

                    } else {
                        throw IllegalStateException()
                        @Suppress("UNREACHABLE_CODE")
                        Any()
                    }
                }
            }
        }
    }

    private fun setAccessible(property: KProperty1<out Any, Any?>) {
        if (property.javaField == null) {
            property.getter.javaMethod?.isAccessible = true
        } else {
            property.javaField!!.isAccessible = true
        }
    }

    private fun getMemberName(member: KProperty1<out Any, Any?>): String = ((member.annotations.find { it is NBTTagName } as NBTTagName?)?.name) ?: member.name
}