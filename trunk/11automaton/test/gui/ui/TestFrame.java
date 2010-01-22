package gui.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class TestFrame extends JFrame{
	 public static void main(String [] args){
		 TestFrame aip = new TestFrame();
	    	aip.setVisible(true);
	 }
	 public TestFrame(){
		 setSize(800,600);
			setTitle("TestFramer");
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			Toolkit toolkit = getToolkit();
			Dimension size = toolkit.getScreenSize();
			setLocation(size.width/2-getWidth()/2,size.height/2-getHeight()/2);
			
			getContentPane().add(new AutomatonInfoPanel());
	 }
}
