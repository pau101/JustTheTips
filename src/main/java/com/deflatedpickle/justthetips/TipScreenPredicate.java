package com.deflatedpickle.justthetips;

import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.multiplayer.GuiConnecting;

import java.util.function.Predicate;

public final class TipScreenPredicate implements Predicate<GuiScreen> {
    @Override
    public boolean test(final GuiScreen screen) {
        return screen instanceof GuiDownloadTerrain ||
            screen instanceof GuiScreenWorking ||
            screen instanceof GuiConnecting ||
            screen instanceof GuiDisconnected;
    }
}
