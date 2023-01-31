package com.github.mrmks.mc.lmf.cab;

import mod.chiselsandbits.chiseledblock.data.VoxelBlobStateInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VoxelBlobStateInstance.class)
public interface VoxelBlobStateInstanceAccessor {
    @Accessor(value = "format", remap = false)
    void setFormat(int f);
}
