package com.github.mrmks.mc.lmf.cab;

import mod.chiselsandbits.chiseledblock.data.VoxelBlobStateInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(value = VoxelBlobStateInstance.class, remap = false)
public abstract class MixinVoxelBlobStateInstance implements Comparable<VoxelBlobStateInstance> {

    @Final
    @Shadow
    public byte[] voxelBytes;

    @Shadow
    private int format;
    private boolean fastFlag = true;

    @Inject(
            method = "getFormat",
            at = @At("HEAD")
    )
    private void fastFormat(CallbackInfoReturnable<Integer> ci) {
        if (format == Integer.MIN_VALUE) {
            if (voxelBytes != null && voxelBytes.length >= 3) {
                switch (voxelBytes[3]) {
                    case 96: format = 0; break;
                    case 100: format = 1; break;
                    case 98: format = 2; break;
                    default: {
                        fastFlag = false;
                    }
                }
            }
        }
    }

    @Inject(
            method = "getFormat",
            at = @At("RETURN")
    )
    private void detectFastFormat(CallbackInfoReturnable<Integer> ci) {
        if (!fastFlag) {
            System.out.println("format of " + Arrays.toString(voxelBytes) + "is " + format);
            fastFlag = true;
        }
    }
}
