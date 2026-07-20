// BedWar 中使用 VariableManage 示例（伪代码）
package com.example.bedwarhook;

import com.zhanan.variablemanage.VariableManage;

public class BedWarHookExample {

    public void onGameStart() {
        // 读取地图配置
        String mapName = VariableManage.getInstance().getVariableService().get("bedwar.map");
        if (mapName != null) {
            // 使用 mapName
        }
        // 设置变量示例
        VariableManage.getInstance().getVariableService().set("bedwar.lastWinner", "TeamA");
    }

    public String formatScoreboard(String template) {
        // 支持占位符替换
        return com.zhanan.variablemanage.PlaceholderUtil.replacePlaceholders(template, null);
    }
}
