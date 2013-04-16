/*
 * Created on Mar 1, 2006
 * By Fabien Benoit - http://www.scramcode.com 
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.jnovation.djinn.util;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

public class GraphicTools {

    public static void drawNode(Graphics g, int centerX, int centerY, Icon image, String label, int insets) {                

        // Get label size in pixels
        FontMetrics metrics = g.getFontMetrics();        
        Rectangle2D textSize = metrics.getStringBounds(label, g);
        
        // +-------------------+
        // | +--+ +----------+ |
        // | Img| |L A B E L | |
        // | +--+ +----------+ |
        // +-------------------+
     
        int labelWidth = (int) textSize.getWidth();
        int labelHeight = (int) textSize.getHeight();
        
        int imageWidth = image.getIconWidth();
        //int imageHeight = image.getIconWidth();
        
        int rectangleWidth = labelWidth + imageWidth + insets*3;
        int rectangleHeight = labelHeight + insets*2;
        
        int topRectangleX = centerX - (rectangleWidth / 2);
        int topRectangleY = centerY - (rectangleHeight / 2);
        
        int topLabelX = centerX - (labelWidth / 2);
        int topLabelY = centerY + (labelHeight / 2);
        
        // Draw rectangle fill
        g.setColor(Color.WHITE);
        g.fillRect(topRectangleX, topRectangleY, rectangleWidth, rectangleHeight);
        // Draw rectangle border        
        g.setColor(Color.BLACK);
        g.drawRect(topRectangleX, topRectangleY, rectangleWidth, rectangleHeight);
        
        g.drawString(label, topLabelX, topLabelY);        
    }

}
