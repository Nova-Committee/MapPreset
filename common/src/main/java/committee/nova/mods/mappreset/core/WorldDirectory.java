package committee.nova.mods.mappreset.core;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import committee.nova.mods.mappreset.MapPreset;
import committee.nova.mods.mappreset.client.MapListScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @Project: mappreset
 * @Author: cnlimiter
 * @CreateTime: 2024/10/1 21:44
 * @Description:
 */
public class WorldDirectory extends WorldInfo {

    public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private String name;
    private AuthorData author;
    private File saveFile;

    public static WorldDirectory loadDir(File inputDir) throws IOException {
        WorldInfo.AuthorData authorData = null;
        File authorFile = new File(inputDir, "info.json");
        if (!authorFile.exists()) {
            authorData = new WorldInfo.AuthorData();
            FileUtils.writeStringToFile(authorFile, GSON.toJson(authorData), Charsets.UTF_8);
        } else {
            authorData = GSON.fromJson(FileUtils.readFileToString(authorFile, Charsets.UTF_8), WorldInfo.AuthorData.class);
        }
        WorldDirectory worldDirectory = new WorldDirectory();

        worldDirectory.name = inputDir.getName();
        worldDirectory.author = authorData;
        worldDirectory.saveFile = inputDir;

        return worldDirectory;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AuthorData getAuthorData() {
        return author;
    }

    @Override
    public File getSaveFile() {
        return saveFile;
    }

    @Override
    public void copy(MapListScreen screen, String name) {
        try {
            FileUtils.copyDirectory(new File(Minecraft.getInstance().gameDirectory + File.separator + "maps" + File.separator + getName()),
                    new File(Minecraft.getInstance().gameDirectory + File.separator + "saves" + File.separator + name));
        } catch (IOException ignored) {
        }

        try {
            LevelStorageSource.LevelStorageAccess storageAccess = screen.getMinecraftInstance().getLevelSource().createAccess(name);

            // Rename the level for our new name
            storageAccess.renameLevel(name);
            storageAccess.close();
        } catch (IOException e) {
            SystemToast.onWorldAccessFailure(screen.getMinecraftInstance(), name);
            MapPreset.LOG.error("Failed to rename level {}", name, e);
        }

        // Load the level
        screen.getMinecraftInstance().createWorldOpenFlows().openWorld(name, () -> {
        });

    }

    @Override
    public Optional<String> valid() {
        if (new File(getSaveFile(), "level.dat").exists()) {
            return Optional.empty();
        }
        return Optional.of("level.dat not found!");
    }
}
