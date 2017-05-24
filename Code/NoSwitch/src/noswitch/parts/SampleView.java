package noswitch.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SampleView {

	@PostConstruct
	public void createPartControl(Composite parent) {
		GridLayout parentLayout = new GridLayout(8, false);
		parent.setLayout(parentLayout);
		prepareWidgets(parent);
	}

	@Focus
	public void setFocus() {
	}

	private void prepareWidgets(Composite parent){
		GridData labelData = new GridData();
		labelData.horizontalSpan = 2;
		labelData.widthHint = 100;
		labelData.horizontalAlignment = GridData.CENTER;
		
		Label termLabel = new Label(parent, SWT.NONE);
		termLabel.setAlignment(SWT.CENTER);
		termLabel.setText("搜索条件:");
		termLabel.setLayoutData(labelData);
		
		Text termText = new Text(parent, SWT.BORDER);
		GridData termTextData = new GridData();
		termTextData.horizontalSpan = 6;
		termTextData.widthHint = 400;
		termText.setLayoutData(termTextData);
		
		GridData tipData = new GridData();
		tipData.horizontalSpan = 8;
		tipData.verticalAlignment = GridData.VERTICAL_ALIGN_BEGINNING;
		tipData.widthHint = 500;
		tipData.heightHint = 30;
		Label tip = new Label(parent, SWT.NONE);
		tip.setLayoutData(tipData);
		tip.setAlignment(SWT.CENTER);
		tip.setText("输入想要搜索的关键词，支持布尔表达式AND OR NOT和(),如(JSON AND i++)");
		
		Label pageLabel = new Label(parent, SWT.NONE);
		pageLabel.setAlignment(SWT.CENTER);
		pageLabel.setText("页数(从0开始):");
		pageLabel.setLayoutData(labelData);
		
		GridData normalTextData = new GridData();
		normalTextData.horizontalSpan = 2;
		normalTextData.widthHint = 150;
		
		Text pageText = new Text(parent, SWT.BORDER);
		pageText.setLayoutData(normalTextData);
		
		Label langLabel = new Label(parent, SWT.NONE);
		langLabel.setAlignment(SWT.CENTER);
		langLabel.setText("编程语言:");
		langLabel.setLayoutData(labelData);
		
		Text langText = new Text(parent, SWT.BORDER);
		langText.setLayoutData(normalTextData);
		
		GridData buttonData = new GridData();
		buttonData.horizontalSpan = 2;
		buttonData.widthHint = 200;
		buttonData.horizontalAlignment = GridData.CENTER;
		
		Button query = new Button(parent, SWT.NONE);
		query.setText("开始搜索");
		query.setLayoutData(buttonData);
		
//		GridData checkLabelData = new GridData();
//		checkLabelData.horizontalSpan = 2;
//		checkLabelData.widthHint = 100;
//		checkLabelData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
//		checkLabelData.grabExcessHorizontalSpace = true;
//		
//		GridData checkData = new GridData();
//		checkData.horizontalSpan = 1;
//		checkData.grabExcessHorizontalSpace = true;
//		checkData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_END;
		
		Button midCheck = new Button(parent, SWT.CHECK|SWT.BORDER);
//		midCheck.setLayoutData(checkData);

		Label midLabel = new Label(parent, SWT.BORDER);
		midLabel.setText("使用缓存服务器");
//		midLabel.setAlignment(SWT.LEFT);
//		midLabel.setLayoutData(checkLabelData);
		
		Button forceCheck = new Button(parent, SWT.CHECK|SWT.BORDER);
//		forceCheck.setLayoutData(checkData);
		
		Label forceLabel = new Label(parent, SWT.BORDER);
//		forceLabel.setAlignment(SWT.LEFT);
		forceLabel.setText("强制缓存服务器重新拉取结果");
//		forceLabel.setLayoutData(checkLabelData);
		
		new Label(parent, SWT.BORDER);new Label(parent, SWT.BORDER);
		
		Button updateOne = new Button(parent, SWT.NONE);
		updateOne.setText("升级缓存服务器指定结果");
		updateOne.setLayoutData(buttonData);
		
		Button updateAll = new Button(parent, SWT.NONE);
		updateAll.setText("升级缓存服务器所有结果");
		updateAll.setLayoutData(buttonData);
	}
		
	/**
	 * This method manages the selection of your current object. In this example
	 * we listen to a single Object (even the ISelection already captured in E3
	 * mode). <br/>
	 * You should change the parameter type of your received Object to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current object received
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object o) {

		// Remove the 2 following lines in pure E4 mode, keep them in mixed mode
		if (o instanceof ISelection) // Already captured
			return;

		// Test if label exists (inject methods are called before PostConstruct)
	}

	/**
	 * This method manages the multiple selection of your current objects. <br/>
	 * You should change the parameter type of your array of Objects to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current array of objects received in case of multiple selection
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object[] selectedObjects) {

		// Test if label exists (inject methods are called before PostConstruct)

	}
}
