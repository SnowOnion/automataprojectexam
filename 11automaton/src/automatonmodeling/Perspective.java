package automatonmodeling;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {
	public void createInitialLayout(IPageLayout layout) {
		final String properties = "org.eclipse.ui.views.PropertySheet";
		final String outline = "org.eclipse.ui.views.ContentOutline";
		final String editorArea = layout.getEditorArea();
		
		IFolderLayout rightBottomFolder = layout.createFolder("RightBottom", IPageLayout.BOTTOM, 0.5f, "RightTop");
		IFolderLayout leftTopFolder = layout.createFolder("leftTop", IPageLayout.BOTTOM, 0.34f, editorArea);
		leftTopFolder.addView(properties);
		rightBottomFolder.addView(outline);
		layout.setEditorAreaVisible(true);
	}
}
