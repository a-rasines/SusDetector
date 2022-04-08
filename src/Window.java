import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Window extends JFrame{
	private static final long serialVersionUID = -8255319694373975038L;
	Exporter ex;
	public static void main(String[] args) {
		new Window();
	}
	
	public Window() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		JLabel foundLabel = new JLabel("Amongus Found: 0");
		JTextField nameField = new JTextField("None", 26);
		JButton importButton = new JButton("Import");
		JButton detectButton = new JButton("Detect");
		JButton exportButton = new JButton("Export");
		
		importButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser= new JFileChooser();
				@SuppressWarnings("serial")
				int choice = chooser.showOpenDialog(new Component() {});
				if (choice != JFileChooser.APPROVE_OPTION) return;
				File chosenFile = chooser.getSelectedFile();
				nameField.setText(chosenFile.getName());
				try {
					ex = new Exporter(ImageIO.read(chosenFile));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Something went wrong: "+e1.getMessage());
				}
			}
			
		});
		detectButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ex.detectAndGen();
				foundLabel.setText("Amongus Found: " + String.valueOf(ex.count));
			}
			
		});
		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ex.export();
			}
			
		});
		
		nameField.setEnabled(false);
		
		contentPane.add(foundLabel);
		contentPane.add(nameField);
		contentPane.add(importButton);
		contentPane.add(detectButton);
		contentPane.add(exportButton);
		
		setVisible(true);
		setMinimumSize(new Dimension(700, 80));
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
