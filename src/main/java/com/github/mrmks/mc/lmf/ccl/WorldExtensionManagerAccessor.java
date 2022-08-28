package com.github.mrmks.mc.lmf.ccl;

import codechicken.lib.world.WorldExtension;
import codechicken.lib.world.WorldExtensionManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WorldExtensionManager.class)
public interface WorldExtensionManagerAccessor {

    @Invoker(value = "getExtensions",remap = false)
    //@Shadow(remap = false)
    public static WorldExtension[] callGetExtensions(World world) {
        return null;
    }

}
