package Thread;

public class Contatore extends Thread {
	private int secondi=0;
	private int oldTime;

	public Contatore( int x) {
		secondi = 0;
		this.oldTime = x;
	}

	public void run() {
		while (true) {
			int time = (int) System.currentTimeMillis();
			secondi += time - oldTime;
			oldTime = time;
		}
	}

	public int getSecondi() {
		return secondi/1000;
	}

}
