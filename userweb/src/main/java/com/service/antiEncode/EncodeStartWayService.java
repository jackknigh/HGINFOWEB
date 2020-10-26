package com.service.antiEncode;

public interface EncodeStartWayService {
    void startway(int start, int total, int batchcCount);
    void getAddr(int start, int total, int batchcCount);

    void getAddrByBD(int start, int total, int batchcCount);
    void getAddrBDD(int start, int total, int batchcCount);
}
