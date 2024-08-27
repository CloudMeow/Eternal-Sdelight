package cn.leolezury.eternalsdelight.neoforge.platform;

import cn.leolezury.eternalsdelight.common.platform.ESDPlatform;
import cn.leolezury.eternalsdelight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalsdelight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalsdelight.common.registry.ESDItems;
import com.google.auto.service.AutoService;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@AutoService(ESDPlatform.class)
public class NeoPlatform implements ESDPlatform {
    public static final List<DeferredRegister<?>> REGISTERS = new ArrayList<>();
    public static final List<Registry<?>> NEW_REGISTRIES = new ArrayList<>();

    @Override
    public Loader getLoader() {
        return Loader.NEOFORGE;
    }

    @Override
    public boolean isPhysicalClient() {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

    @Override
    public <T> RegistrationProvider<T> createRegistrationProvider(ResourceKey<? extends Registry<T>> key, String namespace) {
        NeoForgeRegistrationProvider<T> provider = new NeoForgeRegistrationProvider<>(key, null, namespace);
        if (!REGISTERS.contains(provider.deferredRegister)) {
            REGISTERS.add(provider.deferredRegister);
        }
        return provider;
    }

    @Override
    public <T> RegistrationProvider<T> createNewRegistryProvider(ResourceKey<? extends Registry<T>> key, String namespace) {
        RegistryBuilder<T> builder = new RegistryBuilder<>(key);
        builder.sync(true);
        Registry<T> registry = builder.create();
        NeoForgeRegistrationProvider<T> provider = new NeoForgeRegistrationProvider<>(key, registry, namespace);
        if (!REGISTERS.contains(provider.deferredRegister)) {
            REGISTERS.add(provider.deferredRegister);
        }
        NEW_REGISTRIES.add(registry);
        return provider;
    }

    class NeoForgeRegistrationProvider<T> implements RegistrationProvider<T> {
        private final ResourceKey<? extends Registry<T>> key;
        private final Registry<T> registry;
        private final DeferredRegister<T> deferredRegister;
        private final String namespace;

        NeoForgeRegistrationProvider(ResourceKey<? extends Registry<T>> key, Registry<T> registry, String namespace) {
            this.key = key;
            this.registry = registry;
            this.deferredRegister = DeferredRegister.create(key, namespace);
            this.namespace = namespace;
        }

        @Override
        public Registry<T> registry() {
            return registry == null ? (Registry<T>) BuiltInRegistries.REGISTRY.get(key.location()) : registry;
        }

        @Override
        public <I extends T> RegistryObject<T, I> register(String id, Supplier<? extends I> supplier) {
            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(namespace, id);
            ResourceKey<I> resourceKey = (ResourceKey<I>) ResourceKey.create(key, location);
            DeferredHolder<T, I> holder = deferredRegister.register(id, supplier);
            return new RegistryObject<>() {
                @Override
                public Holder<T> asHolder() {
                    return holder;
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
                    return holder.value();
                }
            };
        }
    }

    @Override
    public CreativeModeTab getESTab() {
        return CreativeModeTab.builder().icon(() -> new ItemStack(ESDItems.TEST.get())).title(Component.translatable("name.eternalsdelight")).displayItems((displayParameters, output) -> {
            for (ResourceKey<Item> entry : ESDItems.REGISTERED_ITEMS) {
                Item item = BuiltInRegistries.ITEM.get(entry);
                if (item != null) {
                    output.accept(item);
                }
            }
        }).build();
    }
}
