package com.github.mrmks.mc.lmf.amws;

import com.mojang.authlib.GameProfile;
import moe.plushie.armourers_workshop.common.GameProfileCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@Mixin(GameProfileCache.ProfileDownloadThread.class)
public abstract class MixinProfileDownloadThread implements Runnable {

    @Shadow(remap = false)
    private GameProfile gameProfile;
    @Shadow(remap = false)
    private GameProfileCache.IGameProfileCallback callback;

    @Shadow(remap = false)
    protected abstract GameProfile fillProfileProperties(GameProfile gameProfile);

    /**
     * @author mrmks
     * @reason profiles may be cached for a really long time if fillProfileProperties throws Exceptions
     */
    @Overwrite(remap = false)
    public void run() {
        final HashMap<String, GameProfile> localDownloadedCache = GameProfileCacheAccessor.getDownloadedCache();
        final ArrayList<GameProfileCache.WaitingClient> localWaitingClients = GameProfileCacheAccessor.getWaitingClients();
        try {
            GameProfile newProfile = fillProfileProperties(gameProfile);
            if (newProfile != null) {
                synchronized (localDownloadedCache) {
                    localDownloadedCache.put(newProfile.getName(), newProfile);
                }
                if (callback != null) {
                    callback.profileDownloaded(newProfile);
                }
                synchronized (localWaitingClients) {
                    Iterator<GameProfileCache.WaitingClient> it = localWaitingClients.iterator();
                    while (it.hasNext()) {
                        GameProfileCache.WaitingClient wc = it.next();
                        if (wc.getProfileName().equals(gameProfile.getName())) {
                            GameProfileCacheAccessor.callSendProfileToClient(wc.getEntityPlayer(), newProfile);
                            it.remove();
                        }
                    }
                }
            }
        } catch (RuntimeException re) {
            synchronized (localWaitingClients) {
                localWaitingClients.removeIf(waitingClient -> waitingClient.getProfileName().equals(gameProfile.getName()));
            }
            throw re;
        }
    }

}
