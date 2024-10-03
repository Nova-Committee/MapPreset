package committee.nova.mods.mappreset.forge;

import committee.nova.mods.mappreset.MapPreset;
import committee.nova.mods.mappreset.ModCmd;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(MapPreset.MOD_ID)
public final class MapPresetForge {
    public MapPresetForge() {
        // Run our common setup.
        MapPreset.init();
        MinecraftForge.EVENT_BUS.addListener(MapPresetForge::cmdRegister);
    }

    public static void cmdRegister(RegisterCommandsEvent event) {
        ModCmd.register(event.getDispatcher());
    }
}
