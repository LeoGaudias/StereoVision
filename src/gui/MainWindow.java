package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.UIManager;
import stereo.StereoImages;
import stereo.myImage;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private JButton jbClose;
	private JButton jbGenerate;
	private JButton jbPoints;
	private JSpinner jsEcart;
	
	private ImageSelector imgLeft;
	private ImageSelector imgRight;
	
	public MainWindow() {
		initElements();
		setWindowProperties();
		setWindowElements();
		setEvents();
	}
	
	public void initElements() {

		jbClose = new JButton();
		jbGenerate = new JButton();
		jbPoints = new JButton();
		jsEcart = new JSpinner();
		imgLeft = new ImageSelector();
		imgRight = new ImageSelector();
	}
	
	public void setWindowProperties() {
		setTitle("StereoVision");
		setLayout(new BorderLayout());
//		setMinimumSize(new Dimension(1000, 750));
		setResizable(false);
		setSize(1000, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	public void setWindowElements() {

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
		jsEcart = new JSpinner();
		jsEcart.setValue(10);
//		jbClose = new JButton("Fermer");
		jbClose.setText("Fermer");
//		jbGenerate = new JButton("Générer");
		jbGenerate.setText("Générer");
		jbPoints.setText("Points verts");
		
		jpBottom.add(jlEcart);
		jpBottom.add(jsEcart);
		jpBottom.add(jbPoints);
		jpBottom.add(jbGenerate);
		jpBottom.add(jbClose);
		
		/*
		 * Main panel
		 */
		JPanel jpMain = new JPanel();
		jpMain.setLayout(new GridLayout(1, 2));
		jpMain.add(imgLeft);
		jpMain.add(imgRight);
	
		/*
		 * Adding elements
		 */
		add(jpTop, BorderLayout.NORTH);
		add(jpBottom, BorderLayout.SOUTH);
		add(jpMain, BorderLayout.CENTER);
	}
	
	public void setEvents() {

		jbClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		jbGenerate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				myImage left = imgLeft.image;
				myImage right = imgRight.image;
				StereoImages si = new StereoImages(left, right, jsEcart.getValue());
				si.calcul();
			}
			
		});
		
		jbPoints.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
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
