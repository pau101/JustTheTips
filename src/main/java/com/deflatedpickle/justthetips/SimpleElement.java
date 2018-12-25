package com.deflatedpickle.justthetips;

public final class SimpleElement implements Element {
    private int x;

    private int y;

    private final int width;

    private final int height;

    public SimpleElement(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public static SimpleElement create(final int x, final int y, final int width, final int height) {
        return new SimpleElement(x, y, width, height);
    }
}
