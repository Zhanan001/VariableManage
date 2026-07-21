package tip.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Minimal Api shim for tip variables registration used by BedWar
 */
public class Api {
    private static final Map<String, Class<?>> regs = new ConcurrentHashMap<>();

    public static void registerVariables(String id, Class<?> clazz) {
        regs.put(id, clazz);
        // We do not instantiate here because BaseVariable requires a Player in constructor.
        // Registration is enough so other systems can instantiate when needed.
    }

    public static Map<String, Class<?>> getRegistered() {
        return regs;
    }
}
