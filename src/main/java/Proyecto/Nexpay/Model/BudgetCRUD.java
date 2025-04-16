package Proyecto.Nexpay.Model;

import java.util.Optional;
import Proyecto.Nexpay.Datastructures.SimpleList;
import Proyecto.Nexpay.Persistence.BudgetPersistence;

public class BudgetCRUD implements CRUD<Budget> {

    private Nexpay nexp;
    private BudgetPersistence persistence = new BudgetPersistence();

    public BudgetCRUD(Nexpay nexp) {
        this.nexp = nexp;
    }

    public Optional<Budget> findBudgetById(String id) {
        return findBudgetRecursively(nexp.getBudgets(), id, 0);
    }

    private Optional<Budget> findBudgetRecursively(SimpleList<Budget> budgets, String id, int index) {
        if (index >= budgets.getSize()) {
            return Optional.empty();
        }

        Budget budget = budgets.get(index);
        if (budget.getId().equals(id)) {
            return Optional.of(budget);
        }

        return findBudgetRecursively(budgets, id, index + 1);
    }

    @Override
    public void update(Budget budget) {
        delete(budget.getId());
        nexp.getBudgets().addLast(budget);
        persistence.saveAllBudgets(nexp.getBudgets());
    }

    @Override
    public Budget create(Budget budget) {
        if (findBudgetById(budget.getId()).isPresent()) {
            throw new IllegalArgumentException("The budget is already registered.");
        }
        nexp.getBudgets().addLast(budget);
        persistence.saveAllBudgets(nexp.getBudgets());
        return budget;
    }

    @Override
    public void delete(String id) {
        Budget budget = read(id);
        nexp.getBudgets().remove(budget);
        persistence.saveAllBudgets(nexp.getBudgets());
    }

    @Override
    public Budget read(String id) {
        return findBudgetById(id)
                .orElseThrow(() -> new IllegalArgumentException("The budget is not registered."));
    }
}



