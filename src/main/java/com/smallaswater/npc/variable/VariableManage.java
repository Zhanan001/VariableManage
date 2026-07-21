package com.smallaswater.npc.variable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Compatibility shim: exposed so plugins like BedWar can register via VariableManage.addVariableV2(...)
 */
public class VariableManage {
    private static final Map<String, Class<?>> regs = new ConcurrentHashMap<>();

    public static void addVariableV2(String name, Class<?> clazz) {
        regs.put(name, clazz);
        // Try to instantiate and trigger a one-time update to collect variables if possible
        try {
            Object inst = clazz.getDeclaredConstructor().newInstance();
            try {
                // Try to call onUpdate(Player, RsNpcConfig) with nulls
                java.lang.reflect.Method m = clazz.getMethod("onUpdate", cn.nukkit.Player.class, com.smallaswater.npc.data.RsNpcConfig.class);
                m.invoke(inst, (Object) null, (Object) null);
            } catch (NoSuchMethodException ignore) {
                // ignore
            }
        } catch (Throwable ignored) {
            // ignore construction errors
        }
    }

    public static Map<String, Class<?>> getRegistered() {
        return regs;
    }
}
