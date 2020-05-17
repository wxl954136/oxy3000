<h1 style="text-align: center">EL-ADMIN 后台管理系统</h1>
<div style="text-align: center">

[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/wxl954136/oxy3000/blob/master/LICENSE)
[![star](https://gitee.com/elunez/eladmin/badge/star.svg?theme=white)](https://github.com/wxl954136/oxy3000)
[![GitHub stars](https://img.shields.io/github/stars/elunez/eladmin.svg?style=social&label=Stars)](https://github.com/wxl954136/oxy3000)
[![GitHub forks](https://img.shields.io/github/forks/elunez/eladmin.svg?style=social&label=Fork)](https://github.com/wxl954136/oxy3000)

</div>

#### 项目简介
一个基于RXTXcomm串口通讯读写案例,如果只有一个可用串口，则默认连接，如果有N个串口，需要链接

**开发文档：**  [本案例](https://www.youxueyou.cn)
**试验：** 仅针对特定系列产品

|     |   程序源码  |   备注  |
|---  |--- | --- |
|  github   |  https://github.com/wxl954136/oxy3000.git   | 开源  |

#### 主要特性
- c/s项目 使用java-swing技术栈。
- 使用最广泛的RXTXcomm串口通讯库。
- 多线程数据采集。
- 此应用未经允许不可商业用途

#### 开发注意事项-导出可执行jar文件
### 1 导出可执行jar文件
- 1.1 File -> Project Structure
- 1.2 在弹出的窗口中左侧选中"Artifacts"，点击"+"选择jar，然后选择"from modules with dependencies"
- 1.3 在配置窗口中配置"Main Class"，这儿选择一个入口java类（有main方法），完成后，点击OK，OK按钮。到这儿就完成了“Project Structure”的设置了。
。
- 1.4 回到IDEA的主菜单，选择“Build - Build Artifacts”下的“Build”或者“Rebuild”即可生成最终的可运行的jar.
- 1.5 参照 : https://www.cnblogs.com/ffaiss/p/10908483.html
- 1.6 切记，生成META-INF/MAINIFEST.MF,必须是在src下面，后面的子路径要删掉

### 1 串口助手使用说明
- 2.1 https://blog.csdn.net/qq_32714173/article/details/105379401
- 2.2 数据解析https://www.cnblogs.com/jinghuyue/p/10226848.html- 1.3 在配置窗口中配置"Main Class"，这儿选择一个入口java类（有main方法），完成后，点击OK，OK按钮。到这儿就完成了“Project Structure”的设置了。
。
- 2.3 串口读取注意事项（注意是jdk下面的jre，不要放错了），开发者选项
    - 2.3.1 Copy RXTXcomm.jar ---> <JAVA_HOME>\jre\lib\ext
    - 2.3.2 Copy rxtxSerial.dll ---> <JAVA_HOME>\jre\bin
    - 2.3.3 Copy rxtxParallel.dll ---> <JAVA_HOME>\jre\bin
    - config 自定义权限实现、redis配置、swagger配置
    - exception 项目统一异常的处理
    - utils 系统通用工具类
- 2.4 如果是用户使用，需将此文件放至jre的安装目录即可


### 3 关于iText->pdf文件导出的说明
- 3.1 页眉页脚参考:https://www.cnblogs.com/joann/p/5511905.html

### 4. exe4j 将jar转换为exe
- 4.1 参考 https://blog.csdn.net/zzzgd_666/article/details/80756430
- 4. exe4j 将jar转换为exe
 
#### 项目结构
项目采用按功能分模块文件开发方式
- libs 串口使用的jar包文件
    - RTXTcomm.jar文件须在idea上做为第三方加载，不可使用pom.xml
- resources 资源文件包
    - 涉及到图片、配置文件等一律从相对路径.\resources\xxx中取出
- src/main/java/bean/DataEntity
    - 采集文件实体类
- src/main/java/common/CommonUtils
    - 数据采集文件核心文件
- src/main/java/common/CommonUtils
    - 数据采集文件核心文件
- src/main/java/pdf
    - iText核心导出类
- src/main/java/utils
    - 工具类
- src/main/java/Main
    - 主窗体类

#### 项目捐赠
项目的发展离不开你的支持，请作者喝杯咖啡吧☕  支付宝:wxl_954136@163.com
#### 反馈交流
- 【悠蓝科技】电话&微信：15272080011
