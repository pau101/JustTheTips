package com.deflatedpickle.justthetips;

import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.shader.Framebuffer;

import java.lang.invoke.MethodHandle;

public final class LoadingScreenWrapper {
    private static final class Handles {
        private static final MethodHandle FB_SETTER;

        private static final MethodHandle FB_GETTER;

        static {
            final Fields.Result result = Fields.lookup(LoadingScreenRenderer.class, "field_146588_g", "framebuffer");
            FB_SETTER = result.setter();
            FB_GETTER = result.getter();
        }
    }

    private final LoadingScreenRenderer instance;

    private LoadingScreenWrapper(final LoadingScreenRenderer instance) {
        this.instance = instance;
    }

    public void setFramebuffer(final Framebuffer framebuffer) {
        try {
            LoadingScreenWrapper.Handles.FB_SETTER.invokeExact(this.instance, framebuffer);
        } catch (final Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public Framebuffer getFramebuffer() {
        try {
            return (Framebuffer) LoadingScreenWrapper.Handles.FB_GETTER.invokeExact(this.instance);
        } catch (final Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public static LoadingScreenWrapper wrap(final LoadingScreenRenderer instance) {
        return new LoadingScreenWrapper(instance);
    }
}