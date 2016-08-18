package com.hitotech.neighbour.http;

import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.data.Constant;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * 获取网络框架类
 */
public class BuildApi {

    private static Retrofit retrofit;

    public static APIService getAPIService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL) //设置Base的访问路径
                    .addConverterFactory(GsonConverterFactory.create()) //设置默认的解析库：Gson
                    .client(NeighbourApplication.defaultOkHttpClient())
                    .build();
        }
        return retrofit.create(APIService.class);
    }
}
