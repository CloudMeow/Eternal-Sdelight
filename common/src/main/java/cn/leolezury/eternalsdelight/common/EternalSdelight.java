package cn.leolezury.eternalsdelight.common;

import cn.leolezury.eternalsdelight.common.registry.ESDCreativeModeTabs;
import cn.leolezury.eternalsdelight.common.registry.ESDItems;
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
