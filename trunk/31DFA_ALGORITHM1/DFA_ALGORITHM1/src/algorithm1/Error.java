/**
 * @Title: Error.java
 * @Description: TODO
 * @Package: algorithm1
 * @Author: zl
 * @Date: 2009-12-24 ����01:00:51
 * @Version: V1.0
 */
package algorithm1;


public class Error {
	public static String ErrorMsg[]={
		"1.���㷨������DFA���뽫�Զ���ת��ΪDFA��",
		"2.������DFA���壡"
	};
    public static String getMsg(int index)
    {
    	String str="���ִ���";
    	str+=ErrorMsg[index];
    	return str;
    }
}
