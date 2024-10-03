package committee.nova.mods.mappreset.mixin;

import committee.nova.mods.mappreset.ModConfig;
import committee.nova.mods.mappreset.client.MapListScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelSummary;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @Project: mappreset
 * @Author: cnlimiter
 * @CreateTime: 2024/10/2 04:45
 * @Description:
 */
@Mixin(SelectWorldScreen.class)
public abstract class MixinSelectWorldScreen extends Screen {
    @Shadow private Button selectButton;

    @Shadow private WorldSelectionList list;

    @Shadow protected EditBox searchBox;

    @Shadow private Button renameButton;

    @Shadow private Button deleteButton;

    @Shadow private Button copyButton;

    @Shadow @Final protected Screen lastScreen;

    @Shadow public abstract void updateButtonStatus(@Nullable LevelSummary levelSummary);

    protected MixinSelectWorldScreen(Component component) {
        super(component);
    }

    @Inject(method="init", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/SelectWorldScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;" ,
     ordinal = 0), cancellable = true)
    private void mappreset$reCreate(CallbackInfo ci){
        this.selectButton = this.addRenderableWidget(Button.builder(Component.translatable("selectWorld.select"), (button) -> {
            this.list.getSelectedOpt().ifPresent(WorldSelectionList.WorldListEntry::joinWorld);
        }).bounds(this.width / 2 - 154, this.height - 52, 100, 20).build());

        var createButton = Button.builder(Component.translatable("selectWorld.create"), (button) -> {
            CreateWorldScreen.openFresh(this.minecraft, this);
        }).bounds(this.width / 2 - 50, this.height - 52, 100, 20).build();
        createButton.visible = ModConfig.CREATE_BUTTON_ENABLED;
        this.addRenderableWidget(createButton);

        this.addRenderableWidget(Button.builder(Component.translatable("selectWorld.use_temp"), (button) -> {
            this.minecraft.setScreen(new MapListScreen(this));
        }).bounds(this.width / 2 + 54, this.height - 52, 100, 20).build());

        this.renameButton = this.addRenderableWidget(Button.builder(Component.translatable("selectWorld.edit"), (button) -> {
            this.list.getSelectedOpt().ifPresent(WorldSelectionList.WorldListEntry::editWorld);
        }).bounds(this.width / 2 - 154, this.height - 28, 72, 20).build());
        this.renameButton.visible = ModConfig.EDIT_BUTTON_ENABLED;

        this.deleteButton = this.addRenderableWidget(Button.builder(Component.translatable("selectWorld.delete"), (button) -> {
            this.list.getSelectedOpt().ifPresent(WorldSelectionList.WorldListEntry::deleteWorld);
        }).bounds(this.width / 2 - 76, this.height - 28, 72, 20).build());

        this.copyButton = this.addRenderableWidget(Button.builder(Component.translatable("selectWorld.recreate"), (button) -> {
            this.list.getSelectedOpt().ifPresent(WorldSelectionList.WorldListEntry::recreateWorld);
        }).bounds(this.width / 2 + 4, this.height - 28, 72, 20).build());
        this.copyButton.visible = ModConfig.REBUILD_BUTTON_ENABLED;

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, (button) -> {
            this.minecraft.setScreen(this.lastScreen);
        }).bounds(this.width / 2 + 82, this.height - 28, 72, 20).build());
        this.updateButtonStatus(null);
        ci.cancel();
    }
}
