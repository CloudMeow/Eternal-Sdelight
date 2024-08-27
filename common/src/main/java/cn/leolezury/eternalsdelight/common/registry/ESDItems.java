package cn.leolezury.eternalsdelight.common.registry;

import cn.leolezury.eternalsdelight.common.EternalSdelight;
import cn.leolezury.eternalsdelight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalsdelight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ESDItems {
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, EternalSdelight.ID);

    public static final List<ResourceKey<Item>> REGISTERED_ITEMS = new ArrayList<>();

    private static RegistryObject<Item, Item> registerItem(String name, Supplier<? extends Item> supplier) {
        REGISTERED_ITEMS.add(ResourceKey.create(Registries.ITEM, EternalSdelight.id(name)));
        return ITEMS.register(name, supplier);
    }

    public static final RegistryObject<Item, Item> TEST = registerItem("test", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(1.5F).effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 400, 0), 0.2F).build())));

    public static void loadClass() {
    }
}
