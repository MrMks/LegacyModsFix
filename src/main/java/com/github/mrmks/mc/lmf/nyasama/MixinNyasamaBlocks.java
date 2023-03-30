package com.github.mrmks.mc.lmf.nyasama;

import club.nsdn.nyasamaelectricity.block.BlockCatenary;
import club.nsdn.nyasamaelectricity.block.BlockHVSign;
import club.nsdn.nyasamaelectricity.block.BlockInsulator;
import club.nsdn.nyasamaelectricity.block.BlockShelf;
import club.nsdn.nyasamaoptics.block.BlockAdsorptionLamp;
import club.nsdn.nyasamaoptics.block.BlockFluorescentLamp;
import club.nsdn.nyasamaoptics.block.BlockSpotLight;
import club.nsdn.nyasamarailway.block.BlockGlassShield;
import club.nsdn.nyasamarailway.block.BlockGlassShieldCorner;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(value = {
        BlockAdsorptionLamp.class, // optics
        BlockFluorescentLamp.class,
        BlockSpotLight.class,
        BlockCatenary.class, // electricity
        BlockHVSign.class,
        BlockInsulator.class,
        BlockShelf.class,
        BlockGlassShield.class, // railway
        BlockGlassShieldCorner.class
})
public class MixinNyasamaBlocks extends Block {

    private static final Map<AxisAlignedBB, AxisAlignedBB> AABBsMap = new ConcurrentHashMap<>();

    public MixinNyasamaBlocks(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Inject(
            method = "getAABB",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private static void conGetAABB(double x1, double y1, double z1, double x2, double y2, double z2, CallbackInfoReturnable<AxisAlignedBB> cir) {
        AxisAlignedBB aabb = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
        AxisAlignedBB i = AABBsMap.putIfAbsent(aabb, aabb);
        cir.setReturnValue(i == null ? aabb : i);
    }
}
