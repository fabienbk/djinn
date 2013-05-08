package com.scramcode.djinn.db.logic;

import com.scramcode.djinn.db.data.Clazz;
import com.scramcode.djinn.db.data.Field;
import com.scramcode.djinn.db.data.Location;
import com.scramcode.djinn.db.data.Method;
import com.scramcode.djinn.db.data.Package;
import com.scramcode.djinn.db.data.Project;

public class JavaItemVistor {

	private boolean memberVisitEnabled;
	private boolean classVisitEnabled = true;

	public void visitClazz(Clazz clazz) {		
	}

	public void visitField(Field field) {		
	}

	public void visitLocation(Location location) {	
	}

	public void visitPackage(Package packageObject) {						
	}

	public void visitMethod(Method method) {	
	}
	
	public void visitProject(Project project) {		
	}
	
	public boolean isMemberVisitEnabled() {		
		return memberVisitEnabled;
	}
	
	public void setMemberVisitEnabled(boolean memberVisitEnabled) {
		this.memberVisitEnabled = memberVisitEnabled;
	}

	public boolean isClassVisitEnabled() {
		return classVisitEnabled;
	}

	public void setClassVisitEnabled(boolean classVisitEnabled) {
		this.classVisitEnabled = classVisitEnabled;
	}

}
