package com.deflatedpickle.justthetips;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.MoreFiles;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class PoolLoader {
    private static final Logger LOGGER = JustTheTips.LOGGER;

    final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(TipFile.class, new TipFile.Serializer())
        .registerTypeAdapter(Tip.class, new SimpleTip.Serializer())
        .registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer())
        .registerTypeHierarchyAdapter(Style.class, new Style.Serializer())
        .registerTypeAdapterFactory(new EnumTypeAdapterFactory())
        .create();

    private final Map<ResourceLocation, PoolLoader.Entry> pools;

    public PoolLoader() {
        this(Maps.newHashMap());
    }

    private PoolLoader(final Map<ResourceLocation, PoolLoader.Entry> pools) {
        this.pools = pools;
    }

    public void load(final AssetsFolder assets) throws IOException {
        try {
            final Path base = assets.getPath("tips");
            Files.walk(base)
                .filter(assets.getPathMatcher("glob:**.json")::matches)
                .map(path -> {
                    final ResourceLocation name = this.toName(base.relativize(path));
                    final TipFile file;
                    try (final BufferedReader reader = Files.newBufferedReader(path)) {
                        file = JsonUtils.fromJson(this.GSON, reader, TipFile.class);
                    } catch (final JsonParseException e) {
                        PoolLoader.LOGGER.error("Parsing error loading tip file {}", name, e);
                    } catch (final IOException e) {
                        PoolLoader.LOGGER.error("Couldn't read tip file {} from {}", name, path, e);
                    }

                    return path;
                });
        } catch (final NoSuchFileException e) {
            // No-op
        }
    }

    private ResourceLocation toName(final Path path) {
        return new ResourceLocation(FilenameUtils.separatorsToUnix(MoreFiles.getNameWithoutExtension(path)));
    }

    private static final class Entry {
        private final ITextComponent title;

        private final List<Tip> tips;

        private Entry(final ITextComponent title, final List<Tip> tips) {
            this.title = title;
            this.tips = tips;
        }

        public static class Serializer implements JsonSerializer<PoolLoader.Entry>, JsonDeserializer<PoolLoader.Entry> {
            @Override
            public JsonElement serialize(final PoolLoader.Entry src, final Type typeOfSrc, final JsonSerializationContext context) {
                final JsonObject object = new JsonObject();
                object.add("title", context.serialize(src.title));
                object.add("tips", context.serialize(src.tips));
                return object;
            }

            @Override
            public PoolLoader.Entry deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
                final JsonObject object = JsonUtils.getJsonObject(json, "tip");
                return new PoolLoader.Entry(
                    JsonUtils.deserializeClass(object, "title", context, ITextComponent.class),
                    Lists.newArrayList(JsonUtils.deserializeClass(object, "tips", context, Tip[].class))
                );
            }
        }
    }
}
