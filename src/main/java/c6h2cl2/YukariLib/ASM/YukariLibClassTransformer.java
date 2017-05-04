package c6h2cl2.YukariLib.ASM;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * @author C6H2Cl2
 */
public class YukariLibClassTransformer implements IClassTransformer {
    private final List<String> TARGETS = new LinkedList<>();

    public YukariLibClassTransformer() {
        TARGETS.add("net.minecraft.client.renderer.block.model.ModelBakery");
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (FMLLaunchHandler.side().isServer() || !isAccept(transformedName)) return basicClass;
        ClassReader cr = new ClassReader(basicClass);
        ClassWriter cw = new ClassWriter(cr, 0);
        createBuiltinModelSetter(cw, transformedName);
        return cw.toByteArray();
    }

    private void createBuiltinModelSetter(ClassWriter cw, String className){

    }

    private boolean isAccept(String name) {
        return TARGETS.contains(name);
    }
}
