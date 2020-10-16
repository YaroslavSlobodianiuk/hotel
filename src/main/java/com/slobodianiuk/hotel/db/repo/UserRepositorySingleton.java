package com.slobodianiuk.hotel.db.repo;

/**
 * Singleton
 *
 * @author Yarosalv Slobodianiuk
 */

public class UserRepositorySingleton extends UserRepository {


    private static UserRepository instance;

    private UserRepositorySingleton() {}

    public static UserRepository getInstance() {
        UserRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (UserRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance  = localInstance = new UserRepository();
                }
            }
        }
        return localInstance;
    }
}