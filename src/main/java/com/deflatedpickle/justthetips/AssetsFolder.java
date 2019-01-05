package com.deflatedpickle.justthetips;

import com.google.common.io.Closeables;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.ModContainer;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.ProviderNotFoundException;
import java.util.Collections;

public final class AssetsFolder implements AutoCloseable {
    private final Path root;

    private final boolean close;

    private AssetsFolder(final Path root, final boolean close) {
        this.root = root;
        this.close = close;
    }

    public Path getPath(final String first, final String... more) {
        return this.root.resolve(this.root.getFileSystem().getPath(first, more));
    }

    public PathMatcher getPathMatcher(final String syntaxAndPattern) {
        return this.root.getFileSystem().getPathMatcher(syntaxAndPattern);
    }

    @Override
    public void close() {
        if (this.close) {
            try {
                Closeables.close(this.root.getFileSystem(), true);
            } catch (final IOException e) {
                throw new AssertionError(e);
            }
        }
    }

    public static AssetsFolder create(final ModContainer container) {
        final URI base;
        if ("minecraft".equals(container.getModId())) {
            base = AssetsFolder.getMinecraftAssetsRoot();
        } else {
            final File file = container.getSource();
            if (file == null) {
                throw new NullPointerException(String.format("Missing mod source for '%s'", container.getModId()));
            }
            base = file.toURI();
        }
        final Path parent;
        final boolean close;
        if ("file".equals(base.getScheme())) {
            try {
                parent = Paths.get(base);
            } catch (final IllegalArgumentException e) {
                throw new RuntimeException(String.format("Bad mod source for '%s'", container.getModId()), e);
            }
            close = false;
        } else {
            final FileSystem fs;
            try {
                fs = FileSystems.newFileSystem(base, Collections.emptyMap());
            } catch (final IllegalArgumentException e) {
                throw new RuntimeException(String.format("Bad mod source for '%s'", container.getModId()), e);
            } catch (final ProviderNotFoundException e) {
                throw new RuntimeException(String.format("Unsupported scheme '%s' for '%s'", base.getScheme(), container.getModId()));
            } catch (final IOException e) {
                throw new RuntimeException(String.format("Unable to open file system for '%s'", container.getModId()), e);
            }
            parent = fs.getPath(fs.getSeparator());
            close = true;
        }
        final Path assets;
        try {
            assets = parent.getFileSystem().getPath("assets", container.getModId());
        } catch (final InvalidPathException e) {
            if (close) {
                try {
                    Closeables.close(parent.getFileSystem(), true);
                } catch (final IOException e1) {
                    throw new AssertionError(e1);
                }
            }
            throw new RuntimeException(String.format("Bad mod id '%s'", container.getModId()), e);
        }
        return new AssetsFolder(parent.resolve(assets), close);
    }

    private static URI getMinecraftAssetsRoot() {
        final URL url = MinecraftServer.class.getResource("/assets/.mcassetsroot");
        if (url == null) {
            throw new NullPointerException("Missing minecraft assets root");
        }
        try {
            return url.toURI();
        } catch (final URISyntaxException e) {
            throw new RuntimeException("Bad minecraft assets root", e);
        }
    }
}
