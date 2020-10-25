package com.slobodianiuk.hotel.db.repo;

/**
 * Singleton
 *
 * @author Yarosalv Slobodianiuk
 */

public class RoomCapacityRepositorySingleton extends RoomCapacityRepository {

    private static volatile RoomCapacityRepository instance;

    private RoomCapacityRepositorySingleton() {}

    public static RoomCapacityRepository getInstance() {
        RoomCapacityRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (RoomCapacityRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance  = localInstance = new RoomCapacityRepository();
                }
            }
        }
        return localInstance;
    }
}