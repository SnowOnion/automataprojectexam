[黄崇迪](mailto:huangcd.thu@gmail.com) 2009210940

# 重要 #
项目已经基本完成，具体情况参考[项目说明文档](http://automataprojectexam.googlecode.com/svn/trunk/21Conversion/doc/%e8%af%b4%e6%98%8e%e6%96%87%e6%a1%a3.pdf)

# 开发环境 #
  * Windows Vista<sup>TM</sup> Home Premuim
  * IntelliJ IDEA 8.1.4
  * JDK1.6.0\_13

# 项目依赖库 #
  * dom4j （目前官网被黑……，依赖于[jaxen](http://jaxen.org/)）
  * [jung](http://jung.sourceforge.net/)（依赖于[Collections-Generic](http://larvalabs.com/collections/)、[Cern Colt Scientific Library 1.2.0](http://dsd.lbl.gov/~hoschek/colt/)）

# 项目简要介绍 #

所选题目为\*NFA/DFA的转换工具

具体要求如下：
  1. 用Java编写，保证可扩展性。
  1. 检查某个字符串是否被指定的自动机接受。
  1. 判断语言是否为空，是否为无穷。
  1. 实现NFA到DFA的相互转换。

考虑到可用性和扩展性需要，如果时间允许，增加下面一些内容：
  1. 实现有限状态自动机的导出（初步考虑dot、tikz、png等格式）。
  1. 实现有限状态自动机的显示，如果可能的话， 在此基础上动态显示字符串接受过程和自动机的转换过程。
  1. 对下推自动机上实现类似功能。

# 项目进度预期安排 #

确定各种格式需求以后开始项目（本周末到下周初）。
基本功能预计花一周时间完成。之后如果时间允许，实现附加部分功能， 每部分预计需要一周时间。

# 研究路线的简要介绍 #

> 一些实现思路如下：
    1. XML文件<=>自动机：考虑使用dom4j+XPath实现。为了保证可扩展性（能在使用不同DTD的XML文件上使用）， 可能会把XPath语句以配置文件的形式保存，或者使用接口的形式实现。
    1. 自动机的表示：将自动机以有向图的形式在内存中表示，其中节点表示状态，边表示转移。
    1. 检查串是否被接受：使用状态迁移的方式计算。
    1. 判断语言是否为空：初步考虑检查从开始状态是否有路径可达某个终止状态。
    1. 判断语言是否为无穷：初步考虑检查从开始状态到终止状态的路径是否存在环。
    1. 自动机的导出：dot、tikz根据相应格式输出，png可能需要使用到显示部分功能。
    1. 自动机的显示：初步考虑使用开源工具JUNG2.0。如果实现有难度，可能改用JUNG1.7.6。