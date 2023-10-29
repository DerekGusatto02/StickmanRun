import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;
import Video.*;
import Classifica.*;
import Thread.*;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class Gioco {

	// Score a sinistra
	public void scroll(Label lbl) {
		int x = lbl.getBounds().x;
		int y = lbl.getBounds().y;
		x -= 10;
		if (x + lbl.getBounds().width < 0) {
			lbl.setVisible(false);
			lbl.setEnabled(false);
		}
		lbl.setBounds(x, y, lbl.getBounds().width, lbl.getBounds().height);
	}

	// Corsa Stickaman
	public void stickManRun(Label lbl) {
		String path = "./images/day/stick/stick_";
		String png = ".png";
		lbl.setImage(new Image(display, path + contatoreCorsaStickMan + png));
		contatoreCorsaStickMan++;
		if (contatoreCorsaStickMan == 7) {
			contatoreCorsaStickMan = 1;
		}

	}

	// Stickman salta su
	public void stickManJump(Label lbl) {
		String path = "./images/day/stick/stick_";
		String png = ".png";
		lbl.setImage(new Image(display, path + 6 + png));
		int x = lbl.getBounds().x;
		int y = lbl.getBounds().y;
		y -= 10;
		lbl.setBounds(x, y, lbl.getBounds().width, lbl.getBounds().height);

	}

	// Stickman scende giu
	public void stickManJumpDown(Label lbl) {
		String path = "./images/day/stick/stick_";
		String png = ".png";
		lbl.setImage(new Image(display, path + contatoreCorsaStickMan + png));
		contatoreCorsaStickMan++;
		if (contatoreCorsaStickMan == 7) {
			contatoreCorsaStickMan = 1;
		}
		int x = lbl.getBounds().x;
		int y = lbl.getBounds().y;
		y += 15;
		lbl.setBounds(x, y, lbl.getBounds().width, lbl.getBounds().height);

	}

	// Moneta ruota su se stessa
	public void rotate(Label lbl, Entita e) {
		lbl.setImage(new Image(display, srcCoins[e.getIndexImage()]));
		int i = e.getIndexImage() + 1;
		if (i == 6)
			i = 0;
		e.setIndexImage(i);

	}

	// Sovrapposizione moneta-Stickman
	public boolean isOver(Label lbl) {
		if (lbl.getEnabled() == false)
			return false;
		if (((lbl.getBounds().x - lblStickMan.getBounds().x) < (lblStickMan.getBounds().width))
				&& ((lbl.getBounds().y + lbl.getBounds().height) > lblStickMan.getBounds().y)) {
			lbl.setVisible(false);
			lbl.setEnabled(false);
			return true;
		}
		return false;
	}

	// Blocco vicino allo stickman
	public boolean isNext(Label lbl) {
		if (lbl.getBounds().x < lblStickMan.getBounds().x + lblStickMan.getBounds().width + 20
				&& (lbl.getBounds().y < lblStickMan.getBounds().y + lblStickMan.getBounds().height)) {
			return true;
		}
		return false;
	}

	protected Shell StickMan;
	private Display display;
	private Label lblStickMan; // Label stickman
	private int contatoreCorsaStickMan = 1; // contatore immagini stickamn
	public final String[] srcCoins = { "./images/day/coins/coin_1.png", "./images/day/coins/coin_2.png",
			"./images/day/coins/coin_3.png", "./images/day/coins/coin_4.png", "./images/day/coins/coin_5.png",
			"./images/day/coins/coin_6.png" }; // lista nomi immagini monete
	public final String[] srcBrick = { "./images/day/brick/brick.png" };
	private Entita[] entita = new Entita[18]; // vettore entita primo livello
	public Contatore contatore; // Conattore secondi (thread)
	private Label lblScore; // Label "score"
	private Label lblTime; // Label "time"
	private Label lblPunti; // Label visualizza score
	private Label[] lblEntita = new Label[18]; // vettore label entita primo livello
	private Label lblSecondi; // Label time
	public int puntiStickMan = 0; // Score
	private Label lblVideo; // Label visualizzazione video
	private Label lblFloor; // Label pavimento
	private Label lblVisulaizzaPunteggio; // Label visualizza punti quando prende moneta
	private boolean salto = false;
	private boolean bloccato = false;
	private boolean discesa = false;
	private boolean up = false;
	private int focusedIndex = 0;
	private boolean game = false;
	private boolean vinto = false;
	private Classifica leaderboard = new Classifica();

	// Bottoni schermate
	private Button btnResume;
	private Button btnRestart;
	private Button btnExit;
	private Button btnLeaderboard;
	private Button btnQuit;
	private Button btnBack;
	private Button btnHelp;
	private Button btnPlay;

	private Label lblPause;
	private Label lblHelp;
	private Label lblLeaderboard;
	private Label lblMenu;
	private Label lblSfondo;
	private Label lblHelpInstrucions;
	

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Gioco window = new Gioco();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		StickMan.open();
		StickMan.layout();
		while (!StickMan.isDisposed()) {
			if (!display.readAndDispatch()) {
				try {
					Thread.sleep(75);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("GAME:" + game);
				System.out.println("VINTO:" + vinto);
				if (vinto == true) {
					String s[]= {""+this.puntiStickMan, ""+contatore.getSecondi()}; 
					Nome.main(s);

					leaderboard.ordina();
					leaderboard.save("leaderboard");
					lblMenu.setEnabled(true);
					lblMenu.setVisible(true);
					btnQuit.setEnabled(true);
					btnHelp.setEnabled(true);
					btnLeaderboard.setEnabled(true);
					btnPlay.setEnabled(true);
					btnQuit.setVisible(true);
					btnHelp.setVisible(true);
					btnLeaderboard.setVisible(true);
					btnPlay.setVisible(true);

				}
				if (game == true && vinto == false) {
					// Condizione vittoria, label Coins: 0 2 3 4 5 8 9 10 12 13 16 17
					if (lblEntita[0].getBounds().x < 0 && lblEntita[2].getBounds().x < 0
							&& lblEntita[3].getBounds().x < 0 && lblEntita[4].getBounds().x < 0
							&& lblEntita[5].getBounds().x < 0 && lblEntita[8].getBounds().x < 0
							&& lblEntita[9].getBounds().x < 0 && lblEntita[10].getBounds().x < 0
							&& lblEntita[12].getBounds().x < 0 && lblEntita[13].getBounds().x < 0
							&& lblEntita[16].getBounds().x < 0 && lblEntita[17].getBounds().x < 0) {
						game = false;
						vinto = true;
					} // else {

					stickManRun(lblStickMan);
					if (salto == true) { // Se devi saltare, salta ;)
						stickManJump(lblStickMan);
						if ((lblStickMan.getBounds().y + lblStickMan.getBounds().height) < lblEntita[focusedIndex]
								.getBounds().y) { // Se sopra al mattoncino non sale piu
							bloccato = false;
							salto = false;
							up = true;
						}
					}

					if (up == true) { // A fine mattone scende
						if ((lblStickMan.getBounds().x + 20) > (lblEntita[focusedIndex].getBounds().x
								+ lblEntita[focusedIndex].getBounds().width)) {
							discesa = true;
							up = false;
							// Eliminazione label mattone
							lblEntita[focusedIndex].setVisible(false);
							lblEntita[focusedIndex].setEnabled(false);
							lblEntita[focusedIndex].dispose();
							for (int j = focusedIndex; j < entita.length - 1; j++) {
								entita[j] = entita[j + 1];
								lblEntita[j] = lblEntita[j + 1];
							}
						}
					}

					// Discesa
					if (discesa == true) {
						stickManJumpDown(lblStickMan);
						if (lblStickMan.getBounds().y > 710) {
							System.out.println("IN");
							discesa = false;
							bloccato = false;
						}
					}

					for (int i = 0; i < entita.length; i++) {

						System.out.println("FI: " + focusedIndex);
						System.out.println("B: " + bloccato);
						if (!bloccato) { // Se NON in attesa di un salto (bloccato)
							scroll(lblEntita[i]);
						}
						if (entita[i].geteffetto() == 1) { // Se moneta
							rotate(lblEntita[i], entita[i]);
							if (isOver(lblEntita[i])) { // Se prende moneta
								puntiStickMan += 5;
							}
						} else if (entita[i].geteffetto() == 0) { // Altrimenti se mattone
							if (isNext(lblEntita[i])) { // Se davanti mattone
								// Aspetta salto
								bloccato = true;
								focusedIndex = i;
							}
						}
					}

					lblSecondi.setText("" + contatore.getSecondi());
					lblPunti.setText("" + puntiStickMan);

					// }

				}
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		StickMan = new Shell();
		StickMan.setImage(SWTResourceManager.getImage("./images/menu/icon.png"));
		StickMan.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				contatore.stop();
			}
		});
		StickMan.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		StickMan.setSize(1920, 1080);
		StickMan.setBackgroundImage(SWTResourceManager.getImage("./images/day/bg.png"));

		StickMan.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 119 || e.keyCode == 16777217 || e.keyCode == 32) {
					if (isNext(lblEntita[focusedIndex])) {
						salto = true;
					} else {
						lblStickMan.setBounds(lblStickMan.getBounds().x, lblStickMan.getBounds().y - 50,
								lblStickMan.getBounds().width, lblStickMan.getBounds().height);

						lblStickMan.setBounds(lblStickMan.getBounds().x, lblStickMan.getBounds().y + 50,
								lblStickMan.getBounds().width, lblStickMan.getBounds().height);
					}
				} else if (e.keyCode == 27) { // tasto Esc
					game = false;
					lblMenu.setEnabled(true);
					lblMenu.setVisible(true);
					btnRestart.setEnabled(true);
					btnResume.setEnabled(true);
					btnQuit.setEnabled(true);
					btnHelp.setEnabled(true);
					btnLeaderboard.setEnabled(true);
					btnExit.setEnabled(true);
					btnRestart.setVisible(true);
					btnResume.setVisible(true);
					btnQuit.setVisible(true);
					btnHelp.setVisible(true);
					btnLeaderboard.setVisible(true);
					btnExit.setVisible(true);
					lblPause.setEnabled(true);
					lblPause.setVisible(true);

				}
			}

		});

		lblStickMan = new Label(StickMan, SWT.NONE);
		lblStickMan.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblStickMan.setBounds(0, 717, 160, 223);
		lblVideo = new Label(StickMan, SWT.NONE);
		lblVideo.setBounds(10, 10, 1920, 1080);
		lblVideo.setVisible(false);
		lblVideo.setEnabled(false);

		// Gestione schermate
		lblPause = new Label(StickMan, SWT.NONE);
		lblPause.setAlignment(SWT.CENTER);
		lblPause.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblPause.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblPause.setImage(SWTResourceManager.getImage("./images/menu/menu_pause.png"));
		lblPause.setBounds(795, 230, 330, 80);
		lblPause.setEnabled(false);
		lblPause.setVisible(false);

		lblHelp = new Label(StickMan, SWT.NONE);
		lblHelp.setAlignment(SWT.CENTER);
		lblHelp.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblHelp.setImage(SWTResourceManager.getImage("./images/menu/menu_help.png"));
		lblHelp.setBounds(825, 230, 270, 80);
		lblHelp.setEnabled(false);
		lblHelp.setVisible(false);

		lblLeaderboard = new Label(StickMan, SWT.NONE);
		lblLeaderboard.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblLeaderboard.setAlignment(SWT.CENTER);
		lblLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/menu_leaderboard.png"));
		lblLeaderboard.setBounds(597, 230, 726, 80);
		lblLeaderboard.setEnabled(false);
		lblLeaderboard.setVisible(false);

		btnResume = new Button(StickMan, SWT.NONE);
		btnResume.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblMenu.setEnabled(false);
				lblMenu.setVisible(false);
				btnRestart.setEnabled(false);
				btnResume.setEnabled(false);
				btnQuit.setEnabled(false);
				btnHelp.setEnabled(false);
				btnLeaderboard.setEnabled(false);
				btnExit.setEnabled(false);
				btnRestart.setVisible(false);
				btnResume.setVisible(false);
				btnQuit.setVisible(false);
				btnHelp.setVisible(false);
				btnLeaderboard.setVisible(false);
				btnExit.setVisible(false);
				lblPause.setEnabled(false);
				lblPause.setVisible(false);
				lblHelp.setEnabled(false);
				lblHelp.setVisible(false);
				btnBack.setEnabled(false);
				btnBack.setVisible(false);
				game = true;
			}
		});
		btnResume.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/hover_resume.png"));
			}

			@Override
			public void focusLost(FocusEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
			}
		});
		btnResume.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/hover_resume.png"));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
			}
		});
		btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
		btnResume.setBounds(860, 360, 200, 50);
		btnResume.setEnabled(false);
		btnResume.setVisible(false);

		btnRestart = new Button(StickMan, SWT.NONE);
		btnRestart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblMenu.setEnabled(false);
				lblMenu.setVisible(false);
				btnRestart.setEnabled(false);
				btnResume.setEnabled(false);
				btnQuit.setEnabled(false);
				btnHelp.setEnabled(false);
				btnLeaderboard.setEnabled(false);
				btnExit.setEnabled(false);
				btnRestart.setVisible(false);
				btnResume.setVisible(false);
				btnQuit.setVisible(false);
				btnHelp.setVisible(false);
				btnLeaderboard.setVisible(false);
				btnExit.setVisible(false);
				lblPause.setEnabled(false);
				lblPause.setVisible(false);
				lblHelp.setEnabled(false);
				lblHelp.setVisible(false);
				btnBack.setEnabled(false);
				btnBack.setVisible(false);
				contatore.stop();
				open();
			}
		});
		btnRestart.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/hover_restart.png"));
			}

			@Override
			public void focusLost(FocusEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
			}
		});
		btnRestart.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/hover_restart.png"));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
			}
		});
		btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
		btnRestart.setBounds(852, 420, 216, 50);
		btnRestart.setEnabled(false);
		btnRestart.setVisible(false);

		btnExit = new Button(StickMan, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent e) {
				// SCHERMATA AVVIO
				lblMenu.setEnabled(false);
				lblMenu.setVisible(false);
				btnRestart.setEnabled(false);
				btnResume.setEnabled(false);
				btnQuit.setEnabled(false);
				btnHelp.setEnabled(false);
				btnLeaderboard.setEnabled(false);
				btnExit.setEnabled(false);
				btnRestart.setVisible(false);
				btnResume.setVisible(false);
				btnQuit.setVisible(false);
				btnHelp.setVisible(false);
				btnLeaderboard.setVisible(false);
				btnExit.setVisible(false);
				lblPause.setEnabled(false);
				lblPause.setVisible(false);
				lblHelp.setEnabled(false);
				lblHelp.setVisible(false);
				btnBack.setEnabled(false);
				btnBack.setVisible(false);
				contatore.stop();
				lblMenu.setEnabled(true);
				lblMenu.setVisible(true);
				btnQuit.setEnabled(true);
				btnHelp.setEnabled(true);
				btnLeaderboard.setEnabled(true);
				btnPlay.setEnabled(true);
				btnQuit.setVisible(true);
				btnHelp.setVisible(true);
				btnLeaderboard.setVisible(true);
				btnPlay.setVisible(true);

			}
		});
		btnExit.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/hover_exit.png"));
			}

			@Override
			public void focusLost(FocusEvent e) {
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
			}
		});
		btnExit.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/hover_exit.png"));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
			}
		});
		btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
		btnExit.setBounds(901, 480, 118, 50);
		btnExit.setEnabled(false);
		btnExit.setVisible(false);

		btnLeaderboard = new Button(StickMan, SWT.NONE);
		btnLeaderboard.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Leaderboard.main(null);
			}
		});
		btnLeaderboard.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/hover_leaderboard.png"));
			}

			@Override
			public void focusLost(FocusEvent e) {
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
			}
		});
		btnLeaderboard.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/hover_leaderboard.png"));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
			}
		});
		btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
		btnLeaderboard.setBounds(890, 710, 364, 50);
		btnLeaderboard.setEnabled(false);
		btnLeaderboard.setVisible(false);

		btnQuit = new Button(StickMan, SWT.NONE);
		btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
		btnQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StickMan.dispose();
			}
		});
		btnQuit.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/hover_quit.png"));
			}

			@Override
			public void focusLost(FocusEvent e) {
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
			}
		});
		btnQuit.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/hover_quit.png"));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
			}
		});
		btnQuit.setBounds(1264, 710, 126, 50);
		btnQuit.setEnabled(false);
		btnQuit.setVisible(false);

		btnBack = new Button(StickMan, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblMenu.setEnabled(true);
				lblMenu.setVisible(true);
				btnRestart.setEnabled(true);
				btnResume.setEnabled(true);
				btnQuit.setEnabled(true);
				btnHelp.setEnabled(true);
				btnLeaderboard.setEnabled(true);
				btnExit.setEnabled(true);
				btnRestart.setVisible(true);
				btnResume.setVisible(true);
				btnQuit.setVisible(true);
				btnHelp.setVisible(true);
				btnLeaderboard.setVisible(true);
				btnExit.setVisible(true);
				lblPause.setEnabled(true);
				lblPause.setVisible(true);
				btnBack.setEnabled(false);
				btnBack.setVisible(false);
				lblHelpInstrucions.setEnabled(false);
				lblHelpInstrucions.setVisible(false);

			}
		});
		btnBack.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/hover_back.png"));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
			}
		});
		btnBack.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("vblack_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("vblack_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/hover_back.png"));
			}

			@Override
			public void focusLost(FocusEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/lack_quit.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
			}
		});
		btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
		btnBack.setBounds(530, 370, 140, 50);
		btnBack.setEnabled(false);
		btnBack.setVisible(false);

		btnHelp = new Button(StickMan, SWT.NONE);
		btnHelp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnRestart.setEnabled(false);
				btnResume.setEnabled(false);
				btnQuit.setEnabled(false);
				btnHelp.setEnabled(false);
				btnLeaderboard.setEnabled(false);
				btnPlay.setEnabled(false);
				btnExit.setEnabled(false);
				btnRestart.setVisible(false);
				btnResume.setVisible(false);
				btnQuit.setVisible(false);
				btnHelp.setVisible(false);
				btnLeaderboard.setVisible(false);
				btnExit.setVisible(false);
				btnPlay.setVisible(false);
				lblPause.setEnabled(false);
				lblPause.setVisible(false);
				lblHelpInstrucions.setEnabled(true);
				lblHelpInstrucions.setVisible(true);
				lblHelp.setEnabled(true);
				lblHelp.setVisible(true);
				btnBack.setEnabled(true);
				btnBack.setVisible(true);

			}
		});
		btnHelp.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/hover_help.png"));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
			}
		});
		btnHelp.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/hover_help.png"));
			}

			@Override
			public void focusLost(FocusEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("vblack_help.png"));
			}
		});
		btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
		btnHelp.setBounds(1260, 370, 130, 50);
		btnHelp.setEnabled(false);
		btnHelp.setVisible(false);

		btnPlay = new Button(StickMan, SWT.NONE);
		btnPlay.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblMenu.setEnabled(false);
				lblMenu.setVisible(false);
				btnQuit.setEnabled(false);
				btnHelp.setEnabled(false);
				btnLeaderboard.setEnabled(false);
				btnPlay.setEnabled(false);
				btnQuit.setVisible(false);
				btnHelp.setVisible(false);
				btnLeaderboard.setVisible(false);
				btnPlay.setVisible(false);
				contatore = new Contatore((int) System.currentTimeMillis());
				contatore.start();
				game = true;
			}
		});
		btnPlay.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/vlack_restart.png"));
				btnPlay.setImage(SWTResourceManager.getImage("./images/menu/hover_play.png"));
			}

			@Override
			public void focusLost(FocusEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnPlay.setImage(SWTResourceManager.getImage("./images/menu/black_play.png"));
			}
		});
		btnPlay.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				btnResume.setImage(SWTResourceManager.getImage("./images/menu/black_resume.png"));
				btnExit.setImage(SWTResourceManager.getImage("./images/menu/black_exit.png"));
				btnLeaderboard.setImage(SWTResourceManager.getImage("./images/menu/black_leaderboard.png"));
				btnQuit.setImage(SWTResourceManager.getImage("./images/menu/black_quit.png"));
				btnBack.setImage(SWTResourceManager.getImage("./images/menu/black_back.png"));
				btnHelp.setImage(SWTResourceManager.getImage("./images/menu/black_help.png"));
				btnRestart.setImage(SWTResourceManager.getImage("./images/menu/black_restart.png"));
				btnPlay.setImage(SWTResourceManager.getImage("./images/menu/hover_play.png"));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				btnPlay.setImage(SWTResourceManager.getImage("./images/menu/black_play.png"));
			}
		});

		btnPlay.setBounds(852, 420, 220, 60);
		btnPlay.setImage(SWTResourceManager.getImage("./images/menu/black_play.png"));
		btnPlay.setEnabled(false);
		btnPlay.setVisible(false);

		lblHelpInstrucions = new Label(StickMan, SWT.NONE);
		lblHelpInstrucions.setImage(SWTResourceManager.getImage("./images/menu/instr_help.png"));
		lblHelpInstrucions.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblHelpInstrucions.setBounds(820, 405, 480, 270);
		lblHelpInstrucions.setEnabled(false);
		lblHelpInstrucions.setVisible(false);

		lblMenu = new Label(StickMan, SWT.NONE);
		lblMenu.setImage(SWTResourceManager.getImage("./images/menu/menu_bg.png"));
		lblMenu.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblMenu.setBounds(480, 270, 960, 540);
		lblMenu.setEnabled(false);
		lblMenu.setVisible(false);



		lblSecondi = new Label(StickMan, SWT.NONE);
		lblSecondi.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblSecondi.setBounds(64, 10, 60, 20);
		lblSecondi.setText("");

		lblPunti = new Label(StickMan, SWT.NONE);
		lblPunti.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblPunti.setBounds(195, 10, 60, 20);

		lblScore = new Label(StickMan, SWT.NONE);
		lblScore.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblScore.setBounds(136, 10, 60, 20);
		lblScore.setText("SCORE");

		lblTime = new Label(StickMan, SWT.NONE);
		lblTime.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		lblTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblTime.setBounds(10, 10, 60, 20);
		lblTime.setText("TIME:");

		lblFloor = new Label(StickMan, SWT.NONE);
		lblFloor.setImage(SWTResourceManager.getImage("./images/road.png"));
		lblFloor.setBounds(0, 940, 1920, 140);

		contatore = new Contatore((int) System.currentTimeMillis());

		Label label_1 = new Label(StickMan, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[0] = label_1;
		lblEntita[0].setBounds(900, 700, 107, 108);

		Label label_2 = new Label(StickMan, SWT.NONE);
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[1] = label_2;
		lblEntita[1].setBounds(500, 833, 120, 110);
		lblEntita[1].setImage(new Image(display, srcBrick[0]));

		Label label_3 = new Label(StickMan, SWT.NONE);
		label_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[2] = label_3;
		lblEntita[2].setBounds(1000, 700, 107, 108);

		Label label_4 = new Label(StickMan, SWT.NONE);
		label_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[3] = label_4;
		lblEntita[3].setBounds(1200, 700, 107, 108);

		Label label_5 = new Label(StickMan, SWT.NONE);
		label_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[4] = label_5;
		lblEntita[4].setBounds(1300, 700, 107, 108);

		Label label_6 = new Label(StickMan, SWT.NONE);
		label_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[5] = label_6;
		lblEntita[5].setBounds(1400, 700, 120, 108);

		Label label_7 = new Label(StickMan, SWT.NONE);
		label_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[6] = label_7;
		lblEntita[6].setBounds(1100, 833, 120, 110);
		lblEntita[6].setImage(new Image(display, srcBrick[0]));

		Label label_8 = new Label(StickMan, SWT.NONE);
		label_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[7] = label_8;
		lblEntita[7].setBounds(1500, 833, 110, 110);
		lblEntita[7].setImage(new Image(display, srcBrick[0]));

		Label label_9 = new Label(StickMan, SWT.NONE);
		label_9.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[8] = label_9;
		lblEntita[8].setBounds(1600, 800, 107, 108);

		Label label = new Label(StickMan, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[9] = label;
		lblEntita[9].setBounds(1750, 700, 107, 108);

		Label label_11 = new Label(StickMan, SWT.NONE);
		label_11.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[10] = label_11;
		lblEntita[10].setBounds(1850, 700, 107, 108);

		Label label_12 = new Label(StickMan, SWT.NONE);
		label_12.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[11] = label_12;
		lblEntita[11].setBounds(1900, 833, 120, 110);
		lblEntita[11].setImage(new Image(display, srcBrick[0]));

		Label label_14 = new Label(StickMan, SWT.NONE);
		label_14.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[12] = label_14;
		lblEntita[12].setBounds(2000, 700, 107, 108);

		Label label_15 = new Label(StickMan, SWT.NONE);
		label_15.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[13] = label_15;
		lblEntita[13].setBounds(2500, 700, 107, 108);

		Label label_17 = new Label(StickMan, SWT.NONE);
		label_17.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[14] = label_17;
		lblEntita[14].setBounds(2500, 833, 120, 110);
		lblEntita[14].setImage(new Image(display, srcBrick[0]));

		Label label_18 = new Label(StickMan, SWT.NONE);
		label_18.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[15] = label_18;
		lblEntita[15].setBounds(2500, 833, 110, 110);
		lblEntita[15].setImage(new Image(display, srcBrick[0]));

		Label label_19 = new Label(StickMan, SWT.NONE);
		label_19.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[16] = label_19;
		lblEntita[16].setBounds(3000, 800, 107, 108);

		Label label_20 = new Label(StickMan, SWT.NONE);
		label_20.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblEntita[17] = label_20;
		lblEntita[17].setBounds(3500, 700, 107, 108);

		entita[0] = new Entita(lblEntita[0].getBounds().x, lblEntita[0].getBounds().y, 1, srcCoins);
		entita[1] = new Entita(lblEntita[1].getBounds().x, lblEntita[1].getBounds().y, 0, srcBrick);
		entita[2] = new Entita(lblEntita[2].getBounds().x, lblEntita[2].getBounds().y, 1, srcCoins);
		entita[3] = new Entita(lblEntita[3].getBounds().x, lblEntita[3].getBounds().y, 1, srcCoins);
		entita[4] = new Entita(lblEntita[4].getBounds().x, lblEntita[4].getBounds().y, 1, srcCoins);
		entita[5] = new Entita(lblEntita[5].getBounds().x, lblEntita[5].getBounds().y, 1, srcCoins);
		entita[6] = new Entita(lblEntita[6].getBounds().x, lblEntita[6].getBounds().y, 0, srcBrick);
		entita[7] = new Entita(lblEntita[7].getBounds().x, lblEntita[7].getBounds().y, 0, srcBrick);
		entita[8] = new Entita(lblEntita[8].getBounds().x, lblEntita[7].getBounds().y, 1, srcCoins);
		entita[9] = new Entita(lblEntita[9].getBounds().x, lblEntita[9].getBounds().y, 1, srcCoins);
		entita[10] = new Entita(lblEntita[10].getBounds().x, lblEntita[10].getBounds().y, 1, srcCoins);
		entita[11] = new Entita(lblEntita[11].getBounds().x, lblEntita[11].getBounds().y, 0, srcBrick);
		entita[12] = new Entita(lblEntita[13].getBounds().x, lblEntita[12].getBounds().y, 1, srcCoins);
		entita[13] = new Entita(lblEntita[13].getBounds().x, lblEntita[13].getBounds().y, 1, srcCoins);
		entita[14] = new Entita(lblEntita[14].getBounds().x, lblEntita[14].getBounds().y, 0, srcBrick);
		entita[15] = new Entita(lblEntita[15].getBounds().x, lblEntita[15].getBounds().y, 0, srcBrick);
		entita[16] = new Entita(lblEntita[16].getBounds().x, lblEntita[16].getBounds().y, 1, srcCoins);
		entita[17] = new Entita(lblEntita[17].getBounds().x, lblEntita[17].getBounds().y, 1, srcCoins);

		// ATTENZIONE! Schermata Avvio
		game = false;
		lblMenu.setEnabled(true);
		lblMenu.setVisible(true);
		btnQuit.setEnabled(true);
		btnHelp.setEnabled(true);
		btnLeaderboard.setEnabled(true);
		btnPlay.setEnabled(true);
		btnQuit.setVisible(true);
		btnHelp.setVisible(true);
		btnLeaderboard.setVisible(true);
		btnPlay.setVisible(true);

	}
}
