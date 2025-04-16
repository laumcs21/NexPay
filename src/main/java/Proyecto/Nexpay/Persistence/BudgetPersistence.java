package Proyecto.Nexpay.Persistence;

import java.io.File;
import java.io.IOException;

import Proyecto.Nexpay.Model.Budget;
import Proyecto.Nexpay.FileUtil.FileUtil;
import Proyecto.Nexpay.Datastructures.SimpleList;

public class BudgetPersistence {

    private static final String FILE_PATH = "src/main/resources/persistence/files/Budgets.txt";
    private static BudgetPersistence instance;

    public static BudgetPersistence getInstance() {
        if (instance == null) {
            synchronized (BudgetPersistence.class) {
                if (instance == null) {
                    instance = new BudgetPersistence();
                }
            }
        }
        return instance;
    }

    public void saveAllBudgets(SimpleList<Budget> budgets) {
        StringBuilder budgetText = new StringBuilder();

        for (Budget budget : budgets) {
            budgetText.append(budget.getUserId()).append("@@");
            budgetText.append(budget.getId()).append("@@");
            budgetText.append(budget.getName()).append("@@");
            budgetText.append(budget.getAmount()).append("@@");
            budgetText.append(budget.getCategoryId()).append("@@");
            budgetText.append(budget.getSpentAmount()).append("\n");
        }

        try {
            FileUtil.saveFile(FILE_PATH, budgetText.toString(), false);
        } catch (IOException e) {
            System.err.println("Error saving budgets: " + e.getMessage());
        }
    }

    public SimpleList<Budget> loadBudgets() throws IOException {
        SimpleList<Budget> budgets = new SimpleList<>();
        File file = new File(FILE_PATH);

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating budget file");
        }

        for (String budgetText : FileUtil.readFile(FILE_PATH)) {
            String[] split = budgetText.split("@@");
            if (split.length < 6) {
                System.err.println("Invalid or incomplete line: " + budgetText);
                continue;
            }

            try {
                Budget budget = new Budget(
                        split[0],
                        split[1],
                        split[2],
                        Double.parseDouble(split[3]),
                        split[4]);

                budget.setSpentAmount(Double.parseDouble(split[5]));

                budgets.addLast(budget);

            } catch (Exception e) {
                System.err.println("Error processing line: " + budgetText);
            }
        }

        return budgets;
    }
}


