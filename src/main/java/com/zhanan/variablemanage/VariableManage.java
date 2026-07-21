package com.zhanan.variablemanage;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.io.File;

public class VariableManage extends PluginBase {

    private static VariableManage instance;
    private VariableService variableService;

    @Override
    public void onEnable() {
        instance = this;
        this.getLogger().info("VariableManage enabling...");
        // create data folder
        this.getDataFolder().mkdirs();
        // init service
        File file = new File(getDataFolder(), "variables.yml");
        Config cfg = new Config(file, Config.YAML);
        this.variableService = new VariableService(cfg);
        // register command
        this.getServer().getCommandMap().register("var", new VarCommand("var", this.getVariableService()));
        // start compatibility refresher to pull variables from registered shims (BedWar compatibility)
        try{
            // schedule every 5 seconds (100 ticks)
            this.getServer().getScheduler().scheduleRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    CompatRefresher.refreshOnce();
                }
            }, 100);
            this.getLogger().info("VariableManage compatibility refresher scheduled (every 5s)");
        }catch (Throwable t){
            // If scheduling fails, log but continue (older/newer API differences)
            this.getLogger().warning("Failed to schedule compatibility refresher: " + t.getMessage());
        }
        this.getLogger().info("VariableManage enabled.");
    }

    @Override
    public void onDisable() {
        if (this.variableService != null) {
            this.variableService.saveAll();
        }
        this.getLogger().info("VariableManage disabled.");
    }

    public static VariableManage getInstance() {
        return instance;
    }

    public VariableService getVariableService() {
        return variableService;
    }

    /**
     * Trigger a one-time compatibility refresh from registered external variable providers
     */
    public void refreshCompatibility() {
        CompatRefresher.refreshOnce();
    }
}
