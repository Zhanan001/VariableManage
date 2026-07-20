VariableManage - Nukkit plugin

功能:
- 全局变量（字符串）持久化到 plugins/VariableManage/variables.yml
- 提供 API VariableManage.getInstance().getVariableService()
- 占位符替换: %key% 与 ${var:key}
- 命令: /var set|get|del|list
- 触发事件 VariableChangeEvent

快速使用:
1. 将编译好的 VariableManage.jar 放入服务器 plugins/ 目录并启动服务器。
2. 在服务器里使用 /var set welcome Hello\ world 设置变量。
3. 在插件中调用:
   String val = VariableManage.getInstance().getVariableService().get("welcome");

示例:
- 占位符替换: String text = PlaceholderUtil.replacePlaceholders("欢迎 %welcome%", sender);
- 监听变量变更:
  server.getPluginManager().registerEvents(new Listener() {
    @EventHandler
    public void onVarChange(VariableChangeEvent e) { ... }
  }, yourPlugin);

如需我把代码提交到你的 GitHub 仓库，请授予写入或告诉我以何种方式提交（PR / push）。
