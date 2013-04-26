package com.scramcode.djinn.bytecode.importer;

import java.io.File;
import java.sql.Connection;
import java.util.jar.JarFile;

import com.scramcode.djinn.bytecode.DefLocationVisitor;
import com.scramcode.djinn.bytecode.JarReader;
import com.scramcode.djinn.db.data.DataHelper;
import com.scramcode.djinn.db.data.Location;
import com.scramcode.djinn.db.mgmt.ConnectionManager;
import com.scramcode.djinn.util.DjinnException;

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
			Connection conn = ConnectionManager.getInstance().getConnection();
			
			Location location = new Location(path, Location.JAR_LOCATION_TYPE);
			DataHelper.putLocation(conn, location);
			
			// visit the jar
			jarReader.accept(new DefLocationVisitor(location));		 
			
			fireProgressUpdate(100);
		} catch (Exception ex) {
			throw new DjinnException("Error while reading " + pathFile.getAbsolutePath() + " : " + ex.getMessage(), ex);
		}
	}
}
