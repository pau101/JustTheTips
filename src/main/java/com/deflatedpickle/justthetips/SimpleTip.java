package com.deflatedpickle.justthetips;

import net.minecraft.util.text.ITextComponent;

public final class SimpleTip implements Tip {
    private final ITextComponent title;

    private final ITextComponent text;

    private SimpleTip(final ITextComponent title, final ITextComponent text) {
        this.title = title;
        this.text = text;
    }

    @Override
    public ITextComponent title() {
        return this.title;
    }

    @Override
    public ITextComponent text() {
        return this.text;
    }

    public static Tip create(final ITextComponent title, final ITextComponent text) {
        return new SimpleTip(title, text);
    }
}
