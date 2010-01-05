package cn.tsinghua.mse.automatondesigner.wizards;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class NewAutomatonWizardPage extends WizardPage {
	private Text ANameText;
	private Combo ATypeCmb;

	/**
	 * @wbp.parser.constructor
	 */
	public NewAutomatonWizardPage(String pageName) {
		super(pageName);
		setTitle("New Automaton");
		setDescription("This wizard creats an automaton model which you could add state and transform to.");
		// TODO Auto-generated constructor stub
	}

	public NewAutomatonWizardPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		Label lblAutomatonName = new Label(container, SWT.NONE);
		lblAutomatonName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAutomatonName.setText("Automaton Name");
		
		ANameText = new Text(container, SWT.BORDER);
		ANameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		ANameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblAutomatonType = new Label(container, SWT.NONE);
		lblAutomatonType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAutomatonType.setText("Automaton Type");
		
		ATypeCmb = new Combo(container, SWT.READ_ONLY);
		ATypeCmb.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dialogChanged();
			}
		});
		ATypeCmb.setItems(new String[] {"NFA", "DFA", "PDA"});
		ATypeCmb.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		dialogChanged();
	}
	
	public String getAutomatonName(){
		return ANameText.getText();
	}
	
	private void dialogChanged() {
		if (getAutomatonName().length() == 0){
			updateStatus("The name of the automaton could not be empty.");
			return;
		}
		if (ATypeCmb.getSelectionIndex() == -1){
			updateStatus("The type of the automaton could not be empty.");
			return;
		}
		updateStatus(null);
	}
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

}
