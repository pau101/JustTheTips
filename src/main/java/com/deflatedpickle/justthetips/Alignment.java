package com.deflatedpickle.justthetips;

public final class Alignment {
    private Alignment() {}

    private enum Placement {
        START {
            @Override
            public int compute(final int size, final int content) {
                return 0;
            }
        },
        MIDDLE {
            @Override
            public int compute(final int size, final int content) {
                return (size - content) / 2;
            }
        },
        END {
            @Override
            public int compute(final int size, final int content) {
                return size - content;
            }
        };

        public abstract int compute(final int size, final int content);
    }

    public enum Horizontal implements AlignFunction {
        LEFT(Alignment.Placement.START),
        CENTER(Alignment.Placement.MIDDLE),
        RIGHT(Alignment.Placement.END);

        private final Alignment.Placement placement;

        Horizontal(final Alignment.Placement placement) {
            this.placement = placement;
        }

        @Override
        public void apply(final Element parent, final Element element) {
            element.setX(parent.getX() + this.placement.compute(parent.getWidth(), element.getWidth()));
        }
    }

    public enum Vertical implements AlignFunction {
        TOP(Alignment.Placement.START),
        CENTER(Alignment.Placement.MIDDLE),
        BOTTOM(Alignment.Placement.END);

        private final Alignment.Placement placement;

        Vertical(final Alignment.Placement placement) {
            this.placement = placement;
        }

        @Override
        public void apply(final Element parent, final Element element) {
            element.setY(parent.getY() + this.placement.compute(parent.getHeight(), element.getHeight()));
        }
    }
}
