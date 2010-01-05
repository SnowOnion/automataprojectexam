package cn.tsinghua.mse.automatondesigner.tools;

import java.util.ArrayList;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class CommonTool {
	private static final int WM_PRINT = 0x0317;
	private static final int PRF_NONCLIENT = 0x00000002;
	private static final int PRF_CLIENT = 0x00000004;
	private static final int PRF_ERASEBKGND = 0x00000008;
	
	public static Image makeShotImage(Control control) {
		int width = control.getBounds().width;
		int height = control.getBounds().height;
		if (width > 0 && height > 0) {
			Image image = new Image(control.getDisplay(), width, height);
			GC gc = new GC(image);
			OS.SendMessage(control.handle, WM_PRINT, gc.handle, PRF_CLIENT
					| PRF_ERASEBKGND | PRF_NONCLIENT);
			gc.dispose();
			return image;
		}
		return null;
	}
	
	
	public static int[] pointArrayToIntArray(ArrayList<Point> ps){
		int[] result = new int[ps.size()*2];
		for (int i = 0; i < ps.size(); i++){
			result[2*i] = ps.get(i).x;
			result[2*i+1] = ps.get(i).y;
		}
		return result;
	}
	
	public static ArrayList<Point> PointsClone(ArrayList<Point> ps){
		ArrayList<Point> result = new ArrayList<Point>();
		for(Point p : ps){
			result.add(new Point(p.x, p.y));
		}
		return result;
	}
}
