package com.slobodianiuk.hotel.db.repo;

/**
 * Singleton
 *
 * @author Yarosalv Slobodianiuk
 */

public class CategoryRepositorySingleton extends CategoryRepository {

    private static CategoryRepository instance;

    private CategoryRepositorySingleton() {}

    public static CategoryRepository getInstance() {
        CategoryRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (UserOrderRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance  = localInstance = new CategoryRepository();
                }
            }
        }
        return localInstance;
    }
}