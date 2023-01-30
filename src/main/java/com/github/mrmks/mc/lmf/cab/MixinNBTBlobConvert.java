package com.github.mrmks.mc.lmf.cab;

import mod.chiselsandbits.chiseledblock.NBTBlobConverter;
import mod.chiselsandbits.chiseledblock.data.VoxelBlobStateReference;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(NBTBlobConverter.class)
public class MixinNBTBlobConvert {

    private static final String NBT_VERSIONED_FORMAT = "f";

    @Inject(
            method = "writeChisleData",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;setByteArray(Ljava/lang/String;[B)V", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void injectWriteChiselData(NBTTagCompound compound, boolean crossWorld, CallbackInfo ci, VoxelBlobStateReference voxelRef, int newFormat) {
        compound.setInteger(NBT_VERSIONED_FORMAT, newFormat);
    }

    @Redirect(
            method = "readChisleData",
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;getInteger(Ljava/lang/String;)I")
            ),
            at = @At(value = "INVOKE", target = "Lmod/chiselsandbits/chiseledblock/data/VoxelBlobStateReference;getFormat()I", ordinal = 0, remap = false)
    )
    public int redirectGetFormat(VoxelBlobStateReference reference, NBTTagCompound compound) {
        if (compound.hasKey(NBT_VERSIONED_FORMAT, 3)) {
            return compound.getInteger(NBT_VERSIONED_FORMAT);
        } else return reference.getFormat();
    }

}
