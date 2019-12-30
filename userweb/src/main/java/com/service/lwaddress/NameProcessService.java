package com.service.lwaddress;

public interface NameProcessService {
    Boolean phoneLikedProcess(String[] a1, String[] a2);
    Boolean nameLikedProcess(String[] a1, String[] a2);
    Boolean a2nameLikedProcess(String[] a1, String[] a2);
    Boolean a3nameLikedProcess(String[] a1, String[] a2);
    Boolean isContainChinese(String a1);

}
