package c6h2cl2.YukariLib.ASM

import net.minecraft.launchwrapper.IClassTransformer
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper
import net.minecraftforge.fml.relauncher.FMLLaunchHandler
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.Opcodes.*

/**
 * @author C6H2Cl2
 */
class YukariLibTransformer : IClassTransformer {
    private val TARGETS = listOfNotNull("net.minecraft.client.renderer.block.model.ModelBakery")

    override fun transform(name: String, transformedName: String, basicClass: ByteArray): ByteArray {
        if (FMLLaunchHandler.side().isServer || !accept(name)) return basicClass
        val cr = ClassReader(basicClass)
        val cw = ClassWriter(cr, ClassWriter.COMPUTE_FRAMES)
        val cv = ModelBakeryClassVisitor(transformedName, cw)
        try {
            cr.accept(cv, 0)
            return cw.toByteArray()
        } catch (e: Exception) {
            println("\n\n\nERROR\n\n\n")
            return basicClass
        }
    }

    private class ModelBakeryClassVisitor(private val transformedName: String, private val cv: ClassVisitor? = null) : ClassVisitor(ASM5, cv) {
        init {
            visitMethod(ACC_PUBLIC or ACC_FINAL or ACC_STATIC, "putBuiltinModel", "(Ljava/lang/String;Ljava/lang/String;)V", null, null)
        }

        override fun visitMethod(access: Int, name: String?, desc: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
            val mv = super.visitMethod(access, name, desc, signature, exceptions)
            return if (access != (ACC_PUBLIC or ACC_FINAL or ACC_STATIC) || name != "putBuiltinModel" || desc != "(Ljava/lang/String;Ljava/lang/String;)V" || signature != null || exceptions != null) {
                mv
            } else {
                ModelBakeryMethodVisitor(transformedName, mv)
            }
        }
    }

    private class ModelBakeryMethodVisitor(private val className: String, mv: MethodVisitor?) : MethodVisitor(ASM5, mv) {
        override fun visitCode() {
            visitFieldInsn(GETSTATIC, className, "BUILT_IN_MODELS", "Ljava/util/HashMap")
            visitVarInsn(ALOAD, 0)
            visitVarInsn(ALOAD, 1)
            visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(java.util.HashMap::class.java), "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava.lang.Object;", false)
            visitMaxs(0, 0)
            super.visitCode()
        }
    }

    private fun accept(className: String): Boolean = TARGETS.contains(className)

    private fun unmapClassName(name: String): String {
        return FMLDeobfuscatingRemapper.INSTANCE.unmap(name.replace('.', '/')).replace('/', '.')
    }

    private fun mapClassName(name: String): String {
        return FMLDeobfuscatingRemapper.INSTANCE.map(name.replace('.', '/')).replace('/', '.')
    }

    private fun mapMethodName(owner: String, methodName: String, desc: String): String {
        return FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, methodName, desc)
    }

    private fun mapFieldName(owner: String, fieldName: String, desc: String): String {
        return FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(owner, fieldName, desc)
    }

    private fun toDesc(returnType: Any, vararg rawDesc: Any): String {
        val sb = StringBuilder("(")
        rawDesc.forEach {
            sb.append(toDesc(it))
        }
        sb.append(')')
        sb.append(toDesc(returnType))
        return sb.toString()
    }

    private fun toDesc(raw: Any): String {
        if (raw is Class<*>) {
            return Type.getDescriptor(raw)
        } else if (raw is String) {
            val desc = raw.replace('.', '/')
            return if (desc.matches(Regex("L.+;"))) {
                desc
            } else {
                "L$desc;"
            }
        } else {
            throw IllegalArgumentException()
        }
    }
}