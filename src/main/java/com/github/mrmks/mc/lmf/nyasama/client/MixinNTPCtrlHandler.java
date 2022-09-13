package com.github.mrmks.mc.lmf.nyasama.client;

import club.nsdn.nyasamarailway.event.NTPCtrlHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = NTPCtrlHandler.class, remap = false)
public class MixinNTPCtrlHandler {
    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lclub/nsdn/nyasamarailway/network/PacketStCHandler;player:Lnet/minecraft/entity/player/EntityPlayer;", opcode = Opcodes.GETSTATIC))
    private EntityPlayer getFieldValue() {
        return Minecraft.getMinecraft().player;
    }
}
