package com.github.mrmks.mc.lmf.ccl;

import org.objectweb.asm.*;

public class WorldExtensionEventHandlerClassVisitor extends ClassVisitor {
    public WorldExtensionEventHandlerClassVisitor(int api, ClassVisitor cv) {
        super(api, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.equals("onChunkUnWatch")) {
            return new MethodVisitor(api, null) {};
        } else return super.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        MethodVisitor mv = super.visitMethod(Opcodes.ACC_PUBLIC, "onChunkUnwatch", "(Lnet/minecraftforge/event/world/ChunkWatchEvent$UnWatch;)V", null, null);
        {
            AnnotationVisitor av = mv.visitAnnotation("Lnet/minecraftforge/fml/common/eventhandler/SubscribeEvent;", true);
            av.visitEnd();
        }

        Label label0 = new Label();
        mv.visitLabel(label0);

        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraftforge/event/world/ChunkWatchEvent$UnWatch", "getPlayer", "()Lnet/minecraft/entity/player/EntityPlayerMP;", false);
        mv.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/entity/player/EntityPlayerMP", "world", "Lnet/minecraft/world/World;");
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraftforge/event/world/ChunkWatchEvent$UnWatch", "getChunk", "()Lnet/minecraft/util/math/ChunkPos;", false);
        mv.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/util/math/ChunkPos", "x", "I");
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraftforge/event/world/ChunkWatchEvent$UnWatch", "getChunk", "()Lnet/minecraft/util/math/ChunkPos;", false);
        mv.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/util/math/ChunkPos", "z", "I");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraft/world/World", "getChunkFromChunkCoords", "(II)Lnet/minecraft/world/chunk/Chunk;", false);
        mv.visitVarInsn(Opcodes.ASTORE, 2);

        Label label1 = new Label();
        mv.visitLabel(label1);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraftforge/event/world/ChunkWatchEvent$UnWatch", "getPlayer", "()Lnet/minecraft/entity/player/EntityPlayerMP;", false);
        mv.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/entity/player/EntityPlayerMP", "world", "Lnet/minecraft/world/World;");
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "codechicken/lib/world/WorldExtensionManager", "access$300", "(Lnet/minecraft/world/World;)[Lcodechicken/lib/world/WorldExtension;", false);
        mv.visitVarInsn(Opcodes.ASTORE, 3);
        mv.visitVarInsn(Opcodes.ALOAD, 3);
        mv.visitInsn(Opcodes.ARRAYLENGTH);
        mv.visitVarInsn(Opcodes.ISTORE, 4);
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitVarInsn(Opcodes.ISTORE, 5);

        Label label2 = new Label();
        Label label5 = new Label();
        mv.visitLabel(label2);
        mv.visitFrame(Opcodes.F_NEW, 6, new Object[]{
                "codechicken/lib/world/WorldExtensionManager$WorldExtensionEventHandler",
                "net/minecraftforge/event/world/ChunkWatchEvent$UnWatch",
                "net/minecraft/world/chunk/Chunk",
                "[Lcodechicken/lib/world/WorldExtension;",
                "I",
                "I"
        },0, null);
        mv.visitVarInsn(Opcodes.ILOAD, 5);
        mv.visitVarInsn(Opcodes.ILOAD, 4);
        mv.visitJumpInsn(Opcodes.IF_ICMPGE, label5);
        mv.visitVarInsn(Opcodes.ALOAD, 3);
        mv.visitVarInsn(Opcodes.ILOAD, 5);
        mv.visitInsn(Opcodes.AALOAD);
        mv.visitVarInsn(Opcodes.ASTORE, 6);

        Label label3 = new Label();
        mv.visitLabel(label3);
        mv.visitVarInsn(Opcodes.ALOAD, 6);
        mv.visitVarInsn(Opcodes.ALOAD, 2);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraftforge/event/world/ChunkWatchEvent$UnWatch", "getPlayer", "()Lnet/minecraft/entity/player/EntityPlayerMP;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "codechicken/lib/world/WorldExtension", "unwatchChunk", "(Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/entity/player/EntityPlayerMP;)V", false);

        Label label4 = new Label();
        mv.visitLabel(label4);
        mv.visitIincInsn(5,1);
        mv.visitJumpInsn(Opcodes.GOTO, label2);

        mv.visitLabel(label5);
        mv.visitFrame(Opcodes.F_NEW, 6, new Object[]{
                "codechicken/lib/world/WorldExtensionManager$WorldExtensionEventHandler",
                "net/minecraftforge/event/world/ChunkWatchEvent$UnWatch",
                "net/minecraft/world/chunk/Chunk",
                "[Lcodechicken/lib/world/WorldExtension;",
                "I",
                "I"
        },0, null);
        mv.visitInsn(Opcodes.RETURN);

        Label label6 = new Label();
        mv.visitLabel(label6);

        mv.visitLocalVariable("this", "Lcodechicken/lib/world/WorldExtensionManager$WorldExtensionEventHandler;", null, label0, label6, 0);
        mv.visitLocalVariable("event", "Lnet/minecraftforge/event/world/ChunkWatchEvent$UnWatch;", null, label0, label6, 1);
        mv.visitLocalVariable("chunk", "Lnet/minecraft/world/chunk/Chunk;", null, label1, label6, 2);
        mv.visitLocalVariable("extension", "Lcodechicken/lib/world/WorldExtension;", null, label3, label4, 6);

        mv.visitMaxs(6, 7);

        super.visitEnd();
    }
}
