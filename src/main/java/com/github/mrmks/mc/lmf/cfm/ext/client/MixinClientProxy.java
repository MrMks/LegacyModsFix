package com.github.mrmks.mc.lmf.cfm.ext.client;

import com.github.mrmks.mc.lmf.cfm.MirrorRenderGlobalAccessor;
import com.mrcrayfish.furniture.proxy.ClientProxy;
import com.mrcrayfish.furniture.proxy.CommonProxy;
import com.mrcrayfish.furniture.render.tileentity.MirrorRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientProxy.class)
public class MixinClientProxy extends CommonProxy {

    @Inject(
            method = "onClientWorldUnload",
            at = @At(value = "INVOKE", target = "Lcom/mrcrayfish/furniture/render/tileentity/MirrorRenderer;clearRegisteredMirrors()V", remap = false),
            remap = false
    )
    private void unloadRenderWorld(CallbackInfo ci) {
        ((MirrorRenderGlobalAccessor) MirrorRenderer.mirrorGlobalRenderer).updateUnloadWorld();
    }
}
