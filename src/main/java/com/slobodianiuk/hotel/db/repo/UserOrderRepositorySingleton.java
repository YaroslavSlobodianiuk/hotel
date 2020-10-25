package com.slobodianiuk.hotel.db.repo;

/**
 * Singleton
 *
 * @author Yarosalv Slobodianiuk
 */

public class UserOrderRepositorySingleton extends UserOrderRepository {


    private static volatile UserOrderRepository instance;

    private UserOrderRepositorySingleton() {}

    public static UserOrderRepository getInstance() {
        UserOrderRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (UserOrderRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance  = localInstance = new UserOrderRepository();
                }
            }
        }
        return localInstance;
    }
}