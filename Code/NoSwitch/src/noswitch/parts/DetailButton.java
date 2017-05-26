package noswitch.parts;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class DetailButton extends Button {
	private Composite lines;
	private boolean isOpen = false;
	private int compHeight = 0;

	public DetailButton(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public Composite getLines() {
		return lines;
	}

	public void setLines(Composite lines) {
		this.lines = lines;
		open();
		this.addSelectionListener(new SelectionListener() {
			
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
		isOpen = true;
		this.setText("-");
		((GridData)lines.getLayoutData()).heightHint = compHeight;
		this.getParent().layout();
	}
	
	private void close() {
		isOpen = false;
		this.setText("+");
		((GridData)lines.getLayoutData()).heightHint = 0;
		this.getParent().layout();
	}
}
