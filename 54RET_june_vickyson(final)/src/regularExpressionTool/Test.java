package regularExpressionTool;

import Automata.NFA;
import GUI.MainForm;

/**
 * 测试类
 * @author 刘军娥、卫松  2009-12-26
 *
 */
public class Test {
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("测试开始：");
		System.out.println("字符串解析为正则表达式：");
		MainForm mf=new MainForm();
		String s="(a+b)(c+d)(e*+f)";
		String reString=mf.pretreatment(s);
		System.out.println(reString);
		StringToREconverter strc=new StringToREconverter();
		RE re =new RE();
		if(strc.convert(reString, re)){
			System.out.println("解析完毕！\n");
		}
		
		System.out.println("正则表达式转换为NFA");
		NFA nfa=new NFA();
		REtoNFAconverter rtnc=new REtoNFAconverter(re, re.getReString());
		nfa=rtnc.convertToNFA(re);
		System.out.println("转换完毕！\n\n");
		String string="aceeeeeeeeeeeeeeeeeeee";
		System.out.println("下面判断串"+string+"是否被正则表达式接受");
		if(nfa.acceptString(string)){
			System.out.println("被接受了！！！");
		}
		else{
			System.out.println("不被接受");
		}			
    
    
		System.out.println("测试结束！");
	}

}
