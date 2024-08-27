package com.cloudmeow.eternalsdelight.common;

import com.cloudmeow.eternalsdelight.common.registry.ESDCreativeModeTabs;
import com.cloudmeow.eternalsdelight.common.registry.ESDItems;
import net.minecraft.resources.ResourceLocation;

public class EternalSdelight {
    public static final String ID = "eternalsdelight";

    public static void init() {
        ESDItems.loadClass();
        ESDCreativeModeTabs.loadClass();
    }

    public static ResourceLocation id(String string) {
        return ResourceLocation.fromNamespaceAndPath(ID, string);
    }
}
