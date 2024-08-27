package com.cloudmeow.eternalsdelight.common.platform;

import com.cloudmeow.eternalsdelight.common.platform.registry.RegistrationProvider;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import java.util.Iterator;
import java.util.ServiceLoader;

public interface ESDPlatform {
    ESDPlatform INSTANCE = Util.make(() -> {
        final ServiceLoader<ESDPlatform> loader = ServiceLoader.load(ESDPlatform.class);
        final Iterator<ESDPlatform> iterator = loader.iterator();
        if (!iterator.hasNext()) {
            throw new RuntimeException("Platform instance not found!");
        } else {
            final ESDPlatform platform = iterator.next();
            if (iterator.hasNext()) {
                throw new RuntimeException("More than one platform instance was found!");
            }
            return platform;
        }
    });

    enum Loader {
        NEOFORGE,
        FABRIC
    }

    Loader getLoader();

    boolean isPhysicalClient();

    // registry utils
    <T> RegistrationProvider<T> createRegistrationProvider(ResourceKey<? extends Registry<T>> key, String namespace);

    <T> RegistrationProvider<T> createNewRegistryProvider(ResourceKey<? extends Registry<T>> key, String namespace);

    CreativeModeTab getESTab();
}
