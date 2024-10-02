package committee.nova.mods.mappreset;

import com.mojang.logging.LogUtils;
import committee.nova.mods.mappreset.util.Config;
import org.slf4j.Logger;

public final class MapPreset {
    public static final String MOD_ID = "mappreset";
    public static final Logger LOG = LogUtils.getLogger();

    public static void init() {
        Config.sync(ModConfig.class);
    }
}
