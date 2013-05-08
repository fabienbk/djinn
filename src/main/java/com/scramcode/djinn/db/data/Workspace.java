package com.scramcode.djinn.db.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Workspace {
	
	private List<JavaItem> topLevelItems = new ArrayList<JavaItem>();
	
	private Map<Long, Project> projects = new HashMap<Long, Project>();
	private Map<Long, Location> locations = new HashMap<Long, Location>();
	
	private Map<Long, Clazz> clazzes = new HashMap<Long, Clazz>();
	private Map<String, List<Clazz>> clazzesByName = new HashMap<String, List<Clazz>>();
	
	private Map<Long, Package> packages = new HashMap<Long, Package>();
	
	private Map<Long, Field> fields = new HashMap<Long, Field>();
	private Map<Long, Method> methods = new HashMap<Long, Method>();
	
	private Map<Long, String> unresolvedRefs = new HashMap<Long, String>();
	
	public Workspace() {		
	}

	public void addProject(Project project) {
		projects.put(project.getKey(), project);
		topLevelItems.add(project);
	}

	public void addLocation(Location location) {
		locations.put(location.getKey(), location);
		if (location.getProject() == null) {
			topLevelItems.add(location);
		}
	}

	public void addClazz(Clazz clazz) {		
		clazzes.put(clazz.getKey(), clazz);				
		if (clazzesByName.containsKey(clazz.getCanonicalName())) {
			clazzesByName.get(clazz.getCanonicalName()).add(clazz);
		}
		else {
			ArrayList<Clazz> arrayList = new ArrayList<Clazz>();
			arrayList.add(clazz);
			clazzesByName.put(clazz.getCanonicalName(), arrayList);
		}
	}

	public void addField(Field field) {
		fields.put(field.getKey(), field);
	}
	
	public void addMethod(Method method) {
		methods.put(method.getKey(), method);
	}

	public void addPackage(Package packageObject) {
		packages.put(packageObject.getKey(), packageObject);
	}

	public void addClassReference(Clazz clazz, String referenceQName) {
		unresolvedRefs.put(clazz.getKey(), referenceQName);
	}
	
	public void resolveReferences() {
		Collection<Clazz> allClazzes = clazzes.values();
		for (Clazz clazz : allClazzes) {
			clazz.clearReferences();
		}
		
		Set<Entry<Long, String>> entrySet = unresolvedRefs.entrySet();
		for (Entry<Long, String> entry : entrySet) {
			Clazz clazz = clazzes.get(entry.getKey());
			
			List<Clazz> referenceList = clazzesByName.get(entry.getValue());
			if (referenceList!=null && referenceList.size() > 0) {
				for (Clazz referencedClazz : referenceList) {
					clazz.addReference(referencedClazz);
				}	
			}			
		}
	}
	
	public List<JavaItem> getTopLevelItems() {
		return topLevelItems;
	}
}
