package com.deflatedpickle.justthetips;

import net.minecraft.util.ResourceLocation;

import java.util.Random;

public final class SimpleTipPool implements TipPool {
    private final ResourceLocation name;

    private final ShufflingSet<Tip> tips;

    private SimpleTipPool(final ResourceLocation name, final ShufflingSet<Tip> tips) {
        this.name = name;
        this.tips = tips;
    }

    @Override
    public ResourceLocation name() {
        return this.name;
    }

    @Override
    public Tip get(final Random rng) {
        return this.tips.draw(rng);
    }

    public static SimpleTipPool create(final ResourceLocation name, final ShufflingSet<Tip> tips) {
        return new SimpleTipPool(name, tips);
    }
}
