package com.github.mrmks.mc.lmf.cab;

import mod.chiselsandbits.chiseledblock.data.VoxelBlobStateInstance;
import mod.chiselsandbits.chiseledblock.data.VoxelBlobStateReference;
import mod.chiselsandbits.helpers.IStateRef;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = VoxelBlobStateReference.class, remap = false)
public abstract class MixinVoxelBlobStateReference implements Comparable<VoxelBlobStateReference>, IStateRef {
    @Final @Shadow private VoxelBlobStateInstance data;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public int hashCode() {
        return data.hash;
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public boolean equals(Object obj) {
        return obj instanceof VoxelBlobStateReference && data.equals(((VoxelBlobStateReference) obj).getInstance());
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public int compareTo(@NotNull VoxelBlobStateReference o) {
        return data.compareTo(o.getInstance());
    }


}
