package com.github.mrmks.mc.lmf.fmp;

import codechicken.multipart.ControlKeyModifer$;
import net.minecraft.entity.player.EntityPlayer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;
import scala.collection.mutable.Map;
import scala.collection.mutable.WeakHashMap;

@Mixin(ControlKeyModifer$.class)
public class MixinControlKeyModifier {

//    @Shadow(remap = false) @Final
//    private Map<EntityPlayer, Boolean> map;

//    @Redirect(method = "<init>", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lcodechicken/multipart/ControlKeyModifer$;map:Lscala/collection/mutable/Map;", remap = false), remap = false)
//    private void setMapValue(@Coerce Object owner, @Coerce Object f) {
//        Map<EntityPlayer, Boolean> m = new WeakHashMap<>();
//        map = m.withDefaultValue(false);
//    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lscala/collection/mutable/Map;withDefaultValue(Ljava/lang/Object;)Lscala/collection/mutable/Map;"), remap = false)
    private Map proxyWithDefaultValue(@Coerce Object callee, @Coerce Object arg) {
        return new WeakHashMap<EntityPlayer, Boolean>().withDefaultValue(Boolean.FALSE);
    }
}
