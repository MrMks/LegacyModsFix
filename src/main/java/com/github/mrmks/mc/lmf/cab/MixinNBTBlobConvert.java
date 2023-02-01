package com.github.mrmks.mc.lmf.cab;

import mod.chiselsandbits.chiseledblock.NBTBlobConverter;
import mod.chiselsandbits.chiseledblock.TileEntityBlockChiseled;
import mod.chiselsandbits.chiseledblock.data.VoxelBlobStateReference;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(NBTBlobConverter.class)
public class MixinNBTBlobConvert {

    @Shadow(remap = false)
    TileEntityBlockChiseled tile;
    private static final String NBT_VERSIONED_FORMAT = "Xf";

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
            at = @At(value = "INVOKE", target = "Lmod/chiselsandbits/chiseledblock/data/VoxelBlobStateReference;getFormat()I", ordinal = 1),
            remap = false
    )
    public int redirectGetFormat(VoxelBlobStateReference reference, NBTTagCompound compound) {
        if (compound.hasKey(NBT_VERSIONED_FORMAT, 3)) {
            int ret = compound.getInteger(NBT_VERSIONED_FORMAT);
            ((VoxelBlobStateInstanceAccessor) (Object) reference.getInstance()).setFormat(ret);
            return ret;
        } else {
            tile.markDirty();
            return reference.getFormat();
        }
    }

    @Redirect(
            method = "readChisleData",
            at = @At(value = "INVOKE", target = "Lmod/chiselsandbits/chiseledblock/data/VoxelBlobStateReference;getFormat()I", ordinal = 0),
            remap = false
    )
    public int redirectGetFormat0(VoxelBlobStateReference reference) {
        return reference.getFormat();
    }

    @Redirect(
            method = "readChisleData",
            at = @At(value = "INVOKE", target = "Lmod/chiselsandbits/chiseledblock/data/VoxelBlobStateReference;getFormat()I", ordinal = 2),
            remap = false
    )
    public int redirectGetFormat2(VoxelBlobStateReference reference) {
        return reference.getFormat();
    }

}
