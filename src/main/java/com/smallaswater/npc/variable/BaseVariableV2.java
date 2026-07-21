package com.smallaswater.npc.variable;

import cn.nukkit.Player;
import com.smallaswater.npc.data.RsNpcConfig;

/**
 * Minimal BaseVariableV2 implementation that BedWar's BedWarVariable can extend.
 * Provides addVariable to write into VariableManage plugin's VariableService.
 */
public abstract class BaseVariableV2 {

    public void onUpdate(Player player, RsNpcConfig rsNpcConfig) {
        // default no-op
    }

    // BedWar's code calls addVariable("{bd-...}", value)
    protected void addVariable(String key, String value) {
        if (key == null) return;
        String norm = key.replaceAll("^\\{\\s*|\\s*\\}$", "");
        try {
            com.zhanan.variablemanage.VariableManage vm = com.zhanan.variablemanage.VariableManage.getInstance();
            if (vm != null && vm.getVariableService() != null) {
                vm.getVariableService().set(norm, value == null ? "" : value);
            }
        } catch (NoClassDefFoundError | Exception e) {
            // ignore if VariableManage plugin not available
        }
    }
}
