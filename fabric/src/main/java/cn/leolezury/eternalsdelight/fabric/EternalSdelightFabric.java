package cn.leolezury.eternalsdelight.fabric;

import cn.leolezury.eternalsdelight.common.EternalSdelight;
import net.fabricmc.api.ModInitializer;

public class EternalSdelightFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EternalSdelight.init();
    }
}
