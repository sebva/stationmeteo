
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * source : http://www.jroller.com/DhilshukReddy/entry/custom_jtabbedpane
 */
public class MeteoTabbedPaneUI extends BasicTabbedPaneUI
	{

	private static final Insets NO_INSETS = new Insets(0, 0, 0, 0);
	private int buttonHeight = 20;

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static ComponentUI createUI(JComponent c)
		{
		return new MeteoTabbedPaneUI();
		}

	@Override
	protected void installDefaults()
		{
		super.installDefaults();
		tabAreaInsets.left = 0;
		selectedTabPadInsets = new Insets(0, 0, 0, 0);
		}

	@Override
	protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex)
		{
		int tw = tabPane.getBounds().width;

		g.setColor(JFrameAfficheurService.BACKGROUND_COLOR);
		g.fillRect(0, 0, tw, buttonHeight);
		g.draw3DRect(0, 0, 0, buttonHeight, true);

		int x = rects[rects.length - 1].x + rects[rects.length - 1].width;
		g.draw3DRect(x, 0, tw - x - 1, buttonHeight, true);

		g.setColor(JFrameAfficheurService.FOREGROUND_COLOR);
		g.drawLine(0, buttonHeight + 1, tw - 1, buttonHeight + 1);

		super.paintTabArea(g, tabPlacement, selectedIndex);
		}

	@Override
	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int tx, int ty, int tw, int th, boolean isSelected)
		{
		Graphics2D g2d = (Graphics2D)g;

		g2d.translate(tx, 0);

		if (isSelected)
			{
			int[] x = new int[3];
			int[] y = new int[3];

			g.setColor(JFrameAfficheurService.FOREGROUND_COLOR);

			g.fillRect(0, 0, tw, buttonHeight);
			g.draw3DRect(0, 0, tw - 1, buttonHeight, true);
			g.fillRect(buttonHeight / 2, buttonHeight, tw - buttonHeight, buttonHeight / 2 + 1);

			// Left Polygon
			x[0] = 0;
			y[0] = buttonHeight;
			x[1] = buttonHeight / 2;
			y[1] = buttonHeight + (buttonHeight / 2);
			x[2] = buttonHeight / 2;
			y[2] = buttonHeight;
			g.fillPolygon(x, y, 3);

			// Right Polygon
			x[0] = tw;
			y[0] = buttonHeight;
			x[1] = tw - buttonHeight / 2;
			y[1] = buttonHeight + (buttonHeight / 2);
			x[2] = tw - buttonHeight / 2;
			y[2] = buttonHeight;
			g.fillPolygon(x, y, 3);

			//Bordures
			g.setColor(JFrameAfficheurService.BACKGROUND_COLOR);

			g.drawLine(0, buttonHeight, buttonHeight / 2, buttonHeight + (buttonHeight / 2));
			g.drawLine(0, buttonHeight + 1, buttonHeight / 2, buttonHeight + (buttonHeight / 2) + 1);
			g.drawLine(buttonHeight / 2, buttonHeight + (buttonHeight / 2) + 1, tw - buttonHeight / 2, buttonHeight + (buttonHeight / 2) + 1);
			g.drawLine(tw - buttonHeight / 2, buttonHeight + (buttonHeight / 2), tw, buttonHeight);
			}
		else
			{
			g.setColor(JFrameAfficheurService.BACKGROUND_COLOR);

			g.fillRect(0, 0, tw, buttonHeight);
			g.draw3DRect(0, 0, tw - 1, buttonHeight, true);

			g.setColor(JFrameAfficheurService.FOREGROUND_COLOR);
			g.drawLine(0, buttonHeight + 1, tw - 1, buttonHeight + 1);
			}

		g2d.translate(-1 * tx, 0);
		}

	@Override
	protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected)
		{
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(textRect.x, 0);

		if (isSelected)
			{
			g.setColor(JFrameAfficheurService.BACKGROUND_COLOR);
			g.drawString(title, (textRect.width / 2 - metrics.stringWidth(title) / 2) + 1, buttonHeight / 2 + metrics.getMaxDescent() + buttonHeight / 2 + 3);

			}
		else
			{
			g.setColor(JFrameAfficheurService.FOREGROUND_COLOR);
			g.drawString(title, (textRect.width / 2 - metrics.stringWidth(title) / 2) + 1, buttonHeight / 2 + metrics.getMaxDescent() + 2);
			}

		g2d.translate(-1 * textRect.x, 0);
		}

	@Override
	public int getTabRunCount(JTabbedPane pane)
		{
		return 1;
		}

	@Override
	protected Insets getContentBorderInsets(int tabPlacement)
		{
		return NO_INSETS;
		}

	@Override
	protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight)
		{
		if (tabPlacement == tabIndex)
			{
			return buttonHeight;
			}
		else
			{
			return buttonHeight + (buttonHeight / 2) + 6;
			}
		}

	@Override
	protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight)
		{
		return buttonHeight + (buttonHeight / 2) + 6;
		}

	@Override
	protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics)
		{
		return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + buttonHeight;
		}

	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{
		// Do nothing
		}

	@Override
	protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
		{
		// Do nothing
		}

	@Override
	protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex)
		{
		// Do nothing
		}
	}
