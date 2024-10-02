package committee.nova.mods.mappreset.forge;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

/**
 * @Project: mappreset
 * @Author: cnlimiter
 * @CreateTime: 2024/10/2 18:58
 * @Description:
 */
public class ModCommonImpl {
    public static Path getConfigPath() {
       return FMLPaths.CONFIGDIR.get();
    }
}
