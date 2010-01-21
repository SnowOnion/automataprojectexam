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
 * 主窗口
 * @author  刘军娥、卫松   2009-12-26
 * 
 */
public class MainForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton jB_OK=new JButton("确定");//“确定”按钮
	private JButton jB_exit=new JButton("退出");//“退出”按钮
	private JLabel jL_RE=new JLabel("正则表达式");//“正则表达式”标签
	private JTextField jT_RE =new JTextField();//“正则表达式”输入框
	private JLabel jL_string=new JLabel("待测字符串");//“待测字符串”标签
	private JTextField jT_string=new JTextField();//“待测字符串”输入框
	public static int stateIDnumber=0;//状态序号
	
	/**
	 * 构造函数
	 */
	public MainForm(){
		/**
		 * 初始化窗体
		 */
		super("正则表达式工具");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 280);
		setResizable(false);
		setLocationRelativeTo(null);
		
		/**
		 * 布局控件
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
		 * 显示窗体
		 */
		setVisible(true);
		
		/**
		 * "退出"按钮事件
		 */
		jB_exit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(null, 
		                "您真的要退出程序吗?", "请确认", JOptionPane.YES_NO_OPTION);        
		            if(result == JOptionPane.OK_OPTION){
		            	System.exit(0);
		            }			
			}
		});
		
		
		/**
		 * "确定"按钮事件  输出检测结果
		 */
		jB_OK.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				/**
				 * 判断一个串是否属于正则表达式所代表的语言
				 */				
				String reString=pretreatment(jT_RE.getText());
				StringToREconverter strc=new StringToREconverter();//将表达串解析为正则表达式
				RE re =new RE();
				System.out.println("正则表达式：\t"+jT_RE.getText());
				System.out.println("待测字符串：\t"+jT_string.getText());
				System.out.println("\n将"+reString+"解析为语法分析树的过程：");
				if(!strc.convert(reString, re)){
					System.out.println("正则表达式输入有误！");
					JOptionPane.showMessageDialog(null,
							"正则表达式输入有误！", 
							"格式错误",
							JOptionPane.ERROR_MESSAGE);
					
				}
				else{
					System.out.println("解析完毕！\n");
					System.out.println("正则表达式转换为NFA的过程：");
					NFA nfa=new NFA();
					REtoNFAconverter rtnc=new REtoNFAconverter(re, re.getReString());//将正则表达式转化为NFA
					nfa=rtnc.convertToNFA(re);
					
					System.out.print("\n判断字符串"+jT_string.getText()+"是否被上述NFA接受：");
					if (!nfa.acceptString(jT_string.getText())) {
						System.out.println("\t不接受\n×××××××××●●字符串不被正则表达式接受●●×××××××××");
						JOptionPane.showMessageDialog(null,
								"字符串不属于正则表达式所代表的语言！", 
								"匹配失败",
								JOptionPane.ERROR_MESSAGE);			
					} else {
						System.out.println("\t接受\n√√√√√√√√√√★★字符串被正则表达式接受了！！！★★√√√√√√√√√√");
						JOptionPane.showMessageDialog(null,
								"字符串属于正则表达式所代表的语言！", 
								"匹配成功",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
			}
		});
	}
	
	/**
	 * 预处理输入的正则表达式串
	 * @param jTstring 输入的串
	 * @return 处理后的能被算法识别的串
	 */
	public String pretreatment(String jTstring) {
		String reString=jTstring;
		int num=0;//已经加的'&'号的个数
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
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		new MainForm();
	}

}
