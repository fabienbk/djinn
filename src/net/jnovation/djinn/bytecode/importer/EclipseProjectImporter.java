/*
 * Created on Feb 8, 2006
 * By Fabien Benoit - http://www.jnovation.net
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
package net.jnovation.djinn.bytecode.importer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.jnovation.djinn.bytecode.DefLocationVisitor;
import net.jnovation.djinn.bytecode.DirectoryReader;
import net.jnovation.djinn.bytecode.JarReader;
import net.jnovation.djinn.bytecode.LocationReader;
import net.jnovation.djinn.db.data.DataHelper;
import net.jnovation.djinn.db.data.Location;
import net.jnovation.djinn.db.data.Project;
import net.jnovation.djinn.db.mgmt.ConnectionManager;
import net.jnovation.djinn.util.DjinnException;

/**
 * Import eclipse project in djinn workspace. Reads the .classpath located under the root
 * directory selected by the user, and then iterate on each entries to analyze java object
 * and put them in the code database.
 * 
 * @author Fabien Benoit <fabien.benoit@gmail.com>
 */
public class EclipseProjectImporter extends Importer {

	File directory;

	public EclipseProjectImporter(File directory) {
		this.directory = directory;    
	}

	/* (non-Javadoc)
	 * @see net.jnovation.djinn.bytecode.importer.Importer#importProject()
	 */
	public void importProject() throws DjinnException {

		fireStatusUpdate("Beginning eclipse import...");
		fireProgressUpdate(0);

		File classPathFile = scanForClasspath();
		Project project = createAndSaveProject(directory.getName());

		List<Location> locations = createAndSaveLocations(classPathFile, project.getKey());

		double progress = 0;
		double delta = 100d / locations.size();

		for (Iterator iter = locations.iterator(); iter.hasNext();) {

			Location loc = (Location) iter.next();
			LocationReader locReader = null;

			fireStatusUpdate(loc.getAbsolutePath());            
			fireProgressUpdate((int)progress);

			if (loc.getType() == Location.DIR_LOCATION_TYPE) {
				locReader = new DirectoryReader(new File(loc.getAbsolutePath()));                
			}
			else if (loc.getType() == Location.JAR_LOCATION_TYPE) {
				try {
					locReader = new JarReader(new JarFile(loc.getAbsolutePath()));                    
				}
				catch (IOException e) {
					throw new DjinnException("Can't open jar file : " + loc.getAbsolutePath(), e);
				}
			}                                    
			else {
				throw new DjinnException("Unknown location type = " + loc.getType());
			}            
			locReader.accept(new DefLocationVisitor(loc.getKey()));

			progress+=delta;
		}        
	}

	public File scanForClasspath() throws DjinnException {        
		File[] dirContentFiles = directory.listFiles();
		for (int i = 0; i < dirContentFiles.length; i++) {
			if (dirContentFiles[i].getName().equals(".classpath")) {
				return dirContentFiles[i]; 
			}
		}        
		throw new DjinnException("Can't locate .classpath file in " + directory.getName());
	}

	public List<Location> createAndSaveLocations(File classPathFile, final int projectKey) throws DjinnException {        

		List<Location> locationList = new ArrayList<Location>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom = null;

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(classPathFile);
		}
		catch(Exception e) {
			throw new DjinnException("Cannot parse .classpath file", e);
		}

		NodeList entryNodeList = dom.getElementsByTagName("classpathentry");
		for(int i = 0; i < entryNodeList.getLength(); i++) {
			Node currentNode = entryNodeList.item(i);

			String kind = currentNode.getAttributes().getNamedItem("kind").getNodeValue();                
			String path = currentNode.getAttributes().getNamedItem("path").getNodeValue();

			int type = -1;
			if (kind.equals("lib")) {
				type = Location.JAR_LOCATION_TYPE;
			}
			else if (kind.equals("output")) {
				type = Location.DIR_LOCATION_TYPE;
			}
			else {
				continue;
			}

			// fix path if relative
			if (!path.matches("[A-Za-z]\\:.*")) {                    
				path = classPathFile.getParent().replaceAll("\\\\", "/") + '/' + path;
			}


			Location location = new Location(path, type, projectKey);
			locationList.add(location);
			DataHelper.putLocation(ConnectionManager.getInstance().getConnection(), location);


		}

		return locationList;
	}
}
