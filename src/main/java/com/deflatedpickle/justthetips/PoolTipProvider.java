package com.deflatedpickle.justthetips;

import java.util.Random;

public final class PoolTipProvider implements TipProvider {
    private final ShufflingSet<TipPool> providers;

    private final Random rng;

    private PoolTipProvider(final ShufflingSet<TipPool> providers, final Random rng) {
        this.providers = providers;
        this.rng = rng;
    }

    @Override
    public Tip next() {
        return this.providers.draw(this.rng).get(this.rng);
    }

    public static PoolTipProvider create(final ShufflingSet<TipPool> providers, final Random rng) {
        return new PoolTipProvider(providers, rng);
    }
}
