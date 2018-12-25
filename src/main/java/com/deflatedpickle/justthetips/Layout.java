package com.deflatedpickle.justthetips;

public interface Layout {
    void apply(final Element element);

    interface Builder {
        Layout.Builder size(final int width, final int height);

        Layout.Builder margin(final int all);

        Layout.Builder margin(final int vertical, final int horizontal);

        Layout.Builder margin(final int top, final int right, final int bottom, final int left);

        Layout.Builder align(final AlignFunction alignment);

        Layout build();
    }
}
