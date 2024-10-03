package committee.nova.mods.mappreset.neoforge;

import committee.nova.mods.mappreset.MapPreset;
import committee.nova.mods.mappreset.ModCmd;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(MapPreset.MOD_ID)
@EventBusSubscriber
public final class MapPresetNeoForge {
    public MapPresetNeoForge() {
        MapPreset.init();
    }

    @SubscribeEvent
    public static void cmdRegister(RegisterCommandsEvent event) {
        ModCmd.register(event.getDispatcher());
    }
}
