package cn.leolezury.eternalsdelight.neoforge;

import cn.leolezury.eternalsdelight.common.EternalSdelight;
import cn.leolezury.eternalsdelight.neoforge.platform.NeoPlatform;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(EternalSdelight.ID)
public class EternalSdelightNeo {
    public EternalSdelightNeo(IEventBus modBus) {
        EternalSdelight.init();
        for (DeferredRegister<?> register : NeoPlatform.REGISTERS) {
            register.register(modBus);
        }
    }
}
