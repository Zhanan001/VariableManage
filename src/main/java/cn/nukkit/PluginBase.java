package cn.nukkit;

import java.io.File;
import java.util.logging.Logger;

/**
 * Minimal PluginBase shim to allow compilation when cn.nukkit is not present.
 * These methods provide no real server integration and are only intended
 * to let this project compile in CI environments that don't provide Nukkit.
 */
public class PluginBase {
    private final Logger logger = Logger.getLogger("PluginBase");

    public Logger getLogger() {
        return logger;
    }

    public File getDataFolder() {
        return new File(".");
    }

    public cn.nukkit.Server getServer() {
        return cn.nukkit.Server.getInstance();
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}
