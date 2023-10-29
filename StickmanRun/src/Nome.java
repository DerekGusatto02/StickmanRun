import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import Classifica.Classifica;
import Classifica.Punteggio;
import org.eclipse.wb.swt.SWTResourceManager;

public class Nome {

	private static int punti;
	private static int tempo;
	protected Shell shell;
	private Label lblSetNome;
	private Text txtGetNome;
	private Classifica leaderboard = new Classifica();
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Nome window = new Nome();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		punti=Integer.parseInt(args[0]);
		tempo=Integer.parseInt(args[1]);
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
		
		lblSetNome = new Label(shell, SWT.NONE);
		lblSetNome.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblSetNome.setBounds(920, 540, 80, 40);
		lblSetNome.setText("Inserisci il tuo nome: ");
		lblSetNome.setEnabled(false);
		lblSetNome.setVisible(false);

		txtGetNome = new Text(shell, SWT.NONE);
		txtGetNome.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		txtGetNome.setBounds(920, 540, 80, 40);
		txtGetNome.setText("...");
		txtGetNome.setEnabled(false);
		txtGetNome.setVisible(false);
		
		if (!(txtGetNome.getText().contentEquals("..."))) {
			Punteggio p = new Punteggio(txtGetNome.getText(),
					(punti + (int) 100 / (tempo)));
			leaderboard.load("leaderboard");
				leaderboard.add(p);
		} else {
			Punteggio p = new Punteggio("AAA", (punti + (int) 100 / (tempo)));
			leaderboard.load("leaderboard");
			leaderboard.add(p);
		}
		leaderboard.save("leaderboard");

	}
}
