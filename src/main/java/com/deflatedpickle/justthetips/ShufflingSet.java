package com.deflatedpickle.justthetips;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class ShufflingSet<E> {
    private final float chance;

    private final List<E> ready;

    private final LinkedList<E> idle;

    private ShufflingSet(final List<E> ready) {
        this.chance = 1.0F / ready.size();
        this.ready = ready;
        this.idle = Lists.newLinkedList();
    }

    public E draw(final Random rng) {
        if (this.idle.size() > this.ready.size()) {
            final E available;
            if (rng.nextFloat() < this.chance) {
                available = this.remove(this.idle, rng);
            } else {
                available = this.idle.removeFirst();
            }
            this.ready.add(available);
        }
        final E drawn = this.remove(this.ready, rng);
        this.idle.addLast(drawn);
        return drawn;
    }

    private E remove(final List<E> collection, final Random rng) {
        return collection.remove(rng.nextInt(collection.size()));
    }

    public static <E> ShufflingSet.BuilderAcceptingFirst<E> builder() {
        return new ShufflingSet.BuilderAcceptingFirst<>();
    }

    public static final class BuilderAcceptingFirst<E> {
        private BuilderAcceptingFirst() {}

        public ShufflingSet.Builder<E> addFirst(final E element) {
            return new ShufflingSet.Builder<>(ImmutableList.<E>builder().add(element));
        }
    }

    public static final class Builder<E> {
        private final ImmutableList.Builder<E> elements;

        private Builder(final ImmutableList.Builder<E> elements) {
            this.elements = elements;
        }

        public ShufflingSet.Builder<E> add(final E element) {
            this.elements.add(element);
            return this;
        }

        public ShufflingSet<E> build() {
            return new ShufflingSet<>(this.elements.build());
        }
    }
}
