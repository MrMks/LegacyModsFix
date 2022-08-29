package com.github.mrmks.mc.lmf.cab;

import mod.chiselsandbits.network.ModPacket;
import mod.chiselsandbits.network.ModPacketTypes;
import mod.chiselsandbits.network.NetworkRouter;
import net.minecraft.network.PacketBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(NetworkRouter.class)
public class MixinNetworkRouter {

    /**
     * @author mrmks
     * @reason release the buffer
     */
    @Overwrite(remap = false)
    private ModPacket parsePacket(PacketBuffer buffer) {
        int id = buffer.readByte();

        try {
            ModPacket packet = ModPacketTypes.constructByID(id);
            packet.readPayload(buffer);
            return packet;
        } catch (InstantiationException var4) {
            throw new RuntimeException(var4);
        } catch (IllegalAccessException var5) {
            throw new RuntimeException(var5);
        } finally {
            buffer.release();
        }
    }

}
