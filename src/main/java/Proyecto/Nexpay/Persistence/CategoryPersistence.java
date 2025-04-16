package Proyecto.Nexpay.Persistence;

import Proyecto.Nexpay.Model.Category;
import Proyecto.Nexpay.FileUtil.FileUtil;
import Proyecto.Nexpay.Datastructures.SimpleList;

import java.io.IOException;

public class CategoryPersistence {

    private static final String FILE_PATH = "src/main/resources/persistence/files/Categories.txt";
    private static final String XML_FILE_PATH = "src/main/resources/persistence/XML/Categories.data";
    private static final String BINARY_FILE_PATH = "src/main/resources/persistence/binary/BinaryCategories.data\\";
    private static CategoryPersistence instance;

    public static CategoryPersistence getInstance() {
        if (instance == null) {
            synchronized (CategoryPersistence.class) {
                if (instance == null) {
                    instance = new CategoryPersistence();
                }
            }
        }
        return instance;
    }

    public void saveAllCategories(SimpleList<Category> categories) {
        StringBuilder categoryText = new StringBuilder();

        for (Category category : categories) {
            categoryText.append(category.getId()).append("@@");
            categoryText.append(category.getName()).append("@@");
            categoryText.append(category.getDescription()).append("\n");
        }

        try {
            FileUtil.saveFile(FILE_PATH, categoryText.toString(), false);
        } catch (IOException e) {
            System.err.println("Error saving categories: " + e.getMessage());
        }
    }

    public SimpleList<Category> loadCategories() throws IOException {
        SimpleList<Category> categories = new SimpleList<>();

        for (String categoryText : FileUtil.readFile(FILE_PATH)) {
            String[] split = categoryText.split("@@");
            try {
                if (split.length == 2) {
                    Category category = new Category.Builder(split[0], split[1]).build();
                    categories.addLast(category);
                } else {
                    Category category = new Category.Builder(split[0], split[1])
                            .withDescription(split[2])
                            .build();
                    categories.addLast(category);
                }
            } catch (Exception e) {
                System.err.println("Error loading category from line: " + categoryText);
            }
        }

        return categories;
    }

    public SimpleList<Category> loadCategoriesFromXML() {
        try {
            return (SimpleList<Category>) FileUtil.loadSerializedXMLResource(XML_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error loading categories from XML file: " + e.getMessage());
            return new SimpleList<>();
        } catch (ClassCastException e) {
            System.err.println("Type conversion error loading categories from XML file: " + e.getMessage());
            return new SimpleList<>();
        }
    }

    public void saveCategoryToXML(Category category) {
        try {
            SimpleList<Category> categories = loadCategoriesFromXML();
            categories.addLast(category);
            FileUtil.saveSerializedXMLResource(XML_FILE_PATH, categories);
        } catch (IOException e) {
            System.out.println("Error saving category to XML: " + e.getMessage());
        }
    }

    public void saveCategoryToBinary(Category category) {
        try {
            SimpleList<Category> categories = loadCategoriesFromBinary();
            categories.addLast(category);
            FileUtil.saveSerializedResource(BINARY_FILE_PATH, categories);
            System.out.println("Category saved to binary: " + category);
        } catch (Exception e) {
            System.out.println("Error saving category to binary: " + e.getMessage());
        }
    }

    private SimpleList<Category> loadCategoriesFromBinary() {
        try {
            return (SimpleList<Category>) FileUtil.loadSerializedResource(BINARY_FILE_PATH);
        } catch (Exception e) {
            System.out.println("Error loading categories from binary: " + e.getMessage());
            return new SimpleList<>();
        }
    }
}


