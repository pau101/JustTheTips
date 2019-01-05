package com.deflatedpickle.justthetips;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.util.text.ITextComponent;

import java.lang.reflect.Type;

public final class TipFile {
    private final ITextComponent title;

    private final boolean replace;

    private final ImmutableSet<Tip> values;

    private TipFile(final ITextComponent title, final boolean replace, final ImmutableSet<Tip> values) {
        this.title = title;
        this.replace = replace;
        this.values = values;
    }

    public static final class Serializer implements JsonDeserializer<TipFile> {
        @Override
        public TipFile deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            return null;
        }
    }
}
