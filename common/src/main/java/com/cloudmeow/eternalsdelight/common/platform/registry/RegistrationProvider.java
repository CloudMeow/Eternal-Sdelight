package com.cloudmeow.eternalsdelight.common.platform.registry;

import com.cloudmeow.eternalsdelight.common.platform.ESDPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public interface RegistrationProvider<T> {
    static <T> RegistrationProvider<T> get(ResourceKey<? extends Registry<T>> key, String namespace) {
        return ESDPlatform.INSTANCE.createRegistrationProvider(key, namespace);
    }

    static <T> RegistrationProvider<T> newRegistry(ResourceKey<? extends Registry<T>> key, String namespace) {
        return ESDPlatform.INSTANCE.createNewRegistryProvider(key, namespace);
    }

    Registry<T> registry();

    <I extends T> RegistryObject<T, I> register(String id, Supplier<? extends I> supplier);
}
