package UI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


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
public class TestUI extends javax.swing.JFrame {
	private JButton minBt;
	private JButton intersection;
	private JButton notBt;
	private JButton testTypeBt;
	private JButton unionBt;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TestUI inst = new TestUI();
				
				inst.setLocationRelativeTo(null);

				inst.setVisible(true);
			}
		});
	}
	
	public TestUI() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				minBt = new JButton();
				getContentPane().add(minBt);
				minBt.setText("\u6700\u5c0f\u5316");
				minBt.setBounds(260, 43, 96, 22);
				minBt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						minBtActionPerformed(evt);
					}
				});
			}
			{
				intersection = new JButton();
				getContentPane().add(intersection);
				intersection.setText("\u4ea4\u64cd\u4f5c");
				intersection.setBounds(38, 113, 82, 22);
				intersection.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						intersectionActionPerformed(evt);
					}
				});
			}
			{
				unionBt = new JButton();
				getContentPane().add(unionBt);
				unionBt.setText("\u81ea\u52a8\u673a\u5e76\u64cd\u4f5c");
				unionBt.setBounds(140, 113, 120, 22);
				unionBt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						unionBtActionPerformed(evt);
					}
				});
			}
			{
				notBt = new JButton();
				getContentPane().add(notBt);
				notBt.setText("\u8865\u64cd\u4f5c");
				notBt.setBounds(280, 113, 76, 22);
				notBt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						notBtActionPerformed(evt);
					}
				});
			}
			{
				testTypeBt = new JButton();
				getContentPane().add(testTypeBt);
				testTypeBt.setText("\u6d4b\u8bd5\u81ea\u52a8\u673a\u7c7b\u578b");
				testTypeBt.setBounds(38, 43, 187, 22);
				testTypeBt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						testTypeBtActionPerformed(evt);
					}
				});
			}
			this.setPreferredSize(new Dimension(410, 207));
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void testTypeBtActionPerformed(ActionEvent evt) {
		System.out.println("testTypeBt.actionPerformed, event="+evt);
		//TODO add your code for testTypeBt.actionPerformed
		TestTypeUI testType = new TestTypeUI();
		testType.setLocationRelativeTo(null);
		testType.setVisible(true);
	}
	
	private void minBtActionPerformed(ActionEvent evt) {
		System.out.println("minBt.actionPerformed, event="+evt);
		//TODO add your code for minBt.actionPerformed
		MinUI minUI = new MinUI();
		minUI.setLocationRelativeTo(null);
		minUI.setVisible(true);
	}
	
	private void intersectionActionPerformed(ActionEvent evt) {
		System.out.println("intersection.actionPerformed, event="+evt);
		//TODO add your code for intersection.actionPerformed
		IntersectionUI intersectionUI = new IntersectionUI();
		intersectionUI.setLocationRelativeTo(null);
		intersectionUI.setVisible(true);
	}
	
	private void unionBtActionPerformed(ActionEvent evt) {
		System.out.println("unionBt.actionPerformed, event="+evt);
		//TODO add your code for unionBt.actionPerformed
		UnionUI unionUI = new UnionUI();
		unionUI.setLocationRelativeTo(null);
		unionUI.setVisible(true);
	}
	
	private void notBtActionPerformed(ActionEvent evt) {
		System.out.println("notBt.actionPerformed, event="+evt);
		//TODO add your code for notBt.actionPerformed
		NotUI notUI = new NotUI();
		notUI.setLocationRelativeTo(null);
		notUI.setVisible(true);
	}

}
