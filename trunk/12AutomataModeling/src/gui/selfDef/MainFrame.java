package gui.selfDef;

import gui.GraphEditor;
import gui.editor.EditorMenuBar;
import gui.editor.EditorToolBar;

import java.awt.*;

import javax.swing.*;

import com.mxgraph.util.mxConstants;

public class MainFrame extends JFrame{

	/**
	 * @param args
	 */
	public MainFrame(){
		Container contentPane=this.getContentPane();
	}
	public static void main(String[] args) {
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}

		mxConstants.SHADOW_COLOR = Color.LIGHT_GRAY;
		MainFrame frameEditor=new MainFrame();
		frameEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameEditor.setSize(870, 640);	
		frameEditor.setVisible(true);
//		GraphEditor editor = new GraphEditor();
//		editor.createFrame(new EditorMenuBar(editor)).setVisible(true);
	}

}
