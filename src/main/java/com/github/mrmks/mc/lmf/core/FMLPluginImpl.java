package com.github.mrmks.mc.lmf.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformerProvider;
import org.spongepowered.asm.service.MixinService;

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

    @Override
    public String[] getASMTransformerClass() {
        String[] ary = new String[]{
                "com.github.mrmks.mc.lmf.core.DelegateTransformer"
        };
        return excludeDelegate(ary);
    }

    private static String[] excludeDelegate(String[] ary) {
        IMixinService service = MixinService.getService();
        ITransformerProvider itp = service == null ? null : service.getTransformerProvider();
        if (itp != null) {
            for (String str : ary) itp.addTransformerExclusion(str);
        }
        return ary;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
