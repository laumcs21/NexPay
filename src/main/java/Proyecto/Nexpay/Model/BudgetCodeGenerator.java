package Proyecto.Nexpay.Model;

import Proyecto.Nexpay.Datastructures.SimpleList;
import java.io.Serializable;
import java.util.Random;

public class BudgetCodeGenerator implements Serializable {

    public static String generateUniqueCode(int length, SimpleList<Budget> existingBudgets) {
        String characters = "abcdefghijklmnñopqrstuvwxyz0123456789";
        Random random = new Random();
        String code;

        do {
            StringBuilder codeBuilder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(characters.length());
                codeBuilder.append(characters.charAt(index));
            }
            code = codeBuilder.toString();
        } while (codeExists(code, existingBudgets));

        return code;
    }

    private static boolean codeExists(String code, SimpleList<Budget> existingBudgets) {
        for (Budget budget : existingBudgets) {
            if (budget.getId().equals(code)) {
                return true;
            }
        }
        return false;
    }
}



