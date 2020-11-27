public class Photo {
	private String path;
	private LinkedList<String> tags;
	
	public Photo(String path, LinkedList<String> tags) {
		this.path = path; //unique
		this.tags = tags; //points to photos
	}
	
	// Return the path (full file name) of the photo. A photo is uniquely identified by its path.
	public String getPath() {
		return path;
	}
	
	// Return all tags associated with the photo
	public LinkedList<String> getTags() {
		return tags;
	}
}
