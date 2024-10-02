package committee.nova.mods.mappreset.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

/**
 * @Project: mappreset
 * @Author: cnlimiter
 * @CreateTime: 2024/10/2 18:58
 * @Description:
 */
public class ModCommonImpl {
    public static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
