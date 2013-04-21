package com.scramcode.djinn.bytecode.importer;

import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.db.data.Location;
import com.scramcode.djinn.db.data.Project;

public class ImporterFactory {

	public static Importer createImporterFor(JavaItem javaItem, ImportListener listener) {
		Importer importer = null;
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
