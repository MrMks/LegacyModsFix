package com.github.mrmks.mc.lmf.nyasamalib;

import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.thewdj.linkage.core.Train;

import java.lang.ref.WeakReference;

@Mixin(Train.class)
public class MixinTrain {
    private WeakReference<World> worldRef;
    @Shadow(remap = false) private World world;

    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    private void onConstruct(CallbackInfo ci) {
        this.worldRef = new WeakReference<>(world);
        this.world = null;
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
