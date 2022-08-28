package com.github.mrmks.mc.lmf.ccl;

import codechicken.lib.world.WorldExtension;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WorldExtension.class)
public interface WorldExtensionAccessor {
    @Invoker(remap = false)
    public void callUnwatchChunk(Chunk chunk, EntityPlayerMP player);
}
