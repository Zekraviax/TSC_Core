package com.soft_cafe.command;

import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;

public class CommandsRegister {
    public static void registerTscClientCommands() {
        ClientCommandManager.DISPATCHER.register(
            ClientCommandManager.literal("tsc")
                .executes(context -> {
                    return ClientCommands.getBirthday(context.getSource());
            })
        );
    }
}