package com.zhanan.variablemanage;

import com.smallaswater.npc.variable.VariableManage as SmVarManage; // alias for clarity

public class CompatRefresher {

    /**
     * One-time refresh: iterate registered com.smallaswater.npc.variable providers and call onUpdate
     */
    public static void refreshOnce() {
        try {
            // Iterate providers registered via our shim
            try {
                java.util.Map<String, Class<?>> regs = com.smallaswater.npc.variable.VariableManage.getRegistered();
                for (Class<?> clazz : regs.values()) {
                    try {
                        Object inst = null;
                        try {
                            inst = clazz.getDeclaredConstructor().newInstance();
                        } catch (NoSuchMethodException e) {
                            // try no-arg fallback
                        }
                        if (inst != null) {
                            try {
                                java.lang.reflect.Method m = clazz.getMethod("onUpdate", cn.nukkit.Player.class, com.smallaswater.npc.data.RsNpcConfig.class);
                                m.invoke(inst, (Object) null, new com.smallaswater.npc.data.RsNpcConfig());
                            } catch (NoSuchMethodException ignored) {
                                // no onUpdate method
                            }
                        }
                    } catch (Throwable t) {
                        // ignore per-provider errors
                    }
                }
            } catch (NoClassDefFoundError ignore) {
                // shim not present
            }

            // tip.utils.Api registered classes are player-contextual; we don't instantiate here to avoid NPEs
        } catch (Throwable t) {
            // overall safety
            com.zhanan.variablemanage.VariableManage.getInstance().getLogger().warning("CompatRefresher error: " + t.getMessage());
        }
    }
}
