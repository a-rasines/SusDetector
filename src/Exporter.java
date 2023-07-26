import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Exporter extends Detector{
	public Exporter(BufferedImage img) {
		super(img);
	}
	public void export() {
		JFileChooser chooser= new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("Select the save directory");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		@SuppressWarnings("serial")
		int choice = chooser.showOpenDialog(new Component() {});
		if (choice != JFileChooser.APPROVE_OPTION) return;
		File chosenFolder = chooser.getSelectedFile();
		System.out.println(chosenFolder.getAbsolutePath());
		String chosenFile = JOptionPane.showInputDialog("Name of the file");
		if(chosenFile == null || chosenFile.strip().equals(""))
			return;
		File outputfile = new File(chosenFolder.getAbsolutePath() + "/" + chosenFile +".png");
		try {
			//outputfile.createNewFile();
			boolean a = ImageIO.write(detectAndGen(), "png", outputfile);
			System.out.println(a);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
