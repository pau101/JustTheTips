package com.deflatedpickle.justthetips;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public final class SimpleTipElement implements TipElement {
    private final FontRenderer font;

    private final String title;

    private final String text;

    private int x;

    private int y;

    private SimpleTipElement(final FontRenderer font, final String title, final String text) {
        this(font, title, text, 0, 0);
    }

    private SimpleTipElement(final FontRenderer font, final String title, final String text, final int x, final int y) {
        this.font = font;
        this.title = title;
        this.text = text;
        this.x = x;
        this.y = y;
    }

    @Override
    public void setX(final int x) {
        this.x = x;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public void setY(final int y) {
        this.y = y;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return 120;
    }

    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public void render() {
        this.font.drawString(this.title, this.x, this.y, 0xFFFFFFFF);
        this.font.drawSplitString(this.text, this.x, this.y + this.font.FONT_HEIGHT, 256, 0xFFFFFFFF);
    }

    public static SimpleTipElement create(final Tip value, final FontRenderer font) {
        return new SimpleTipElement(font, value.title().getFormattedText(), value.text().getFormattedText());
    }
}
