package cc.dingemans.bigibas123.geyser.group.link.common;

import lombok.experimental.UtilityClass;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.types.InheritanceNode;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.floodgate.api.FloodgateApi;
import org.slf4j.Logger;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@UtilityClass
public class Logic {

    public CompletableFuture<Void> modifyUserGroups(UUID uuid, Logger logger) {
        var config = ConfigLoader.getConfig();
        var javaGroup = config.getJavaGroupName();
        var bedrockGroup = config.getBedrockGroupName();
        CompletableFuture<?>[] futures = new CompletableFuture<?>[2];
        if (FloodgateApi.getInstance().isFloodgatePlayer(uuid)) {
            futures[0] = (removeGroupIfPresent(javaGroup, uuid, logger));
            futures[1] = (addGroupIfPresent(bedrockGroup, uuid, logger));
        } else {
            futures[0] = (removeGroupIfPresent(bedrockGroup, uuid, logger));
            futures[1] = (addGroupIfPresent(javaGroup, uuid, logger));
        }
        return CompletableFuture.allOf(futures);
    }

    private CompletableFuture<Void> removeGroupIfPresent(String group, @NonNull UUID player, @NonNull Logger logger) {
        if (isNotEmpty(group)) {
            return LuckPermsProvider.get().getUserManager().modifyUser(player, user -> {
                InheritanceNode n = InheritanceNode.builder().group(group).build();
                user.transientData().remove(n);
                logger.debug("Removing {} from {} using {}", player, group, n);
            });
        } else {
            logger.debug("Not removing empty group name for player [{}]", player);
            return CompletableFuture.completedFuture(null);
        }
    }

    private CompletableFuture<Void> addGroupIfPresent(String group, @NonNull UUID player, @NonNull Logger logger) {
        if (isNotEmpty(group)) {
            return LuckPermsProvider.get().getUserManager().modifyUser(player, user -> {
                InheritanceNode n = InheritanceNode.builder().group(group).build();
                user.transientData().add(n);
                logger.debug("Adding {} to {} using {}", player, group, n);
            });

        } else {
            logger.debug("Not adding empty group name for player [{}]", player);
            return CompletableFuture.completedFuture(null);
        }
    }

    @EnsuresNonNullIf(expression = "#1", result = true)
    public boolean isNotEmpty(String groupName) {
        return groupName != null && !groupName.isEmpty();
    }

}
