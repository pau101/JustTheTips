package com.deflatedpickle.justthetips;

import com.google.common.collect.Maps;
import com.google.common.io.MoreFiles;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class PoolLoader {
    final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Tip.class, new SimpleTip.Serializer())
        .registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer())
        .registerTypeHierarchyAdapter(Style.class, new Style.Serializer())
        .registerTypeAdapterFactory(new EnumTypeAdapterFactory())
        .create();

    private final Map<ResourceLocation, PoolLoader.PoolBuilder> pools;

    public PoolLoader() {
        this(Maps.newHashMap());
    }

    private PoolLoader(final Map<ResourceLocation, PoolLoader.PoolBuilder> pools) {
        this.pools = pools;
    }

    public void load(final AssetsFolder assets) throws IOException {
        try {
            final Path base = assets.getPath("tips");
            Files.walk(base)
                .filter(assets.getPathMatcher("glob:**.json")::matches)
                .map(path -> {
                    final JsonObject obj;
                    try (final BufferedReader reader = Files.newBufferedReader(path)) {
                        obj = JsonUtils.fromJson(GSON, reader, JsonObject.class);
                    } catch (JsonParseException jsonparseexception) {
                        //LOGGER.error("Parsing error loading recipe {}", resourcelocation, jsonparseexception);
                    } catch (IOException ioexception) {
                        //LOGGER.error("Couldn't read recipe {} from {}", resourcelocation, path1, ioexception);
                    }
                    final ResourceLocation name = this.toName(base.relativize(path));

                    return path;
                })
                .forEach(System.out::println);
        } catch (final NoSuchFileException e) {
            // No-op
        }
    }

    private ResourceLocation toName(final Path path) {
        return new ResourceLocation(FilenameUtils.separatorsToUnix(MoreFiles.getNameWithoutExtension(path)));
    }

    private final class PoolBuilder {
        private final ITextComponent title;

        private final Set<Tip> tips;

        private PoolBuilder(final ITextComponent title, final Set<Tip> tips) {
            this.title = title;
            this.tips = tips;
        }
    }
}
