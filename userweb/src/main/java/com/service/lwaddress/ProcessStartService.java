package com.service.lwaddress;

public interface ProcessStartService {
    void startway(int start, int total, int batchcCount);

    void compare();

    void compareSelf();

    void update();
}
