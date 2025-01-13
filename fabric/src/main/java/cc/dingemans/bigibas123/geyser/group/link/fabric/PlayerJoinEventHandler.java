package cc.dingemans.bigibas123.geyser.group.link.fabric;

import cc.dingemans.bigibas123.geyser.group.link.common.Logic;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.slf4j.Logger;

public class PlayerJoinEventHandler implements ServerPlayConnectionEvents.Join {
    private final Logger logger;

    public PlayerJoinEventHandler(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        if (handler.player == null) {
            logger.error("Player object is null");
            return;
        }
        Logic.modifyUserGroups(handler.player.getUuid(), logger).join();
    }
}
