package com.github.mrmks.mc.lmf.core;

import com.github.mrmks.mc.lmf.cab.visitor.BlockBitInfoClassVisitor;
import com.github.mrmks.mc.lmf.cab.visitor.ModelUtilClassVisitor;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import static org.objectweb.asm.Opcodes.ASM5;

public class VisitorTransformer implements IClassTransformer {
    private final String[] nameList = new String[]{
    };
    private final TransformerBuilder[] transList = new TransformerBuilder[] {
    };
    private final String[] nameListClient = new String[] {
            "mod.chiselsandbits.render.helpers.ModelUtil",
            "mod.chiselsandbits.chiseledblock.BlockBitInfo"
    };
    private final TransformerBuilder[] transListClient = new TransformerBuilder[] {
            ModelUtilClassVisitor::new,
            BlockBitInfoClassVisitor::new
    };
    private final String[] nameListServer = new String[]{
    };
    private final TransformerBuilder[] transListServer = new TransformerBuilder[]{
    };

    private final boolean isClient = FMLLaunchHandler.side().isClient();

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {

        // since the LaunchClassLoader go through all transformers, it should be ok we compare our targets here.
        // besides, to be compatible with SpongePowered Mixin, we should make sure that
        // the class have the same structure after it goes through this transformer multiple times.
        int i;
        if ((i = matchClass(name, nameList)) >= 0)
            return runVisitor(transList[i], name, basicClass);

        String[] names = isClient ? nameListClient : nameListServer;
        TransformerBuilder[] builders = isClient ? transListClient : transListServer;

        if ((i = matchClass(name, names)) >= 0)
            return runVisitor(builders[i], name, basicClass);

        return basicClass;
    }

    private static int matchClass(String name, String[] names) {
        for (int i = 0; i < names.length; i ++) {
            if (names[i].equals(name)) return i;
        }
        return -1;
    }

    private static byte[] runVisitor(TransformerBuilder builder, String name, byte[] basic) {
        ClassReader cr = new ClassReader(basic);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = builder.build(ASM5, cw);
        cr.accept(cv, 0);

        return TransformHelper.transformedSave(name, cw.toByteArray());
    }

    private interface TransformerBuilder {
        ClassVisitor build(int api, ClassVisitor cv);
    }

}
