package com.deflatedpickle.justthetips;

import net.minecraft.util.ResourceLocation;

import java.util.Random;

public interface TipPool {
    ResourceLocation name();

    Tip get(final Random rng);
}
