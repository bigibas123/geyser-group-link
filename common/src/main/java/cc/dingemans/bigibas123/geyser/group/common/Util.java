package cc.dingemans.bigibas123.geyser.group.common;

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
public class Util {

    @EnsuresNonNullIf(expression = "#1", result = true)
    public boolean isNotEmpty(String groupName) {
        return groupName != null && !groupName.isEmpty();
    }


    public CompletableFuture<Void> modifyUserGroups(UUID uuid, Logger logger, String bedrockGroup, String javaGroup) {
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

    public CompletableFuture<Void> removeGroupIfPresent(String group, @NonNull UUID player, @NonNull Logger logger) {
        if (isNotEmpty(group)) {
            return LuckPermsProvider.get().getUserManager().modifyUser(player, user -> {
                InheritanceNode n = InheritanceNode.builder().group(group).build();
                user.transientData().remove(n);
                logger.info("Removing {} from {} using {}", player, group, n);
            });
        } else {
            logger.debug("Not removing empty group name for player [{}]", player);
            return CompletableFuture.completedFuture(null);
        }
    }

    public CompletableFuture<Void> addGroupIfPresent(String group, @NonNull UUID player, @NonNull Logger logger) {
        if (isNotEmpty(group)) {
            return LuckPermsProvider.get().getUserManager().modifyUser(player, user -> {
                InheritanceNode n = InheritanceNode.builder().group(group).build();
                user.transientData().add(n);
                logger.info("Adding {} to {} using {}", player, group, n);
            });

        } else {
            logger.debug("Not adding empty group name for player [{}]", player);
            return CompletableFuture.completedFuture(null);
        }
    }
}
