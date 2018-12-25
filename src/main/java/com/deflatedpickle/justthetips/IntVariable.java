package com.deflatedpickle.justthetips;

public final class IntVariable {
    private int value;

    public IntVariable(final int value) {
        this.value = value;
    }

    public boolean set(final int value) {
        return this.value != (this.value = value);
    }

    public int get() {
        return this.value;
    }
}
