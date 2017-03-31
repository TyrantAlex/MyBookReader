package com.example.administrator.mybookreader.base;

import com.example.administrator.mybookreader.utils.AppUtils;
import com.example.administrator.mybookreader.utils.FileUtils;

/**
 * 全局常量类
 * Created by Administrator on 2017/3/30 0030.
 */

public class Constant {

    public static final String ISNIGHT = "isNight";

    public static String PATH_DATA = FileUtils.createRootPath(AppUtils.getAppContext()) + "/cache";

    public static String PATH_TXT = PATH_DATA + "/book/";

    public static String PATH_EPUB = PATH_DATA + "/epub";

    public static String PATH_CHM = PATH_DATA + "/chm";

    public static final String SUFFIX_TXT = ".txt";
    public static final String SUFFIX_PDF = ".pdf";
    public static final String SUFFIX_EPUB = ".epub";
    public static final String SUFFIX_ZIP = ".zip";
    public static final String SUFFIX_CHM = ".chm";
}
