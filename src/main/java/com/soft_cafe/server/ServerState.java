package com.soft_cafe.server;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class ServerState extends PersistentState {
    public HashMap<UUID, PlayerState> players = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound playersNbtCompound = new NbtCompound();

        players.forEach((UUID, playerState) -> {
            NbtCompound playerStateNbt = new NbtCompound();

            playerStateNbt.putInt("birthDayAsNumber", playerState.birthDayAsNumber);
            playerStateNbt.putInt("birthMonthAsNumber", playerState.birthMonthAsNumber);
            playerStateNbt.putInt("birthYear", playerState.birthYear);

            playersNbtCompound.put(String.valueOf(UUID), playersNbtCompound);
        });

        nbt.put("players", playersNbtCompound);

        return nbt;
    }

    public static ServerState createFromNbt(NbtCompound tag) {
        ServerState serverState = new ServerState();

        NbtCompound playersTag = tag.getCompound("players");
        playersTag.getKeys().forEach(key -> {
            PlayerState playerState = new PlayerState();

            playerState.birthDayAsNumber = playersTag.getCompound(key).getInt("birthDayAsNumber");
            playerState.birthMonthAsNumber = playersTag.getCompound(key).getInt("birthMonthAsNumber");
            playerState.birthYear = playersTag.getCompound(key).getInt("birthYear");

            UUID uuid = UUID.fromString(key);
            serverState.players.put(uuid, playerState);
        });

        return serverState;
    }

    public static ServerState getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        ServerState serverState = persistentStateManager.getOrCreate(ServerState::createFromNbt, ServerState::new, "tsc_core");

        return serverState;
    }

    public static PlayerState getPlayerState(LivingEntity player) {
        ServerState serverState = getServerState(player.world.getServer());

        // Either get the player using their UUID, or create a new player state if there's no date for the player yet
        PlayerState playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerState());

        return playerState;
    }
}
