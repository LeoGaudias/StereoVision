package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -2278436951424873713L;
	
	private class ImageSelector extends JPanel {
		private static final long serialVersionUID = -3754752595031246142L;
		
		private JFileChooser jfcImage;
		private JTextField jtxtImage;
		public String filePath;
		
		public ImageSelector() {
			JPanel top = new JPanel();
			top.setLayout(new FlowLayout());
			
			jtxtImage = new JTextField(25);
			JButton jb = new JButton("Choose");
			
			top.add(jtxtImage);
			top.add(jb);
			
			setLayout(new BorderLayout());
			
			class ImagePanel extends JPanel{
				private static final long serialVersionUID = -3316075427568868829L;
				
				private BufferedImage image;
				
				public ImagePanel() {
					image = null;
				}
				
				public void setImage(String path) {
					try {
						image = ImageIO.read(new File(path));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				 
				protected void paintComponent(Graphics g) {
				 	super.paintComponent(g);
				 	if (image != null) {
				 		double scale = (double)image.getWidth()/(double)getWidth();
				 		double height = (double)image.getHeight()/scale;
					 	g.drawImage(image, 0, 0, getWidth(), (int) height, null);
				 	}
				}
			}
			
			final ImagePanel jpImage = new ImagePanel();
			add(top, BorderLayout.NORTH);
			add(jpImage, BorderLayout.CENTER);
			
			jb.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					jfcImage = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
					        "JPG & GIF Images", "jpg", "gif");
					jfcImage.setFileFilter(filter);
					int returnVal = jfcImage.showOpenDialog(ImageSelector.this);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						filePath = jfcImage.getSelectedFile().getAbsolutePath();
						jtxtImage.setText(filePath);
						 
						jpImage.setImage(filePath);
						jpImage.repaint();
				    }
				}
				
			});
		}
		
	}

	public MainWindow() {
		/*
		 * Window properties
		 */
		setTitle("StereoVision");
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(1000, 750));
		setSize(1000, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		/*
		 * 	Window elements
		 */
			/*
			 * Top panel
			 */
			JPanel jpTop = new JPanel();
			jpTop.setLayout(new FlowLayout());
			
			String txtExplain = "Choisir deux images séparées par un intervalle à préciser. Cliquer sur générer.";
			JLabel jlExplain = new JLabel(txtExplain);
			
			jpTop.add(jlExplain);
		
			/*
			 *	Bottom panel 
			 */
			JPanel jpBottom = new JPanel();
			jpBottom.setLayout(new FlowLayout());
			
			JButton jbClose = new JButton("Fermer");
			JButton jbGenerate = new JButton("Générer");
			
			jpBottom.add(jbGenerate);
			jpBottom.add(jbClose);
			
			/*
			 * Main panel
			 */
			JPanel jpMain = new JPanel();
			jpMain.setLayout(new GridLayout(1, 2));
			ImageSelector imgLeft = new ImageSelector();
			ImageSelector imgRight = new ImageSelector();
			jpMain.add(imgLeft);
			jpMain.add(imgRight);
		
		add(jpTop, BorderLayout.NORTH);
		add(jpBottom, BorderLayout.SOUTH);
		add(jpMain, BorderLayout.CENTER);

		/*
		 * Events
		 */
		jbClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
	}
	
	public static void main(String[] args) {
		
//		try {
//			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//		} catch(Exception e) {
//			try {
//		        UIManager.setLookAndFeel(
//	        		UIManager.getSystemLookAndFeelClassName()
//		        );
//			} catch(Exception e2) { }
//	    } 
		
		MainWindow m = new MainWindow();
		m.setVisible(true);
	}
}
