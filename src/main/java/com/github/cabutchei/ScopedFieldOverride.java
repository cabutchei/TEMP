package com.github.cabutchei;

import java.lang.reflect.Field;
import java.util.function.Supplier;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.arc.ClientProxy;

@ApplicationScoped
public class ScopedFieldOverride {

    public <T> T withFieldValue(
            Object beanOrProxy,
            Class<?> declaringClass,
            String fieldName,
            Object replacement,
            Supplier<T> action) {
        Object bean = ClientProxy.unwrap(beanOrProxy);

        synchronized (bean) {
            Field field = findField(declaringClass, fieldName);

            try {
                Object original = field.get(bean);
                field.set(bean, replacement);

                try {
                    return action.get();
                } finally {
                    field.set(bean, original);
                }
            } catch (IllegalAccessException exception) {
                throw new IllegalStateException("Failed to replace field " + fieldName, exception);
            }
        }
    }

    private Field findField(Class<?> declaringClass, String fieldName) {
        try {
            Field field = declaringClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException exception) {
            throw new IllegalStateException("Field " + fieldName + " was not found on " + declaringClass.getName(), exception);
        }
    }
}
