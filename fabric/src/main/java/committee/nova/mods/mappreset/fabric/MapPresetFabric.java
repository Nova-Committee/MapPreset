package committee.nova.mods.mappreset.fabric;

import committee.nova.mods.mappreset.ModCmd;
import net.fabricmc.api.ModInitializer;

import committee.nova.mods.mappreset.MapPreset;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public final class MapPresetFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        MapPreset.init();
        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandBuildContext, commandSelection) -> ModCmd.register(commandDispatcher));
    }
}
