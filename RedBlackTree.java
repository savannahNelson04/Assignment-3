public class RedBlackTree {
    private final Node NIL = new Node(null, "BLACK"); // Sentinel NIL node
    private Node root;

    private class Node {
        Product product;
        String color;
        Node left, right, parent;

        Node(Product product, String color) {
            this.product = product;
            this.color = color;
            this.left = NIL;
            this.right = NIL;
            this.parent = NIL;
        }
    }

    public RedBlackTree() {
        root = NIL;
    }

    public void insert(Product product) throws IllegalArgumentException {
        Node newNode = new Node(product, "RED");
        Node y = null;
        Node x = root;

        while (x != NIL) {
            y = x;
            if (product.getProductId().compareTo(x.product.getProductId()) == 0) {
                throw new IllegalArgumentException("Product already exists with ID: " + product.getProductId());
            } else if (product.getProductId().compareTo(x.product.getProductId()) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        newNode.parent = y;
        if (y == null) {
            root = newNode;
        } else if (product.getProductId().compareTo(y.product.getProductId()) < 0) {
            y.left = newNode;
        } else {
            y.right = newNode;
        }

        newNode.left = NIL;
        newNode.right = NIL;
        balanceInsert(newNode);
    }
    // RedBlackTree.java

    public Product search(String productId) {
        Node current = root;
        while (current != NIL) {
            int comparison = productId.compareTo(current.product.getProductId());
            if (comparison == 0) {
                return current.product;  // Found the product
            } else if (comparison < 0) {
                current = current.left;  // Go left
            } else {
                current = current.right; // Go right
            }
        }
        return null;  // Product not found
    }
    private void balanceInsert(Node k) {
        Node u;
        while (k.parent != null && k.parent.color.equals("RED")) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u != null && u.color.equals("RED")) {
                    u.color = "BLACK";
                    k.parent.color = "BLACK";
                    k.parent.parent.color = "RED";
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rotateRight(k);
                    }
                    k.parent.color = "BLACK";
                    if (k.parent.parent != null) {
                        k.parent.parent.color = "RED";
                        rotateLeft(k.parent.parent);
                    }
                }
            } else {
                u = k.parent.parent.right;  // uncle node
                if (u != null && u.color.equals("RED")) {
                    u.color = "BLACK";
                    k.parent.color = "BLACK";
                    k.parent.parent.color = "RED";
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        rotateLeft(k);
                    }
                    k.parent.color = "BLACK";
                    if (k.parent.parent != null) {
                        k.parent.parent.color = "RED";
                        rotateRight(k.parent.parent);
                    }
                }
            }
            if (k == root) break;
        }
        root.color = "BLACK";
    }

    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != NIL) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != NIL) y.right.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.right) x.parent.right = y;
        else x.parent.left = y;
        y.right = x;
        x.parent = y;
    }
}