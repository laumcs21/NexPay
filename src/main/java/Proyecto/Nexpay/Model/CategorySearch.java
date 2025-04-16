package Proyecto.Nexpay.Model;

import Proyecto.Nexpay.Datastructures.SimpleList;

public class CategorySearch {

    private static Nexpay nexpay;

    static {
        nexpay = Nexpay.getInstance();
    }

    public static Category findCategoryById(String id) {
        return findCategoryById(nexpay.getCategories(), id, 0);
    }

    private static Category findCategoryById(SimpleList<Category> categories, String id, int index) {
        if (index >= categories.getSize()) {
            return null;
        }

        Category category = categories.get(index);
        if (category.getId().equals(id)) {
            return category;
        }

        return findCategoryById(categories, id, index + 1);
    }
}


