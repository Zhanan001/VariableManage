// RYS-MenuX 中的示例（伪代码），在生成菜单项文本前替换占位符
package com.example.menuhook;

import com.zhanan.variablemanage.PlaceholderUtil;
import cn.nukkit.command.CommandSender;

public class MenuXHookExample {

    public String prepareMenuLine(String line, CommandSender viewer) {
        // 让 MenuX 在渲染文本时调用此方法
        return PlaceholderUtil.replacePlaceholders(line, viewer);
    }
}
