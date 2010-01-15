package regularExpressionTool;

import java.util.LinkedList;
import java.util.Vector;

/**
 * �ַ�����������ʽ�Ľ�����
 * @author ���������� 2009-12-26
 */
public class StringToREconverter {
	private String reString="";//δ�����ı��ʽ��
	
	public StringToREconverter(){}
	/**
	 * ���캯��
	 * @param s ��ת��Ϊ������ʽ�Ĵ�
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
	 * ���ַ�������Ϊ������ʽ
	 * @param s ���������ַ���
	 * @param re �������������ʽ
	 * @return boolean �����Ƿ�ת���ɹ�
	 * */
	public boolean convert(String reString,RE re) {
		re.setReString(reString);//������ʽ��
		char operationChar=' ';//������
		LinkedList<RE> res=new LinkedList<RE>();//��������ʽ
		int backetLeft=0;//�����Ÿ���
		int backetRight=0;//�����Ÿ���
		Vector<String> reStrings=new Vector<String>();//��Ŀ���ֳ��Ӵ�
		
		/*����'+'��*/
		int lastIndex=0;//��¼���һ���Ӵ�����ʼ�±�
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
				if(backetRight>backetLeft){//����������ŷ��������Ĵ���
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
		
		/*����'&'��*/
		if(reStrings.size()==0){
			backetLeft=0;//�����Ÿ���
			backetRight=0;//�����Ÿ���
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
		
		/*����'*'��*/
		if(reStrings.size()==0){
			backetLeft=0;//�����Ÿ���
			backetRight=0;//�����Ÿ���
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
		
		/*���һ��������ס����������������ȥ������������*/
		if(reStrings.size()==0 && backetLeft==backetRight && reString.charAt(0)=='(' && reString.charAt(reString.length()-1)==')'){
			reString=reString.substring(1,reString.length()-1);
			if(!convert(reString,re)){
				return false;
			}
			return true;
		}
		
		/*�����һ���Ӵ���ȡ����*/
		if(reStrings.size()!=0 && operationChar!='*'){
			reStrings.addElement(reString.substring(lastIndex));
		}
		
		/*����ݹ鵽ֻ�е���һ���ַ�ʱ��ֹͣ�ݹ�*/
		if(reStrings.size()==0 && backetLeft==backetRight && backetLeft==0){
			re.setOperationChar(' ');
			re.setRes(null);
			System.out.println(re.getOperationChar()+"\t"+re.getReString());
			return true;
		}
		
		
		
		
		
		/*�����﷨������*/
		for (int i = 0; i < reStrings.size(); i++) {
			res.add(i, new RE(reStrings.get(i)));
		}
		re.setRes(res);
		re.setOperationChar(operationChar);
		System.out.println(re.getOperationChar()+"\t"+re.getReString());
		
		/*�ݹ鹹���﷨������*/
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
