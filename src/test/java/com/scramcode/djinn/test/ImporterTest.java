package com.scramcode.djinn.test;

import java.io.File;

import org.junit.Test;

import com.scramcode.djinn.bytecode.importer.EclipseProjectImporter;
import com.scramcode.djinn.util.DjinnException;

public class ImporterTest {

	@Test
	public void testEclipseImport() throws DjinnException {
		EclipseProjectImporter eclipseProjectImporter = new EclipseProjectImporter(new File("."));
		eclipseProjectImporter.performImport();
	}

}
