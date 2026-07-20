package com.zhanan.variablemanage;

import cn.nukkit.utils.Config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VariableService {

    private final Config config;
    private final Map<String, String> variables = Collections.synchronizedMap(new HashMap<>());

    public VariableService(Config config) {
        this.config = config;
        // load all stringifiable values
        Map<String, Object> all = config.getAll();
        if (all != null) {
            for (Map.Entry<String, Object> e : all.entrySet()) {
                if (e.getValue() != null) {
                    variables.put(e.getKey(), String.valueOf(e.getValue()));
                }
            }
        }
    }

    public String get(String key) {
        return variables.get(key);
    }

    public Map<String, String> getAll() {
        synchronized (variables) {
            return new HashMap<>(variables);
        }
    }

    public Set<String> keys() {
        return getAll().keySet();
    }

    public void set(String key, String value) {
        String old = variables.put(key, value);
        config.set(key, value);
        config.save();
        // fire event
        VariableChangeEvent evt = new VariableChangeEvent(key, old, value);
        getPlugin().getServer().getPluginManager().callEvent(evt);
    }

    public boolean remove(String key) {
        if (variables.containsKey(key)) {
            String old = variables.remove(key);
            config.remove(key);
            config.save();
            VariableChangeEvent evt = new VariableChangeEvent(key, old, null);
            getPlugin().getServer().getPluginManager().callEvent(evt);
            return true;
        }
        return false;
    }

    public void saveAll() {
        synchronized (variables) {
            for (Map.Entry<String, String> e : variables.entrySet()) {
                config.set(e.getKey(), e.getValue());
            }
            config.save();
        }
    }

    private VariableManage getPlugin() {
        return VariableManage.getInstance();
    }
}
