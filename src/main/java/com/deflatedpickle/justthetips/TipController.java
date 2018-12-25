package com.deflatedpickle.justthetips;

import net.minecraft.client.gui.ScaledResolution;

public final class TipController {
    private final TipProvider provider;

    private final TipElementFactory.Constructor layoutConstructor;

    private TipController.State state;

    public TipController(final TipProvider provider, final TipElementFactory.Constructor layoutConstructor) {
        this(provider, layoutConstructor, new TipController.UninitializedState());
    }

    private TipController(final TipProvider provider, final TipElementFactory.Constructor layoutConstructor, final TipController.State state) {
        this.provider = provider;
        this.layoutConstructor = layoutConstructor;
        this.state = state;
    }

    public void resize(final ScaledResolution resolution) {
        this.state = new TipController.InitializedState(new DisplayReadyState(this.layoutConstructor.apply(resolution)));
    }

    public void refresh() {
        this.state.refresh();
    }

    public void render() {
        this.state.render();
    }

    private interface DisplayState {
        TipController.DisplayState update();

        void render();
    }

    private final class DisplayReadyState implements TipController.DisplayState {
        private final TipElementFactory layout;

        DisplayReadyState(final TipElementFactory layout) {
            this.layout = layout;
        }

        @Override
        public TipController.DisplayState update() {
            return new DisplayHoldState(this.layout.create(TipController.this.provider.nextTip()));
        }

        @Override
        public void render() {}
    }

    private final class DisplayHoldState implements TipController.DisplayState {
        private final TipElement item;

        DisplayHoldState(final TipElement item) {
            this.item = item;
        }

        @Override
        public TipController.DisplayState update() {
            return this;
        }

        @Override
        public void render() {
            this.item.render();
        }
    }

    private interface State {
        void refresh();

        void render();
    }

    private static final class UninitializedState implements TipController.State {
        UninitializedState() {}

        @Override
        public void refresh() {}

        @Override
        public void render() {}
    }

    @FunctionalInterface
    private interface DriverState {
        TipController.DisplayState update(final TipController.DisplayState state);
    }

    private static final class InitializedState implements TipController.State {
        private final TipController.DriverState riseState;

        private final TipController.DriverState holdState;

        private TipController.DriverState driver;

        private TipController.DisplayState display;

        InitializedState(final TipController.DisplayState display) {
            this(state -> display, state -> state, display);
        }

        private InitializedState(final TipController.DriverState riseState, final TipController.DriverState holdState, final TipController.DisplayState display) {
            this.riseState = riseState;
            this.holdState = holdState;
            this.driver = this.riseState;
            this.display = display;
        }

        @Override
        public void refresh() {
            this.display = this.driver.update(this.display);
            this.driver = this.riseState;
        }

        @Override
        public void render() {
            this.display = this.display.update();
            this.display.render();
            this.driver = this.holdState;
        }
    }
}
