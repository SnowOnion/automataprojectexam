package cn.tsinghua.mse.automatondesigner.ui;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.EditorPart;
import org.w3c.dom.Document;

import automatondesigner.SystemConstant;

import cn.tsinghua.mse.automatondesigner.dataobject.Automaton;
import cn.tsinghua.mse.automatondesigner.dataobject.AutomatonConst;
import cn.tsinghua.mse.automatondesigner.exporttoxml.DomBaseParser;
import cn.tsinghua.mse.automatondesigner.exporttoxml.PDADomParser;
import cn.tsinghua.mse.automatondesigner.exporttoxml.AutomatonParserFactory;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Canvas_Automaton;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Circle_State;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Graphic_MiddleAutomaton;
import cn.tsinghua.mse.automatondesigner.graphicsobj.Polyline_Trans;
import cn.tsinghua.mse.automatondesigner.interfaces.ICanvasContainer;
import cn.tsinghua.mse.automatondesigner.tools.CommonTool;

public class Editor_Main extends EditorPart implements ICanvasContainer {
	public static final String ID = "cn.tsinghua.mse.automatondesigner.ui.Editor_Main";
	
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	public static int INSTANCENUM = 0;

	private Automaton m_Automaton = null;
	private Canvas_Automaton canvas;
	private IWorkbenchWindow mainWindow = null;
	private String AutomatonPrefix = SystemConstant.PREFIX_STATE_NAME;
	
	private boolean isDirty = false;
	
	private String filePathAndName = null;
	
	
	public String getFilePathAndName() {
		return filePathAndName;
	}

	public void setFilePathAndName(String filePathAndName) {
		this.filePathAndName = filePathAndName;
	}

	public Editor_Main() {
		super();
		m_Automaton = new Automaton();
	}
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		if (filePathAndName == null){
			FileDialog dlg = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
			dlg.setFileName(this.getTitle()+".xml");
			dlg.setFilterNames(new String[]{"XML File"});
			dlg.setFilterExtensions(new String[]{"*.xml"});
			filePathAndName = dlg.open();
			if (filePathAndName == null){
				return;
			}
			File file = new File(filePathAndName);
			if (file.exists()){
				if(!MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "已存在", "该文件已经存在，是否覆盖？"))
					filePathAndName = null;
					return;
			}
			DomBaseParser parser = AutomatonParserFactory.getParser(m_Automaton.getM_Type());			
			Document document = parser.getDocumentFromAutomaton(getGMiddleAutomaton());
			parser.writeDocumentToFile(document, file);
		}else{
			File file = new File(filePathAndName);
			DomBaseParser parser = AutomatonParserFactory.getParser(m_Automaton.getM_Type());			
			Document document = parser.getDocumentFromAutomaton(getGMiddleAutomaton());
			parser.writeDocumentToFile(document, file);
		}
		
		setDirty(false);
		firePropertyChange(PROP_DIRTY);
	}
	
	public Graphic_MiddleAutomaton getGMiddleAutomaton(){
		return new Graphic_MiddleAutomaton(m_Automaton, canvas.getM_Polylines(), canvas.getM_Circles());
	}

	@Override
	public void doSaveAs() {
		FileDialog dlg = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
		dlg.setFileName(this.getTitle()+".xml");
		dlg.setFilterNames(new String[]{"XML File"});
		dlg.setFilterExtensions(new String[]{"*.xml"});
		filePathAndName = dlg.open();
		if (filePathAndName == null){
			return;
		}
		File file = new File(filePathAndName);
		if (file.exists()){
			if(!MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "已存在", "该文件已经存在，是否覆盖？"))
				filePathAndName = null;
				return;
		}
		DomBaseParser parser = AutomatonParserFactory.getParser(m_Automaton.getM_Type());			
		Document document = parser.getDocumentFromAutomaton(getGMiddleAutomaton());
		parser.writeDocumentToFile(document, file);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		this.setSite(site);
		this.setInput(input);
		this.setPartName(input.getName());
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		toolkit.paintBordersFor(container);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		ScrolledForm scrolledForm = toolkit.createScrolledForm(container);
		toolkit.paintBordersFor(scrolledForm);

		canvas = new Canvas_Automaton(scrolledForm.getBody(), SWT.DOUBLE_BUFFERED, this);
		canvas.setSize(1500, 1500);
		toolkit.adapt(canvas);
		toolkit.paintBordersFor(canvas);
		canvas.setPrefix_StateName(AutomatonPrefix);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				canvas.paint(e.gc);
			}
		});
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public Automaton getM_Automaton() {
		return m_Automaton;
	}

	/* (non-Javadoc)
	 * @see cn.tsinghua.mse.automatondesigner.ui.CanvasContainerIFC#setM_Automaton(cn.tsinghua.mse.automatondesigner.dataobject.Automaton)
	 */
	public void setM_Automaton(Automaton mAutomaton) {
		m_Automaton = mAutomaton;
	}


	/* (non-Javadoc)
	 * @see cn.tsinghua.mse.automatondesigner.ui.CanvasContainerIFC#doDelete()
	 */
	public boolean doDelete() {
		boolean result = canvas.doDelete();
		if (result){
			setDirty(true);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see cn.tsinghua.mse.automatondesigner.ui.CanvasContainerIFC#doAlign(byte)
	 */
	public boolean doAlign(byte direction) {
		boolean result = canvas.doAlign(direction);
		if (result){
			setDirty(true);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see cn.tsinghua.mse.automatondesigner.ui.CanvasContainerIFC#isContainsCiricle()
	 */
	public boolean isContainsCiricle(){
		if (canvas.getNumofSelectedCircle() == 0){
			return false;
		}
		return true;
	}

	@Override
	public String getAutomatonPrefix() {
		// TODO Auto-generated method stub
		return this.AutomatonPrefix;
	}

	@Override
	public IWorkbenchWindow getMainWindow() {
		// TODO Auto-generated method stub
		return this.mainWindow;
	}

	@Override
	public void setAutomatonPrefix(String automatonPrefix) {
		this.AutomatonPrefix = automatonPrefix;
	}

	@Override
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(PROP_DIRTY);
	}

	@Override
	public void setMainWindow(IWorkbenchWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	@Override
	public void saveAsImage() {
		if (!OS.IsWin32s && !OS.IsWinNT && !OS.IsWin95){
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "错误", "本操作仅支持Win32系统！");
			return;
		}
		FileDialog dlg = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
		dlg.setFileName(this.getTitle()+".jpg");
		dlg.setFilterNames(new String[]{"Image Files"});
		dlg.setFilterExtensions(new String[]{"*.jpg"});
		String fileName = dlg.open();
		if (fileName == null){
			return;
		}
		File file = new File(fileName);
		if (file.exists()){
			if(!MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "已存在", "该文件已经存在，是否覆盖？"))
				return;
		}
		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData []{CommonTool.makeShotImage(canvas).getImageData()};
		loader.save(fileName, SWT.IMAGE_JPEG);
	}
	
	public void setCanvasProperties(ArrayList<Circle_State> s, ArrayList<Polyline_Trans> p){
		if (canvas != null){
			canvas.setM_Circles(s);
			canvas.setM_Polylines(p);
		}
	}

	@Override
	public Canvas_Automaton getCanvas() {
		return canvas;
	}

}
