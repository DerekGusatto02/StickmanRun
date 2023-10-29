import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import Classifica.Classifica;
import Classifica.Punteggio;
import org.eclipse.wb.swt.SWTResourceManager;

public class Leaderboard {

	protected Shell shell;
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Leaderboard window = new Leaderboard();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(272, 358);
		shell.setText("SWT Application");
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setFont(SWTResourceManager.getFont("Gisha", 15, SWT.BOLD));
		table.setBounds(0, 0, 305, 325);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn Index = new TableColumn(table, SWT.NONE);
		Index.setWidth(49);
		
		TableColumn Player = new TableColumn(table, SWT.NONE);
		Player.setWidth(100);
		Player.setText("Player");
		
		TableColumn Score = new TableColumn(table, SWT.NONE);
		Score.setWidth(100);
		Score.setText("Score");
		
		Classifica c = new Classifica();
		c.load("leaderboard");
		
		for(int i=0; i<c.size(); i++) {
			TableItem t = new TableItem (table, 0);
			
			t.setText(0, ""+ (i+1));
			t.setText(1, c.get(i).getNome());
			t.setText(2, ""+ c.get(i).getPunti());
		}
		
		System.out.println(table.getItemCount());

	}
}
