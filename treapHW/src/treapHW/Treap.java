package treapHW;
import java.util.Random;
import java.util.Stack;

//BENJAMIN GRIEPP
//I PLEDGE MY HONOR I HAVE I ABIDED BY STEVENS HONOR SYSTEM
/**
 * @author Benjamin Griepp
 *
 * @param <E>
 */
public class Treap<E extends Comparable<E>> {
	
	private Random priorityGenerator;
	private Node<E> root;
	
	//Constructor
	public Treap() {
		//Generates random priority without seed
		this.priorityGenerator = new Random();
	}
	
	/**
	 * @param seed: seed for random priority generator
	 */
	public Treap(long seed) {
		//Generates random priority using seed
		this.priorityGenerator = new Random(seed);
	}
	
	
	private static class Node<E>{
		public E data;
		public int priority;
		public Node<E> left;
		public Node<E> right;
		
		public Node(E data, int priority) {
			if(data == null) {
				throw new IllegalArgumentException("Node: data null");
			}
			
			this.data = data;
			this.priority = priority;
			this.left = null;
			this.right = null;
		}
		
		Node<E> rotateRight(){
			//Perform a right rotation
			
			Node<E> t1 = this.left;
			Node<E> t2 = this.left.right;
			//Assigns left node and left node's right node to temporary variables
			
			t1.right = this;
			//Reassigns the left node's right node (saved as t2) to current node
			
			this.left = t2;
			//Sets the node left of current to temporary node t2, completing the right rotation
			
			return t1;
		}
		
		Node<E> rotateLeft(){
			//Perform a left rotation
			
			Node<E> t1 = this.right.left;
			Node<E> t2 = this.right;
			//Assigns right node and right node's left node to temporary variables
			
			t2.left = this;
			//Reassigns the right node's left node (saved as t1) to current node
			
			this.right = t1;
			//Sets the node right of current to temporary node t1, completing the left rotation
			
			return t2;
		}
	}
	
	boolean add(E key) {
		//Passes add with a random priority if not given one
		return add(key, this.priorityGenerator.nextInt(1000));
	}
	
	/**
	 * @param key: data of added node
	 * @param priority: determines height the node will sit at
	 * @return true if node added, false if node fails to be added
	 */
	boolean add(E key, int priority) {
		Stack<Node<E>>stack = new Stack<Node<E>>();
		
		//Assigns root if empty
		if(this.root == null) {
			this.root = new Node<E>(key, priority);
			return true;
		}
		
		Node<E>current = this.root;
		
		if(find(key)) {
			//Key already exists, returns false
			return false;
		}
		
		//Construct BTree Iteratively, storing data in stack for use in reheap()
		while(true) {
			if(current == null) {
				//Reached leaf of tree
				break;
			}
			if(key.compareTo(current.data) > 0) {
				//data is greater than current, continue right of node
				stack.push(current);
				current = current.right;
			}
			else {
				//data is less than current, continue left of node
				stack.push(current);
				current = current.left;
			}
		}
		Node<E> newNode = new Node<E>(key, priority);
		//Creates new node
		
		//Finds parent node using stack
		if(key.compareTo(stack.peek().data) > 0) {
			stack.peek().right = newNode;
		}
		else {
			stack.peek().left = newNode;
		}

		reheap(stack, newNode);
		//Call reheap to heap tree
		
		return true;
	}
	
	/**
	 * @param stack: stack from add to make references to parent nodes
	 * @param newNode: node added to tree
	 */
	void reheap(Stack<Node<E>> stack, Node<E> newNode) {
		//Performs rotations to heap tree given stack in order of priority
		
		while(newNode.priority > stack.peek().priority) {
			if(stack.peek().left == newNode) {
				Node<E> temp = stack.peek();
				if(stack.size() == 1) {
					this.root = temp.rotateRight();
					break;
				}
				else {
					stack.pop();
					if(stack.peek().left == temp) {
						stack.peek().left = temp.rotateRight();
					}
					else {
						stack.peek().right = temp.rotateRight();
					}
				}
			}
			else {
				Node<E> temp = stack.peek();
				if(stack.size() == 1) {
					this.root = temp.rotateLeft();
					break;
				}
				else {
					stack.pop();
					if(stack.peek().left == temp) {
						stack.peek().left = temp.rotateLeft();
					}
					else {
						stack.peek().right = temp.rotateLeft();
					}
				}
			}
		}
	}
	
	/**
	 * @param key data of the desired node to delete
	 * @return true if node is found and deleted, false if not found
	 */
	boolean delete(E key) {
		if(find(key) == false) {
			return false;
		}
		Node<E> parent = null;
		Node<E> tempParent = null;
		Node<E> current = root;
		boolean left = false;
		
		
		//find item using find
		while(current.data != key) {
			if(find(current.left, key)) {
				parent = current;
				current = current.left;
				left = true;
			}
			else {
				parent = current;
				current = current.right;
				left = false;
			}
		}
		
		if(current == root) {
			if(current.left == null && current.right == null) {
			}
			else if(current.left==null) {
				tempParent = current.right;
	            root = current.rotateLeft();
	            parent = tempParent;
	            left = true;
	        }else if(current.right==null) {
	            //swap left
	            tempParent = current.left;
	            root = current.rotateRight();
	            parent = tempParent;
	            left = false;
	        }else {
	            //swap highest priority
	            if(current.left.priority<current.right.priority) {
	                tempParent = current.right;
	                root = current.rotateLeft();
	                parent = tempParent;
	                left = true;
	            }else {
	                tempParent = current.left;
	                root = current.rotateRight();
	                parent = tempParent;
	                left = false;
	            }
	        }
			}
		
		//rotate node to bottom
		
		while(!(current.left == null && current.right == null)) {
			
			if(current.left == null) {
				tempParent = current.right;
				if(left) {
					parent.left = current.rotateLeft();
				}
				else {
					parent.right = current.rotateLeft();
				}
					parent = tempParent;
				left = true;
			}
			else if(current.right == null) {
				tempParent = current.left;
				if(left) {
					parent.left = current.rotateRight();
				}
				else {
					parent.right = current.rotateRight();
				}
				parent = tempParent;
				left = false;
			}
			else {
				if(current.left.priority < current.right.priority) {
					tempParent = current.right;
					if(left) {
						parent.left = current.rotateLeft();
					}
					else {
						parent.right = current.rotateLeft();
					}
					parent = tempParent;
					left = true;
				}
				else {
					tempParent = current.left;
					if(left) {
						parent.left = current.rotateRight();
					}
					else {
						parent.right = current.rotateRight();
					}
					parent = tempParent;
					left = false;
				}
			}
		}
		//Remove node
		current = null;
		
		//Adjust parent
		if(left && parent != null) {
			parent.left = null;
		}
		else if (parent != null){
			parent.right = null;
		}
		else {
			this.root = null;
		}
		return true;
	}
	private boolean find(Node<E> root, E key) {
		if(root == null) {
			return false;
		}
		if(root.data == key) {
			return true;
		}
		return find(root.left, key) || find(root.right, key);
	}
	public boolean find(E key) {
		return find(root, key);
	}
	public String toString() {
		return toString(root, 0);
	}
	
	public String toString(Node<E> current, int level) {
		StringBuilder s = new StringBuilder();
		for (int i=0; i<level;i++) {
			s.append(" ");
		}
		if (current==null) {
			s.append("null\n");
		} else {
			s.append("(key=" + current.data + ", priority = " + current.priority + ")\n");
			s.append(toString(current.left, level+1));
			s.append(toString(current.right,level+1));
		}
		return s.toString();
	}

	public static void main(String[] args) {

	}
}
