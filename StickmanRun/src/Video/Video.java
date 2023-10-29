package Video;

import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
public class Video {
	
	public static void videoInizo (Label lbl){
		lbl.setEnabled(true);
		lbl.setVisible(true);
		for(int i=1; i<300; i++) {
			String stringa= "./images/video/intro/"+i;
			lbl.setImage(SWTResourceManager.getImage(stringa));
			
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lbl.setVisible(false);
		lbl.setEnabled(false);
		
	}
	
	public static void videoFine (Label lbl){
		lbl.setEnabled(true);
		lbl.setVisible(true);
		for(int i=1; i<120; i++) {
			String stringa= "./images/video/intro"+i;
			lbl.setImage(SWTResourceManager.getImage(stringa));
		}
		lbl.setVisible(false);
		lbl.setEnabled(false);
	}
}
