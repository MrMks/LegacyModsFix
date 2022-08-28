package com.github.mrmks.mc.lmf.ccl;

import codechicken.lib.world.WorldExtension;
import codechicken.lib.world.WorldExtensionManager;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldExtensionManager.WorldExtensionEventHandler.class)
public abstract class MixinWorldExtensionEventHandler {

    @Redirect(method = "onChunkUnLoad", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/world/World;isRemote:Z"))
    private boolean getIsRemoteValue(World world) {
        return true;
    }

    /**
     * @author mrmks
     * @reason remove players from the Map to release memories.
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public void onChunkUnWatch(ChunkWatchEvent.UnWatch event) {
        Chunk chunk = event.getPlayer().world.getChunkFromChunkCoords(event.getChunk().x, event.getChunk().z);
        WorldExtension[] var3 = WorldExtensionManagerAccessor.callGetExtensions(event.getPlayer().world);

        for (WorldExtension extension : var3) {
            ((WorldExtensionAccessor) extension).callUnwatchChunk(chunk, event.getPlayer());
        }
    }
}
