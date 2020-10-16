package com.slobodianiuk.hotel.db.repo;

/**
 * Singleton
 *
 * @author Yarosalv Slobodianiuk
 */

public class ApartmentRepositorySingleton extends ApartmentRepository {

    private static ApartmentRepository instance;

    private ApartmentRepositorySingleton() {}

    public static ApartmentRepository getInstance() {
        ApartmentRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (ApartmentRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance  = localInstance = new ApartmentRepository();
                }
            }
        }
        return localInstance;
    }
}