package com.deflatedpickle.justthetips;

import com.google.common.collect.ImmutableSet;

public final class SimpleLayout implements Layout {
    private final Element content;

    private final ImmutableSet<AlignFunction> alignments;

    private SimpleLayout(final Element content, final ImmutableSet<AlignFunction> alignments) {
        this.content = content;
        this.alignments = alignments;
    }

    @Override
    public void apply(final Element element) {
        for (final AlignFunction func : this.alignments) {
            func.apply(this.content, element);
        }
    }

    public static Layout.Builder builder() {
        return new SimpleLayout.Builder();
    }

    public static final class Builder implements Layout.Builder {
        private int width = 0;

        private int height = 0;

        private int marginTop = 0;

        private int marginRight = 0;

        private int marginBottom = 0;

        private int marginLeft = 0;

        private final ImmutableSet.Builder<AlignFunction> alignments = ImmutableSet.builder();

        @Override
        public Layout.Builder size(final int width, final int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        @Override
        public Layout.Builder margin(final int all) {
            return this.margin(all, all);
        }

        @Override
        public Layout.Builder margin(final int vertical, final int horizontal) {
            return this.margin(vertical, horizontal, vertical, horizontal);
        }

        @Override
        public Layout.Builder margin(final int marginTop, final int marginRight, final int marginBottom, final int marginLeft) {
            this.marginTop = marginTop;
            this.marginRight = marginRight;
            this.marginBottom = marginBottom;
            this.marginLeft = marginLeft;
            return this;
        }

        @Override
        public Layout.Builder align(final AlignFunction alignment) {
            this.alignments.add(alignment);
            return this;
        }

        @Override
        public Layout build() {
            return new SimpleLayout(
                SimpleElement.create(this.marginLeft, this.marginTop, this.width - this.marginLeft - this.marginRight, this.height - this.marginTop - this.marginBottom),
                this.alignments.build()
            );
        }
    }
}
