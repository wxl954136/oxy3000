1.导出可执行jar文件
    1.1 File -> Project Structure
    1.2 在弹出的窗口中左侧选中"Artifacts"，点击"+"选择jar，然后选择"from modules with dependencies"
    1.3 在配置窗口中配置"Main Class"，这儿选择一个入口java类（有main方法），完成后，点击OK，OK按钮。到这儿就完成了“Project Structure”的设置了。
    1.4 回到IDEA的主菜单，选择“Build - Build Artifacts”下的“Build”或者“Rebuild”即可生成最终的可运行的jar.
    1.5 参照 : https://www.cnblogs.com/ffaiss/p/10908483.html
    1.6 切记，生成META-INF/MAINIFEST.MF,必须是在src下面，后面的子路径要删掉

2.串口助手使用说明
    2.1 https://blog.csdn.net/qq_32714173/article/details/105379401
    2.2 数据解析https://www.cnblogs.com/jinghuyue/p/10226848.html
    2.3 串口读取注意事项（注意是jdk下面的jre，不要放错了）
        2.3.1 Copy RXTXcomm.jar ---> <JAVA_HOME>\jre\lib\ext
        2.3.2 Copy rxtxSerial.dll ---> <JAVA_HOME>\jre\bin
        2.3.3 Copy rxtxParallel.dll ---> <JAVA_HOME>\jre\bin


3.关于iText->pdf文件导出的说明
    3.1 页眉页脚参考:https://www.cnblogs.com/joann/p/5511905.html
4. exe4j 将jar转换为exe
    4.1 参考 https://blog.csdn.net/zzzgd_666/article/details/80756430
