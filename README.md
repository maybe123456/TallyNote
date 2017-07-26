# TallyNote
我的记账本

生成jks证书命令行：keytool -genkey -alias tallyNote.jks -keyalg RSA -validity 40000 -keystore tallyNote.jks

D:\tallyNote.jks

key alias wuhuihui

921023wuhuihui

C:\Users\fengyangtech\Desktop\

功能说明：

一、密保模块：
1.记录找回密码key，灵活密保；（完成 20170703）

====================================================================================================

二、首页模块
1.首页分栏显示:记账，理财，我的（完成 20170705）
2.界面返回时需要刷新页面；(完成 20170710)
3.界面可左右滑动选择页面；（完成 20170706）
4.账本功能：
  a.首页日账月账按钮中图标文字对齐美化；（完成 20170703）
  b.首页显示最近支出增加点击事件跳转日账列表；（完成 20170703）
  c.增加手动刷新功能（完成 20170713）
5.理财功能：
  a.显示理财未完成的记录中最近一次理财详情；（完成 20170703）
  b.新建理财记录后刷新页面；（完成 20170706）
  c.增加手动刷新功能（完成 20170713）
6.我的功能：
  a.文件导入/导出（完成待测 20170705，测试完成 20170706）
  b.重置密保（完成待测 20170705，测试完成 20170706）
  c.计算日收益，比较往期投资日收益；（完成 20170707）

  清除数据（完成待测 20170705，考虑到数据的完整性，不经意被删除的情况，去除此功能 20170706）

7.新增记录界面返回时增加“退出编辑”提示，“备注”改“说明”；（完成 20170725）
8.首页显示版本号；（完成 20170726）

====================================================================================================

三、列表显示模块：
1.列表显示item美化；（20% 20170703，已完成 20170712）
2.数值显示规范化；（80% 20170703，已完成 20170712）
3.第一个item增加删除记录操作；
  （增加详情界面，跳转详情时判断是否为第一条记录，是则有删除操作，完成 20170703）
  （去除详情界面，在列表中完成删除功能，完成 20170711）
4.日账单列表分类型显示（已完成 20170712）
5.报表功能(无)
6.列表显示列表数，删除某一项后刷新数据，列表中说明为空时显示“无”；(完成 20170725)
7.理财item中第一个时间显示为到期日期；(完成 20170725)
8.界面切换时列表刷新代码优化；(完成 20170726)

====================================================================================================

四、月账单模块：
月结账单的本次结余不可编辑，使用自动计算显示计算（完成 20170703）

====================================================================================================

五、理财模块：
1..理财时限整数显示，预期年化显示以%结束；（完成 20170703）
2.理财计算剩余天数是负数；（完成 20170703）
3.理财列表中排序按钮不可用；（完成 20170704）
4.理财导出文件中没有说明列；（完成 20170704）
5.提交理财时说明没显示；（完成 20170704）
6.点击完成理财闪退；（完成 20170704）
7.新建理财记录的拟日收益不可编辑，使用自动计算显示计算（完成 20170707）
8.income 增加id字段，理财模块强化显示未完成理财项目；（完成 20170726）

====================================================================================================

六、文件导入/导出模块：
1.导出文件名以小写字母名；（完成 20170703）
2.导出文件的表头规范化；（完成 20170703）
3.文件导出后可直接发送蓝牙/QQ/微信等 (无)
4.导出文件将旧文件清除；(完成 20170725)
5.导入文件后回调刷新，提示优化；(完成 20170725)

====================================================================================================

七、我的工具模块
1.显示版本名；(无)）
2.开启理财到期提醒；(无)

====================================================================================================

八、全局优化
1.代码优化；(无)
  a.去除网络监听工具，Activity工具，清除工具类中不必要方法；(完成 20170725)
  b.资源瘦身；(无 20170726)

2.成功失败时Toast显示图标；(完成 20170710)
3.布局调整(无)

====================================================================================================




