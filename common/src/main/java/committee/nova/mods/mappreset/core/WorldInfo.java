package committee.nova.mods.mappreset.core;

import committee.nova.mods.mappreset.MapPreset;
import committee.nova.mods.mappreset.client.MapListScreen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @Project: mappreset
 * @Author: cnlimiter
 * @CreateTime: 2024/10/1 12:31
 * @Description:
 */
public abstract class WorldInfo {
    public abstract String getName();

    public abstract AuthorData getAuthorData();

    public abstract File getSaveFile();

    public abstract void copy(MapListScreen screen, String name);

    public abstract Optional<String> valid();

    public static WorldInfo load(File inputFile) {
        if (inputFile.isDirectory()) {
            File levelData = new File(inputFile, "level.dat");
            if(levelData.exists()){
                try {
                    return WorldDirectory.loadDir(inputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                MapPreset.LOG.error(inputFile.getName() + " is not supported!");
                return null;
            }

        }
//        if(inputFile.getName().endsWith(".zip")){
//            return WorldZip.loadZip(inputFile);
//        }
        MapPreset.LOG.error(inputFile.getName() + " is not a valid template file!");
        return null;
    }

    public static class AuthorData {
        public String author = "No Author";
        public String description = "";
        public boolean enabled = true;
        public int sort = 0;
    }
}
