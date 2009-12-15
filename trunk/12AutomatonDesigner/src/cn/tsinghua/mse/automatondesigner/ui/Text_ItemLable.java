/**
 * 
 */
package cn.tsinghua.mse.automatondesigner.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import automatondesigner.SystemConstant;

import cn.tsinghua.mse.automatondesigner.interfaces.IPaint;

/**
 * @author David
 *
 */
public class Text_ItemLable implements IPaint {
	
	private Point leftTopPtn = null;
	private String contentStr = "";

	/* (non-Javadoc)
	 * @see cn.tsinghua.mse.automatondesigner.interfaces.IPaint#paint(org.eclipse.swt.graphics.GC, byte, java.lang.String)
	 */
	@Override
	public void paint(GC gc, byte statue) {
		if (statue == SystemConstant.IMAGE_TYPE_SELECTED)
			gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_RED));
		else if (statue == SystemConstant.IMAGE_TYPE_COMMON)
			gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_BLUE));
		gc.drawText(contentStr, leftTopPtn.x, leftTopPtn.y, SWT.DRAW_DELIMITER);
	}
	
	public Text_ItemLable(){
		leftTopPtn = null;
		contentStr = "";
	}

	public Text_ItemLable(String content, Point ptn){
		leftTopPtn = ptn;
		contentStr = content;
	}

	@Override
	public void updateOriginalLocation() {
		// TODO Auto-generated method stub
		
	}
	
}
