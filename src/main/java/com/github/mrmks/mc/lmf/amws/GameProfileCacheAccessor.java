package com.github.mrmks.mc.lmf.amws;

import com.mojang.authlib.GameProfile;
import moe.plushie.armourers_workshop.common.GameProfileCache;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.HashMap;

@Mixin(value = GameProfileCache.class, remap = false)
public interface GameProfileCacheAccessor {

    @Accessor
    public static HashMap<String, GameProfile> getDownloadedCache() {
        throw new UnsupportedOperationException();
    }

    @Accessor
    public static ArrayList<GameProfileCache.WaitingClient> getWaitingClients() {
        throw new UnsupportedOperationException();
    }

    @Invoker
    public static void callSendProfileToClient(EntityPlayerMP player, GameProfile profile) {
        throw new UnsupportedOperationException();
    }

}
