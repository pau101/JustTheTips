package com.deflatedpickle.justthetips;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.function.Consumer;

public final class WindowResizeListener {
    private final Consumer<ScaledResolution> action;

    private final IntVariable width;

    private final IntVariable height;

    public WindowResizeListener(final Consumer<ScaledResolution> action) {
        this(action, new IntVariable(0), new IntVariable(0));
    }

    private WindowResizeListener(final Consumer<ScaledResolution> action, final IntVariable width, final IntVariable height) {
        this.action = action;
        this.width = width;
        this.height = height;
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (event.phase == TickEvent.Phase.END && (this.width.set(mc.displayWidth) | this.height.set(mc.displayHeight))) {
            this.action.accept(new ScaledResolution(mc));
        }
    }

    public static WindowResizeListener create(final Consumer<ScaledResolution> action) {
        return new WindowResizeListener(action);
    }
}
