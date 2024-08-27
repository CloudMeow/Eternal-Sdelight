package cn.leolezury.eternalsdelight.common.registry;

import cn.leolezury.eternalsdelight.common.EternalSdelight;
import cn.leolezury.eternalsdelight.common.platform.ESDPlatform;
import cn.leolezury.eternalsdelight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalsdelight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;

public class ESDCreativeModeTabs {
    public static final RegistrationProvider<CreativeModeTab> TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, EternalSdelight.ID);
    public static final RegistryObject<CreativeModeTab, CreativeModeTab> TAB = TABS.register("eternalsdelight", ESDPlatform.INSTANCE::getESTab);

    public static void loadClass() {
    }
}
