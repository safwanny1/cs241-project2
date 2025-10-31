// CS 241: Project 2
// Implements the BST library class to be used with BSTExample
// Created by Liz, Aleks, and Safwan

public class BST {
    
    // Inner Node class
    private class Node {
        private int key;
        private Node parent;
        private Node leftChild;
        private Node rightChild;
        
        // Default constructor - initializes key to -1 and all references to null
        public Node() {
            key = -1;
            parent = null;
            leftChild = null;
            rightChild = null;
        }
        
        // Constructor with key parameter
        public Node(int keyValue) {
            key = keyValue;
            parent = null;
            leftChild = null;
            rightChild = null;
        }
        
        // Constructor with all parameters
        public Node(int keyValue, Node parentNode, Node leftChildNode, Node rightChildNode) {
            key = keyValue;
            parent = parentNode;
            leftChild = leftChildNode;
            rightChild = rightChildNode;
        }
        
        // Getters
        public int getKey() {
            return key;
        }
        
        public Node getParent() {
            return parent;
        }
        
        public Node getLeftChild() {
            return leftChild;
        }
        
        public Node getRightChild() {
            return rightChild;
        }
        
        // Setters
        public void setKey(int newKey) {
            key = newKey;
        }
        
        public void setParent(Node newParent) {
            parent = newParent;
        }
        
        public void setLeftChild(Node newLeftChild) {
            leftChild = newLeftChild;
        }
        
        public void setRightChild(Node newRightChild) {
            rightChild = newRightChild;
        }
    }
    
    // BST fields
    private Node root;
    private int counter;
    
    // Default constructor - initializes root to null and counter to 0
    public BST() {
        root = null;
        counter = 0;
    }
    
    // Returns the number of elements in the tree
    public int size() {
        return counter;
    }
    
    // Inserts a new element into the tree
    public void insert(int elementValue) {
        // If tree is empty, create root node
        if (root == null) {
            root = new Node(elementValue);
            counter++;
            return;
        }
        
        Node current = root;
        Node parent = null;
        
        // Find the appropriate position for insertion
        while (current != null) {
            parent = current;
            
            // Element already exists - no duplicates allowed
            if (elementValue == current.getKey()) {
                System.out.println("Element is already in the tree!");
                return;
            }
            // Go left if element is smaller
            else if (elementValue < current.getKey()) {
                current = current.getLeftChild();
            }
            // Go right if element is larger
            else {
                current = current.getRightChild();
            }
        }
        
        // Create new node with parent reference
        Node newNode = new Node(elementValue);
        newNode.setParent(parent);
        
        // Insert as left or right child based on comparison
        if (elementValue < parent.getKey()) {
            parent.setLeftChild(newNode);
        } else {
            parent.setRightChild(newNode);
        }
        
        counter++; // Increment counter after successful insertion
    }
    
    // Removes the specified element from the tree
    public void delete(int elementValue) {
        Node nodeToDelete = search(elementValue);
        
        // Element not found in tree
        if (nodeToDelete == null) {
            System.out.println("Element not found!");
            return;
        }
        
        // Case 1: Node has no children (leaf node)
        if (nodeToDelete.getLeftChild() == null && nodeToDelete.getRightChild() == null) {
            // If it's the root node
            if (nodeToDelete == root) {
                root = null;
            }
            // Update parent's reference
            else if (nodeToDelete.getParent().getLeftChild() == nodeToDelete) {
                nodeToDelete.getParent().setLeftChild(null);
            } else {
                nodeToDelete.getParent().setRightChild(null);
            }
        }
        // Case 2: Node has only right child
        else if (nodeToDelete.getLeftChild() == null) {
            // If it's the root node
            if (nodeToDelete == root) {
                root = nodeToDelete.getRightChild();
                root.setParent(null);
            }
            // Update parent's reference and child's parent
            else {
                if (nodeToDelete.getParent().getLeftChild() == nodeToDelete) {
                    nodeToDelete.getParent().setLeftChild(nodeToDelete.getRightChild());
                } else {
                    nodeToDelete.getParent().setRightChild(nodeToDelete.getRightChild());
                }
                nodeToDelete.getRightChild().setParent(nodeToDelete.getParent());
            }
        }
        // Case 3: Node has only left child
        else if (nodeToDelete.getRightChild() == null) {
            // If it's the root node
            if (nodeToDelete == root) {
                root = nodeToDelete.getLeftChild();
                root.setParent(null);
            }
            // Update parent's reference and child's parent
            else {
                if (nodeToDelete.getParent().getLeftChild() == nodeToDelete) {
                    nodeToDelete.getParent().setLeftChild(nodeToDelete.getLeftChild());
                } else {
                    nodeToDelete.getParent().setRightChild(nodeToDelete.getLeftChild());
                }
                nodeToDelete.getLeftChild().setParent(nodeToDelete.getParent());
            }
        }
        // Case 4: Node has both children - use successor (minimum in right subtree)
        else {
            Node successor = findMin(nodeToDelete.getRightChild());
            int successorKey = successor.getKey();
            
            // Recursively delete the successor
            delete(successorKey);
            
            // Replace node's key with successor's key
            nodeToDelete.setKey(successorKey);
            
            return; // Recursive call already decremented counter
        }
        
        counter--; // Decrement counter after successful deletion
    }
    
    // Helper method to find a node with given key
    private Node search(int keyValue) {
        Node current = root;
        
        // Traverse the tree to find the node
        while (current != null) {
            if (keyValue == current.getKey()) {
                return current;
            } else if (keyValue < current.getKey()) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }
        
        return null; // Element not found
    }
    
    // Helper method to find minimum node in a subtree
    private Node findMin(Node startNode) {
        // Keep going left until we find the leftmost node
        while (startNode.getLeftChild() != null) {
            startNode = startNode.getLeftChild();
        }
        return startNode;
    }
    
    // Prints elements in preorder traversal (root, left, right)
    public void preorder() {
        preorderHelper(root);
        System.out.println(); // Print newline after traversal
    }
    
    // Recursive helper for preorder traversal
    private void preorderHelper(Node currentNode) {
        if (currentNode != null) {
            System.out.print(currentNode.getKey() + " "); // Visit root
            preorderHelper(currentNode.getLeftChild());     // Traverse left subtree
            preorderHelper(currentNode.getRightChild());    // Traverse right subtree
        }
    }
    
    // Prints elements in postorder traversal (left, right, root)
    public void postorder() {
        postorderHelper(root);
        System.out.println(); // Print newline after traversal
    }
    
    // Recursive helper for postorder traversal
    private void postorderHelper(Node currentNode) {
        if (currentNode != null) {
            postorderHelper(currentNode.getLeftChild());    // Traverse left subtree
            postorderHelper(currentNode.getRightChild());   // Traverse right subtree
            System.out.print(currentNode.getKey() + " ");  // Visit root
        }
    }
    
    // Prints elements in inorder traversal (left, root, right)
    public void inorder() {
        inorderHelper(root);
        System.out.println(); // Print newline after traversal
    }
    
    // Recursive helper for inorder traversal
    private void inorderHelper(Node currentNode) {
        if (currentNode != null) {
            inorderHelper(currentNode.getLeftChild());      // Traverse left subtree
            System.out.print(currentNode.getKey() + " ");  // Visit root
            inorderHelper(currentNode.getRightChild());     // Traverse right subtree
        }
    }
}
