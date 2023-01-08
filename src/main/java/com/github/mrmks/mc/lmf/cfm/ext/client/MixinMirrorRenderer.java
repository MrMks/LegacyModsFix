package com.github.mrmks.mc.lmf.cfm.ext.client;

import com.github.mrmks.mc.lmf.cfm.MirrorRenderGlobalAccessor;
import com.mrcrayfish.furniture.client.MirrorRenderGlobal;
import com.mrcrayfish.furniture.render.tileentity.MirrorRenderer;
import com.mrcrayfish.furniture.tileentity.TileEntityMirror;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MirrorRenderer.class)
public abstract class MixinMirrorRenderer extends TileEntitySpecialRenderer<TileEntityMirror> {

    @Redirect(method = "onTick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;", opcode = Opcodes.PUTFIELD))
    private void putGlobalRender(Minecraft mc, RenderGlobal renderGlobal) {
        if (renderGlobal instanceof MirrorRenderGlobal) {
            ((MirrorRenderGlobalAccessor) renderGlobal).delegateRenderGlobal(mc.renderGlobal);
        } else {
            ((MirrorRenderGlobalAccessor) mc.renderGlobal).delegateRenderGlobal(null);
        }
        mc.renderGlobal = renderGlobal;
    }
}
