package committee.nova.mods.mappreset;

import com.mojang.brigadier.CommandDispatcher;
import committee.nova.mods.mappreset.util.Config;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

/**
 * @Project: mappreset
 * @Author: cnlimiter
 * @CreateTime: 2024/10/2 19:04
 * @Description:
 */
public class ModCmd {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mappreset").then(Commands.literal("reload").executes(context -> {
            Config.sync(ModConfig.class);
            context.getSource().sendSuccess(() -> Component.literal("Config reloaded"), true);
            return 1;
        })));
    }
}
