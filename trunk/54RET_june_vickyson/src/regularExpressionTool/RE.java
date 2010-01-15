package regularExpressionTool;

import java.util.LinkedList;

/**
 * 正则表达式类
 * @author 刘军娥、卫松   2009-12-26
 * 
 */
public class RE {
	private char operationChar=' ';//父节点存放的关系字符
	private String reString="";//正则表达式串
	private LinkedList<RE> res =new LinkedList<RE>(); //父节点的子树
	
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
	 * 不带参数的构造函数
	 * */
	public RE(){}
	/**
	 * 构造函数
	 * @param operationChar 父节点存放的关系字符
	 * @param REstring 正则表达式串
	 * @param res 父节点的子树
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
