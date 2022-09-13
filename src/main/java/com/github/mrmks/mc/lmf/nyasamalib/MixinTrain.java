package com.github.mrmks.mc.lmf.nyasamalib;

import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.thewdj.linkage.core.Train;

import java.lang.ref.WeakReference;

@Mixin(Train.class)
public class MixinTrain {
    private WeakReference<World> worldRef;

    @Redirect(method = "<init>(Lnet/minecraft/entity/item/EntityMinecart;)V", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lorg/thewdj/linkage/core/Train;world:Lnet/minecraft/world/World;"), remap = false)
    private void setWorldValueInit(Train train, World world) {
        this.worldRef = new WeakReference<>(world);
    }

    @Redirect(method = {"lambda$get$0", "lambda$getTrainRaw$2", "access$302"}, at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lorg/thewdj/linkage/core/Train;world:Lnet/minecraft/world/World;"), remap = false)
    private static void setWorldValue(Train train, World world) {
        ((MixinTrain)(Object) train).worldRef = new WeakReference<>(world);
    }

    @Redirect(method = "getCart", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lorg/thewdj/linkage/core/Train;world:Lnet/minecraft/world/World;"), remap = false)
    private World getCartGetWorld(Train train) {
        return worldRef.get();
    }

}
