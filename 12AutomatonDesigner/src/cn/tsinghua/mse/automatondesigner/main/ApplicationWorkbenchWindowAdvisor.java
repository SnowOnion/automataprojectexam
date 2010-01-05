package cn.tsinghua.mse.automatondesigner.main;

import java.awt.Rectangle;

import org.eclipse.jface.action.ExternalActionManager;
import org.eclipse.jface.action.ExternalActionManager.IBindingManagerCallback;
import org.eclipse.jface.bindings.IBindingManagerListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        //org.eclipse.swt.graphics.Rectangle screenSize = Display.getDefault().getClientArea();
        //configurer.setInitialSize(new Point(screenSize.width, screenSize.height));
        //configurer.setInitialSize(new Point(800, 600));
        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(false);
        configurer.setTitle("Automaton Designer");
    }
    
   public void postWindowCreate() { 
	   super.postWindowCreate();
	   getWindowConfigurer().getWindow().getShell().setMaximized(true);
   }
}
