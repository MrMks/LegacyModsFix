package com.github.mrmks.mc.lmf.fmp;

import codechicken.multipart.ControlKeyModifer$;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;
import scala.collection.mutable.Map;
import scala.collection.mutable.WeakHashMap;

@Mixin(ControlKeyModifer$.class)
public class MixinControlKeyModifier {

    @SuppressWarnings("rawtypes")
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lscala/collection/mutable/Map;withDefaultValue(Ljava/lang/Object;)Lscala/collection/mutable/Map;"), remap = false)
    private Map proxyWithDefaultValue(@Coerce Map callee, @Coerce Object arg) {
        return new WeakHashMap<EntityPlayer, Boolean>().withDefaultValue(Boolean.FALSE);
    }
}
