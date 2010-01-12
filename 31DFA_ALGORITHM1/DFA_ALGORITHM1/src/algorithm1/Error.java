/**
 * @Title: Error.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2009-12-24 下午01:00:51
 * @Version: V1.0
 */
package algorithm1;


public class Error {
	public static String ErrorMsg[]={
		"1.该算法适用于DFA，请将自动机转换为DFA！",
		"2.不符合DFA定义！"
	};
    public static String getMsg(int index)
    {
    	String str="出现错误：";
    	str+=ErrorMsg[index];
    	return str;
    }
}
