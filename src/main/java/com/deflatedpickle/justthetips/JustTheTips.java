package com.deflatedpickle.justthetips;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = JustTheTips.ID, useMetadata = true)
public final class JustTheTips {
    public static final String ID = "justthetips";

    public static final Logger LOGGER = LogManager.getLogger(JustTheTips.ID);

    private static final class Holder {
        private static final JustTheTips INSTANCE = new JustTheTips();
    }

    @SidedProxy(
        clientSide = "com.deflatedpickle.justthetips.client.ClientProxy",
        serverSide = "com.deflatedpickle.justthetips.server.ServerProxy"
    )
    private static Proxy proxy;

    @Mod.EventHandler
    public void init(final FMLPreInitializationEvent event) {
        this.requireProxy().init();
    }

    @Mod.InstanceFactory
    public static JustTheTips instance() {
        return JustTheTips.Holder.INSTANCE;
    }

    private Proxy requireProxy() {
        if (JustTheTips.proxy == null) {
            throw new IllegalStateException("Proxy not initialized");
        }
        return JustTheTips.proxy;
    }
}
