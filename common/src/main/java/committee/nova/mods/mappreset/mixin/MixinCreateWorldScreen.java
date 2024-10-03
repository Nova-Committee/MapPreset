package committee.nova.mods.mappreset.mixin;

import committee.nova.mods.mappreset.ModConfig;
import committee.nova.mods.mappreset.client.MapListScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
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
@Mixin(CreateWorldScreen.class)
public abstract class MixinCreateWorldScreen extends Screen {

    @Shadow
    @Nullable
    private TabNavigationBar tabNavigationBar;
    @Shadow
    @Final
    WorldCreationUiState uiState;

    protected MixinCreateWorldScreen(Component component) {
        super(component);
    }

    @Shadow
    protected abstract void onCreate();

    @Shadow
    public abstract void popScreen();

    @Shadow @Final private HeaderAndFooterLayout layout;

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/HeaderAndFooterLayout;addToFooter(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;"
    ), cancellable = true)
    private void mappreset$reCreate(CallbackInfo ci) {
        LinearLayout linearLayout = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));

        var createButton = Button.builder(Component.translatable("selectWorld.create"), (button) -> {
            this.onCreate();
        }).build();
        linearLayout.addChild(createButton);
        createButton.visible = ModConfig.CREATE_BUTTON_ENABLED;

        linearLayout.addChild(Button.builder(Component.translatable("selectWorld.use_temp"), (button) -> {
            this.minecraft.setScreen(new MapListScreen(this));
        }).build());

        linearLayout.addChild(Button.builder(CommonComponents.GUI_CANCEL, (button) -> {
            this.popScreen();
        }).build());

        this.layout.visitWidgets((abstractWidget) -> {
            abstractWidget.setTabOrderGroup(1);
            this.addRenderableWidget(abstractWidget);
        });
        this.tabNavigationBar.selectTab(0, false);
        this.uiState.onChanged();
        this.repositionElements();
        ci.cancel();
    }

}
