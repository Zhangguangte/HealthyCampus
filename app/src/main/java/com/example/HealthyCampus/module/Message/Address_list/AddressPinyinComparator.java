package com.example.HealthyCampus.module.Message.Address_list;

import com.example.HealthyCampus.common.network.vo.AddressListVo;

import java.util.Comparator;

public class AddressPinyinComparator implements Comparator<AddressListVo> {
    public int compare(AddressListVo o1, AddressListVo o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
