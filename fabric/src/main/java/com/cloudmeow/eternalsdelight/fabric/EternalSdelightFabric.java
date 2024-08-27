package com.cloudmeow.eternalsdelight.fabric;

import com.cloudmeow.eternalsdelight.common.EternalSdelight;
import net.fabricmc.api.ModInitializer;

public class EternalSdelightFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EternalSdelight.init();
    }
}
