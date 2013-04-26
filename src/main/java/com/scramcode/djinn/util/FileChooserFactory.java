/*
 * Created on Dec 3, 2005
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
package com.scramcode.djinn.util;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileChooserFactory {
    
    private final static String ROOT_DIR = "c://";    

    private static FileChooserFactory instance;
        
    private JFileChooser directoryChooser;
    private JFileChooser jarChooser;
    
    public static FileChooserFactory getInstance() {
        if (instance == null) {
            instance = new FileChooserFactory();
        }
        return instance;
    }
    
    public FileChooserFactory() {

        directoryChooser = new JFileChooser(new File(ROOT_DIR));
        directoryChooser.setMultiSelectionEnabled(false);
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        jarChooser = new JFileChooser(new File(ROOT_DIR));        
        jarChooser.setMultiSelectionEnabled(true);
        jarChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jarChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".jar") || f.isDirectory();
            }
            @Override
            public String getDescription() {
                return "*.jar";
            }            
        });
    }
    
    public JFileChooser getJarChooser() {        
        return jarChooser;        
    }

    public JFileChooser getDirectoryChooser() {        
        return directoryChooser;        
    }
}
