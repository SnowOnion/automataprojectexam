package cn.tsinghua.mse.automatondesigner.tools;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

public class CommonTool {
	
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
