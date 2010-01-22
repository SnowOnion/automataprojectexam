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
public class NotUI extends javax.swing.JFrame {
	private JLabel jLabel1;
	private JButton Bt_not;
	private JButton Bt_cancel;
	private JLabel jLabel2;
	private JTextField fileSaveTF;
	private JButton saveFileBt;
	private JButton chooseFile;
	private JTextField fileNameTF;
	public String filename_open;
	public String filename_save;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				NotUI inst = new NotUI();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public NotUI() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			{
				setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				this.setTitle("自动机补操作");
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
					Bt_not = new JButton();
					getContentPane().add(Bt_not);
					Bt_not.setText("\u81ea\u52a8\u673a\u8865\u64cd\u4f5c");
					Bt_not.setBounds(43, 175, 129, 22);
					Bt_not.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							Bt_notActionPerformed(evt);
						}
					});
				}
				{
					Bt_cancel = new JButton();
					getContentPane().add(Bt_cancel);
					Bt_cancel.setText("\u53d6\u6d88");
					Bt_cancel.setBounds(329, 175, 65, 22);
					Bt_cancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							Bt_cancelActionPerformed(evt);
						}
					});
				}
				{
					saveFileBt = new JButton();
					getContentPane().add(saveFileBt);
					saveFileBt.setText("\u9009\u62e9\u6587\u4ef6");
					saveFileBt.setBounds(310, 129, 89, 22);
					saveFileBt.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							saveFileBtActionPerformed(evt);
						}
					});
				}
				{
					fileSaveTF = new JTextField();
					getContentPane().add(fileSaveTF);
					fileSaveTF.setEditable(false);
					fileSaveTF.setBounds(43, 129, 235, 22);
				}
				{
					jLabel2 = new JLabel();
					getContentPane().add(jLabel2);
					jLabel2.setText("\u4fdd\u5b58\u6587\u4ef6\uff1a");
					jLabel2.setBounds(43, 102, 82, 15);
				}
			}
			this.setPreferredSize(new Dimension(450, 277));
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
		chooser.setDialogTitle("打开文件");
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			filename_open = chooser.getSelectedFile().getAbsolutePath();
			fileNameTF.setText(filename_open);
		}

	}
	
	private void Bt_notActionPerformed(ActionEvent evt) {
		System.out.println("Bt_not.actionPerformed, event="+evt);
		//TODO add your code for Bt_not.actionPerformed
		Automaton notAuto,test;
		try{
			AutomatonFactory autoFact = AutomatonFactory.getInstance();
			test = autoFact.getAutomatonFromXml(new File(filename_open));
			DFAOpInterface testAlg= new DFAAlgorithm1();
			try {
				notAuto = testAlg.notOP(test);
			} catch (NotDFAException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e.getMessage(),"alert",JOptionPane.ERROR_MESSAGE);;
				return;
			}
			autoFact.writeAutomatonToXml(notAuto, new File(filename_save));
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
	
	private void saveFileBtActionPerformed(ActionEvent evt) {
		System.out.println("saveFileBt.actionPerformed, event="+evt);
		//TODO add your code for saveFileBt.actionPerformed
		JFileChooser chooser = new JFileChooser(new File(".//"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "xml文档", "xml");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("保存文件");
		int returnVal = chooser.showSaveDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			filename_save = chooser.getSelectedFile().getAbsolutePath();
			filename_save += ".xml";
			fileSaveTF.setText(filename_save);
		}
	}

}
