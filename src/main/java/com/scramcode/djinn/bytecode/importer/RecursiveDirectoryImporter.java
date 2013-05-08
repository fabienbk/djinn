/*
 * Created on Feb 8, 2006
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
package com.scramcode.djinn.bytecode.importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;

import com.scramcode.djinn.bytecode.DefLocationVisitor;
import com.scramcode.djinn.bytecode.JarReader;
import com.scramcode.djinn.db.data.DataHelper;
import com.scramcode.djinn.db.data.Location;
import com.scramcode.djinn.db.data.Project;
import com.scramcode.djinn.util.DjinnException;


/**
 * Using a quick file comparison algorithm, this importers tries to avoid duplicates.
 * 
 * @author Fabien
 */
public class RecursiveDirectoryImporter extends AbstractImporter {

	private File selectedDir;
	private Set<FileIdentifier> visitedFilesSet = new HashSet<FileIdentifier>();
	
	public RecursiveDirectoryImporter(File selectedDir) {
		this.selectedDir = selectedDir;
	}

	public void performImport() throws DjinnException {
		try {
			fireStatusUpdate("Beginning directory import...");
			
			List<File> jarList = getFileListing(selectedDir);			
			Project project = createAndSaveProject(selectedDir.getName(), selectedDir);			
						
			double progress = 0;
			double delta = 100d / jarList.size();
			
			for (File file : jarList) {
								
				try {
					
					fireStatusUpdate(file.getAbsolutePath());
			    	fireProgressUpdate((int)progress);
			    	
					JarReader locReader = new JarReader(new JarFile(file));
					
					// Create location for this jar					
					Location location = new Location(file.getAbsolutePath(), Location.JAR_LOCATION_TYPE, project);					
					DataHelper.putLocation(location);
		            		            
					// Visit the jar
					locReader.accept(new DefLocationVisitor(location));
					
				} catch (IOException e) {
					//TODO Display a warning somewhere
					e.printStackTrace();
					continue;
				}
				
				progress+=delta;
				
			}
		}
		catch(FileNotFoundException e) {
			throw new DjinnException("Cannot import the directory", e);
		}
		
	}
	
	public List<File> getFileListing(File startingDir) throws FileNotFoundException{
		validateDirectory(startingDir);
		List<File> result = new ArrayList<File>();

		File[] filesAndDirs = startingDir.listFiles();
		List<File> filesDirs = Arrays.asList(filesAndDirs);
		Iterator<File> filesIter = filesDirs.iterator();
		File file = null;
		while(filesIter.hasNext()) {
			file = filesIter.next();			
			if (file.isFile() && file.getName().endsWith(".jar")) {
				result.add(file); // Add only jars!
			}			
			if (!file.isFile()) {
				
				// Check for duplicates
				FileIdentifier id = getIdentifier(file);
				if (!visitedFilesSet.contains(id)) {
					visitedFilesSet.add(id);
					List<File> deeperList = getFileListing(file);
					result.addAll(deeperList);	
				}				
			}
		}
		return result;
	}
 	
	private FileIdentifier getIdentifier(File file) {
		FileIdentifier id = new FileIdentifier();
		id.name = file.getName();
		id.size = file.length();
		return id;
	}

	/**
	 * Directory is valid if it exists, does not represent a file, and can be read.
	 */
	private void validateDirectory (File aDirectory) throws FileNotFoundException {
		if (aDirectory == null) {
			throw new IllegalArgumentException("Directory should not be null.");
		}
		if (!aDirectory.exists()) {
			throw new FileNotFoundException("Directory does not exist: " + aDirectory);
		}
		if (!aDirectory.isDirectory()) {
			throw new IllegalArgumentException("Is not a directory: " + aDirectory);
		}
		if (!aDirectory.canRead()) {
			throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
		}
	}
	
	public static void main(String[] args) throws DjinnException {
		
		RecursiveDirectoryImporter rdi = new RecursiveDirectoryImporter(new File("C:/workspace/Djinn-tool"));
		rdi.performImport();
		
	}
	
	private static class FileIdentifier {
		public long size;
		public String name;
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + (int) (size ^ (size >>> 32));
			return result;
		}
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final FileIdentifier other = (FileIdentifier) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (size != other.size)
				return false;
			return true;
		}
				
	}
	

} 