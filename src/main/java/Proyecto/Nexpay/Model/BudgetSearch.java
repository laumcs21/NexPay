package Proyecto.Nexpay.Model;

import Proyecto.Nexpay.Datastructures.SimpleList;

import java.util.Optional;

public class BudgetSearch {

    private static Nexpay nexpay;

    static {
        nexpay = Nexpay.getInstance();
    }

    public static Budget findBudgetById(String id) {
        return findBudgetById(nexpay.getBudgets(), id, 0);
    }

    public static Optional<Budget> findBudgetByCategoryCode(String categoryCode) {
        return findBudgetByCategoryCode(nexpay.getBudgets(), categoryCode, 0);
    }

    private static Budget findBudgetById(SimpleList<Budget> budgets, String id, int index) {
        if (index >= budgets.getSize()) {
            return null;
        }

        Budget budget = budgets.get(index);
        if (budget.getId().equals(id)) {
            return budget;
        }

        return findBudgetById(budgets, id, index + 1);
    }

    private static Optional<Budget> findBudgetByCategoryCode(SimpleList<Budget> budgets, String categoryCode, int index) {
        if (index >= budgets.getSize()) {
            return Optional.empty();
        }

        Budget budget = budgets.get(index);
        if (budget.getCategoryId().equals(categoryCode)) {
            return Optional.of(budget);
        }

        return findBudgetByCategoryCode(budgets, categoryCode, index + 1);
    }
}


