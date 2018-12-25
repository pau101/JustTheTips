package com.deflatedpickle.justthetips;

import net.minecraft.client.shader.Framebuffer;

public abstract class ForwardingFramebuffer extends Framebuffer {
    private final Framebuffer delegate;

    protected ForwardingFramebuffer(final Framebuffer delegate) {
        super(delegate.framebufferWidth, delegate.framebufferHeight, delegate.useDepth);
        this.delegate = delegate;
    }

    @Override
    public void createBindFramebuffer(final int width, final int height) {}

    @Override
    public void deleteFramebuffer() {
        this.delegate.deleteFramebuffer();
    }

    @Override
    public void createFramebuffer(final int width, final int height) {
        this.delegate.createFramebuffer(width, height);
    }

    @Override
    public void setFramebufferFilter(final int filter) {
        this.delegate.setFramebufferFilter(filter);
    }

    @Override
    public void checkFramebufferComplete() {
        this.delegate.checkFramebufferComplete();
    }

    @Override
    public void bindFramebufferTexture() {
        this.delegate.bindFramebufferTexture();
    }

    @Override
    public void unbindFramebufferTexture() {
        this.delegate.unbindFramebufferTexture();
    }

    @Override
    public void bindFramebuffer(final boolean setViewport) {
        this.delegate.bindFramebuffer(setViewport);
    }

    @Override
    public void unbindFramebuffer() {
        this.delegate.unbindFramebuffer();
    }

    @Override
    public void setFramebufferColor(final float red, final float green, final float blue, final float alpha) {
        this.delegate.setFramebufferColor(red, green, blue, alpha);
    }

    @Override
    public void framebufferRender(final int width, final int height) {
        this.delegate.framebufferRender(width, height);
    }

    @Override
    public void framebufferRenderExt(final int width, final int height, final boolean disableBlend) {
        this.delegate.framebufferRenderExt(width, height, disableBlend);
    }

    @Override
    public void framebufferClear() {
        this.delegate.framebufferClear();
    }

    @Override
    public boolean enableStencil() {
        return this.delegate.enableStencil();
    }

    @Override
    public boolean isStencilEnabled() {
        return this.delegate.isStencilEnabled();
    }
}
