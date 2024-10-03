package committee.nova.mods.mappreset.client;

import committee.nova.mods.mappreset.core.TemplateSaveLoader;
import committee.nova.mods.mappreset.core.WorldDirectory;
import committee.nova.mods.mappreset.core.WorldInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @Project: mappreset
 * @Author: cnlimiter
 * @CreateTime: 2024/10/2 02:10
 * @Description:
 */
public class MapSelectionList extends ObjectSelectionList<MapSelectionList.MapListEntry> {
    private final MapListScreen screen;
    public TemplateSaveLoader saveLoader;
    public List<WorldInfo> saveList;

    public MapSelectionList(MapListScreen mapListScreen, int i, int j, int k, int l, int m) {
        super(mapListScreen.getMinecraftInstance(), i, j, k, l, m);
        this.screen = mapListScreen;
        saveLoader = new TemplateSaveLoader(new File(Minecraft.getInstance().gameDirectory, "maps"));
        saveList = saveLoader.getSaveList();
        saveList.sort(Comparator.comparingInt(o -> o.getAuthorData().sort));
        fillLevels();

        if (this.getSelected() != null) {
            this.centerScrollOn(this.getSelected());
        }
    }

    public MapListScreen getScreen() {
        return this.screen;
    }


    private void fillLevels() {
        this.clearEntries();
        for (WorldInfo worldInfo : saveList){
            if (worldInfo instanceof WorldDirectory worldDirectory) {
                this.addEntry(new MapSelectionList.MapListEntry(this, worldDirectory));
            }
        }
        this.notifyListUpdated();
    }


    public Optional<MapSelectionList.MapListEntry> getSelectedOpt() {
        MapListEntry entry = this.getSelected();
        if (entry != null) {
            return Optional.of(entry);
        } else {
            return Optional.empty();
        }
    }

    private void notifyListUpdated() {
        this.screen.triggerImmediateNarration(true);
    }

    @Override
    protected void renderBackground(GuiGraphics guiGraphics)
    {
        this.screen.renderBackground(guiGraphics);
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 20;
    }

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 50;
    }

    @Override
    public void setSelected(@Nullable MapListEntry entry) {
        super.setSelected(entry);
        this.screen.updateButtonStatus(entry != null);
    }


    public class MapListEntry extends ObjectSelectionList.Entry<MapListEntry>{
        MapSelectionList selectionList;
        WorldInfo worldInfo;
        Minecraft minecraft;
        private long lastClickTime;

        public MapListEntry(MapSelectionList selectionList, WorldInfo worldInfo){
            this.selectionList = selectionList;
            this.minecraft = selectionList.minecraft;
            this.worldInfo = worldInfo;
        }

        @Override
        public @NotNull Component getNarration() {
            return Component.empty();
        }

        @Override
        public void render(GuiGraphics guiGraphics, int x, int y, int k, int l, int m, int n, int o, boolean bl, float f) {
            String displayName = worldInfo.getName();
            String author = worldInfo.getAuthorData().author;
            String by = I18n.get("gui.mappreset.by");
            String topLine = displayName + ", " + ChatFormatting.ITALIC + ChatFormatting.BOLD + by + ChatFormatting.RESET + ": " + author;
            String middleLine = worldInfo.getAuthorData().description;

            if(worldInfo.valid().isPresent()){
                middleLine += "   " + ChatFormatting.RED + worldInfo.valid().get();
            }
            guiGraphics.drawString(this.minecraft.font, topLine, x + 140, y + 5, 16777215);
            guiGraphics.drawString(this.minecraft.font, middleLine, x + 140, y + 17, 8421504);
        }

        @Override
        public boolean mouseClicked(double d, double e, int i) {

                select();
                if (Util.getMillis() - this.lastClickTime < 250L) {
                    this.worldInfo.copy(this.selectionList.screen, Util.sanitizeName(getScreen().nameEdit.getValue(), ResourceLocation::validPathChar));
                } else {
                    this.lastClickTime = Util.getMillis();
                }
                return true;

        }

        void select() {
            MapSelectionList.this.setSelected(this);
            this.selectionList.screen.nameEdit.setValue(this.worldInfo.getName());
        }
    }

}
