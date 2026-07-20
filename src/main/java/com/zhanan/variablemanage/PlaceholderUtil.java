package com.zhanan.variablemanage;

import cn.nukkit.command.CommandSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderUtil {

    // %key% OR ${var:key}
    private static final Pattern PERCENT = Pattern.compile("%([a-zA-Z0-9_\\-\\.]+)%");
    private static final Pattern BRACE = Pattern.compile("\\$\\{var:([a-zA-Z0-9_\\-\\.]+)\\}");

    public static String replacePlaceholders(String input, CommandSender sender) {
        if (input == null) return null;
        VariableService svc = VariableManage.getInstance().getVariableService();
        // %key% style
        Matcher m = PERCENT.matcher(input);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String key = m.group(1);
            String val = svc.get(key);
            if (val == null) val = "";
            m.appendReplacement(sb, Matcher.quoteReplacement(val));
        }
        m.appendTail(sb);
        String mid = sb.toString();
        // ${var:key} style
        m = BRACE.matcher(mid);
        sb = new StringBuffer();
        while (m.find()) {
            String key = m.group(1);
            String val = svc.get(key);
            if (val == null) val = "";
            m.appendReplacement(sb, Matcher.quoteReplacement(val));
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
