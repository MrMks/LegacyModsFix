package com.github.mrmks.mc.lmf.nyasama.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "club/nsdn/nyasamarailway/network/PacketStCHandler", remap = false)
@Pseudo
public class MixinPacketStCHandler {

    @Shadow public static EntityPlayer player;

    @Redirect(method = "onMessage", at = @At(value = "FIELD", target = "Lclub/nsdn/nyasamarailway/network/PacketStCHandler;player:Lnet/minecraft/entity/player/EntityPlayer;", opcode = Opcodes.GETSTATIC))
    private EntityPlayer getFieldValue() {
        return Minecraft.getMinecraft().player;
    }

    @Inject(method = "lambda$onMessage$0", at = @At("HEAD"), cancellable = true)
    private void onLambdaHead(CallbackInfo ci) {
        player = Minecraft.getMinecraft().player;
        if (player == null) ci.cancel();
    }

    @Inject(method = "lambda$onMessage$0", at = @At("TAIL"))
    private void onLambdaTail(CallbackInfo ci) {
        player = null;
    }
}
