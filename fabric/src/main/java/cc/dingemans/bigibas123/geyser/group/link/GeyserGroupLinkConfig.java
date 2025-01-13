package cc.dingemans.bigibas123.geyser.group.link;


import eu.midnightdust.lib.config.MidnightConfig;



public class GeyserGroupLinkConfig extends MidnightConfig {
    public static final String CAT_GROUP = "groups";
    @Entry(category = CAT_GROUP)
    @Server
    public static String bedrockGroupName = "";
    @Entry(category = CAT_GROUP)
    @Server
    public static String javaGroupName = "";
}
