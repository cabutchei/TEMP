package com.github.cabutchei;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.quarkus.arc.ClientProxy;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

public final class ByteBuddyRuntimeMocks {

    private ByteBuddyRuntimeMocks() {
    }

    public static FieldOverrideSession session() {
        return new FieldOverrideSession();
    }

    @SuppressWarnings("unchecked")
    public static <T> T createInterfaceMock(Class<T> type, InvocationHandler handler) {
        try {
            Class<? extends T> generatedClass = (Class<? extends T>) new ByteBuddy()
                    .subclass(Object.class)
                    .implement(type)
                    .method(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class)))
                    .intercept(InvocationHandlerAdapter.of(handler))
                    .make()
                    .load(type.getClassLoader())
                    .getLoaded();

            return generatedClass.getDeclaredConstructor().newInstance();
        } catch (Exception exception) {
            throw new IllegalStateException("Erro ao criar mock ByteBuddy para " + type.getName(), exception);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T createSubclassMock(Class<T> type, String methodName, InvocationHandler handler) {
        try {
            Class<? extends T> generatedClass = (Class<? extends T>) new ByteBuddy()
                    .subclass(type)
                    .method(ElementMatchers.named(methodName))
                    .intercept(InvocationHandlerAdapter.of(handler))
                    .make()
                    .load(type.getClassLoader())
                    .getLoaded();

            return generatedClass.getDeclaredConstructor().newInstance();
        } catch (Exception exception) {
            throw new IllegalStateException("Erro ao criar mock ByteBuddy para " + type.getName(), exception);
        }
    }

    public static final class FieldOverrideSession implements AutoCloseable {

        private final List<FieldOverride> overrides = new ArrayList<>();

        public void replaceField(Object target, String fieldName, Object replacement) {
            Object bean = ClientProxy.unwrap(target);

            try {
                Field field = findField(bean.getClass(), fieldName);
                field.setAccessible(true);
                Object originalValue = field.get(bean);
                overrides.add(new FieldOverride(bean, field, originalValue));
                field.set(bean, replacement);
            } catch (Exception exception) {
                throw new IllegalStateException("Erro ao substituir o campo " + fieldName, exception);
            }
        }

        public List<String> describeOverrides() {
            return overrides.stream()
                    .map(override -> override.field().getDeclaringClass().getSimpleName() + "." + override.field().getName())
                    .toList();
        }

        @Override
        public void close() {
            List<FieldOverride> reversed = new ArrayList<>(overrides);
            Collections.reverse(reversed);

            for (FieldOverride override : reversed) {
                try {
                    override.field().setAccessible(true);
                    override.field().set(override.target(), override.originalValue());
                } catch (Exception exception) {
                    throw new IllegalStateException("Erro ao restaurar o campo " + override.field().getName(), exception);
                }
            }
        }

        private Field findField(Class<?> type, String fieldName) throws NoSuchFieldException {
            Class<?> current = type;
            while (current != null) {
                try {
                    return current.getDeclaredField(fieldName);
                } catch (NoSuchFieldException ignored) {
                    current = current.getSuperclass();
                }
            }
            throw new NoSuchFieldException(fieldName);
        }

        private record FieldOverride(Object target, Field field, Object originalValue) {
        }
    }
}
