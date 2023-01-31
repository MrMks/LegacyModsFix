package com.github.mrmks.mc.lmf.cab.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ModelUtilClassVisitor extends ClassVisitor {
    public ModelUtilClassVisitor(int api, ClassVisitor cv) {
        super(api, cv);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if ("cache".equals(name) || "breakCache".equals(name)) {
            return super.visitField(access, name, "Ljava/util/Map;", "Ljava/util/Map" + signature.substring(desc.length() - 1), value);
        } else
            return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if ("getCachedFace".equals(name) || "getInnerCachedFace".equals(name) || "<init>".equals(name) || "getBreakingModel".equals(name) || "clearCache".equals(name)) {
            return new MethodVisitor(api, mv) {
                boolean flag = false;
                @Override
                public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                    if ("cache".equals(name) || "breakCache".equals(name)) {
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
        else if ("<clinit>".equals(name)) {
            return new MethodVisitor(api, mv) {
                @Override
                public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                    if (opcode == Opcodes.PUTSTATIC && ("cache".equals(name) || "breakCache".equals(name))) {
                        super.visitInsn(Opcodes.POP);
                        super.visitTypeInsn(Opcodes.NEW, "java/util/concurrent/ConcurrentHashMap");
                        super.visitInsn(Opcodes.DUP);
                        super.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/concurrent/ConcurrentHashMap", "<init>", "()V", false);
                        super.visitFieldInsn(opcode, owner, name, "Ljava/util/Map;");
                    } else
                        super.visitFieldInsn(opcode, owner, name, desc);
                }
            };
        }
        else return mv;
    }
}
