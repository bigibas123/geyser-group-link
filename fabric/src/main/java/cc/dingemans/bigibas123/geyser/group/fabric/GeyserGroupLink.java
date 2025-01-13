package cc.dingemans.bigibas123.geyser.group.fabric;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeyserGroupLink implements DedicatedServerModInitializer {
    public static final String MOD_ID = "geyser-group-link";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeServer() {
        MidnightConfig.init(MOD_ID, GeyserGroupLinkConfig.class);
        ServerPlayConnectionEvents.JOIN.register(new PlayerJoinEventHandler(LOGGER));
        LOGGER.info("Initialized {}", MOD_ID);
    }

}