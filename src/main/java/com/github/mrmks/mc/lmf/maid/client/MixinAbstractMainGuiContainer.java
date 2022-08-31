package com.github.mrmks.mc.lmf.maid.client;

import com.github.tartaricacid.touhoulittlemaid.client.gui.inventory.AbstractMaidGuiContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Mixin(AbstractMaidGuiContainer.class)
public abstract class MixinAbstractMainGuiContainer extends GuiContainer {

    private Future<?> timerTask;

    public MixinAbstractMainGuiContainer(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Redirect(method = "syncEffectThread", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/ScheduledExecutorService;scheduleAtFixedRate(Ljava/lang/Runnable;JJLjava/util/concurrent/TimeUnit;)Ljava/util/concurrent/ScheduledFuture;"), remap = false)
    private ScheduledFuture<?> onCallSchedule(ScheduledExecutorService service, Runnable runnable, long arg0, long arg1, TimeUnit timeUnit) {
        ScheduledFuture<?> r = service.scheduleAtFixedRate(runnable, arg0, arg1, timeUnit);
        timerTask = r;
        return r;
    }

    @Redirect(method = "onGuiClosed", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/ScheduledExecutorService;shutdownNow()Ljava/util/List;"))
    private List<Runnable> onShutdownNow(ScheduledExecutorService service) {
        timerTask.cancel(true);
        timerTask = null;
        service.shutdown();
        return null;
    }

}
