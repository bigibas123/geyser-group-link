package cc.dingemans.bigibas123.geyser.group.link.fabric;

import cc.dingemans.bigibas123.geyser.group.link.common.ConfigLoader;
import cc.dingemans.bigibas123.geyser.group.link.common.Logic;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeyserGroupLink implements DedicatedServerModInitializer, ServerPlayConnectionEvents.Join {
    public final Logger logger = LoggerFactory.getLogger(BuildConstants.MODID);

    @Override
    public void onInitializeServer() {
        ConfigLoader.loadConfig(FabricLoader.getInstance().getConfigDir().resolve(BuildConstants.MODID + ".toml"));
        ServerPlayConnectionEvents.JOIN.register(this);
        logger.info("Initialized {} ({})", BuildConstants.PRETTY_NAME, BuildConstants.MODID);
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