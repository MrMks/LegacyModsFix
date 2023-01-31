package com.github.mrmks.mc.lmf.cab;

import mod.chiselsandbits.render.chiseledblock.ChiseledBlockBaked;
import mod.chiselsandbits.render.helpers.ModelQuadLayer;
import mod.chiselsandbits.render.helpers.ModelUtil;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(value = ModelUtil.class, remap = false)
public class MixinModelUtil {
    private static final Map<Integer, ModelQuadLayer[]> conCache = new ConcurrentHashMap<>();
    private static final Map<Integer, ChiseledBlockBaked> conBreakCache = new ConcurrentHashMap<>();

    @Inject(
            method = "clearCache",
            at = @At("RETURN")
    )
    private void clearCache(CallbackInfo ci) {
        conCache.clear();
        conBreakCache.clear();
    }

    @Redirect(
            method = "getBreakingModel",
            at = @At(value = "INVOKE", target = "Ljava/util/HashMap;get(Ljava/lang/Object;)Ljava/lang/Object;")
    )
    private static <K, V> Object getOnBreakCache(HashMap<K, V> map, Object key) {
        return conBreakCache.get((Integer) key);
    }

    @Redirect(
            method = "getBreakingModel",
            at = @At(value = "INVOKE", target = "Ljava/util/HashMap;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;")
    )
    private static <K, V> Object putOnBreakCache(HashMap<K, V> map, Object k, Object v) {
        return conBreakCache.put((Integer) k, (ChiseledBlockBaked) v);
    }

    @Redirect(
            method = {"getCachedFace", "getInnerCachedFace"},
            slice = @Slice(
                    from = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lmod/chiselsandbits/render/helpers/ModelUtil;cache:Ljava/util/HashMap;"),
                    to = @At(value = "INVOKE", opcode = Opcodes.INVOKEDYNAMIC, target = "Ljava/util/HashMap;get(Ljava/lang/Object;)Ljava/lang/Object;")
            ),
            at = @At(value = "INVOKE", target = "Ljava/util/HashMap;get(Ljava/lang/Object;)Ljava/lang/Object;")
    )
    private static <K, V> Object getOnCache(HashMap<K, V> map, K key) {
        return conCache.get((Integer) key);
    }

    @Redirect(
            method = "getInnerCachedFace",
            at = @At(value = "INVOKE", target = "Ljava/util/HashMap;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 0)
    )
    private static <K, V> Object putOnCache0(HashMap<K, V> instance, K k, V v) {
        return conCache.put((Integer) k, (ModelQuadLayer[]) v);
    }

    @Redirect(
            method = "getInnerCachedFace",
            at = @At(value = "INVOKE", target = "Ljava/util/HashMap;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 2)
    )
    private static <K, V> Object putOnCache2(HashMap<K, V> instance, K k, V v) {
        return conCache.put((Integer) k, (ModelQuadLayer[]) v);
    }
}
