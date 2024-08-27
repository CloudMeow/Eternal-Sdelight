package com.cloudmeow.eternalsdelight.common.registry;

import com.cloudmeow.eternalsdelight.common.EternalSdelight;
import com.cloudmeow.eternalsdelight.common.platform.ESDPlatform;
import com.cloudmeow.eternalsdelight.common.platform.registry.RegistrationProvider;
import com.cloudmeow.eternalsdelight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;

public class ESDCreativeModeTabs {
    public static final RegistrationProvider<CreativeModeTab> TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, EternalSdelight.ID);
    public static final RegistryObject<CreativeModeTab, CreativeModeTab> TAB = TABS.register("eternalsdelight", ESDPlatform.INSTANCE::getESTab);

    public static void loadClass() {
    }
}
