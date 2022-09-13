package com.github.mrmks.mc.lmf.thutcore;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thut.api.entity.ai.AIThreadManager;

import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(AIThreadManager.class)
public class MixinAIThreadManager {

    @Shadow(remap = false) @Final
    public static HashMap<Integer, Vector<Object>> worldPlayers;
    @Shadow(remap = false) @Final
    public static ConcurrentHashMap<Integer, Vector<Entity>> worldEntities;

    @SubscribeEvent
    public void unloadWorld(WorldEvent.Unload evt) {
        if (evt.getWorld() == null) return;
        Integer dim = evt.getWorld().provider.getDimension();

        worldPlayers.remove(dim);
        worldEntities.remove(dim);
    }

    @Inject(method = "clear", at = @At(value = "INVOKE", target = "Lthut/api/TickHandler;getInstance()Lthut/api/TickHandler;", remap = false), remap = false)
    private static void clearEntities(CallbackInfo ci) {
        worldEntities.clear();
    }

}
