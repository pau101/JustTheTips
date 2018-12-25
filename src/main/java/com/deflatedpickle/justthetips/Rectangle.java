package com.deflatedpickle.justthetips;

public final class Rectangle implements Element {
    private final int width;

    private final int height;

    private int x;

    private int y;

    private Rectangle(final int width, final int height) {
        this(width, height, 0, 0);
    }

    private Rectangle(final int width, final int height, final int x, final int y) {
        this.width = width;
        this.height = height;
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
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public Rectangle pad(final int amount) {
        return new Rectangle(this.getWidth() - amount * 2, this.getHeight() - amount * 2, this.getX() + amount, this.getY() + amount);
    }

    public static Rectangle create(final int width, final int height) {
        return new Rectangle(width, height);
    }

    public static Rectangle create(final int width, final int height, final int x, final int y) {
        return new Rectangle(width, height, x, y);
    }
}
