package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean result = false;
        if (!accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            result = true;
        }
        return result;
    }

    public synchronized boolean update(Account account) {
        boolean result = false;
        if (accounts.containsKey(account.id())) {
            accounts.replace(account.id(), account);
            result = true;
        }
        return result;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        if (accounts.containsKey(fromId) && accounts.containsKey(toId)) {
            Account from = accounts.get(fromId);
            Account to = accounts.get(toId);
            if (from.amount() >= amount) {
                Account updatedFrom = new Account(from.id(), from.amount() - amount);
                Account updatedTo = new Account(to.id(), to.amount() + amount);
                accounts.put(fromId, updatedFrom);
                accounts.put(toId, updatedTo);
                result = true;
            }
        }
        return result;
    }
}
