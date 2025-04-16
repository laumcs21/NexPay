package Proyecto.Nexpay.Model;

import Proyecto.Nexpay.Datastructures.SimpleList;

import java.io.Serializable;
import java.util.Random;

public class CategoryCodeGenerator implements Serializable {

    public static String generateUniqueCode(int length, SimpleList<Category> existingCategories) {
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
        } while (codeExists(code, existingCategories));

        return code;
    }

    private static boolean codeExists(String code, SimpleList<Category> existingCategories) {
        for (Category category : existingCategories) {
            if (category.getId().equals(code)) {
                return true;
            }
        }
        return false;
    }
}


