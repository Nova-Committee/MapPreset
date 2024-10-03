package committee.nova.mods.mappreset.client;

import committee.nova.mods.mappreset.core.WorldInfo;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.navigation.CommonInputs;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * @Project: mappreset
 * @Author: cnlimiter
 * @CreateTime: 2024/10/1 12:33
 * @Description:
 */
public class MapListScreen extends Screen {
    public Screen lastScreen;
    public EditBox nameEdit;
    public Button createButton;
    public Button cancelButton;
    private MapSelectionList selectionList;


    public MapListScreen(Screen parent) {
        super(Component.translatable("selectWorld.use_temp"));
        this.lastScreen = parent;

    }

    @Override
    protected void setInitialFocus() {
        this.setInitialFocus(this.nameEdit);
    }

    @Override
    protected void init() {
        this.nameEdit = new EditBox(this.font, this.width / 2 - 100, 22, 200, 20, this.nameEdit, Component.translatable("selectWorld.search"));
        this.nameEdit.setFocused(true);
        this.nameEdit.setValue("New World");
        this.addWidget(this.nameEdit);

        this.selectionList = this.addWidget(new MapSelectionList(this, this.width, this.height - 112, 48, 36));

        this.createButton = this.addRenderableWidget(Button.builder(Component.translatable("selectWorld.create"), (button) -> {
            if (!this.nameEdit.getValue().isEmpty()) {
                this.createMap();
            }
        }).bounds(this.width / 2 - 154, this.height - 52, 150, 20).build());
        this.cancelButton = this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, (button) -> {
            this.popScreen();
        }).bounds(this.width / 2 + 32, this.height - 52, 150, 20).build());
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        this.selectionList.render(guiGraphics, i, j, f);
        this.nameEdit.render(guiGraphics, i, j, f);
        this.createButton.render(guiGraphics, i, j, f);
        this.cancelButton.render(guiGraphics, i, j, f);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (CommonInputs.selected(i)) {
            MapSelectionList.MapListEntry entry = this.selectionList.getSelected();
            if (entry != null) {
                entry.select();
                this.createMap();
                return true;
            }
        }
        return super.keyPressed(i, j, k) || this.nameEdit.keyPressed(i, j, k);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.lastScreen);
    }

    @Override
    public boolean charTyped(char c, int i) {
        return this.nameEdit.charTyped(c, i);
    }

    @Override
    public void resize(Minecraft mc, int width, int height) {
        this.init(mc, width, height);
    }


    private WorldInfo getSave() {
        if (this.selectionList.getSelectedOpt().isEmpty()) {
            return null;
        }
        return this.selectionList.getSelectedOpt().get().worldInfo;
    }

    private void createMap() {
        if (getSave() != null)
            getSave().copy(this, Util.sanitizeName(nameEdit.getValue(), ResourceLocation::validPathChar));
    }

    public void popScreen() {
        this.minecraft.setScreen(this.lastScreen);
    }

    public void updateButtonStatus(boolean bl) {
        this.createButton.active = bl;
    }

    public Minecraft getMinecraftInstance() {
        return minecraft;
    }
}
