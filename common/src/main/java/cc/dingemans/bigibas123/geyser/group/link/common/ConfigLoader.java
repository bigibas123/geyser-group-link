package cc.dingemans.bigibas123.geyser.group.link.common;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Path;


@UtilityClass
public class ConfigLoader {
    private static GeyserGroupLinkConfig config;

    public GeyserGroupLinkConfig getConfig() {
        if (config == null) {
            throw new NullPointerException("Config not loaded!");
        }
        return config;
    }

    public void loadConfig(Path filePath) {
        if (config == null) {
            if (filePath.toFile().exists() && !filePath.toFile().isDirectory()) {
                Toml toml = new Toml().read(filePath.toFile());
                config = toml.to(GeyserGroupLinkConfig.class);
            } else {
                config = new GeyserGroupLinkConfig("bedrock", "java");
                TomlWriter toml = new TomlWriter();
                try {
                    if (filePath.toFile().isDirectory()) {
                        filePath.toFile().delete();
                    }
                    filePath.getParent().toFile().mkdirs();
                    toml.write(config, filePath.toFile());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
