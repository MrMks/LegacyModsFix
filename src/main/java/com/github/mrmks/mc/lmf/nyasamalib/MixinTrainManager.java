package com.github.mrmks.mc.lmf.nyasamalib;

import com.google.common.collect.ForwardingMap;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.thewdj.linkage.core.Train;

import java.lang.ref.WeakReference;
import java.util.UUID;

@Mixin(Train.Manager.class)
public abstract class MixinTrainManager extends ForwardingMap<UUID, Train> {

    WeakReference<World> worldRef;

    @Redirect(method = "<init>", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lorg/thewdj/linkage/core/Train$Manager;world:Lnet/minecraft/world/World;"), remap = false)
    private void setWorldRef(@Coerce Object obj, World world) {
        worldRef = new WeakReference<>(world);
    }

    @Redirect(method = "tick", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lorg/thewdj/linkage/core/Train$Manager;world:Lnet/minecraft/world/World;"), remap = false)
    private World getWorldRef(@Coerce Object obj) {
        return worldRef.get();
    }
}
