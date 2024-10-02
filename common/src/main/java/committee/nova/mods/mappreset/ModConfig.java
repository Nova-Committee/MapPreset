package committee.nova.mods.mappreset;

import committee.nova.mods.mappreset.util.Config;

/**
 * @Project: mappreset
 * @Author: cnlimiter
 * @CreateTime: 2024/10/2 18:59
 * @Description:
 */
@Config.Comment("Configuration File")
@Config.Name("general")
@Config.File("mappreset.cfg")
public class ModConfig {
    @Config.Comment("Enable edit button")
    public static boolean EDIT_BUTTON_ENABLED = true;
    @Config.Comment("Enable rebuild button")
    public static boolean REBUILD_BUTTON_ENABLED = true;
    @Config.Comment("Enable create button")
    public static boolean CREATE_BUTTON_ENABLED = true;
}
