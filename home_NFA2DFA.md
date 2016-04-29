# Introduction #

  * 姓名：		李大鹏
  * 学号：		2009220652
  * 小组：		22
  * 开发环境：		用NetBeans6 . 0 . 1 开发。
  * 状态：		代码已经上传到SVN
  * 程序说明：		已经实现了NFA到DFA的转换。不过暂时还没实现识别字符串，正在做。用书上（中文版）50页的e-NFA做为输入，53页的DFA作为输出，做为测试用例，可以转换成功。
  * 目录结构：
    * NFA2DFA		----源代码
    * DesignSpec.docx	----详细设计文档
    * javadoc		----类说明文档


# Details #

  * 程序输入与输出：

  * 输入
    * Start Status:q0
    * Final Status:q5,
    * All Status:q0,q1,q2,q3,q4,q5,
    * Symbols:+,-;0,1,...,9;.;
    * Transactions:
    * q0->q1:~;+,-;
    * q1->q1:0,1,...,9;
    * q1->q2:.;
    * q2->q3:0,1,...,9;
    * q3->q3:0,1,...,9;
    * q3->q5:~;
    * q1->q4:0,1,...,9;
    * q4->q3:.;

  * 输出
    * Start Status:q0q1
    * Final Status:q3q5,q2q3q5,
    * All Status:q2,q1,q3q5,q1q4,q2q3q5,q0q1,
    * Symbols:+,-;0,1,...,9;.;
    * Transactions:
    * q2->q3q5:0,1,...,9;
    * q1->q2:.;
    * q1->q1q4:0,1,...,9;
    * q3q5->q3q5:0,1,...,9;
    * q1q4->q1q4:0,1,...,9;
    * q1q4->q2q3q5:.;
    * q2q3q5->q3q5:0,1,...,9;
    * q0q1->q2:.;
    * q0q1->q1:+,-;
    * q0q1->q1q4:0,1,...,9;