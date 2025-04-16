package Proyecto.Nexpay.Model;

import java.io.Serializable;
import java.util.Optional;

import Proyecto.Nexpay.Datastructures.SimpleList;
import Proyecto.Nexpay.Persistence.CategoryPersistence;

public class CategoryCRUD implements CRUD<Category>, Serializable {

    private Nexpay nexpay;
    private CategoryPersistence persistence = new CategoryPersistence();

    public CategoryCRUD(Nexpay nexpay) {
        this.nexpay = nexpay;
    }

    public Optional<Category> findCategoryById(String id) {
        return recursiveSearch(nexpay.getCategories(), id, 0);
    }

    private Optional<Category> recursiveSearch(SimpleList<Category> categories, String id, int index) {
        if (index >= categories.getSize()) {
            return Optional.empty();
        }

        Category category = categories.get(index);
        if (category.getId().equals(id)) {
            return Optional.of(category);
        }

        return recursiveSearch(categories, id, index + 1);
    }

    @Override
    public void update(Category category) {
        delete(category.getId());
        nexpay.getCategories().addLast(category);
        persistence.saveAllCategories(nexpay.getCategories());
    }

    @Override
    public Category create(Category category) {
        if (findCategoryById(category.getId()).isPresent()) {
            throw new IllegalArgumentException("The category is already registered.");
        }
        nexpay.getCategories().addLast(category);
        persistence.saveAllCategories(nexpay.getCategories());
        return category;
    }

    @Override
    public void delete(String id) {
        Category category = read(id);
        nexpay.getCategories().remove(category);
        persistence.saveAllCategories(nexpay.getCategories());
    }

    @Override
    public Category read(String id) {
        return findCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("The category is not registered."));
    }
}


