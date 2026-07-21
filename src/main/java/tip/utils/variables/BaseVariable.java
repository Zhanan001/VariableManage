package tip.utils.variables;

import cn.nukkit.Player;

/**
 * Minimal BaseVariable shim for TipVariable compatibility.
 */
public abstract class BaseVariable {
    protected final Player player;

    public BaseVariable(Player player) {
        this.player = player;
    }

    public abstract void strReplace();

    protected void addStrReplaceString(String key, String value) {
        if (key == null) return;
        String norm = key.replaceAll("^\\{\\s*|\\s*\\}$", "");
        try {
            com.zhanan.variablemanage.VariableManage vm = com.zhanan.variablemanage.VariableManage.getInstance();
            if (vm != null && vm.getVariableService() != null) {
                vm.getVariableService().set(norm, value == null ? "" : value);
            }
        } catch (NoClassDefFoundError | Exception e) {
            // ignore
        }
    }
}
