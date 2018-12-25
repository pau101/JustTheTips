package com.deflatedpickle.justthetips;

import com.google.common.collect.ImmutableList;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Iterator;

public final class Fields {
    private Fields() {}

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    public static Fields.Result lookup(final Class<?> refc, final String first, final String... names) {
        return Fields.lookup(refc, ImmutableList.<String>builder().add(first).add(names).build());
    }

    private static Fields.Result lookup(final Class<?> refc, final Iterable<String> names) {
        for (final Iterator<String> it = names.iterator(); ; ) {
            final String name = it.next();
            final MethodHandle setter;
            final MethodHandle getter;
            try {
                final Field f = refc.getDeclaredField(name);
                f.setAccessible(true);
                setter = Fields.LOOKUP.unreflectSetter(f);
                getter = Fields.LOOKUP.unreflectGetter(f);
            } catch (final NoSuchFieldException | IllegalAccessException e) {
                if (it.hasNext()) {
                    continue;
                }
                throw new RuntimeException(e);
            }
            return new Fields.Result(setter, getter);
        }
    }

    public static final class Result {
        private final MethodHandle setter;

        private final MethodHandle getter;

        private Result(final MethodHandle setter, final MethodHandle getter) {
            this.setter = setter;
            this.getter = getter;
        }

        public MethodHandle setter() {
            return this.setter;
        }

        public MethodHandle getter() {
            return this.getter;
        }
    }
}
