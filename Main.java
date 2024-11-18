import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Adds data from file onto a new RedBlackTree
        RedBlackTree tree = new RedBlackTree();
        DataLoader.loadData("amazon-product-data.csv", tree);

        //3 search queries
        Product item1 = tree.search("4c69b61db1fc16e7013b43fc926e502d");
        System.out.println(item1);
        Product item2 = tree.search("02d5d3748dc98cf913b93dc8b5c05c8b");
        System.out.println(item2);
        Product item3 = tree.search("2d584ce3af7e45ddaf7cf6b437ee2dd4");
        System.out.println(item3);

        //2 insertions

        System.out.println();

        //new insert
        try {
            Product newProduct = new Product("6ab64ce3af7e45ddaf7cf3a837ee2dd4", "Fidget Toy", "Toys", 20.99);
            tree.insert(newProduct);
            System.out.println("Inserted new product: " + newProduct);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        //duplicate insert
        try {
            Product duplicateProduct = new Product("4c69b61db1fc16e7013b43fc926e502d", "Duplicate Toy", "Toy", 100.99);
            tree.insert(duplicateProduct);
            System.out.println("Inserted new product: " + duplicateProduct);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

    }
}
