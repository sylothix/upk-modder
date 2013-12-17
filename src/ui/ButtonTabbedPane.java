package ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

/**
 * Custom tabbed pane featuring a 'Close' button in its tabs.
 * @author XMS
 */
public class ButtonTabbedPane extends JTabbedPane {
	
	// TODO: focus traversal on tabs is dodgy, investigate

	/**
	 * Creates a tabbed pane featuring a 'Close' button in its tabs.
	 * @param tabPlacement the placement for the tabs relative to the content
	 * @param tabLayoutPolicy the policy for laying out tabs when all tabs will not fit on one run
	 */
	public ButtonTabbedPane(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
	}
	
	@Override
	public void addTab(String title, Component component) {
		super.addTab(title, component);
		// add 'Close' button to new tab
		this.setTabComponentAt(this.getTabCount() - 1, new ButtonTabComponent(this));
	}
	
	/**
	 * Component to be used inside tabbed pane tabs.<br>
	 * Based on a <a href="http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TabComponentsDemoProject/src/components/ButtonTabComponent.java">Java Tutorials Code Sample</a>
	 * @author XMS
	 */
	private class ButtonTabComponent extends JPanel {
		
		/**
		 * The reference to the parent tabbed pane.
		 */
		private JTabbedPane tabPane;
	 
	    /**
	     * Creates a panel containing the tab title and a 'Close' button
	     * @param tabPane the reference to the parent tabbed pane
	     */
	    public ButtonTabComponent(final JTabbedPane tabPane) {
	        super(new BorderLayout(5, 0));
	        
	        this.tabPane = tabPane;

			// make component transparent
			this.setOpaque(false);
			
			// create label, make it display its corresponding tab title as text
			JLabel label = new JLabel() {
				public String getText() {
					int index = tabPane.indexOfTabComponent
							(ButtonTabComponent.this);
					if (index != -1) {
						return tabPane.getTitleAt(index);
					}
					return null;
				}
			};

			this.add(label, BorderLayout.CENTER);
			TabButton tabButton = new TabButton();
			
			JToolBar tb = new JToolBar();
			tb.add(tabButton);
			
			this.add(tabButton, BorderLayout.EAST);
		}

	    /**
	     * 'Close' button for tabs.
	     * @author XMS
	     */
		private class TabButton extends JButton implements ActionListener {
			
			/**
			 * Constructs a 'Close' button.
			 */
			public TabButton() {
				int size = 17;
				this.setPreferredSize(new Dimension(size, size));

				// configure visuals
				this.setToolTipText("Close this document");
				this.setFocusable(false);
				
				// install action listener to close tab on click
				this.addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent evt) {
				int i = tabPane.indexOfTabComponent(ButtonTabComponent.this);
				if (i != -1) {
					tabPane.remove(i);
				}
			}

			// paint the cross
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setStroke(new BasicStroke(2));
				g2.setColor(Color.DARK_GRAY);
				int delta = 6;
				g2.drawLine(delta, delta,
						getWidth() - delta, getHeight() - delta);
				g2.drawLine(getWidth() - delta, delta,
						delta, getHeight() - delta);
				g2.dispose();
			}
			
		}
		
	}

}