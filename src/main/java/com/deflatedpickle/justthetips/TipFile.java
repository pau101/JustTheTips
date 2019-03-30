package com.deflatedpickle.justthetips;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.text.ITextComponent;

import java.lang.reflect.Type;

public final class TipFile {
    private final ITextComponent title;

    private final boolean replace;

    private final ImmutableList<ITextComponent> values;

    private TipFile(final ITextComponent title, final boolean replace, final ImmutableList<ITextComponent> values) {
        this.title = title;
        this.replace = replace;
        this.values = values;
    }

    public ITextComponent getTitle() {
        return this.title;
    }

    public boolean isReplace() {
        return this.replace;
    }

    public ImmutableList<ITextComponent> getValues() {
        return this.values;
    }

    public static final class Serializer implements JsonDeserializer<TipFile> {
        @Override
        public TipFile deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject object = JsonUtils.getJsonObject(json, "tip file");
            return new TipFile(
                JsonUtils.deserializeClass(object, "title", context, ITextComponent.class),
                JsonUtils.getBoolean(object, "replace", false),
                ImmutableList.copyOf(JsonUtils.deserializeClass(object, "tips", context, ITextComponent[].class))
            );
        }
    }
}
