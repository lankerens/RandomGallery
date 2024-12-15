package com.lankerens.gallery.randomgallery.helper;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;

/*
 *@title RandomGalleryHelper
 *@description
 *@author zhus
 *@version 1.0
 *@create 2024-12-16 1:31
 */
@Slf4j
public class RandomGalleryHelper {

    // https://t.alcy.cc/ycy/?json&quantity=1000
    static final String bdUrl = "https://t.alcy.cc/bd/?json&quantity=1000";
    static final String bdPath = "src/main/resources/static/agc/bd/";

    static final String mpUrl = "https://t.alcy.cc/mp/?json&quantity=1000";
    static final String mpPath = "src/main/resources/static/agc/mp/";

    static final String txUrl = "https://t.alcy.cc/tx/?json&quantity=1000";
    static final String txPath = "src/main/resources/static/agc/tx/";


    public static void main(String[] args) {
        log.info("-------------------start spider [first].-------------------");
        createDir(bdPath);
        spiderImgResources(bdUrl, bdPath);
        log.warn("file size={}", FileSizeHelper.formatSize(FileSizeHelper.calculateFolderSize(new File(bdPath))));
        log.info("-------------------finished spider [first].-------------------");

        log.info("-------------------start spider [sec].-------------------");
        createDir(mpPath);
        spiderImgResources(mpUrl, mpPath);
        log.warn("file size={}", FileSizeHelper.formatSize(FileSizeHelper.calculateFolderSize(new File(mpPath))));
        log.info("-------------------finished spider [sec].-------------------");

        log.info("-------------------start spider [third].-------------------");
        createDir(txPath);
        spiderImgResources(txUrl, txPath);
        log.warn("file size={}", FileSizeHelper.formatSize(FileSizeHelper.calculateFolderSize(new File(txPath))));
        log.info("-------------------finished spider [third].-------------------");
    }

    private static void createDir(String basePath){
        try {
            File dir = new File(basePath);
            if (!dir.exists()) {
                log.warn("dir not exist, so create now, dir = {}", basePath);
                dir.mkdirs();
            }
        } catch ( Exception e) {
            log.error("e = ", e);
        }

    }


    private static  void spiderImgResources(String spiderUrl, String storePath) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(spiderUrl).get().build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            String respJson = body.string();
            String[] split = respJson.split("\n");
            log.info("size={}", split.length);
            int index = 1;
            for (String url : split) {
                long startTime = System.currentTimeMillis();
                log.info("deal index = {}, start time={} ms", index++, startTime);
                Request imgReq = new Request.Builder().url(url).get().build();
                String imgName = getFileNameFromUrl(url);
                File temp = new File(storePath + imgName);
                if (temp.exists()) {
                    log.warn("file is exist.. fileName = {}",  imgName);
                    continue;
                }
                try (Response img = client.newCall(imgReq).execute();
                     FileOutputStream fos = new FileOutputStream(temp)) {
                    ResponseBody responseBody = img.body();
                    fos.write(responseBody.bytes());
                    fos.flush();
                } catch (Exception e) {
                    log.error("e=", e);
                }

                log.info("deal finish, cost time= {} ms", System.currentTimeMillis() - startTime);
            }
        } catch (Exception e) {
            log.error("e=", e);
        }
    }


    // 从 URL 中提取文件名
    private static String getFileNameFromUrl(String imageUrl) {
        // 获取 URL 中的最后一部分作为文件名
        return imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
    }

}
