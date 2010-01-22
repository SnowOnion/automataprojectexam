package UI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import DFAalg1.DFAAlgorithm1;
import DFAalg1.DFAOpInterface;
import Exception.NotDFAException;
import automaton.Automaton;
import automaton.AutomatonFactory;


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
public class UnionUI extends javax.swing.JFrame {
	private JLabel jLabel1;
	private JButton Bt_union;
	private JButton Bt_cancel;
	private JLabel jLabel2;
	private JTextField filename2FT;
	private JButton chooseFile2Bt;
	private JButton chooseFile;
	private JTextField fileName1TF;
	public String filename_open1;
	public String filename_open2;
	private JLabel jLabel3;
	private JTextField saveFileTF;
	private JButton saveBt;
	public String filename_save;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UnionUI inst = new UnionUI();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public UnionUI() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			{
				setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				this.setTitle("自动机并操作");
				getContentPane().setLayout(null);
				{
					jLabel1 = new JLabel();
					getContentPane().add(jLabel1);
					jLabel1.setText("\u9009\u62e9\u6587\u4ef61\uff1a");
					jLabel1.setBounds(44, 34, 82, 15);
				}
				{
					fileName1TF = new JTextField();
					getContentPane().add(fileName1TF);
					fileName1TF.setBounds(44, 61, 235, 22);
					fileName1TF.setEditable(false);
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
					Bt_union = new JButton();
					getContentPane().add(Bt_union);
					Bt_union.setText("\u81ea\u52a8\u673a\u5e76\u64cd\u4f5c");
					Bt_union.setBounds(43, 284, 146, 22);
					Bt_union.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							Bt_unionActionPerformed(evt);
						}
					});
				}
				{
					Bt_cancel = new JButton();
					getContentPane().add(Bt_cancel);
					Bt_cancel.setText("\u53d6\u6d88");
					Bt_cancel.setBounds(328, 284, 65, 22);
					Bt_cancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							Bt_cancelActionPerformed(evt);
						}
					});
				}
				{
					chooseFile2Bt = new JButton();
					getContentPane().add(chooseFile2Bt);
					chooseFile2Bt.setText("\u9009\u62e9\u6587\u4ef6");
					chooseFile2Bt.setBounds(312, 129, 89, 22);
					chooseFile2Bt.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							chooseFile2BtActionPerformed(evt);
						}
					});
				}
				{
					filename2FT = new JTextField();
					getContentPane().add(filename2FT);
					filename2FT.setEditable(false);
					filename2FT.setBounds(43, 129, 235, 22);
				}
				{
					jLabel2 = new JLabel();
					getContentPane().add(jLabel2);
					jLabel2.setText("\u9009\u62e9\u6587\u4ef62\uff1a");
					jLabel2.setBounds(43, 102, 82, 15);
				}
				{
					saveBt = new JButton();
					getContentPane().add(saveBt);
					saveBt.setText("\u9009\u62e9\u6587\u4ef6");
					saveBt.setBounds(312, 214, 89, 22);
					saveBt.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							saveBtActionPerformed(evt);
						}
					});
				}
				{
					saveFileTF = new JTextField();
					getContentPane().add(saveFileTF);
					saveFileTF.setEditable(false);
					saveFileTF.setBounds(43, 214, 235, 22);
				}
				{
					jLabel3 = new JLabel();
					getContentPane().add(jLabel3);
					jLabel3.setText("\u4fdd\u5b58\u6587\u4ef6\uff1a");
					jLabel3.setBounds(43, 178, 82, 15);
				}
			}
			this.setPreferredSize(new Dimension(450, 407));
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
		chooser.setDialogTitle("选择文件");
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			filename_open1 = chooser.getSelectedFile().getAbsolutePath();
			fileName1TF.setText(filename_open1);
		}

	}
	
	private void Bt_unionActionPerformed(ActionEvent evt) {
		System.out.println("Bt_union.actionPerformed, event="+evt);
		//TODO add your code for Bt_union.actionPerformed
		Automaton auto1,auto2,intersectionAuto;
		try{
			AutomatonFactory autoFact = AutomatonFactory.getInstance();
			auto1 = autoFact.getAutomatonFromXml(new File(filename_open1));
			auto2 = autoFact.getAutomatonFromXml(new File(filename_open2));
			DFAOpInterface testAlg= new DFAAlgorithm1();
			try {
				intersectionAuto = testAlg.unionOP(auto1, auto2);
			} catch (NotDFAException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e.getMessage(),"alert",JOptionPane.ERROR_MESSAGE);;
				return;
			}
			autoFact.writeAutomatonToXml(intersectionAuto, new File(filename_save));
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "错误文件",  "alert",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
		JOptionPane.showMessageDialog(null, "保存成功");
	}
	
	private void Bt_cancelActionPerformed(ActionEvent evt) {
		System.out.println("Bt_cancel.actionPerformed, event="+evt);
		//TODO add your code for Bt_cancel.actionPerformed
		this.dispose();
	}
	
	private void chooseFile2BtActionPerformed(ActionEvent evt) {
		System.out.println("chooseFile2Bt.actionPerformed, event="+evt);
		//TODO add your code for chooseFile2Bt.actionPerformed
		JFileChooser chooser = new JFileChooser(new File(".//"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "xml文档", "xml");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("选择文件");
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			filename_open2 = chooser.getSelectedFile().getAbsolutePath();
			filename2FT.setText(filename_open2);
		}
		
	}
	
	private void saveBtActionPerformed(ActionEvent evt) {
		System.out.println("saveBt.actionPerformed, event="+evt);
		//TODO add your code for saveBt.actionPerformed
		JFileChooser chooser = new JFileChooser(new File(".//"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "xml文档", "xml");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("保存文件");
		int returnVal = chooser.showSaveDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			filename_save = chooser.getSelectedFile().getAbsolutePath();
			filename_save += ".xml";
			saveFileTF.setText(filename_save);
		}
	}

}
