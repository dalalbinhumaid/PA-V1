class BSTNode<T> {
	public String key;
	public T data;
	public BSTNode<T> left, right;

	public BSTNode(String key, T data) {
		this.key = key;
		this.data = data;
		left = right = null;
	}
}

public class BST<T> {

	private BSTNode<T> root, current;

	public BST() {
		current = root = null;
	}

	public void clear() {
		current = root = null;
	}

	public boolean empty() {
		return root == null;
	}

	public boolean full() {
		return false;
	}

	public T retrieve() {
		return current.data;
	}

	public void update(T val) {
		current.data = val;
	}

	public boolean findKey(String k) {

		BSTNode<T> p = root;
		while (p != null) {
			current = p;
			if (k.equals(p.key)) {
				return true;
			} else if (k.compareTo(p.key) < 0) {
				p = p.left;
			} else {
				p = p.right;
			}
		}
		return false;
	}

	public boolean insert(String k, T val) {
		if (root == null) {
			current = root = new BSTNode<T>(k, val);
			return true;
		}

		BSTNode<T> p = current;
		if (findKey(k)) {
			current = p;
			return false;
		}

		BSTNode<T> tmp = new BSTNode<T>(k, val);
		if (k.compareTo(p.key) < 0) {
			current.left = tmp;
		} else {
			current.right = tmp;
		}
		current = tmp;
		return true;
	}

	public boolean removeKey(String k) {
		// Search for k
		String k1 = k;
		BSTNode<T> p = root;
		BSTNode<T> q = null; // Parent of p
		while (p != null) {

			if (k1.compareTo(p.key) < 0) {
				q = p;
				p = p.left;
			} else if (k1.compareTo(p.key) > 0) {
				q = p;
				p = p.right;
			} else { // Found the key

				// Check the three cases
				if ((p.left != null) && (p.right != null)) { // Case 3: two
																// children

					// Search for the min in the right subtree
					BSTNode<T> min = p.right;
					q = p;
					while (min.left != null) {
						q = min;
						min = min.left;
					}
					p.key = min.key;
					p.data = min.data;
					k1 = min.key;
					p = min;
					// Now fall back to either case 1 or 2
				}

				// The subtree rooted at p will change here
				if (p.left != null) { // One child
					p = p.left;
				} else { // One or no children
					p = p.right;
				}

				if (q == null) { // No parent for p, root must change
					root = p;
				} else {
					if (k1.compareTo(q.key) < 0) {
						q.left = p;
					} else {
						q.right = p;
					}
				}
				current = root;
				return true;

			}
		}

		return false; // Not found
	}

	public int countComp(String tag) {
		int numOfComp = 0;
		BSTNode<T> p = root;
		while (p != null) {
			numOfComp++;
			current = p;
			if (tag.equals(p.key)) 
				return numOfComp;
			else if (tag.compareTo(p.key) < 0) 
				p = p.left;
			 else 
				p = p.right;
		}
		return numOfComp;
	}

	public int count(String tag) {
		int numOfComp = 0;
		BSTNode<T> p = root;
		return countComp(tag, p, numOfComp);
	}

	private int countComp(String tag, BSTNode<T> p, int count) {
		if (p == null)
			return count;
		if (tag.equals(p.key))
			return ++count;
		if (tag.compareTo(p.key) < 0)
			return countComp(tag, p.left, ++count);
		return countComp(tag, p.right, ++count);
	}

	@SuppressWarnings("unchecked")
	public void inorder() {
		if (empty())
			System.out.println("Empty Tree.");
		else
			inorder((BSTNode<LinkedList<Photo>>) root);
	}

	// recursively traverse the BST
	private void inorder(BSTNode<LinkedList<Photo>> root) {
		if (root != null) {
			inorder(root.left);
			System.out.println("KEY:" + root.key + " ");
			System.out.println();
			display(root.data);
			inorder(root.right);
		}
	}

	public void display(LinkedList<Photo> l) {
		if (l.empty())
			System.out.println("No List");
		else {
			l.findFirst();
			while (!l.last()) {
				System.out.println(l.retrieve().getPath() + " ");
				l.findNext();
			}
			System.out.println(l.retrieve().getPath() + " ");
			System.out.println("______________________________");
		}
	}
}
