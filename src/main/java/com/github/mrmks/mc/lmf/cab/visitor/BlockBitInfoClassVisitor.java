package com.github.mrmks.mc.lmf.cab.visitor;

import com.google.common.collect.ImmutableList;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class BlockBitInfoClassVisitor extends ClassVisitor {

    private static final List<String> fields = ImmutableList.of("ignoreLogicBlocks", "stateBitInfo", "supportedBlocks", "forcedStates", "fluidBlocks", "bitColor");

    public BlockBitInfoClassVisitor(int api, ClassVisitor cv) {
        super(api, cv);
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if (fields.contains(name)) {
            return super.visitField(access, name, "Ljava/util/Map;", "Ljava/util/Map" + signature.substring(desc.length() - 1), value);
        } else
            return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if ("<clinit>".equals(name)) {
            return new MethodVisitor(api, mv) {
                @Override
                public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                    if (opcode == Opcodes.PUTSTATIC && fields.contains(name)) {
                        super.visitInsn(Opcodes.POP);
                        super.visitTypeInsn(Opcodes.NEW, "java/util/concurrent/ConcurrentHashMap");
                        super.visitInsn(Opcodes.DUP);
                        super.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/concurrent/ConcurrentHashMap", "<init>", "()V", false);
                        super.visitFieldInsn(opcode, owner, name, "Ljava/util/Map;");
                    } else if (opcode == Opcodes.GETSTATIC && fields.contains(name)) {
                        super.visitFieldInsn(opcode, owner, name, "Ljava/util/Map;");
                    } else if (opcode == Opcodes.PUTSTATIC && "fluidStates".equals(name)) {
                        super.visitMethodInsn(Opcodes.INVOKESTATIC, "gnu/trove/TCollections", "synchronizedMap", "(" + desc + ")" + desc, false);
                        super.visitFieldInsn(opcode, owner, name, desc);
                    } else
                        super.visitFieldInsn(opcode, owner, name, desc);
                }

                @Override
                public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                    if ("put".equals(name)) {
                        super.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", name, desc, true);
                    } else
                        super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            };
        }
        else {
            return new MethodVisitor(api, mv) {
                boolean flag = false;
                @Override
                public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                    if (fields.contains(name)) {
                        super.visitFieldInsn(opcode, owner, name, "Ljava/util/Map;");
                        flag = true;
                    } else
                        super.visitFieldInsn(opcode, owner, name, desc);
                }

                @Override
                public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                    if (flag && "java/util/HashMap".equals(owner)) {
                        super.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", name, desc, true);
                        flag = false;
                    } else
                        super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            };
        }
    }
}
