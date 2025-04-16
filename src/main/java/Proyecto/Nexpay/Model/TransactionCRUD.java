package Proyecto.Nexpay.Model;

import java.io.Serializable;
import java.util.Optional;

import Proyecto.Nexpay.Persistence.TransactionPersistence;
import Proyecto.Nexpay.Datastructures.DoubleLinkedList;

public class TransactionCRUD implements CRUD<Transaction>, Serializable {

    private Nexpay nexpay;
    private TransactionPersistence persistence = new TransactionPersistence();

    public TransactionCRUD(Nexpay nexpay) {
        this.nexpay = nexpay;
    }

    public Optional<Transaction> findTransactionById(String id) {
        return findTransactionRecursively(nexpay.getTransactions(), id, 0);
    }

    private Optional<Transaction> findTransactionRecursively(DoubleLinkedList<Transaction> transactions, String id, int index) {
        if (index >= transactions.getSize()) {
            return Optional.empty();
        }

        Transaction transaction = transactions.get(index);
        if (transaction.getId().equals(id)) {
            return Optional.of(transaction);
        }

        return findTransactionRecursively(transactions, id, index + 1);
    }

    @Override
    public void update(Transaction transaction) {
        delete(transaction.getId());
        nexpay.getTransactions().addLast(transaction);
        persistence.saveAllTransactions(nexpay.getTransactions());
    }

    @Override
    public Transaction create(Transaction transaction) {
        if (findTransactionById(transaction.getId()).isPresent()) {
            throw new IllegalArgumentException("The transaction is already registered.");
        }
        nexpay.getTransactions().addLast(transaction);
        persistence.saveAllTransactions(nexpay.getTransactions());
        return transaction;
    }

    @Override
    public void delete(String id) {
        Transaction transaction = read(id);
        nexpay.getTransactions().remove(transaction);
        persistence.saveAllTransactions(nexpay.getTransactions());
    }

    @Override
    public Transaction read(String id) {
        return findTransactionById(id)
                .orElseThrow(() -> new IllegalArgumentException("The transaction is not registered."));
    }
}


