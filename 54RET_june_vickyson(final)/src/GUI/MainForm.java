package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Automata.NFA;

import regularExpressionTool.RE;
import regularExpressionTool.REtoNFAconverter;
import regularExpressionTool.StringToREconverter;

/**
 * ������
 * @author  ����������   2009-12-26
 * 
 */
public class MainForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton jB_OK=new JButton("ȷ��");//��ȷ������ť
	private JButton jB_exit=new JButton("�˳�");//���˳�����ť
	private JLabel jL_RE=new JLabel("������ʽ");//��������ʽ����ǩ
	private JTextField jT_RE =new JTextField();//��������ʽ�������
	private JLabel jL_string=new JLabel("�����ַ���");//�������ַ�������ǩ
	private JTextField jT_string=new JTextField();//�������ַ����������
	public static int stateIDnumber=0;//״̬���
	
	/**
	 * ���캯��
	 */
	public MainForm(){
		/**
		 * ��ʼ������
		 */
		super("������ʽ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 280);
		setResizable(false);
		setLocationRelativeTo(null);
		
		/**
		 * ���ֿؼ�
		 */
		setLayout(null);
		jB_OK.setBounds(50, 180, 100, 30);
		jB_exit.setBounds(250, 180, 100, 30);
		jL_RE.setBounds(20, 30, 80, 30);
		jT_RE.setBounds(100, 30, 270, 30);
		jL_string.setBounds(20, 90, 80, 30);
		jT_string.setBounds(100, 90, 270, 30);
		
		add(jB_OK);
		add(jB_exit);
		add(jL_RE);
		add(jT_RE);
		add(jL_string);
		add(jT_string);
		
		/**
		 * ��ʾ����
		 */
		setVisible(true);
		
		/**
		 * "�˳�"��ť�¼�
		 */
		jB_exit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(null, 
		                "�����Ҫ�˳�������?", "��ȷ��", JOptionPane.YES_NO_OPTION);        
		            if(result == JOptionPane.OK_OPTION){
		            	System.exit(0);
		            }			
			}
		});
		
		
		/**
		 * "ȷ��"��ť�¼�  ��������
		 */
		jB_OK.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				/**
				 * �ж�һ�����Ƿ�����������ʽ�����������
				 */				
				String reString=pretreatment(jT_RE.getText());
				StringToREconverter strc=new StringToREconverter();//����ﴮ����Ϊ������ʽ
				RE re =new RE();
				System.out.println("������ʽ��\t"+jT_RE.getText());
				System.out.println("�����ַ�����\t"+jT_string.getText());
				System.out.println("\n��"+reString+"����Ϊ�﷨�������Ĺ��̣�");
				if(!strc.convert(reString, re)){
					System.out.println("������ʽ��������");
					JOptionPane.showMessageDialog(null,
							"������ʽ��������", 
							"��ʽ����",
							JOptionPane.ERROR_MESSAGE);
					
				}
				else{
					System.out.println("������ϣ�\n");
					System.out.println("������ʽת��ΪNFA�Ĺ��̣�");
					NFA nfa=new NFA();
					REtoNFAconverter rtnc=new REtoNFAconverter(re, re.getReString());//��������ʽת��ΪNFA
					nfa=rtnc.convertToNFA(re);
					
					System.out.print("\n�ж��ַ���"+jT_string.getText()+"�Ƿ�����NFA���ܣ�");
					if (!nfa.acceptString(jT_string.getText())) {
						System.out.println("\t������\n����������������������ַ�������������ʽ���ܡ�������������������");
						JOptionPane.showMessageDialog(null,
								"�ַ���������������ʽ����������ԣ�", 
								"ƥ��ʧ��",
								JOptionPane.ERROR_MESSAGE);			
					} else {
						System.out.println("\t����\n�̡̡̡̡̡̡̡̡̡̡���ַ�����������ʽ�����ˣ��������̡̡̡̡̡̡̡̡̡�");
						JOptionPane.showMessageDialog(null,
								"�ַ�������������ʽ����������ԣ�", 
								"ƥ��ɹ�",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
			}
		});
	}
	
	/**
	 * Ԥ���������������ʽ��
	 * @param jTstring ����Ĵ�
	 * @return �������ܱ��㷨ʶ��Ĵ�
	 */
	public String pretreatment(String jTstring) {
		String reString=jTstring;
		int num=0;//�Ѿ��ӵ�'&'�ŵĸ���
		for (int i = 0; i < jTstring.length()-1; i++) {
			if((jTstring.charAt(i)==')' && !(jTstring.charAt(i+1)=='+' || jTstring.charAt(i+1)=='*' || jTstring.charAt(i+1)==')')) ||
					(jTstring.charAt(i)=='*' && !(jTstring.charAt(i+1)=='*' || jTstring.charAt(i+1)==')'||jTstring.charAt(i+1)=='+')) ||
					(!(jTstring.charAt(i)=='(' || jTstring.charAt(i)==')' || jTstring.charAt(i)=='+' || jTstring.charAt(i)=='*') && 
					!(jTstring.charAt(i+1)==')' || jTstring.charAt(i+1)=='+' || jTstring.charAt(i+1)=='*'))){
				num++;
				String s=reString.substring(0, i+num)+"&";
				String t=reString.substring(i+num);
				reString=s+t;
			}
		}
		return reString;
	}
	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		new MainForm();
	}

}
