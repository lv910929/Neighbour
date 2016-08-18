package com.hitotech.neighbour.data;

import android.os.Environment;

/**
 * Created by Lv on 2016/6/1.
 */
public class NeighbourConfig {

    public static final String SDCARD_ROOT_PATH = Environment.getExternalStorageDirectory().getPath();

    public static final String SAVE_PATH_IN_SDCARD = "/neighbour/download/";

    public static final String SAVE_IMAGE_PATH = "/neighbour/Images/";

    public static final String PLATFORM_TYPE = "Android";

    public static final String ALIAS = "neighbour";

    public static final String PACKAGE_NAME = "com.hitotech.neighbour";
}
