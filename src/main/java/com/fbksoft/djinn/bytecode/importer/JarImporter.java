package com.fbksoft.djinn.bytecode.importer;

import java.io.File;
import java.util.jar.JarFile;

import com.fbksoft.djinn.bytecode.DefLocationVisitor;
import com.fbksoft.djinn.bytecode.JarReader;
import com.fbksoft.djinn.db.data.DataHelper;
import com.fbksoft.djinn.db.data.Location;
import com.fbksoft.djinn.util.DjinnException;

public class JarImporter extends AbstractImporter {

	private File pathFile;

	public JarImporter(File pathFile) {
		this.pathFile = pathFile;
	}

	@Override
	public void performImport() throws DjinnException {
		try {
			fireProgressUpdate(0);
			fireStatusUpdate(pathFile.getAbsolutePath());
			
			// the jar path
			String path = pathFile.getAbsolutePath();

			// the jar visitor
			JarReader jarReader = new JarReader(new JarFile(path));
						
			Location location = new Location(path, Location.JAR_LOCATION_TYPE, null);
			DataHelper.putLocation(location);
			
			// visit the jar
			jarReader.accept(new DefLocationVisitor(location));		 
			
			fireProgressUpdate(100);
		} catch (Exception ex) {
			throw new DjinnException("Error while reading " + pathFile.getAbsolutePath() + " : " + ex.getMessage(), ex);
		}
	}
}
