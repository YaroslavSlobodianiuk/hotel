package com.slobodianiuk.hotel.db.repo;

/**
 * Singleton
 *
 * @author Yarosalv Slobodianiuk
 */

public class TransactionsRepositorySingleton extends TransactionsRepository {

    private static volatile TransactionsRepository instance;

    private TransactionsRepositorySingleton() {}

    public static TransactionsRepository getInstance() {
        TransactionsRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (TransactionsRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance  = localInstance = new TransactionsRepository();
                }
            }
        }
        return localInstance;
    }
}