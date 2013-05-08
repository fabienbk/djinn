/*
 * Created on Feb 8, 2006
 * By Fabien Benoit - http://www.scramcode.com 
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.scramcode.djinn.db.data;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.scramcode.djinn.db.logic.JavaItemVistor;
import com.scramcode.djinn.ui.i18n.Images;

public class Project extends AbstractJavaItem {

	public static final ImageIcon ICON = Images.getIcon("Project.graph.icon");

	private String projectName;
	private File directory;

	private List<Location> locations = new ArrayList<Location>();

	public Project(String name, File directory) {
		this.projectName = name;
		this.directory = directory;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String getLabel() {
		return projectName;
	}

	@Override
	public ImageIcon getImage() {
		return ICON;
	}

	@Override
	public Color getColor() {
		return Color.BLUE;
	}

	public File getDirectory() {
		return directory;
	}

	public List<Location> getLocations() {
		return locations;
	}
	
	@Override
	public void accept(JavaItemVistor javaItemVistor) {
		javaItemVistor.visitProject(this);
		for (Location location : locations) {
			location.accept(javaItemVistor);
		}
	}
	
	@Override
	public boolean isContainedBy(JavaItem destinationObject) {	
		return false;
	}

}
