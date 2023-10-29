package Classifica;
import java.io.*;

public class Punteggio implements Comparable{
	private String nome;
	private int punti;


	public Punteggio(){
		this.nome="sconosciuto";
		this.punti=0;
	}

	public Punteggio (String nome, int punti) {
		if(nome!=null) {
			this.nome=nome;
		}else {
			this.nome="sconosciuto";
		}
		if(punti>=0) {
			this.punti=punti;
		}else {
			this.punti=0;
		}
	}

	public Punteggio (Punteggio p) {
		if(p!=null) {
			this.nome=p.nome;
			this.punti=p.punti;
		}else {
			this.nome="sconosciuto";
			this.punti=0;
		}
	}

	public String getNome() {
		return this.nome;
	}
	public int getPunti() {
		return this.punti;
	}

	public void setNome(String nome) {
		if(nome!=null) {
			this.nome=nome;
		}else {
			this.nome="sconosciuto";
		}
	}
	public void setPunti(int punti) {
		if(punti>=0) {
			this.punti=punti;
		}else {
			this.punti=0;
		}
	}

	public boolean equals(Punteggio p) {
		if(p==null) return false;
		return (this.nome.equals(p.nome)&&this.punti==p.punti);
	}

	public String toString() {
		return this.nome+";"+this.punti;
	}
	public Punteggio load(String nomeFile) {
		try {
			BufferedReader file = new BufferedReader(new FileReader(nomeFile+".csv"));
			String s="";
			s=file.readLine();
			if( s!=null){
				//System.out.println(s);
				String v[]=new String [2];
				v=s.split(";");

				this.nome=v[0];
				try {
					this.punti=Integer.parseInt(v[1]);
				}catch(Exception e) {
					this.punti=0;
				}
				file.close();
				return this;
			}else {
				file.close();
				return null;
			}


		} catch (Exception e) {
			return null;
		}

	}

	public boolean save(String nomeFile) {
		try {
			BufferedWriter file = new BufferedWriter(new FileWriter(nomeFile+".csv", true));
			file.write(this.toString());
			file.newLine();
			file.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Punteggio p = (Punteggio)(o);
		if(this.punti>p.punti) {
			return 1;
		}else if(this.punti==p.punti) {
			return 0;
		}
		return -1;
	}
	


}
