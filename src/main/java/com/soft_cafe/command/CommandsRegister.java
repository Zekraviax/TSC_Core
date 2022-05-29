package com.soft_cafe.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class CommandsRegister {
    public static void registerTSCClientCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<FabricClientCommandSource>literal("tsc")
            .then(LiteralArgumentBuilder.literal("year"))
                .then(LiteralArgumentBuilder.literal("set"))
                    .then(RequiredArgumentBuilder.<FabricClientCommandSource, Integer>argument("year", IntegerArgumentType.integer())
                        .executes(ctx -> {
                            return ServerCommands.setCalendarYear(ctx.getArgument("year", Integer.class));
                        }))
                .then(LiteralArgumentBuilder.literal("add"))
                    .then(RequiredArgumentBuilder.<FabricClientCommandSource, Integer>argument("year", IntegerArgumentType.integer())
                        .executes(ctx -> {
                            return ServerCommands.addCalendarYear(ctx.getArgument("year", Integer.class));
                        })));
    }

    public void registerSetYear() {

    }
}