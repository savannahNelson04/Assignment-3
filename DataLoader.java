import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class DataLoader {
    private static final Pattern CSV_PATTERN = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    private static final Pattern PRICE_CLEANER = Pattern.compile("[^0-9.]");

    public static void loadData(String filePath, RedBlackTree tree) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line using the regex pattern
                String[] fields = CSV_PATTERN.split(line);

                // Skip invalid lines
                if (fields.length < 4) {
                    continue;
                }

                // Extract and clean the fields
                String productId = fields[0].replace("\"", "").trim();
                String name = fields[1].replace("\"", "").trim();
                String category = fields[2].replace("\"", "").trim();
                String priceField = fields[3].replace("\"", "").trim();

                // Clean and parse the price
                priceField = PRICE_CLEANER.matcher(priceField).replaceAll("");
                double price = 0.0;
                if (!priceField.isEmpty()) {
                    try {
                        price = Double.parseDouble(priceField);
                    } catch (NumberFormatException e) {
                        continue; // Skip products with invalid prices
                    }
                }

                // Create the Product object
                Product product = new Product(productId, name, category, price);

                // Insert the product into the tree
                try {
                    tree.insert(product);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Data loaded successfully");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}