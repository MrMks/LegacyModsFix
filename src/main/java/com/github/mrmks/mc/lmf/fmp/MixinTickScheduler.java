package com.github.mrmks.mc.lmf.fmp;

import codechicken.lib.world.WorldExtension;
import codechicken.multipart.TickScheduler;
import codechicken.multipart.TickScheduler$;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.io.File;

@Mixin(TickScheduler.WorldTickScheduler.class)
public abstract class MixinTickScheduler extends WorldExtension {
    public MixinTickScheduler(World world) {
        super(world);
    }

    /**
     * @author MrMks
     * @reason let multipart.dat file be saved in correct folder.
     */
    @Overwrite(remap = false)
    public File saveDir() {
        World w = world;
        if (w instanceof WorldServer) return ((WorldServer) w).getChunkSaveLocation();

        if (w.provider.getDimension() == 0) return TickScheduler$.MODULE$.serverDir();
        else {
            return new File(TickScheduler$.MODULE$.serverDir(), w.provider.getSaveFolder());
        }
    }
}
