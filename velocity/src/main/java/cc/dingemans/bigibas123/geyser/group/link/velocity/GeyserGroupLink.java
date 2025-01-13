package cc.dingemans.bigibas123.geyser.group.link.velocity;

import cc.dingemans.bigibas123.geyser.group.link.common.ConfigLoader;
import cc.dingemans.bigibas123.geyser.group.link.common.Logic;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = BuildConstants.MODID,
        name = BuildConstants.PRETTY_NAME,
        version = BuildConstants.VERSION,
        authors = {"bigibas123"},
        dependencies = {
                @Dependency(id = "luckperms"),
                @Dependency(id = "floodgate")
        }
)
public class GeyserGroupLink {

    private final Logger logger;
    private final Path dataDirectory;

    private GeyserGroupLink(Logger logger, @DataDirectory Path dataDirectory) {
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }


    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        ConfigLoader.loadConfig(dataDirectory.resolve(BuildConstants.MODID + ".toml"));
    }

    @Subscribe
    public void onLogin(LoginEvent event) {
        Logic.modifyUserGroups(event.getPlayer().getUniqueId(), logger);
    }
}
