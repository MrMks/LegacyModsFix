package com.github.mrmks.mc.lmf.cfm.ext.client;

import com.github.mrmks.mc.lmf.cfm.MirrorRenderGlobalAccessor;
import com.mrcrayfish.furniture.client.MirrorRenderGlobal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MirrorRenderGlobal.class)
public abstract class MixinMirrorRenderGlobal extends RenderGlobal implements MirrorRenderGlobalAccessor {

    public MixinMirrorRenderGlobal(Minecraft mcIn) {
        super(mcIn);
    }

    private boolean firstLoad = true;
    private WorldClient world = null;

    @Override
    public void setWorldAndLoadRenderers(@Nullable WorldClient worldClientIn) {
        if (firstLoad) {
            super.setWorldAndLoadRenderers(worldClientIn);
        } else {
            this.world = worldClientIn;
        }
    }

    public void updateUnloadWorld() {
        firstLoad = world == null;
        super.setWorldAndLoadRenderers(world);
        world = null;
    }

}
