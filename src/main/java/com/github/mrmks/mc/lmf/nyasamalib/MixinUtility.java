package com.github.mrmks.mc.lmf.nyasamalib;

import cn.ac.nya.common.util.Utility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.net.URLConnection;

@Mixin(value = Utility.class, remap = false)
public class MixinUtility {

    @Redirect(method = "GET", at = @At(value = "INVOKE", target = "Ljava/net/URLConnection;connect()V"))
    private static void onConnect(URLConnection connection) throws IOException {
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000);
        connection.connect();
    }

}
