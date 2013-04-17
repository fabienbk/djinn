/*
 * Created on Feb 8, 2006
 * Created by Fabien Benoit
 * 
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
package com.scramcode.djinn.control.wizards;

import java.util.Hashtable;

import com.scramcode.djinn.control.Application;
import com.scramcode.djinn.ui.wizards.ProjectWizardPanel;



public class WizardCollection {
    
    private Hashtable<Class, Wizard> wizards; 
    
    public WizardCollection(Application application) {
        
        this.wizards = new Hashtable<Class, Wizard>();
        
        // Add Project wizard to collection
        wizards.put(ProjectWizardPanel.class, new ProjectWizard(application));
    }

    /**
     * Runs a wizard.
     * @param wizardClass The type of wizard to run.
     */
    public void runWizard(Class wizardClass) {
        Wizard wizard = wizards.get(wizardClass);
        wizard.show();
    }

}
