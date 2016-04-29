# Introduction #


完成进度：字符串~~表达式树~~NFA--DFA

支持的模式格式：闭包（？、**、+），任意字符（.）,|、[.md](.md)、()，终结符包括0-9、a-z、A-Z**

输入格式举例：
P83 3.4.3	（书中的模式）        (0+1)**1(0+1)+(0+1)**1(0+1)(0+1)

> （本程序的对应写法）   (0|1)**1(0|1)|(0|1)**1(0|1)(0|1)
> > [01](01.md)**1[01](01.md)|[01](01.md)**1[01](01.md)[01](01.md)

接口见detais的demo

# Details #


demo.java
<pre>
package cn.edu.thss.retool;<br>
<br>
public class Demo {<br>
<br>
/**<br>
* @param args<br>
*/<br>
public static void main(String[] args) {<br>
// TODO Auto-generated method stub<br>
String patternString;<br>
Pattern pattern;<br>
InputSequence inputs;<br>
patternString = "I(ve.?ry)+happy";//"[521]*";//"10*[12]|ab+[bc]?|011(12*|abc)+z";<br>
String input = "Iveryveryveryhappy";<br>
inputs = new InputSequence(input);<br>
<br>
pattern = new Pattern(patternString);<br>
<br>
System.out.println("The model of the DFA!");<br>
pattern.PrintPattern();<br>
<br>
Matcher matcher = pattern.CreateMatcher(inputs);<br>
if(matcher.Matches())<br>
{<br>
System.out.println("match!");<br>
System.out.println("Pattern String: " + patternString);<br>
System.out.println("Matched String: " + matcher.GetInputs());<br>
}<br>
else<br>
System.out.println("No match");<br>
<br>
}<br>
<br>
}<br>
<br>
</pre>