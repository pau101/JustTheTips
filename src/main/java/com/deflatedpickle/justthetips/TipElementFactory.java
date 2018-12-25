package com.deflatedpickle.justthetips;

import net.minecraft.client.gui.ScaledResolution;

import java.util.function.Function;

public interface TipElementFactory {
    TipElement create(final Tip tip);

    interface Constructor extends Function<ScaledResolution, TipElementFactory> {}
}
