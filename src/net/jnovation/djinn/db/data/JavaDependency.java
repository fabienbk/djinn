package net.jnovation.djinn.db.data;

public class JavaDependency {

	private JavaItem sourceItem;
	private JavaItem destinationItem;
		
	public JavaDependency(JavaItem sourceItem, JavaItem destinationItem) {
		super();
		this.sourceItem = sourceItem;
		this.destinationItem = destinationItem;
	}
	
	public JavaItem getSourceItem() {
		return sourceItem;
	}
	public void setSourceItem(JavaItem sourceItem) {
		this.sourceItem = sourceItem;
	}
	public JavaItem getDestinationItem() {
		return destinationItem;
	}
	public void setDestinationItem(JavaItem destinationItem) {
		this.destinationItem = destinationItem;
	}
	
}
