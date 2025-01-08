package cc.dingemans.bigibas123.geyser.group.link;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeyserGroupLink implements ModInitializer {
    public static final String MOD_ID = "geyser-group-link";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        MidnightConfig.init(MOD_ID, GeyserGroupLinkConfig.class);
        ServerPlayConnectionEvents.JOIN.register(new PlayerJoinEventHandler(LOGGER));
        LOGGER.info("Initialized {}", MOD_ID);
    }

}