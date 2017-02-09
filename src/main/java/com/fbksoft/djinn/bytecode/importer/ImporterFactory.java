package com.fbksoft.djinn.bytecode.importer;

import com.fbksoft.djinn.db.data.JavaItem;
import com.fbksoft.djinn.db.data.Location;
import com.fbksoft.djinn.db.data.Project;

public final class ImporterFactory {
	
	private ImporterFactory() {		
	}

	public static AbstractImporter createImporterFor(JavaItem javaItem, ImportListener listener) {
		AbstractImporter importer = null;
		if (javaItem instanceof Project) {
			importer = new EclipseProjectImporter(((Project)javaItem).getDirectory());
		}			
		else if (javaItem instanceof Location) {			
			Location location = (Location)javaItem;
			if (location.getType() == Location.JAR_LOCATION_TYPE) {
				importer = new JarImporter(location.getPathFile());
			}
			else if (location.getType() == Location.DIR_LOCATION_TYPE) {
				importer = new ClassDirectoryImporter(location.getPathFile());
			}
			else {
				throw new RuntimeException("Unkown location type " + location.getType());
			}
		}
		else {
			throw new RuntimeException("No importer defined for java item <"+javaItem.getClass().getSimpleName()+">");
		}
		importer.addImportListener(listener);
		return importer;
	}
	
}
