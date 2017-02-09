package com.fbksoft.djinn.bytecode.importer;

import com.fbksoft.djinn.bytecode.DefLocationVisitor;
import com.fbksoft.djinn.bytecode.DirectoryReader;
import com.fbksoft.djinn.bytecode.LocationReader;
import com.fbksoft.djinn.db.data.DataHelper;
import com.fbksoft.djinn.db.data.Location;
import java.io.File;

import com.fbksoft.djinn.util.DjinnException;

public class ClassDirectoryImporter extends AbstractImporter {

	File directory;

	public ClassDirectoryImporter(File directory) {
		this.directory = directory;    
	}

	@Override
	public void performImport() throws DjinnException {
		fireStatusUpdate("Beginning classe directory import...");
		fireProgressUpdate(0);

		LocationReader locationReader = new DirectoryReader(new File(directory.getAbsolutePath()));                
		Location location = new Location(directory.getAbsolutePath(), Location.DIR_LOCATION_TYPE, null);
		locationReader.accept(new DefLocationVisitor(location));

		DataHelper.putLocation(location);
		
	}

}
