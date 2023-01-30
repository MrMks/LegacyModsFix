package com.github.mrmks.mc.lmf.core;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

import java.io.File;

public class LMFModContainer extends DummyModContainer {
    public LMFModContainer() {
        super(new ModMetadata());
        ModMetadata metadata = getMetadata();
        metadata.modId = "legacy_mod_fix";
        metadata.version = "0.0.6.14";
        metadata.name = "LegacyModFix";
        metadata.screenshots=new String[0];
        metadata.logoFile="";
    }

    @Override
    public File getSource() {
        return FMLPluginImpl.sourceFile;
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        return true;
    }
}
