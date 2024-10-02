package committee.nova.mods.mappreset;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

/**
 * @Project: mappreset
 * @Author: cnlimiter
 * @CreateTime: 2024/10/2 18:58
 * @Description:
 */
public class ModCommon {
    @ExpectPlatform
    public static Path getConfigPath() {
        throw new IllegalStateException();
    }
}
