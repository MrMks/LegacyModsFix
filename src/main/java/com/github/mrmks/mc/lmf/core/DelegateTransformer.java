package com.github.mrmks.mc.lmf.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DelegateTransformer implements IClassTransformer {

    private final boolean[] flags;
    private final String[] klass;
    private final String[] json;
    private final int size;
    private int count;

    private static final String[] KLASS = {
            "club.nsdn.nyasamarailway.NyaSamaRailway",
            "cn.ac.nya.NyaSamaLib",
            "thut.api.entity.ai.AIThreadManager",
            "codechicken.multipart.handler.MultipartMod",
            "codechicken.lib.CodeChickenLib",
            "com.mrcrayfish.furniture.MrCrayfishFurnitureMod",
            "mod.chiselsandbits.core.ChiselsAndBits",
            "moe.plushie.armourers_workshop.ArmourersWorkshop",
            "com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid"
    };
    private static final String[] JSON = {
            "nyasama",
            "nyasamalib",
            "thutcore",
            "fmp",
            "ccl",
            "cfm",
            "cab",
            "amws",
            "maid"
    };

    public DelegateTransformer() {

        if (KLASS.length != JSON.length) throw new IllegalStateException("UNMATCHED SIZE between KLASS and JSON");

        count = 0;
        size = KLASS.length;
        this.klass = KLASS;
        this.json = JSON;
        this.flags = new boolean[size];
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {

        if (count < size) {
            for (int i = 0; i < size; i++) {
                if (!flags[i] && klass[i].equals(transformedName)) {
                    flags[i] = true;
                    count++;

                    Mixins.addConfiguration("mixins.legacymodsfix." + json[i] + ".json");
                    rerunMixin();
                    break;
                }
            }
        }

        return basicClass;
    }

    private static void rerunMixin() {
        try {

            // We have to use reflections here to let configs be parsed.
            // To be clear, re-select the currentEnvironment is allowed
            // if the processor transformed no classes but there are still
            // some classes are prepared.
            // It is exactly what checkSelect does.
            // Then what if transformed classes is 0? It should don't matter.

            MixinEnvironment env = MixinEnvironment.getCurrentEnvironment();
            Object tmp = env.getActiveTransformer();

            if (tmp == null) return;
            Class<?> klass = tmp.getClass(); //Class.forName("org.spongepowered.asm.mixin.transformer.MixinTransformer");
            Field field = klass.getDeclaredField("processor");

            field.setAccessible(true);
            tmp = field.get(tmp);

            if (tmp == null) return;
            klass = tmp.getClass(); //Class.forName("org.spongepowered.asm.mixin.transformer.MixinProcessor");
            Method method = klass.getDeclaredMethod("select", MixinEnvironment.class);

            method.setAccessible(true);
            method.invoke(tmp, env);
        } catch (NoSuchMethodException | NoSuchFieldException | InvocationTargetException | IllegalAccessException e) {
            // no-op
        }
    }


}
