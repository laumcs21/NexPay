package Proyecto.Nexpay.Model;

import java.io.IOException;
import java.io.Serializable;

import Proyecto.Nexpay.Persistence.UserPersistence;
import Proyecto.Nexpay.Persistence.AccountPersistence;
import Proyecto.Nexpay.Persistence.TransactionPersistence;
import Proyecto.Nexpay.Persistence.CategoryPersistence;
import Proyecto.Nexpay.Persistence.BudgetPersistence;
import Proyecto.Nexpay.Datastructures.SimpleList;
import Proyecto.Nexpay.Datastructures.DoubleLinkedList;

public class Nexpay implements Serializable {

    private static final long serialVersionUID = 1L;
    private static Nexpay instance;

    private SimpleList<User> users;
    private SimpleList<Account> accounts;
    private DoubleLinkedList<Transaction> transactions;
    private SimpleList<Category> categories;
    private SimpleList<Budget> budgets;

    private UserCRUD userCRUD;
    private AccountCRUD accountCRUD;
    private TransactionCRUD transactionCRUD;
    private CategoryCRUD categoryCRUD;
    private BudgetCRUD budgetCRUD;

    private Thread backupThread;

    private Nexpay() {
        this.users = new SimpleList<>();
        this.accounts = new SimpleList<>();
        this.transactions = new DoubleLinkedList<>();
        this.budgets = new SimpleList<>();
        this.categories = new SimpleList<>();

        this.userCRUD = new UserCRUD(this);
        this.accountCRUD = new AccountCRUD(this);
        this.transactionCRUD = new TransactionCRUD(this);
        this.categoryCRUD = new CategoryCRUD(this);
        this.budgetCRUD = new BudgetCRUD(this);
    }

    public static Nexpay getInstance() {
        if (instance == null) {
            synchronized (Nexpay.class) {
                if (instance == null) {
                    instance = new Nexpay();
                    instance.loadUserData();
                    instance.loadAccountData();
                    instance.loadTransactionData();
                    instance.loadCategoryData();
                    instance.loadBudgetData();
                }
            }
        }
        return instance;
    }

    public SimpleList<User> getUsers() {
        return users;
    }

    public void setUsers(SimpleList<User> users) {
        this.users = users;
    }

    public SimpleList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(SimpleList<Account> accounts) {
        this.accounts = accounts;
    }

    public DoubleLinkedList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(DoubleLinkedList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public SimpleList<Category> getCategories() {
        return categories;
    }

    public void setCategories(SimpleList<Category> categories) {
        this.categories = categories;
    }

    public SimpleList<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(SimpleList<Budget> budgets) {
        this.budgets = budgets;
    }

    public UserCRUD getUserCRUD() {
        return userCRUD;
    }

    public void setUserCRUD(UserCRUD userCRUD) {
        this.userCRUD = userCRUD;
    }

    public AccountCRUD getAccountCRUD() {
        return accountCRUD;
    }

    public void setAccountCRUD(AccountCRUD accountCRUD) {
        this.accountCRUD = accountCRUD;
    }

    public TransactionCRUD getTransactionCRUD() {
        return transactionCRUD;
    }

    public void setTransactionCRUD(TransactionCRUD transactionCRUD) {
        this.transactionCRUD = transactionCRUD;
    }

    public CategoryCRUD getCategoryCRUD() {
        return categoryCRUD;
    }

    public void setCategoryCRUD(CategoryCRUD categoryCRUD) {
        this.categoryCRUD = categoryCRUD;
    }

    public BudgetCRUD getBudgetCRUD() {
        return budgetCRUD;
    }

    public void setBudgetCRUD(BudgetCRUD budgetCRUD) {
        this.budgetCRUD = budgetCRUD;
    }

    private void loadUserData() {
        UserPersistence persistence = UserPersistence.getInstance();
        try {
            for (User u : persistence.loadUsers()) {
                this.users.addLast(u);
            }
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
        }
    }

    private void loadAccountData() {
        AccountPersistence persistence = AccountPersistence.getInstance();
        try {
            for (Account a : persistence.loadAccounts()) {
                this.accounts.addLast(a);
            }
        } catch (IOException e) {
            System.err.println("Error loading accounts from file: " + e.getMessage());
        }
    }

    private void loadTransactionData() {
        TransactionPersistence persistence = TransactionPersistence.getInstance();
        try {
            for (Transaction t : persistence.loadTransactions()) {
                this.transactions.addLast(t);
            }
        } catch (IOException e) {
            System.err.println("Error loading transactions from file: " + e.getMessage());
        }
    }

    private void loadCategoryData() {
        CategoryPersistence persistence = CategoryPersistence.getInstance();
        try {
            for (Category c : persistence.loadCategories()) {
                this.categories.addLast(c);
            }
        } catch (IOException e) {
            System.err.println("Error loading categories from file: " + e.getMessage());
        }
    }

    private void loadBudgetData() {
        BudgetPersistence persistence = BudgetPersistence.getInstance();
        try {
            for (Budget b : persistence.loadBudgets()) {
                this.budgets.addLast(b);
            }
        } catch (IOException e) {
            System.err.println("Error loading budgets from file: " + e.getMessage());
        }
    }
}


