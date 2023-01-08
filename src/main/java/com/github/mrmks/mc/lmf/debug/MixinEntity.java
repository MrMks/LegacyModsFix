package com.github.mrmks.mc.lmf.debug;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Inject(method = "setDead", at = @At("HEAD"))
    private void traceSetDead(CallbackInfo ci) {
        if ((Object)this instanceof EntityLivingBase) {
            //Thread.dumpStack();
        }
    }
}
