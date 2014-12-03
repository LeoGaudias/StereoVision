package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import stereo.Mask;
import stereo.StereoImages;
import stereo.myImage;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private class ImageSelector extends JPanel {
		
		private JFileChooser jfcImage;
		private JTextField jtxtImage;
		public String filePath;
		public myImage image;
		public BufferedImage croix;
		public BufferedImage Redcroix;
		
		public ImageSelector() {
			JPanel top = new JPanel();
			top.setLayout(new FlowLayout());
			
			jtxtImage = new JTextField(25);
			JButton jb = new JButton("Choose");
			
			top.add(jtxtImage);
			top.add(jb);
			
			setLayout(new BorderLayout());
			
			class ImagePanel extends JPanel{
				
				public ImagePanel() {
					image = null;
					croix=null;
				}
				
				public void setImage(String path) {
					try {
						image = new myImage(ImageIO.read(new File(path)));
						croix =  ImageIO.read(new File("img/point.png"));
						Redcroix =  ImageIO.read(new File("img/RedPoint.png"));
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
					 	
					 	ArrayList<Point> greenPoints = image.findGreenPoints();
					 	image.IsCarree();
					 	
					 	for(Point pi : greenPoints){
					 		if(image.fond[0].equals(pi) || image.fond[1].equals(pi) || image.fond[2].equals(pi) || image.fond[3].equals(pi)){
					 			g.drawImage(Redcroix, (int) ((pi.x)/scale-10), (int)((pi.y)/scale-10), null);
					 		}
					 		else {
					 			g.drawImage(croix, (int) ((pi.x)/scale-10), (int)((pi.y)/scale-10), null);

					 		}
					 	}
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
					jfcImage.setCurrentDirectory(new File("img"));
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
//		setMinimumSize(new Dimension(1000, 750));
		setResizable(false);
		setSize(1000, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
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
			
			JLabel jlEcart = new JLabel("Ecart entre les deux images en cm :");
			JSpinner jsEcart = new JSpinner();
			jsEcart.setValue(10);
			JButton jbClose = new JButton("Fermer");
			JButton jbGenerate = new JButton("Générer");
			
			jpBottom.add(jlEcart);
			jpBottom.add(jsEcart);
			jpBottom.add(jbGenerate);
			jpBottom.add(jbClose);
			
			/*
			 * Main panel
			 */
			JPanel jpMain = new JPanel();
			jpMain.setLayout(new GridLayout(1, 2));
			final ImageSelector imgLeft = new ImageSelector();
			final ImageSelector imgRight = new ImageSelector();
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
		
		jbGenerate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				myImage left = new myImage(imgLeft.image);
				myImage left = imgLeft.image;
				myImage right = imgRight.image;
				StereoImages si = new StereoImages(left, right, jsEcart.getValue());
//				left.findGreenPoints();
//				Mask m1 = left.getMask(left.reperes.get(0), 10);
//				m1.print();
//				myImage right = new myImage(imgRight.image);
//				Mask m2 = right.getMask(right.reperes.get(1), 10);
//				System.out.println(right.findMask(m2));
//				m2.print();
//				System.out.println(m1.equals(m2));
			}
			
		});
		
	}
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			try {
		        UIManager.setLookAndFeel(
	        		UIManager.getSystemLookAndFeelClassName()
		        );
			} catch(Exception e2) { }
	    } 
		
		MainWindow m = new MainWindow();
		m.setVisible(true);
	}
}
