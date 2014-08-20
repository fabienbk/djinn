package com.scramcode.djinn.bytecode.importer;

import com.scramcode.djinn.bytecode.DefLocationVisitor;
import com.scramcode.djinn.bytecode.DirectoryReader;
import com.scramcode.djinn.bytecode.LocationReader;
import com.scramcode.djinn.db.data.DataHelper;
import com.scramcode.djinn.db.data.Location;
import java.io.File;

import com.scramcode.djinn.util.DjinnException;

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
