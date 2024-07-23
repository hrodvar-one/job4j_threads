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
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        Optional<Account> fromOpt = getById(fromId);
        Optional<Account> toOpt = getById(toId);

        if (fromOpt.isPresent() && toOpt.isPresent()) {
            Account from = fromOpt.get();
            Account to = toOpt.get();

            if (from.amount() >= amount) {
                Account updatedFrom = new Account(from.id(), from.amount() - amount);
                Account updatedTo = new Account(to.id(), to.amount() + amount);
                accounts.replace(fromId, updatedFrom);
                accounts.replace(toId, updatedTo);
                result = true;
            }
        }

        return result;
    }
}
