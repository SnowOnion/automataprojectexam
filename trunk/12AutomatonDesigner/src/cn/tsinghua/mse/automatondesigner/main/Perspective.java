package cn.tsinghua.mse.automatondesigner.main;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import cn.tsinghua.mse.automatondesigner.ui.Editor_Main;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		//String editorArea = layout.getEditorArea();
		//layout.setEditorAreaVisible(true);
		//layout.addView("cn.tsinghua.mse.automatondesigner.ui.View_ToolBox", IPageLayout.LEFT, 0.2f, editorArea);
		//layout.addView("cn.tsinghua.mse.automatondesigner.ui.View_Property", IPageLayout.RIGHT, 0.75f, editorArea);
		//layout.addView("cn.tsinghua.mse.automatondesigner.ui.View_Main", IPageLayout.TOP, IPageLayout.RATIO_MAX, editorArea);
		//IFolderLayout folder = layout.createFolder("automaton", IPageLayout.TOP, 0.95f, editorArea);
		//folder.addPlaceholder(Editor_Main.ID + ":*");
		//folder.addView(View_Main.ID);
	}
}
