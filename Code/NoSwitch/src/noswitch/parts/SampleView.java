package noswitch.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.HttpHostConnectException;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.sf.json.JSONObject;
import util.NoSwitchHttpQuery;
import util.NoSwitchHttpQueryBuilder;
import util.NoSwitchHttpQueryBuilder.queryFunction;
import util.NoSwitchHttpQueryBuilder.serverType;

public class SampleView {
	Text termText;
	Text pageText;
	Text langText;
	Button query;
	Button midCheck;
	Button forceCheck;
	Button updateOne;
	Button updateAll;
	Button lastPage;
	Button nextPage;
	
	Composite root;
	
	@PostConstruct
	public void createPartControl(Composite parent) {
		root = parent;
		GridLayout parentLayout = new GridLayout(8, false);
		parent.setLayout(parentLayout);
		prepareWidgets(parent);
		addListeners();
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
		
		termText = new Text(parent, SWT.BORDER);
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
		
		pageText = new Text(parent, SWT.BORDER);
		pageText.setLayoutData(normalTextData);
		pageText.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent arg0) {
				// TODO Auto-generated method stub
				boolean b = ("0123456789".indexOf(arg0.text)>=0);     
				arg0.doit = b; 
			}
		});
		
		Label langLabel = new Label(parent, SWT.NONE);
		langLabel.setAlignment(SWT.CENTER);
		langLabel.setText("编程语言:");
		langLabel.setLayoutData(labelData);
		
		langText = new Text(parent, SWT.BORDER);
		langText.setLayoutData(normalTextData);
		
		GridData buttonData = new GridData();
		buttonData.horizontalSpan = 2;
		buttonData.widthHint = 200;
		buttonData.horizontalAlignment = GridData.CENTER;
		
		query = new Button(parent, SWT.NONE);
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
		
		midCheck = new Button(parent, SWT.CHECK|SWT.BORDER);
//		midCheck.setLayoutData(checkData);

		Label midLabel = new Label(parent, SWT.BORDER);
		midLabel.setText("使用缓存服务器");
//		midLabel.setAlignment(SWT.LEFT);
//		midLabel.setLayoutData(checkLabelData);
		
		forceCheck = new Button(parent, SWT.CHECK|SWT.BORDER);
//		forceCheck.setLayoutData(checkData);
		
		Label forceLabel = new Label(parent, SWT.BORDER);
//		forceLabel.setAlignment(SWT.LEFT);
		forceLabel.setText("强制缓存服务器重新拉取结果");
//		forceLabel.setLayoutData(checkLabelData);
		
		new Label(parent, SWT.BORDER);new Label(parent, SWT.BORDER);
		
		updateOne = new Button(parent, SWT.NONE);
		updateOne.setText("更新缓存服务器指定结果");
		updateOne.setLayoutData(buttonData);
		
		updateAll = new Button(parent, SWT.NONE);
		updateAll.setText("更新缓存服务器所有结果");
		updateAll.setLayoutData(buttonData);
		
		GridData pageButtonData = new GridData();
		pageButtonData.horizontalSpan = 2;
		pageButtonData.widthHint = 100;
		pageButtonData.horizontalAlignment = GridData.CENTER;
		
		lastPage = new Button(parent, SWT.NONE);
		lastPage.setText("上一页");
		lastPage.setLayoutData(pageButtonData);
		
		nextPage = new Button(parent, SWT.NONE);
		nextPage.setText("下一页");
		nextPage.setLayoutData(pageButtonData);
		
		midCheck.setSelection(true);
		
//		Button updown = new Button(parent, SWT.NONE);
//		updown.addSelectionListener(new SelectionListener() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent arg0) {
//				// TODO Auto-generated method stub
//				tipData.heightHint = 30-tipData.heightHint;
//				root.layout();
//			}
//			
//			@Override
//			public void widgetDefaultSelected(SelectionEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}
	
	public void queryFunction() {
		NoSwitchHttpQueryBuilder builder = new NoSwitchHttpQueryBuilder();
		builder.setServer(midCheck.getSelection()?serverType.MiddleServer:serverType.RootServer);
		builder.setLanguage(langText.getText());
		if(!pageText.getText().matches("[\\s]*")){
			builder.setPage(Integer.parseInt(pageText.getText()));
		}
		if (termText.getText().matches("[\\s]*")) {
			sendDialog("错误", "请输入搜索条件！");
			return;
		}else {
			builder.setSearchTerm(termText.getText());
		}
		builder.setFunction(forceCheck.getSelection()?queryFunction.ForceFetch:queryFunction.Search);
		try {
			NoSwitchHttpQuery httpQuery = builder.build();
			String result = httpQuery.sendRequest();
			JSONObject resJson =JSONObject.fromObject(result);
			System.out.println(result);
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			System.out.println("2222");
			e.printStackTrace();
		} catch (HttpHostConnectException e) {
			// TODO Auto-generated catch block
			System.out.println(4444);
			sendDialog("错误", e.toString()+"\n连接服务器失败，请尝试使用另一种服务器。");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("3333");
			System.out.println(e);
		}
	}
	
	private void addListeners() {
		nextPage.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				int page =0;
				if(!pageText.getText().matches("[\\s]*")){
					page = Integer.parseInt(pageText.getText());
				}
				pageText.setText(""+(page+1));
				queryFunction();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		lastPage.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				int page =0;
				if(!pageText.getText().matches("[\\s]*")){
					page = Integer.parseInt(pageText.getText());
				}
				pageText.setText(""+(page-1));
				queryFunction();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		query.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				queryFunction();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		updateAll.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				NoSwitchHttpQueryBuilder builder = new NoSwitchHttpQueryBuilder();
				builder.setServer(serverType.MiddleServer);
				builder.setFunction(queryFunction.UpdateAll);
				try {
					NoSwitchHttpQuery httpQuery = builder.build();
					String result = httpQuery.sendRequest();
					JSONObject resJson =JSONObject.fromObject(result);
					if(resJson.get("result").equals("started")){
						sendDialog("成功", "缓存服务器已经开始更新所有条目的缓存结果！");
					}else {
						sendDialog("失败", "未能成功开始更新所有条目的缓存结果！");
					}
				} catch (UnsupportedOperationException e) {
					// TODO Auto-generated catch block
					System.out.println("2222");
					e.printStackTrace();
				} catch (HttpHostConnectException e) {
					// TODO Auto-generated catch block
					System.out.println(4444);
					sendDialog("错误", e.toString()+"\n连接服务器失败，请尝试使用另一种服务器。");
				} catch (ClientProtocolException e) {
					// TODO: handle exception
					sendDialog("错误", e.toString()+"\n服务器地址有误，请检查。");
				}catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("3333");
					System.out.println(e);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		updateOne.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				NoSwitchHttpQueryBuilder builder = new NoSwitchHttpQueryBuilder();
				builder.setServer(serverType.MiddleServer);
				builder.setLanguage(langText.getText());
				if(!pageText.getText().matches("[\\s]*"))
					builder.setPage(Integer.parseInt(pageText.getText()));
				if (termText.getText().matches("[\\s]*")) {
					sendDialog("错误", "请输入搜索条件！");
					return;
				}else {
					builder.setSearchTerm(termText.getText());
				}
				builder.setFunction(queryFunction.UpdateOne);
				try {
					NoSwitchHttpQuery httpQuery = builder.build();
					String result = httpQuery.sendRequest();
					JSONObject resJson =JSONObject.fromObject(result);
					if(resJson.get("result").equals("successful")){
						sendDialog("成功", "成功更新指定条目缓存结果！");
					}else {
						sendDialog("失败", "未能成功更新指定条目缓存结果！");
					}
				} catch (UnsupportedOperationException e) {
					// TODO Auto-generated catch block
					System.out.println("2222");
					e.printStackTrace();
				} catch (HttpHostConnectException e) {
					// TODO Auto-generated catch block
					System.out.println(4444);
					sendDialog("错误", e.toString()+"\n连接服务器失败，请尝试使用另一种服务器。");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("3333");
					System.out.println(e);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void sendDialog(String title, String message) {
		MessageDialog.openInformation(root.getShell(), title, message);
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
