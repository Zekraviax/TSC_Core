package com.soft_cafe.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.soft_cafe.TSC_Core;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.TranslatableText;

public class ClientCommands {
    public static int printDate(CommandContext<FabricClientCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        String timeString = TSC_Core.getCalendar().getTimeAsFormattedString();
        String dateString = TSC_Core.getCalendar().getDateAsFormattedString();
        String printString = timeString + "\n" + dateString;

        serverCommandSourceCommandContext.getSource().getPlayer().sendMessage(new TranslatableText(printString), false);
        return 1;
    }


    public static int printHemisphere(CommandContext<FabricClientCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        serverCommandSourceCommandContext.getSource().getPlayer().sendMessage(new TranslatableText(TSC_Core.getHemisphere()), false);
        return 1;
    }
}