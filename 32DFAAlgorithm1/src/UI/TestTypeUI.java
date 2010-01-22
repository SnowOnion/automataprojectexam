package UI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import DFAalg1.DFAAlgorithm1;
import DFAalg1.DFAOpInterface;
import automaton.*;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class TestTypeUI extends javax.swing.JFrame {
	private JLabel jLabel1;
	private JButton Bt_test;
	private JButton Bt_cancel;
	private JButton chooseFile;
	private JTextField fileNameTF;
	public String filename;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				TestTypeUI inst = new TestTypeUI();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public TestTypeUI() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			{
				setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				this.setTitle("测试自动机类型");
				getContentPane().setLayout(null);
				{
					jLabel1 = new JLabel();
					getContentPane().add(jLabel1);
					jLabel1.setText("\u9009\u62e9\u6587\u4ef6\uff1a");
					jLabel1.setBounds(44, 34, 82, 15);
				}
				{
					fileNameTF = new JTextField();
					getContentPane().add(fileNameTF);
					fileNameTF.setBounds(44, 61, 235, 22);
					fileNameTF.setEditable(false);
				}
				{
					chooseFile = new JButton();
					getContentPane().add(chooseFile);
					chooseFile.setText("\u9009\u62e9\u6587\u4ef6");
					chooseFile.setBounds(312, 61, 89, 22);
					chooseFile.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							chooseFileActionPerformed(evt);
						}
					});
				}
				{
					Bt_test = new JButton();
					getContentPane().add(Bt_test);
					Bt_test.setText("\u6d4b\u8bd5");
					Bt_test.setBounds(44, 118, 70, 22);
					Bt_test.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							Bt_testActionPerformed(evt);
						}
					});
				}
				{
					Bt_cancel = new JButton();
					getContentPane().add(Bt_cancel);
					Bt_cancel.setText("\u53d6\u6d88");
					Bt_cancel.setBounds(331, 118, 65, 22);
					Bt_cancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							Bt_cancelActionPerformed(evt);
						}
					});
				}
			}
			this.setPreferredSize(new Dimension(440, 207));
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void chooseFileActionPerformed(ActionEvent evt) {
		System.out.println("chooseFile.actionPerformed, event="+evt);
		//TODO add your code for chooseFile.actionPerformed
		JFileChooser chooser = new JFileChooser(new File(".//"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "xml文档", "xml");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("选择自动机文件");
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			filename = chooser.getSelectedFile().getAbsolutePath();
			fileNameTF.setText(filename);
		}

	}
	
	private void Bt_testActionPerformed(ActionEvent evt) {
		System.out.println("Bt_test.actionPerformed, event="+evt);
		//TODO add your code for Bt_test.actionPerformed
		if(filename != null){
			Automaton test;
			if(!filename.equals("")){
				AutomatonFactory autoFact = AutomatonFactory.getInstance();
				try {
					test = autoFact.getAutomatonFromXml(new File(filename));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "错误文件",  "alert",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					return;
				}
				DFAOpInterface testAlg= new DFAAlgorithm1();
				JOptionPane.showMessageDialog(null, testAlg.getAutomatonType(test));
				return;
			}
		}
		JOptionPane.showMessageDialog(null, "请选择文件", "alert", JOptionPane.ERROR_MESSAGE);
		
	}
	
	private void Bt_cancelActionPerformed(ActionEvent evt) {
		System.out.println("Bt_cancel.actionPerformed, event="+evt);
		//TODO add your code for Bt_cancel.actionPerformed
		this.dispose();
	}

}
