package com.zhanan.variablemanage;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.io.File;
import java.util.Map;

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
        // register listeners if needed (none by default)
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
}
