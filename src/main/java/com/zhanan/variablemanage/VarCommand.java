package com.zhanan.variablemanage;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import java.util.Map;

public class VarCommand extends Command {

    private final VariableService service;

    public VarCommand(String name, VariableService service) {
        super(name, "VariableManage command", "/var <get|set|del|list|refresh> ...", null);
        this.service = service;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("/var set <key> <value>");
            sender.sendMessage("/var get <key>");
            sender.sendMessage("/var del <key>");
            sender.sendMessage("/var list");
            sender.sendMessage("/var refresh");
            return true;
        }
        String cmd = args[0].toLowerCase();
        switch (cmd) {
            case "set":
                if (args.length < 3) {
                    sender.sendMessage("Usage: /var set <key> <value>");
                    return true;
                }
                String key = args[1];
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    if (i > 2) sb.append(' ');
                    sb.append(args[i]);
                }
                String val = sb.toString();
                service.set(key, val);
                sender.sendMessage("Set " + key + " = " + val);
                return true;
            case "get":
                if (args.length < 2) {
                    sender.sendMessage("Usage: /var get <key>");
                    return true;
                }
                String g = service.get(args[1]);
                sender.sendMessage(args[1] + " = " + (g == null ? "null" : g));
                return true;
            case "del":
            case "remove":
                if (args.length < 2) {
                    sender.sendMessage("Usage: /var del <key>");
                    return true;
                }
                boolean ok = service.remove(args[1]);
                sender.sendMessage(ok ? "Removed " + args[1] : "Not found: " + args[1]);
                return true;
            case "list":
                Map<String,String> all = service.getAll();
                if (all.isEmpty()) {
                    sender.sendMessage("No variables set.");
                } else {
                    sender.sendMessage("Variables:");
                    for (Map.Entry<String,String> e : all.entrySet()) {
                        sender.sendMessage(e.getKey() + " = " + e.getValue());
                    }
                }
                return true;
            case "refresh":
                // trigger compatibility refresh
                try{
                    VariableManage vm = VariableManage.getInstance();
                    if(vm != null){
                        vm.refreshCompatibility();
                        sender.sendMessage("Compatibility refresh triggered.");
                    }else{
                        sender.sendMessage("VariableManage not initialized.");
                    }
                }catch (Exception ex){
                    sender.sendMessage("Refresh failed: " + ex.getMessage());
                }
                return true;
            default:
                sender.sendMessage("Unknown subcommand: " + cmd);
                return true;
        }
    }
}
