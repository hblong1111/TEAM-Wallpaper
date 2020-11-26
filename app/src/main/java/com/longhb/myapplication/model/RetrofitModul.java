package com.longhb.myapplication.model;

import com.longhb.myapplication.network.RetrofitService;
import com.longhb.myapplication.utils.Conts;

public class RetrofitModul {
    private static RetrofitService INSTANCE;

    private RetrofitModul() {
    }

    public static RetrofitService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = RetrofitClient.getINSTANCE(Conts.BASE_URL).create(RetrofitService.class);
        }
        return INSTANCE;
    }
}
