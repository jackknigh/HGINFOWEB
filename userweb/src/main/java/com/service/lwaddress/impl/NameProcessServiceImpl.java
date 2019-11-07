package com.service.lwaddress.impl;

import com.service.lwaddress.NameProcessService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NameProcessServiceImpl implements NameProcessService {

    @Override
    public Boolean nameLikedProcess(String[] a1, String[] a2) {
        /*判断被传入字符是否为无星星字符*/
        for(int i=0;i<a2.length;i++){
             if(a2[i].equals("*")){
                 return false;
             };
        }

            if (a1.length == a2.length) {
                for (int i = 0; i <=a1.length; i++) {
                    if(i==a1.length) {
                        return true;
                    }
                        if (a1[i].equals(a2[i])|| a1[i].equals("*") ) {
                            continue;
                        }else {
                            return false;
                        }
                        }

                }else{
                return  false;
            }

        return false;
        }

    @Override
    public Boolean a2nameLikedProcess(String[] a1, String[] a2) {
        int index=0;
        /*判断被传入字符是否为无星星字符*/
        for(int i=0;i<a2.length;i++){
            if(a2[i].equals("*")){
                index=1;

            };
        }
        if(index==1){
            if (a1.length == a2.length) {
                for (int i = 0; i <= a1.length; i++) {
                    if(i==a1.length) {
                        return true;
                    }
                    if (a1[i].equals(a2[i]) || a2[i] .equals("*") ) {
                        continue;
                    }else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Boolean a3nameLikedProcess(String[] a1, String[] a2) {
        if(a1[0]==a2[0]){
            return true;
        }
        else{
            return false;
        }
    }

    public  Boolean isContainChinese(String  a1){
        String regex = "先生|小姐|女士";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(a1);
        while(matcher.find()){
            return true;
        }
        return false;
    }

}

