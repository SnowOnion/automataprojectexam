package regularExpressionTool;

import java.util.LinkedList;

/**
 * ������ʽ��
 * @author ����������   2009-12-26
 * 
 */
public class RE {
	/**
	 * ���ڵ��ŵĹ�ϵ�ַ�
	 */
	private char operationChar=' ';
	/**
	 * ������ʽ��
	 */
	private String reString="";
	/**
	 * ���ڵ������
	 */
	private LinkedList<RE> res =new LinkedList<RE>(); 
	
	public void setReString(String reString) {
		this.reString = reString;
	}
	public String getReString() {
		return reString;
	}
	public void setOperationChar(char operationChar) {
		this.operationChar = operationChar;
	}
	public char getOperationChar() {
		return operationChar;
	}
	public void setRes(LinkedList<RE> res) {
		this.res = res;
	}
	public LinkedList<RE> getRes() {
		return res;
	}

	
	/**
	 * ���������Ĺ��캯��
	 * */
	public RE(){}
	/**
	 * ���캯��
	 * @param operationChar ���ڵ��ŵĹ�ϵ�ַ�
	 * @param REstring ������ʽ��
	 * @param res ���ڵ������
	 * */
	public RE(char operationChar,String reString,LinkedList<RE> res){
		this.operationChar=operationChar;
		this.reString=reString;
		this.res=res;
	}
	public RE(String reString){
		this.reString=reString;
	}
}
