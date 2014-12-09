package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import stereo.myImage;

@SuppressWarnings("serial")
public class ImageSelector extends JPanel {
	
	private JFileChooser jfcImage;
	private JTextField jtxtImage;
	public String filePath;
	public myImage image;
	public BufferedImage croix;
	public BufferedImage Redcroix;
	public ImagePanel jpImage;
	private JButton jb;
	
	public boolean boolPoint; 
	
	public ImageSelector() {
		setWindowElements();
		setEvents();
	}
	
	public void setWindowElements() {

		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		
		jtxtImage = new JTextField(25);
		jb = new JButton("Choose");
		
		top.add(jtxtImage);
		top.add(jb);
		
		setLayout(new BorderLayout());

		
		
		jpImage = new ImagePanel();
		add(top, BorderLayout.NORTH);
		add(jpImage, BorderLayout.CENTER);
		
		boolPoint = false;
	}
	
	public void drawPoints(){
		boolPoint = true;
		this.repaint();
	}
	public void setEvents() {
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
	
	class ImagePanel extends JPanel {
		
		public ImagePanel() {
			image = null;
//			croix=null;
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
			 	
			 	if(boolPoint) {
			 		ArrayList<Point> greenPoints = image.findGreenPoints();
			 	
			 	
				 	for(Point pi : greenPoints){
			 			String s = "["+pi.getX()+","+pi.getY()+"]";
				 		if(image.fond[0].equals(pi) || image.fond[1].equals(pi) || image.fond[2].equals(pi) || image.fond[3].equals(pi)){
					 			g.drawImage(Redcroix, (int) ((pi.x)/scale-10), (int)((pi.y)/scale-10), null);
					 			g.drawChars(s.toCharArray(), 0, s.length(), (int) ((pi.x)/scale-10)+5, (int)((pi.y)/scale-10)+5);
	
				 		}
				 		else {
				 			g.drawImage(croix, (int) ((pi.x)/scale-10), (int)((pi.y)/scale-10), null);
				 			g.drawChars(s.toCharArray(), 0, s.length(), (int) ((pi.x)/scale-10)+5, (int)((pi.y)/scale-10)+5);
	
				 		}
				 	}
			 	}
		 	}
		}
	}
	
	
}