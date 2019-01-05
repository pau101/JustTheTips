package com.deflatedpickle.justthetips;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.text.ITextComponent;

import java.lang.reflect.Type;

public final class SimpleTip implements Tip {
    private final ITextComponent title;

    private final ITextComponent text;

    private SimpleTip(final ITextComponent title, final ITextComponent text) {
        this.title = title;
        this.text = text;
    }

    @Override
    public ITextComponent title() {
        return this.title;
    }

    @Override
    public ITextComponent text() {
        return this.text;
    }

    public static Tip create(final ITextComponent title, final ITextComponent text) {
        return new SimpleTip(title, text);
    }

    public static class Serializer implements JsonSerializer<Tip>, JsonDeserializer<Tip> {
        @Override
        public JsonElement serialize(final Tip src, final Type typeOfSrc, final JsonSerializationContext context) {
            final JsonObject object = new JsonObject();
            object.add("title", context.serialize(src.title()));
            object.add("text", context.serialize(src.text()));
            return object;
        }

        @Override
        public Tip deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject object = JsonUtils.getJsonObject(json, "tip");
            return SimpleTip.create(
                JsonUtils.deserializeClass(object, "title", context, ITextComponent.class),
                JsonUtils.deserializeClass(object, "text", context, ITextComponent.class)
            );
        }
    }
}
