package noswitch.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class DetailButton {
	private Composite lines;
	private ScrolledComposite scrolledComposite;
	
	private boolean isOpen = false;
	private int compHeight = 0;
	Button actualButton;

	public DetailButton(Composite parent, int style) {
		actualButton = new Button(parent, style);
	}

	public Composite getLines() {
		return lines;
	}

	public void setComposites(Composite lines, ScrolledComposite scrolledComposite) {
		this.lines = lines;
		this.scrolledComposite = scrolledComposite;
		Point size = lines.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		compHeight = size.y;
		((GridData)lines.getLayoutData()).widthHint = size.x;
		close();
		actualButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				if(isOpen){
					close();
				}else {
					open();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void open() {
		Point location = actualButton.getLocation();
		isOpen = true;
		actualButton.setText("-");
		GridData layoutData = (GridData)lines.getLayoutData();
		if(layoutData==null){
			layoutData = new GridData();
			lines.setLayoutData(layoutData);
		}
		layoutData.heightHint = compHeight;
		actualButton.getParent().layout();
		
		Point size = lines.getParent().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		lines.getParent().layout();
		scrolledComposite.setContent(lines.getParent());
		scrolledComposite.setMinWidth(size.x);
		scrolledComposite.setMinHeight(size.y);
		scrolledComposite.setOrigin(location);
	}
	
	private void close() {
		Point location = actualButton.getLocation();
		isOpen = false;
		actualButton.setText("+");
		GridData layoutData = (GridData)lines.getLayoutData();
		if(layoutData==null){
			layoutData = new GridData();
			lines.setLayoutData(layoutData);
		}
		layoutData.heightHint = 0;
		actualButton.getParent().layout();
		
		Point size = lines.getParent().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		lines.getParent().layout();
		scrolledComposite.setContent(lines.getParent());
		scrolledComposite.setMinWidth(size.x);
		scrolledComposite.setMinHeight(size.y);
		scrolledComposite.setOrigin(location);
	}
}
