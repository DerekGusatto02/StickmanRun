package Classifica;
import java.io.*;
import java.util.*;

public class Classifica {
	private Punteggio ep[];
	private final int NMP = 10;

	public Classifica() {
		this.ep=new Punteggio[0];

	}

	public Classifica(Punteggio[] v) {
		if(v.length>=NMP) {
			this.ep=Arrays.copyOf(v, NMP);
		}else{
			this.ep=Arrays.copyOf(v, v.length);
		}

	}

	public Classifica(Classifica a) {
		if(a!=null) {
			this.ep=Arrays.copyOf(a.ep, a.ep.length);
		}else {
			this.ep=new Punteggio[0];
		}
	}

	public String toString() {
		return Arrays.toString(this.ep);
	}

	public int size() {
		return this.ep.length;
	}

	public int indexOf (Punteggio p) {
		if(p==null) return -1;
		for(int i=0; i<ep.length; i++) {
			if(ep[i].equals(p)) return i;
		}
		return -1;
	}

	public boolean contains(Punteggio p) {
		if(p==null) return false;
		for(int i=0; i<ep.length; i++) {
			if(ep[i].equals(p)) return true;
		}
		return false;
	}

	public boolean add(Punteggio p) {
		if(p==null) return false;
		if(ep.length<NMP) {
			ep=Arrays.copyOf(ep, ep.length+1);
			ep[ep.length-1]= new Punteggio(p);
			return true;
		}
		return false;
	}

	public boolean add(int pos, Punteggio p) {
		if(p==null) return false;
		if(pos<0||pos>=NMP) return false;
		if(ep.length<NMP) {
			ep=Arrays.copyOf(ep, ep.length+1);
			ep[ep.length-1]=ep[pos];
			ep[pos]=new Punteggio(p);
			return true;
		}
		return false;
	}

	public boolean remove(int pos) {
		if(pos<0||pos>=ep.length) return false;
		ep[pos]=ep[ep.length-1];
		ep=Arrays.copyOf(ep, ep.length-1);
		return true;
	}

	public boolean remove(Punteggio p) {
		if(p==null) return false;
		for(int i=0; i<ep.length; i++) {
			if(ep[i].equals(p)) {
				ep[i]=ep[ep.length-1];
				ep=Arrays.copyOf(ep, ep.length-1);
				return true;
			}
		}
		return false;
	}

	public Punteggio get(int pos) {
		if(pos<0||pos>=ep.length) return null;
		return new Punteggio (ep[pos]);
	}
	public Punteggio set(int pos, Punteggio p) {
		if(pos<0||pos>=NMP) return null;
		if(pos<ep.length) {
			ep[pos]=new Punteggio (p);
		}else {
			ep=Arrays.copyOf(ep, ep.length+1);
			ep[ep.length-1]=new Punteggio (p);
		}
		return p;
	}
	
	public void ordina() {
		Arrays.sort(ep);
	}
	public boolean save(String nomeFile) {
		boolean b=true;
		try {
			BufferedWriter file = new BufferedWriter(new FileWriter(nomeFile+".csv"));
			file.write("");
			file.close();
		} catch (Exception e) {
			return false;
		}
		for(int i=0; i<ep.length; i++) {
			b=ep[i].save(nomeFile);
			if(b==false) {
				return false;
			}
		}
		return true;
	}

	public boolean load(String nomeFile) {
		ep=new Punteggio[0];
		Punteggio p=new Punteggio();
		try {
			BufferedReader file = new BufferedReader(new FileReader(nomeFile+".csv"));
			String s="";
			s=file.readLine();
			while(s!=null){
				String v[]=new String [2];
				v=s.split(";");
				p.setNome(v[0]);
				try {
					p.setPunti(Integer.parseInt(v[1]));
				}catch(Exception e) {
					p.setPunti(0);
				}
				ep=Arrays.copyOf(ep, ep.length+1);
				ep[ep.length-1]=new Punteggio(p);
				s=file.readLine();
			}
			file.close();
		} catch (Exception e) {

		}
		if(ep.length!=0) {
			return true;
		}
		return false;
	}

	public void removeAll() {
		this.ep=new Punteggio [0];
	}

}
