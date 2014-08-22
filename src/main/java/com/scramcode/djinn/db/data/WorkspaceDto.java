package com.scramcode.djinn.db.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class WorkspaceDto {
	
	public List<JavaItem> topLevelItems = new ArrayList<JavaItem>();
	public Map<Long, Project> projects = new HashMap<Long, Project>();
	public Map<Long, Location> locations = new HashMap<Long, Location>();

}
