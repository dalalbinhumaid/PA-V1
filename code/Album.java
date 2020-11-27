public class Album {
	private String name;
	private String condition;
	private PhotoManager manager;

	public Album(String name, String condition, PhotoManager manager) {
		this.name = name;
		this.condition = condition;
		this.manager = manager;

	}

	// Return all photos that satisfy the album condition
	public LinkedList<Photo> getPhotos() {
		if (condition.equals("")) {
			return manager.getAllPhotos();
		}

		LinkedList<Photo> photos = new LinkedList<>(); // all photos that satisfy the condition
		BST<LinkedList<Photo>> p = manager.getPhotos(); // inverted index of tags

		String[] conditions = condition.split(" AND "); // array of wanted tags

		for (int i = 0; i < conditions.length; i++) { // for each condition look for its photos
			if (!p.findKey(conditions[i])) // if key does not exist skip
				continue;

			LinkedList<Photo> add = p.retrieve(); // retrieve all its photos
			add.findFirst();
			if (photos.empty()) { // if the list is empty immediately insert them, non need for comparisons
				while (!add.lastNull()) {
					photos.insert(add.retrieve());
					add.findNext();
				}
				continue;
			}
			while (!add.lastNull()) {
				photos.findFirst();
				while (!photos.last()) {
					if (photos.retrieve().getPath().equals(add.retrieve().getPath()))
						break;
					else
						photos.findNext();
				}
				if (!photos.retrieve().getPath().equals(add.retrieve().getPath()))
					photos.insert(add.retrieve());

				else {
					photos.findNext();
					add.findNext();
				}
			}
		}
		return photos;
	}

	// Return the number of tag comparisons used to find all photos of the album
	public int getNbComps() {
		if(condition.equals(""))
			return 0;
		
		BST<LinkedList<Photo>> invertedIndex = manager.getPhotos();
		String[] conditions = condition.split(" AND ");
		int comparisons = 0;

		for (int i = 0; i < conditions.length; i++)
			comparisons += invertedIndex.count(conditions[i]);

		return comparisons;
	}

	// Return the name of the album
	public String getName() {
		return name;
	}

	// Return the condition associated with the album
	public String getCondition() {
		return condition;
	}

	// Return the manager
	public PhotoManager getManager() {
		return manager;
	}
}
