package dk.japps.sqlite.gui.temp;

import javax.swing.*;

public class TestSpringLayout {
	
	public static void main(String[] args) {
		new TestSpringLayout().show();
	}

	public void show() {
		String[] labels = { "Name: ", "Fax: ", "Email: ", "Address: " };
		int numPairs = labels.length;

		// Create and populate the panel.
		JPanel panel = new JPanel(new SpringLayout());
		for (int i = 0; i < numPairs; i++) {
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			panel.add(l);
			JTextField textField = new JTextField(10);
			l.setLabelFor(textField);
			panel.add(textField);
		}

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(panel, numPairs, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad

		JFrame frame = new JFrame("SpringForm");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.setOpaque(true); // content panes must be opaque
		frame.setContentPane(panel);

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

}
