package com.ngerancang.textconjunctionhtml2;

/**
 * Created by N Zul on 2/26/2019.
 */
public class TextUtil {

    public static String validTextToSendSubscribe(String rawText){
        String nt = rawText.replace(" ", "-");
        return nt.toLowerCase();
    }

    public static String obtainTitleFromUrl(String urlText){

        String arr[] = urlText.split("/");
        return arr[arr.length-1].replace("-", " ");
    }

    public static String getUrlforYoutubeThumbnail(String idYoutube) {

        String urlThumbnail = "https://i.ytimg.com/vi/#{id}/hqdefault.jpg";
        String newUrlThumbnail = urlThumbnail.replace("#{id}", idYoutube);
        return newUrlThumbnail;
    }

}
