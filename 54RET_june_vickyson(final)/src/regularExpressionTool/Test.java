package regularExpressionTool;

import Automata.NFA;
import GUI.MainForm;

/**
 * ������
 * @author ����������  2009-12-26
 *
 */
public class Test {
	
	/**
	 * ����
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("���Կ�ʼ��");
		System.out.println("�ַ�������Ϊ������ʽ��");
		MainForm mf=new MainForm();
		String s="(a+b)(c+d)(e*+f)";
		String reString=mf.pretreatment(s);
		System.out.println(reString);
		StringToREconverter strc=new StringToREconverter();
		RE re =new RE();
		if(strc.convert(reString, re)){
			System.out.println("������ϣ�\n");
		}
		
		System.out.println("������ʽת��ΪNFA");
		NFA nfa=new NFA();
		REtoNFAconverter rtnc=new REtoNFAconverter(re, re.getReString());
		nfa=rtnc.convertToNFA(re);
		System.out.println("ת����ϣ�\n\n");
		String string="aceeeeeeeeeeeeeeeeeeee";
		System.out.println("�����жϴ�"+string+"�Ƿ�������ʽ����");
		if(nfa.acceptString(string)){
			System.out.println("�������ˣ�����");
		}
		else{
			System.out.println("��������");
		}			
    
    
		System.out.println("���Խ�����");
	}

}
