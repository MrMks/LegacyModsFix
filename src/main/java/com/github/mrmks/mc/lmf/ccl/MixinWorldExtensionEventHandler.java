package com.github.mrmks.mc.lmf.ccl;

import codechicken.lib.world.WorldExtension;
import codechicken.lib.world.WorldExtensionManager;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldExtensionManager.WorldExtensionEventHandler.class)
public abstract class MixinWorldExtensionEventHandler {

    @Redirect(method = "onChunkUnLoad", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/world/World;isRemote:Z"))
    private boolean getIsRemoteValue(World world) {
        return true;
    }

    /**
     * Add this method back on server side then players can unwatch those chunks
     * <p>
     */
    @SubscribeEvent
    @Unique(silent = true)
    public void onChunkUnWatch(ChunkWatchEvent.UnWatch event) {
        Chunk chunk = event.getChunkInstance();

        if (chunk == null) return;
        WorldExtension[] var3 = WorldExtensionManagerAccessor.callGetExtensions(event.getPlayer().world);

        if (var3 == null) return;
        for (WorldExtension extension : var3) {
            ((WorldExtensionAccessor) extension).callUnwatchChunk(chunk, event.getPlayer());
        }
    }
}
