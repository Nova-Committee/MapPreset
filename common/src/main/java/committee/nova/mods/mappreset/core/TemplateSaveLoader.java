package committee.nova.mods.mappreset.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Project: mappreset
 * @Author: cnlimiter
 * @CreateTime: 2024/10/1 12:35
 * @Description:
 */
public class TemplateSaveLoader {
    public File saveFolder;

    public TemplateSaveLoader(File save_folder) {
        this.saveFolder = save_folder;
        if(!save_folder.exists()){
            save_folder.mkdir();
        }
    }

    public List<WorldInfo> getSaveList() {

        List<WorldInfo> worldInfoList = new ArrayList<>();
        File[] files = saveFolder.listFiles();
        if(files == null){
            return worldInfoList;
        }
        for (File file : files) {
            WorldInfo worldInfo = WorldInfo.load(file);
            if(worldInfo == null){
                continue;
            }
            worldInfoList.add(worldInfo);
        }
        return worldInfoList;
    }
}
