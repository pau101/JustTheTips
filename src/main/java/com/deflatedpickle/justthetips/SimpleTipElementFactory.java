package com.deflatedpickle.justthetips;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

import java.util.function.Supplier;

public final class SimpleTipElementFactory implements TipElementFactory {
    private final Layout layout;

    private final Supplier<FontRenderer> font;

    private SimpleTipElementFactory(final Layout layout, final Supplier<FontRenderer> font) {
        this.layout = layout;
        this.font = font;
    }

    @Override
    public TipElement create(final Tip tip) {
        final TipElement component = SimpleTipElement.create(tip, this.font.get());
        this.layout.apply(component);
        return component;
    }

    public static SimpleTipElementFactory create(final ScaledResolution resolution, final Supplier<FontRenderer> font) {
        return new SimpleTipElementFactory(
            SimpleLayout.builder()
                .size(resolution.getScaledWidth(), resolution.getScaledHeight())
                .margin(4)
                .align(Alignment.Horizontal.LEFT)
                .align(Alignment.Vertical.BOTTOM)
                .build(),
            font
        );
    }
}
