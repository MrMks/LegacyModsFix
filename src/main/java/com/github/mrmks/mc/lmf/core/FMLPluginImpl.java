package com.github.mrmks.mc.lmf.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformerProvider;
import org.spongepowered.asm.service.MixinService;

import java.io.File;
import java.util.Map;

@IFMLLoadingPlugin.Name("LegacyModsFix")
@IFMLLoadingPlugin.TransformerExclusions("com.github.mrmks.mc.lmf.core")
@IFMLLoadingPlugin.MCVersion("1.12.2")
public class FMLPluginImpl implements IFMLLoadingPlugin {

    static {
        // this is used in development, use MixinTweaker in production;
        // init mixin asap
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.legacymodsfix.debug.json");
    }

    static File sourceFile = null;

    @Override
    public String[] getASMTransformerClass() {
        String[] ary = new String[]{
                "com.github.mrmks.mc.lmf.core.DelegateTransformer",
                "com.github.mrmks.mc.lmf.core.VisitorTransformer"
        };
        return excludeDelegate(ary);
    }

    private static String[] excludeDelegate(String[] ary) {
        try {
            IMixinService service = MixinService.getService();
            ITransformerProvider itp = service == null ? null : service.getTransformerProvider();
            if (itp != null) {
                for (String str : ary) itp.addTransformerExclusion(str);
            }
        } catch (NoSuchMethodError error) {
            // ITransformerProvider is not available at this version, maybe 8.0 below
            MixinEnvironment env = MixinEnvironment.getCurrentEnvironment();
            if (env != null) {
                for (String str : ary) env.addTransformerExclusion(str);
            }
        }
        return ary;
    }

    @Override
    public String getModContainerClass() {
        return "com.github.mrmks.mc.lmf.core.LMFModContainer";
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        sourceFile = (File) data.get("coremodLocation");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
