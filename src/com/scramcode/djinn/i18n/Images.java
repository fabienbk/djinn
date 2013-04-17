/*
 * Created on Nov 26, 2005
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
package com.scramcode.djinn.i18n;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

public class Images {
    private static final String BUNDLE_NAME = "com.scramcode.djinn.i18n.images";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    public static ImageIcon getIcon(String key) {
        URL res = null;
        String urlPath = null;
        try {
            urlPath = RESOURCE_BUNDLE.getString(key);
            res = Images.class.getResource(urlPath);
            return new ImageIcon(res);
        }
        catch (Exception e) {   
            System.err.println("Can't find : " + urlPath);
            System.err.println("Classpath : " +  System.getProperty("java.class.path"));
            e.printStackTrace();
            System.exit(2);
        }
        return null;
    }
}
