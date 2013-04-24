package com.scramcode.djinn.db.data;

public class JavaDependency {

	private AbstractJavaItem sourceItem;
	private AbstractJavaItem destinationItem;
		
	public JavaDependency(AbstractJavaItem sourceItem, AbstractJavaItem destinationItem) {
		super();
		this.sourceItem = sourceItem;
		this.destinationItem = destinationItem;
	}
	
	public AbstractJavaItem getSourceItem() {
		return sourceItem;
	}
	public void setSourceItem(AbstractJavaItem sourceItem) {
		this.sourceItem = sourceItem;
	}
	public AbstractJavaItem getDestinationItem() {
		return destinationItem;
	}
	public void setDestinationItem(AbstractJavaItem destinationItem) {
		this.destinationItem = destinationItem;
	}
	
}
