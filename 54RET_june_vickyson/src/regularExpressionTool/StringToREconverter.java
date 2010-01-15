package regularExpressionTool;

import java.util.LinkedList;
import java.util.Vector;

/**
 * 字符串到正则表达式的解释器
 * @author 刘军娥、卫松 2009-12-26
 */
public class StringToREconverter {
	private String reString="";//未解析的表达式串
	
	public StringToREconverter(){}
	/**
	 * 构造函数
	 * @param s 待转换为正则表达式的串
	 */
	StringToREconverter(String reString){
		this.reString=reString;
		
	}

	public void setS(String reString) {
		this.reString =reString;
	}

	public String getS() {
		return reString;
	}
	
	/**
	 * 将字符串解析为正则表达式
	 * @param s 待解析的字符串
	 * @param re 解析完的正则表达式
	 * @return boolean 返回是否转换成功
	 * */
	public boolean convert(String reString,RE re) {
		re.setReString(reString);//正则表达式串
		char operationChar=' ';//操作符
		LinkedList<RE> res=new LinkedList<RE>();//子正则表达式
		int backetLeft=0;//左括号个数
		int backetRight=0;//右括号个数
		Vector<String> reStrings=new Vector<String>();//将目串分成子串
		
		/*解析'+'号*/
		int lastIndex=0;//记录最后一个子串的起始下标
		for (int i = 0; i < reString.length(); i++) {
			if(reString.charAt(i)=='+' && backetLeft==backetRight){
				if(i==0||i==reString.length()-1||
						reString.charAt(i-1)=='('||reString.charAt(i+1)==')'||
						reString.charAt(i+1)=='*'||reString.charAt(i+1)=='+'){
					return false;
				}
				else {
					reStrings.addElement(reString.substring(lastIndex, i));
					lastIndex=i+1;
					operationChar='+';
				}		
			}
			else if(reString.charAt(i)=='('){
				backetLeft++;
			}
			else if(reString.charAt(i)==')'){
				backetRight++;
				if(backetRight>backetLeft){//如果出现括号反向的情况的处理
					return false;
				}
			}
			else{
				
			}	
//			System.out.println(backetLeft+"\t"+backetRight);
		}
		
		if(backetLeft!=backetRight){
			return false;
		}
		
		/*解析'&'号*/
		if(reStrings.size()==0){
			backetLeft=0;//左括号个数
			backetRight=0;//右括号个数
			lastIndex=0;
			for (int i = 0; i < reString.length(); i++) {
				if(reString.charAt(i)=='&' && backetLeft==backetRight){
					reStrings.addElement(reString.substring(lastIndex, i));
					lastIndex=i+1;
					operationChar='&';
				}
				else if(reString.charAt(i)=='('){
					backetLeft++;
				}
				else if(reString.charAt(i)==')'){
					backetRight++;
					if(backetRight>backetLeft){
						return false;
					}
				}
				else{
					
				}
			}
		}
		
		/*解析'*'号*/
		if(reStrings.size()==0){
			backetLeft=0;//左括号个数
			backetRight=0;//右括号个数
			lastIndex=0;
			for (int i = 0; i < reString.length(); i++) {
				if(reString.charAt(i)=='*' && backetLeft==backetRight){
					if(i==0||reString.charAt(i-1)=='('||reString.charAt(i-1)=='+'){
						return false;
					}
					else{
						reStrings.addElement(reString.substring(lastIndex, i));
						lastIndex=i+1;
						operationChar='*';
					}
				}
				else if(reString.charAt(i)=='('){
					backetLeft++;
				}
				else if(reString.charAt(i)==')'){
					backetRight++;
					if(backetRight>backetLeft){
						return false;
					}
				}
				else{
					
				}
			}
		}
		
		/*如果一个括号括住了整个串，将括号去掉，继续解析*/
		if(reStrings.size()==0 && backetLeft==backetRight && reString.charAt(0)=='(' && reString.charAt(reString.length()-1)==')'){
			reString=reString.substring(1,reString.length()-1);
			if(!convert(reString,re)){
				return false;
			}
			return true;
		}
		
		/*将最后一段子串提取出来*/
		if(reStrings.size()!=0 && operationChar!='*'){
			reStrings.addElement(reString.substring(lastIndex));
		}
		
		/*如果递归到只有单独一个字符时，停止递归*/
		if(reStrings.size()==0 && backetLeft==backetRight && backetLeft==0){
			re.setOperationChar(' ');
			re.setRes(null);
			System.out.println(re.getOperationChar()+"\t"+re.getReString());
			return true;
		}
		
		
		
		
		
		/*构建语法分析树*/
		for (int i = 0; i < reStrings.size(); i++) {
			res.add(i, new RE(reStrings.get(i)));
		}
		re.setRes(res);
		re.setOperationChar(operationChar);
		System.out.println(re.getOperationChar()+"\t"+re.getReString());
		
		/*递归构造语法分析树*/
		if(re.getRes()!=null && re.getOperationChar()!=' '){
			for (int i = 0; i < res.size(); i++) {
				if(!convert(reStrings.get(i),res.get(i))){
					return false;
				};
			}
		}
		
		return true;
	}
}
