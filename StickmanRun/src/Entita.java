import java.util.Arrays;

public class Entita {
	private int x;
	private int y;
	private int effetto = 0; // FALSO-final: cambia per ogni tipo di entita
	private String imgsrc[];
	private int imgIndex;

	public Entita() {
		this.x = 0;
		this.y = 0;
		this.effetto = 0;
		this.imgsrc = new String[0];
		this.imgIndex = 0;
	}

	public int getIndexImage() {
		return imgIndex;
	}

	public void setIndexImage(int imgIndex) {
		this.imgIndex = imgIndex;
	}

	public Entita(int x, int y, int effetto, String[] imgsrc) {
		if (x >= 0)
			this.x = x;
		else
			this.x = 0;

		if (y >= 0)
			this.x = y;
		else
			this.y = 0;

		this.effetto = effetto;

		this.imgsrc = Arrays.copyOf(imgsrc, imgsrc.length);
		this.imgIndex = 0;
	}

	public Entita(Entita e) {
		if (e != null) {
			if (e.x >= 0)
				this.x = e.x;
			else
				this.x = 0;

			if (e.y >= 0)
				this.x = e.y;
			else
				this.y = 0;

			this.effetto = e.effetto;

			this.imgsrc = Arrays.copyOf(e.imgsrc, e.imgsrc.length);
		} else {
			this.x = 0;
			this.y = 0;
			this.effetto = 0;
			this.imgsrc = new String[0];
		}
		this.imgIndex = 0;
	}

	public String toString() {
		return this.x + ";" + this.y + ";" + this.effetto + ";" + Arrays.toString(this.imgsrc);
	}

	public boolean equals(Entita e) {
		return ((this.x == e.x) && (this.y == e.y) && (this.effetto == e.effetto)
				&& Arrays.equals(this.imgsrc, e.imgsrc));
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int geteffetto() {
		return effetto;
	}

	public void seteffetto(int effetto) {
		this.effetto = effetto;
	}

}
