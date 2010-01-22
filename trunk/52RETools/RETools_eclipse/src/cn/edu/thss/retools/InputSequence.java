/**
 * 
 */
package cn.edu.thss.retools;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * �������������������String�ķ�װ���ܹ�ʵ��ת�壬�����������֧�ֵ��ַ���
 * @author Huhao lifengxiang dengshuang
 *
 */
public class InputSequence {
	
	public static Set<Character> acceptChars = new LinkedHashSet<Character>();
	public static Set<Character> metaChars = new LinkedHashSet<Character>();
	public static char epsilon = '\0';//'#';
	public static char stackBottom = '$';
	public static char anything = '.';
	public static char startPosition = '^';
	public static int NO_IGNORE_CASE = 0;
	public static int PART_MATCH = 0;
	public static int IGNORE_CASE = 1;
	public static int FULL_MATCH = 1;
	public static int LONGEST_MATCH = 1;
	public static int SHORTEST_MATCH = 0;
	public static int ESCAPE = 1;	//�������� pattern�� ��Ҫ����ת��
	public static int NO_ESCAPE = 0; //����ƥ������Ĵ��� ����Ҫת��
	
	private int escape;			//��Ǹ����������ڶ�ȡʱ�Ƿ���Ҫת��
	private String input;	
	private int index;		//��ǰ�����ַ����±�
	private int length;
	
	static {
		char[] chars = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o' ,'p','q','r',
				's','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O'
				,'P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','0',
				' ', '!', ',', ';', '\'', '\"', '\t', ':', '/', '=', '&', '%', '~'};
		for(int i = 0; i < chars.length; i ++)
			acceptChars.add(Character.valueOf(chars[i]));
		
		char[] meta = new char[] {'.','?','*','+','|', '#', '$','\\', '(', ')', '[', ']', '-', '^'};
		for(int i = 0; i < meta.length; i ++)
			metaChars.add(Character.valueOf(meta[i]));
	}
	
	/**
	 * �ж�ch�Ƿ�Ϊʵ���ַ�
	 * @param ch
	 * @return
	 */
	public static boolean isRealInput(char ch){
		return acceptChars.contains(Character.valueOf(ch));
	}
	
	/**
	 * �ж�ch�Ƿ�Ϊת���ַ�
	 * @param ch
	 * @return
	 */
	public static boolean isEscape(char ch){
		return ch == '\\';
	}
	
	/**
	 * �ж�ch�Ƿ�ΪԪ�ַ�
	 * @param ch
	 * @return
	 */
	public static boolean isMetaChar(char ch){
		return metaChars.contains(Character.valueOf(ch));
	}
	
	public static boolean isValidInput(char ch){
		return acceptChars.contains(Character.valueOf(ch));
	}
	
	/**
	 * Ϊģʽ������������
	 * @param input
	 * @return
	 */
	public static InputSequence CreatePatternInput(String rep){
		return new InputSequence(rep, InputSequence.ESCAPE);
	}
	
	/**
	 * Ϊ��ƥ����ı�������������
	 * @param input
	 * @return
	 */
	public static InputSequence CreateTextInput(String input){
		return new InputSequence(input, InputSequence.NO_ESCAPE);
	}
	
	/**
	 * �жϵ�ǰ����Ϊģʽ���񣬻���Ϊ�ı�����
	 * @return
	 */
	public boolean forPattern(){
		return escape == 1;
	}
	
	protected InputSequence(String input, int escape) {
		this.input = input.concat("$");
		index = 0;
		length = input.length();
		this.escape = escape;
	}
	
	protected InputSequence(String input, int index, int escape){
		this.input = input;// + "$";//input.concat("$");
		this.index = index;
		length = input.length();
		this.escape = escape;
	}
	
	/**
	 * ��ȡ���ĵ�ǰ�ַ���ָ��ͬʱ����
	 * @return
	 */
	public char Getc() {
		char ch = input.charAt(index);
		if(this.needEscape()){
			ch = input.charAt(index + 1);
			if(ch == 't')
				ch = '\t';
			index += 2;
		}else{
			if(index < length){
				index ++;
			}	
		}			
		return ch;
	}
	
	/**
	 * ��ȡ��ǰ���ĵ�ǰ�ַ���ָ�벻��
	 * @return
	 */
	public char Peek() {
		char ch = '$';
		if(this.forPattern()){
			if(index <= length && (ch = input.charAt(index)) != '\\'){
				return ch;
			}else if(ch == '\\'){
				ch = input.charAt(index + 1);
				if(ch == 't')
					ch = '\t';
			}
		}else{
			ch = input.charAt(index);
		}			
		return ch;
	}
	
	/**
	 * ����InputSequenceΪPattern�����ҵ�ǰ�ַ�Ϊ\ʱ����Ӧ��ת��
	 * @return
	 */
	public boolean needEscape(){
		return this.forPattern() && input.charAt(index) == '\\';
	}
	
	/**
	 * ������һ���ַ�
	 */
	public void Ungetc() {
		if(this.forPattern()){
			if(index > 0 && InputSequence.isMetaChar(input.charAt(index)) 
					&& input.charAt(index - 1) == '\\')
				index -= 2;
			else if(index > 0)
				index --;
		}else{
			if(index > 0)
				-- index;
		}
	}
	
	public boolean IsEmpty() {
		return index == length; 
	}

	/**
	 * ��⵱ǰ�����Ƿ���δ���κ����
	 * @return
	 */
	public boolean outputEmpty(){
		return index == 0;
	}
	public String GetAllInputs(){
		return input.substring(0, length);
	}
	
	public String GetAllConsumed(){
		return input.substring(0, index);
	}
	
	public InputSequence Backup(){
		return new InputSequence(input.substring(index), escape);
	}
	
	public void Reset(){
		index = 0;
	}
	
	/**
	 * ����ǰ������
	 * @return
	 */
	public InputSequence reverse() {
		// TODO Auto-generated method stub
		String in = "";
		if(this.forPattern()){
			for(int i = length - 1; i >= 0; i-- ){
				if(i > 0 && InputSequence.isMetaChar(input.charAt(i))
						&& input.charAt(i - 1) == '\\'){
					in = in + '\\' + input.charAt(i);
					i --;
				}else{
					in = in + input.charAt(i);
				}
			}
		}else{
			for(int i = length - 1; i >= 0; i-- ){
				in = in + input.charAt(i);
			}
		}
		return new InputSequence(in, escape);
	}

	/**
	 * ���Ѿ����ĵ����ַ�����
	 * @return
	 */
	public String reverseConsumed() {
		// TODO Auto-generated method stub
		String out = "";
		if(this.forPattern()){
			String in = "";
			for(int i = 0; i < index; i ++){
				if(input.charAt(i) == '\\'){
					if(input.charAt(i+1) == 't')
						in += '\t';
					else					
						in += input.charAt(i + 1);
					i ++;
				}else
					in += input.charAt(i);
			}			
			for(int i = in.length() - 1; i >= 0; i-- ){
				out += in.charAt(i);
			}
		}else{
			for(int i = index - 1; i >= 0; i --)
				out += input.charAt(i);
		}
		return out;
	}
	
	/**
	 * ���Ŀ�¡��ֻ����index
	 */
	public Object clone(){
		return new InputSequence(input, index, escape);
	}

	/**
	 * ����ǰƥ����ַ�ȡ������������ƥ�䡣
	 * ת���ַ� ������Ҫ�ر���
	 * @return
	 */
	public InputSequence reverseByIndex() {
		// TODO Auto-generated method stub
		String in = "";
		if(this.forPattern()){
			for(int i = index - 1; i > 0; i-- ){
				if(input.charAt(i - 1) == '\\'){
					if(input.charAt(i) == 't')
						in = in + '\t';
					else
						in = in + "\\" + input.charAt(i);
				}else if(input.charAt(i) != '\\')				
					in = in + input.charAt(i);
			}
			if(input.charAt(0) != '\\')
				in = in + input.charAt(0);	
		}else{
			for(int i = index - 1; i >= 0; i --)
				in += input.charAt(i);
		}
		return new InputSequence(in, escape);
	}
	
	/**
	 * ��ȡ��ż��case�� Сд��ô�д����д���Сд
	 * @param ch
	 * @return
	 */
	public static char getSymCase(char ch){
		if(Character.isLowerCase(ch))
			return Character.toUpperCase(ch);
		else if(Character.isUpperCase(ch)){
			return Character.toLowerCase(ch);
		}
		return ch;
	}
}
