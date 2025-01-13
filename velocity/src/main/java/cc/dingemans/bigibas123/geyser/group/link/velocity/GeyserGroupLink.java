package cc.dingemans.bigibas123.geyser.group.link.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

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

    @Inject
    private Logger logger;


    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

    }
}
