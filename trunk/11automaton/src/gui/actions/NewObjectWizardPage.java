package gui.actions;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewObjectWizardPage extends WizardPage implements IWizardPage {
	static final String NEW_OBJECT_WIZARD_PAGE_NAME = "#NewObectWizardPage";
	private  Text sourceFileField;
	public Text text;
	
	protected NewObjectWizardPage() {
		super(NEW_OBJECT_WIZARD_PAGE_NAME);
		//setTitle("config file");
		setDescription("create new file");
		setPageComplete(false);
		//createTreeViewer(comp);
		setTitle("create new third");
		
	}
	
	public String getName() {
		return this.NEW_OBJECT_WIZARD_PAGE_NAME;
	}

	public void createControl(Composite parent) {
		System.out.println("go into createcontrol");
		// TODO Auto-generated method stub
		Composite comp = new Composite(parent,SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns=2;
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		layout.verticalSpacing = 10;
		layout.horizontalSpacing = 10;
		comp.setLayout(layout);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		comp.setFont(parent.getFont());
		
		Label label = new Label(comp,SWT.NONE);
		label.setText("输入文件名： ");
		label.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,2,1));
		text = new Text(comp, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,2,1));
		
		final Label label_path = new Label(comp, SWT.NONE);
		label_path.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,2,1));
		label_path.setText("选择地址 :");
		final GridData girdData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		sourceFileField = new Text(comp,SWT.BORDER);
		sourceFileField.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				updatePageComplete();
			}

			private void updatePageComplete() {
				// TODO Auto-generated method stub
				
			}
		});
		sourceFileField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Button button = new Button(comp,SWT.NONE);
		button.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				browseForSourceFile();
			}

			private void browseForSourceFile() {
				// TODO Auto-generated method stub
				DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.OPEN);
				
				dialog.open();
				String path = dialog.getFilterPath();
				sourceFileField.setText(path);
				((NewEntityModelWizard) NewObjectWizardPage.this.getWizard()).setPath(path);
				
			}
		});
		button.setText("Browse...");
		/*Button button = new Button(comp,SWT.CHECK);
		button.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,1,1));
		button.setText("DFA");
		Button button1 = new Button(comp,SWT.CHECK);
		button.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,1,1));
		button1.setText("NFA");
		Button button2 = new Button(comp,SWT.CHECK);
		button.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,1,1));
		button2.setText("PDA");*/
		//createTreeViewer(comp);
		Label label1 = new Label(comp,SWT.NONE);
		label1.setText("选择自动机类型：");
		label1.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,2,1));
		final Combo combo = new Combo(comp,SWT.DROP_DOWN);
		combo.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,2,1));
		combo.add("PDA");
		combo.add("NFA");
		combo.add("DFA");
		combo.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				String a =combo.getItem(combo.getSelectionIndex());
				((NewEntityModelWizard) NewObjectWizardPage.this.getWizard()).setType(a);
			}
			
		});
		setControl(comp);
		/*Label labelChar = new Label(comp,SWT.NONE);
		labelChar.setText("选择字符集");
		labelChar.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,2,1));
		final Combo comboChar  = new Combo(comp,SWT.DROP_DOWN);
		comboChar.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,2,1));
		comboChar.add("0-1字符集");
		comboChar.add("0,1,2");
		comboChar.add("a,b");
		comboChar.add("a,b,c");
		comboChar.add("所有字母集");

		Label labelOtherChar = new Label(comp,SWT.NONE);
		labelOtherChar.setText("其它字符集(以\',\'隔开) ");
		labelOtherChar.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,2,1));
		Text textChar = new Text(comp,SWT.BORDER);
		textChar.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false,2,1));*/
		//textChar.setSize(30, 30);
	}
	
	/*private TreeViewer createTreeViewer(Composite comp) {
		TreeViewer viewer = new TreeViewer(comp,SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
		viewer.getControl().setLayoutData(new GridData(GridData.FILL,GridData.FILL,true,true,1,1));
		viewer.setContentProvider(new TreeContentProvider());
		viewer.setLabelProvider(new TreeLabelProvider());
		viewer.setSorter(new ViewerSorter(){
		public int category(Object element) {
		return element instanceof IWizardCategory ? 1:0;
	}});
		IWizardRegistry registry = ( StingPlugin.getDefault()).getWorkbench().getNewWizardRegistry();
		viewer.setInput(registry);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				NewEntityModelWizard parentWizard = (NewEntityModelWizard) NewObjectWizardPage.this.getWizard();
				
				IStructuredSelection iss = (IStructuredSelection) event.getSelection();
				if (iss.isEmpty()) {
					setPageComplete(false);
					setMessage("");
					parentWizard.setCurrentWizard(null);
			} else{
				Object object = iss.getFirstElement();
				if (object instanceof IWizardCategory) {
					setPageComplete(false);
					setMessage("");
					parentWizard.setCurrentWizard(null);
				} else {
					setPageComplete(true);
					IWizardDescriptor descriptor = (IWizardDescriptor)object;
					setMessage(descriptor.getDescription());
					
					try{
						IWizard wizard = descriptor.createWizard();
						parentWizard.setCurrentWizard(wizard);
					} catch (CoreException e){
						System.err.println(e);
					}
				}
			}
			}
		});
		
	return viewer;
	}
	
	private void updatePageComplete() {
		setPageComplete(false);
		
		IPath Loc = getSourceLocation();
		if (Loc == null || !Loc.toFile().exists()) {
			setMessage(null);
			setErrorMessage("Please select an existing location");
			return;
		}
		setPageComplete(true);
		
	}
	
	protected void browseForSourceFile() {
		IPath path = browse(getSourceLocation(),false);
		if (path == null)
			return;
		IPath rootLoc = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		if (rootLoc.isPrefixOf(path))
			path = path.setDevice(null).removeFirstSegments(rootLoc.segmentCount());
		sourceFileField.setText(path.toString());
	}
	
	private IPath browse(IPath path, boolean mustExist) {
		FileDialog dialog = new FileDialog(getShell(),mustExist?SWT.OPEN:SWT.SAVE);
		if (path!=null) {
			if(path.segmentCount()>1)
				dialog.setFilterPath(path.removeLastSegments(1).toOSString());
			if (path.segmentCount() >0)
				dialog.setFileName(path.lastSegment());
		}
		String result = dialog.open();
		if (result==null)
			return null;
		return new Path(result);
	}
	
	public IPath getSourceLocation() {
		String text = sourceFileField.getText().trim();
		if (text.length()==0)
			return null;
		IPath path = new Path(text);
		if (!path.isAbsolute())
			path = ResourcesPlugin.getWorkspace().getRoot().getLocation().append(path);
		return path;
	}*/

}
