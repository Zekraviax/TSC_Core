package com.soft_cafe.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.soft_cafe.Atmosphere;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.TranslatableText;

public class ClientCommands {
    public static int printDate(CommandContext<FabricClientCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        String timeString = Atmosphere.getCalendar().getTimeAsFormattedString();
        String dateString = Atmosphere.getCalendar().getDateAsFormattedString();
        String printString = timeString + "\n" + dateString;

        serverCommandSourceCommandContext.getSource().getPlayer().sendMessage(new TranslatableText(printString), false);
        return 1;
    }


    public static int printHemisphere(CommandContext<FabricClientCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        serverCommandSourceCommandContext.getSource().getPlayer().sendMessage(new TranslatableText(Atmosphere.getHemisphere()), false);
        return 1;
    }
}