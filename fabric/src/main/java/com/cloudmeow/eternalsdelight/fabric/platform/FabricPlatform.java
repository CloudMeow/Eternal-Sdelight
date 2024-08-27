package com.cloudmeow.eternalsdelight.fabric.platform;

import com.cloudmeow.eternalsdelight.common.platform.ESDPlatform;
import com.cloudmeow.eternalsdelight.common.platform.registry.RegistrationProvider;
import com.cloudmeow.eternalsdelight.common.platform.registry.RegistryObject;
import com.cloudmeow.eternalsdelight.common.registry.ESDItems;
import com.google.auto.service.AutoService;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

@AutoService(ESDPlatform.class)
public class FabricPlatform implements ESDPlatform {
    @Override
    public Loader getLoader() {
        return Loader.FABRIC;
    }

    @Override
    public boolean isPhysicalClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public <T> RegistrationProvider<T> createRegistrationProvider(ResourceKey<? extends Registry<T>> key, String namespace) {
        return new RegistrationProvider<>() {
            private final Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(key.location());

            @Override
            public Registry<T> registry() {
                return registry;
            }

            @Override
            public <I extends T> RegistryObject<T, I> register(String id, Supplier<? extends I> supplier) {
                ResourceLocation location = ResourceLocation.fromNamespaceAndPath(namespace, id);
                ResourceKey<I> resourceKey = (ResourceKey<I>) ResourceKey.create(registry.key(), location);
                I object = Registry.register(registry, location, supplier.get());
                return new RegistryObject<>() {
                    @Override
                    public Holder<T> asHolder() {
                        return registry.getHolderOrThrow((ResourceKey<T>) this.getResourceKey());
                    }

                    @Override
                    public ResourceKey<I> getResourceKey() {
                        return resourceKey;
                    }

                    @Override
                    public ResourceLocation getId() {
                        return location;
                    }

                    @Override
                    public I get() {
                        return object;
                    }
                };
            }
        };
    }

    @Override
    public <T> RegistrationProvider<T> createNewRegistryProvider(ResourceKey<? extends Registry<T>> key, String namespace) {
        MappedRegistry<T> mappedRegistry = new MappedRegistry<>(key, Lifecycle.stable(), false);
        FabricRegistryBuilder<T, MappedRegistry<T>> builder = FabricRegistryBuilder.from(mappedRegistry);
        builder.attribute(RegistryAttribute.SYNCED);
        builder.buildAndRegister();
        return createRegistrationProvider(key, namespace);
    }

    @Override
    public CreativeModeTab getESTab() {
        return FabricItemGroup.builder().title(Component.translatable("name.eternalsdelight")).icon(() -> new ItemStack(ESDItems.TEST.get())).displayItems((displayParameters, output) -> {
            for (ResourceKey<Item> entry : ESDItems.REGISTERED_ITEMS) {
                Item item = BuiltInRegistries.ITEM.get(entry);
                if (item != null) {
                    output.accept(item);
                }
            }
        }).build();
    }
}
