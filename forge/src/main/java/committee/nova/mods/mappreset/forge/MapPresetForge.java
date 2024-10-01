package committee.nova.mods.mappreset.forge;

import net.minecraftforge.fml.common.Mod;

import committee.nova.mods.mappreset.MapPreset;

@Mod(MapPreset.MOD_ID)
public final class MapPresetForge {
    public MapPresetForge() {
        // Run our common setup.
        MapPreset.init();
    }
}
