
public class PhotoManager {
	LinkedList<Photo> manager;
	private BST<LinkedList<Photo>> invertedIndex;
	private LinkedList<String> allTags;

	// Constructor
	public PhotoManager() {
		manager = new LinkedList<>();
		invertedIndex = new BST<>();
		allTags = new LinkedList<>();
	}

	public void addPhoto(Photo p) {
		LinkedList<String> tagsOfP = p.getTags();

		if(tagsOfP.empty()) {
			System.out.println("Can't be added");
			return;
		}
		
		// check if the path is unique
		if (uniquePath(p)) {
			manager.insert(p);
		} else {
			System.out.println("Can't be added");
			return;
		}

		tagsOfP.findFirst();

		while (!tagsOfP.lastNull()) {
			if (invertedIndex.findKey(tagsOfP.retrieve())) { // if the tag exists update it
				invertedIndex.retrieve().insert(p);
			} else {
				LinkedList<Photo> pList = new LinkedList<>();
				pList.insert(p);
				invertedIndex.insert(tagsOfP.retrieve(), pList); // if tag is new insert it
				allTags.insert(tagsOfP.retrieve());
			}
			tagsOfP.findNext();
		}
	}

	public void deletePhoto(String path) {
			
		LinkedList<String> tags = null;
		boolean found = false;

		// find the photo and delete it from the list
		manager.findFirst();
		while (!manager.lastNull()) {
			if (manager.retrieve().getPath().equals(path)) {
				tags = manager.retrieve().getTags(); // retrieve all the photo's tags
				manager.remove();
				found = true; // found the photo
				break;
			}
			manager.findNext();
		}
		if (!found) {
			System.out.println("Unable to delete a photo that does not exist.");
			return;
		}
		
		tags.findFirst();
		while (!tags.lastNull()) {
			if (invertedIndex.findKey(tags.retrieve())) { // search the tree
				LinkedList<Photo> p = invertedIndex.retrieve();
				p.findFirst();
				while (!p.lastNull()) {
					if (p.retrieve().getPath().equals(path)) {
						p.remove();
						continue;
					}
					p.findNext();
				}
				if (invertedIndex.retrieve().empty()) {
					invertedIndex.removeKey(tags.retrieve());
					deleteTag(tags.retrieve());
				}
			}
			tags.findNext();
		}
	}

	public BST<LinkedList<Photo>> getPhotos() {
		return invertedIndex;
	}

	public LinkedList<String> getAllTags() {
		return allTags;
	}

	public LinkedList<Photo> getAllPhotos() {
		return manager;
	}

	private boolean uniquePath(Photo p) {
		if (manager.empty())
			return true; // empty list, no other path

		manager.findFirst();
		while (!manager.last()) {// until last node
			if (manager.retrieve().getPath().equals(p.getPath())) // if other photo exists return false
				return false;
			manager.findNext();
		}
		if (manager.retrieve().getPath().equals(p.getPath())) // if other photo exists return false
			return false;

		return true;
	}

	private void deleteTag(String tag) {
		allTags.findFirst();
		while (!allTags.lastNull()) {
			if (allTags.retrieve().equals(tag)) {
				allTags.remove();
				return;
			}
			allTags.findNext();
		}
	}
}
