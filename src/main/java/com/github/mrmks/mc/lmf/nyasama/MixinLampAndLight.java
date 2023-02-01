package com.github.mrmks.mc.lmf.nyasama;

import club.nsdn.nyasamaoptics.block.BlockAdsorptionLamp;
import club.nsdn.nyasamaoptics.block.BlockFluorescentLamp;
import club.nsdn.nyasamaoptics.block.BlockSpotLight;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(value = {BlockAdsorptionLamp.class, BlockFluorescentLamp.class, BlockSpotLight.class})
public class MixinLampAndLight {

    private static final Map<AxisAlignedBB, AxisAlignedBB> AABBsMap = new ConcurrentHashMap<>();

    @Inject(
            method = "getAABB",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private static void conGetAABB(double x1, double y1, double z1, double x2, double y2, double z2, CallbackInfoReturnable<AxisAlignedBB> cir) {
        AxisAlignedBB aabb = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
        AxisAlignedBB i = AABBsMap.putIfAbsent(aabb, aabb);
        cir.setReturnValue(i);
    }
}
