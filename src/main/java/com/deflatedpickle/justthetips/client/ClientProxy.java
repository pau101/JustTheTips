package com.deflatedpickle.justthetips.client;

import com.deflatedpickle.justthetips.*;
import com.deflatedpickle.justthetips.ForwardingFramebuffer;
import com.deflatedpickle.justthetips.LoadingScreenWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.mutable.MutableInt;

public final class ClientProxy extends Proxy {
    @Override
    public void init() {
        super.init();
        final MutableInt count = new MutableInt(1);
        final TipController controller = new TipController(
            () -> SimpleTip.create(
                new TextComponentString("Tip #" + count.getAndIncrement())
                    .setStyle(new Style().setColor(TextFormatting.YELLOW)),
                new TextComponentString("Don't dig straight down")
            ),
            res -> SimpleTipElementFactory.create(res, () -> Minecraft.getMinecraft().fontRenderer)
        );
        MinecraftForge.EVENT_BUS.register(WindowResizeListener.create(resolution -> {
            final Minecraft mc = Minecraft.getMinecraft();
            final LoadingScreenWrapper wrapper = LoadingScreenWrapper.wrap(mc.loadingScreen);
            controller.resize(resolution);
            wrapper.setFramebuffer(new ForwardingFramebuffer(wrapper.getFramebuffer()) {
                @Override
                public void unbindFramebuffer() {
                    controller.render();
                    super.unbindFramebuffer();
                }
            });
        }));
        MinecraftForge.EVENT_BUS.register(new Object() {
            @SubscribeEvent
            public void onGameRender(final TickEvent.RenderTickEvent event) {
                if (event.phase == TickEvent.Phase.END) {
                    controller.refresh();
                }
            }
        });
        MinecraftForge.EVENT_BUS.register(new Object() {
            private final TipScreenPredicate isTipScreen = new TipScreenPredicate();

            // TODO: Use BackgroundDrawnEvent when fixed for all backgrounds
            @SubscribeEvent
            public void onBackgroundRender(final GuiScreenEvent.DrawScreenEvent.Post event) {
                if (this.isTipScreen.test(event.getGui())) {
                    controller.render();
                }
            }
        });
    }
}