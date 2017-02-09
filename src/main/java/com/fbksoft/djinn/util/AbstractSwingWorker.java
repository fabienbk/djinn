/*
 * Created on Mar 11, 2006
 * By Fabien Benoit - http://www.fbksoft.com
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
package com.fbksoft.djinn.util;

import javax.swing.SwingUtilities;

import com.fbksoft.djinn.ui.dialogs.ProgressDialog;


/**
 * Modified version of the classical SwingWorker (added a progress bar 
 * functionnality).
 */
public abstract class AbstractSwingWorker {
    
    private Object value;  // see getValue(), setValue()
        
    private ProgressDialog progressDialog = new ProgressDialog(null);
        
    private ThreadVar threadVar;
    
    /** 
     * Get the value produced by the worker thread, or null if it 
     * hasn't been constructed yet.
     */
    protected synchronized Object getValue() { 
        return value; 
    }
    
    /** 
     * Set the value produced by worker thread 
     */
    private synchronized void setValue(Object x) { 
        value = x; 
    }
    
    /** 
     * Compute the value to be returned by the <code>get</code> method. 
     */
    public abstract Object construct();

    /** 
     * Update the progress indicator (if displayed)
     */
    public void setProgressIndeterminate() {        
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    progressDialog.getProgressBar().setIndeterminate(true);       
                }
            });
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }                                
    }
    
    /** 
     * Update the progress indicator (if displayed)
     */
    public void updateProgress(final int percent) {        
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    progressDialog.getProgressBar().setValue(percent);       
                }
            });
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }                                
    }
    
    /** 
     * Update the progress indicator (if displayed)
     */
    public void updateMessage(final String message) {        
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    progressDialog.getDialogLabel().setText(message);        
                }
            });
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }                            
    }
    
    
    /**
     * Called on the event dispatching thread (not on the worker thread)
     * after the <code>construct</code> method has returned.
     */
    public void finished() {
        // Do nothing.
    }
    
    /**
     * A new method that interrupts the worker thread.  Call this method
     * to force the worker to stop what it's doing.
     */
    public void interrupt() {
        Thread t = threadVar.get();
        if (t != null) {
            t.interrupt();
        }
        threadVar.clear();
    }
    
    /**
     * Return the value created by the <code>construct</code> method.  
     * Returns null if either the constructing thread or the current
     * thread was interrupted before a value was produced.
     * 
     * @return the value created by the <code>construct</code> method
     */
    public Object get() {
        while (true) {  
            Thread t = threadVar.get();
            if (t == null) {
                return getValue();
            }
            try {
                t.join();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // propagate
                return null;
            }
        }
    }
    
    
    /**
     * Start a thread that will call the <code>construct</code> method
     * and then exit.
     */
    public AbstractSwingWorker() {
        this(false);
    }
    
    /**
     * Start a thread that will call the <code>construct</code> method
     * and then exit.
     * @param withProgressBar if true, display a dialog on top with a progress indicator
     */
    public AbstractSwingWorker(final boolean withProgressBar) {
                
        final Runnable doFinished = new Runnable() {
            public void run() { 
                finished();                 
            }                        
        };
        
        Runnable doConstruct = new Runnable() { 
            public void run() {
            	
            	progressDialog.setVisible(withProgressBar);
            	
                try {
                    setValue(construct());
                }
                finally {
                    threadVar.clear();
                }
                
                SwingUtilities.invokeLater(doFinished);                
                
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {progressDialog.setVisible(false);}
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
        };
        
        Thread t = new Thread(doConstruct);
        threadVar = new ThreadVar(t);
        
    }
    
    /**
     * Start the worker thread.
     */
    public void start() {
        Thread t = threadVar.get();
        if (t != null) {
            t.start();
        }
    }
    

    /** 
     * Class to maintain reference to current worker thread
     * under separate synchronization control.
     */
    private static class ThreadVar {
        private Thread thread;
        ThreadVar(Thread t) { thread = t; }
        synchronized Thread get() { return thread; }
        synchronized void clear() { thread = null; }
    }
}
