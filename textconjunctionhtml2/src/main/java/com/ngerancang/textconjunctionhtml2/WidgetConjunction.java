package com.ngerancang.textconjunctionhtml2;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngerancang.textconjunctionhtml2.model.ContentArticle;
import com.ngerancang.textconjunctionhtml2.widget.OpenImageView;
import com.ngerancang.textconjunctionhtml2.widget.OpenRelativeLayout;
import com.ngerancang.textconjunctionhtml2.widget.OpenYoutubeView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class WidgetConjunction {

    private static final String TAG = "WidgetConjunction";

    private Activity activity;
    private OpenRelativeLayout openRelativeLayout;
    private OnTextConjunctionListener onTextConjunctionListener;
    private String[] alienTag;
    private String tagCaption;

    public interface OnTextConjunctionListener {

        void callBackForOpenTextView(TextView tv);

        void  callBackForOpenImageView(TextView cap, OpenImageView im);

        void callBackForYoutubeView(OpenYoutubeView openYoutubeView, String ytID);
    }

    // initialize
    public WidgetConjunction(Activity activity, String[] alienTag, String tagCaption, OpenRelativeLayout orLayout, OnTextConjunctionListener listener){
        this.activity = activity;
        this.onTextConjunctionListener = listener;
        this.alienTag = alienTag;
        this.tagCaption = tagCaption;
        this.openRelativeLayout = orLayout;
    }

    public void breakContent(String htmlContent){

        //convert content to list
        List<ContentArticle> ls = parseContent(htmlContent);

        for (ContentArticle ca : ls) {

            int codeView = resolvingViewContent(ca.getP());
            switch (codeView){
                case 0:
                    TextView tv = new TextView(activity);
                    tv.setPadding(25, 5, 20, 10);
                    onTextConjunctionListener.callBackForOpenTextView(renderTextContent(tv, ca.getP(), activity));
                    break;
                case 1:
                    String urlImage = resolveUrlImage(ca.getP());
                    String captionImage = resolveCaptionImage(ca.getP(), tagCaption);
                    OpenImageView im = new OpenImageView(activity, urlImage, openRelativeLayout);

                    TextView txCaption = new TextView(activity);
                    if(captionImage != null){
                        txCaption.setText(captionImage);
                        txCaption.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        txCaption.setTextSize(14f);
                        txCaption.setTypeface(null, Typeface.BOLD);
                        txCaption.setGravity(Gravity.CENTER);
                    }
                    onTextConjunctionListener.callBackForOpenImageView(txCaption, im);
                    break;
                case 2:
                    // youtube
                    String ytThumbnail = getYoutubeTumbnail(ca.getP());
                    OpenYoutubeView yt = new OpenYoutubeView(activity, ytThumbnail);
                    String urlYt = resolveUrlIframe(ca.getP());
                    onTextConjunctionListener.callBackForYoutubeView(yt, urlYt);

                    break;
                case 3:
                    break;
            }

        }

    }

    private static TextView renderTextContent(TextView contentNews, String p, Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            CharSequence sequence = Html.fromHtml(p, Html.FROM_HTML_MODE_COMPACT);
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(sequence);
            URLSpan[] urls = stringBuilder.getSpans(0, sequence.length(), URLSpan.class);
            for(URLSpan span : urls){
                makeLinkClickable(stringBuilder, span, activity);
            }
            contentNews.setText(trimTrailingWhitespace(stringBuilder));
            contentNews.setMovementMethod(LinkMovementMethod.getInstance());

        }else {
            // attempt 4
            CharSequence sequence = Html.fromHtml(p);
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(sequence);
            URLSpan[] urls = stringBuilder.getSpans(0, sequence.length(), URLSpan.class);
            for(URLSpan span : urls){
                makeLinkClickable(stringBuilder, span, activity);
            }
            contentNews.setText(trimTrailingWhitespace(stringBuilder));
            contentNews.setMovementMethod(LinkMovementMethod.getInstance());
        }

        return contentNews;
    }

    private static void makeLinkClickable(SpannableStringBuilder strBuilder, URLSpan span, Activity activity)
    {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                //Intent intent = new Intent(activity, ReadWebActivity.class);
                //intent.putExtra(ReadWebActivity.EXTRA_URL_NEWS, span.getURL());
                //activity.startActivity(intent);
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    private String resolveCaptionImage(String p, String tagCaption) {
        Document doc = Jsoup.parse(p);
        Element capE = doc.select(tagCaption).first(); //figcaption
        if(capE != null){
            return capE.text();
        }else {
            return null;
        }
    }

    private String resolveUrlImage(String p) {
        Document doc = Jsoup.parse(p);
        Element imgE = doc.select("img").first();
        return imgE.absUrl("src");
    }

    public static String resolveUrlIframe(@NonNull String p) {
        Document doc = Jsoup.parse(p);
        Element ytE = doc.select("iframe").first();
        return ytE.absUrl("src");
    }

    private int resolvingViewContent(String article) {

        Document doc = Jsoup.parse(article);
        Elements elements = doc.select("img");
        if(!elements.isEmpty()){
            return 1; // image
        }

        Elements elements2 = doc.select("iframe");
        if(!elements2.isEmpty()){
            // if video (lagi)
            Element el = elements2.first();
            String src = el.absUrl("src");
            if(src.contains("youtube")){
                return 2;
            }
            else if(src.contains("instagram") || src.contains("twitter.com")){
                return 3;
            }
        }

        return 0; // default text
    }

    private static String getYoutubeTumbnail(@NonNull String p) {
        Document doc = Jsoup.parse(p);
        Element ytE = doc.select("iframe").first();
        String url = ytE.absUrl("src");
        String idYoutube = url.substring(url.lastIndexOf('/') + 1, url.length());
        return TextUtil.getUrlforYoutubeThumbnail(idYoutube);
    }

    /**
     * wrap all alien tag to paragraph <p></p>
     * Sample: if <figure></figure> then type 'figure'
     * @param htmlContent
     * @return
     */
    private List<ContentArticle> parseContent(String htmlContent) {

        List<ContentArticle> contents = new ArrayList<>();
        Document doc = Jsoup.parse(htmlContent);

        for(int i=0; i<alienTag.length; i++){
            Elements elAlien = doc.select(alienTag[i]);
            for (int k =0; k<elAlien.size(); k++){
                Element element = elAlien.get(k);
                element.wrap("<p></p>");
            }
        }


        Log.d(TAG, "parseContent: " + doc.outerHtml());
        
        Elements elP = doc.select("p");
        for (Element el : elP){
            String v = el.text();
            String p = el.outerHtml();
            ContentArticle ca = new ContentArticle();
            ca.setValue(v);
            ca.setP(p);
            contents.add(ca);
        }
        return contents;

    }

    /** Trims trailing whitespace. Removes any of these characters:
     * 0009, HORIZONTAL TABULATION
     * 000A, LINE FEED
     * 000B, VERTICAL TABULATION
     * 000C, FORM FEED
     * 000D, CARRIAGE RETURN
     * 001C, FILE SEPARATOR
     * 001D, GROUP SEPARATOR
     * 001E, RECORD SEPARATOR
     * 001F, UNIT SEPARATOR
     * @return "" if source is null, otherwise string with all trailing whitespace removed
     */
    private static CharSequence trimTrailingWhitespace(CharSequence source) {

        if(source == null)
            return "";

        int i = source.length();
        // loop back to the first non-whitespace character
        while(--i >= 0 && Character.isWhitespace(source.charAt(i))) {
        }
        return source.subSequence(0, i+1);
    }


    public static class Builder {

        private String tagCaption;
        private Activity activity;
        private String[] alienTag;
        private OnTextConjunctionListener listener;
        private OpenRelativeLayout layout;

        public Builder(Activity activity, OpenRelativeLayout layout){
            this.activity = activity;
            this.layout = layout;
        }

        /**
         * Determine your tag Caption type, jika bentuknya <tagCaption></tagCaption> maka ditulis "tagCaption"
         * @param tagCaption jenis tag untuk caption gambar (bisa jadi berbeda-beda)
         * @return
         */
        public Builder setTagCaption(String tagCaption) {
            this.tagCaption = tagCaption;
            return this;
        }

        /**
         * Tags which missed from observed from library, jika ada tag yang beda sendiri dan ingin ditangkap untuk
         * ditampilakn kebentuk Widgetnya
         * @param alienTag tag yang Asing dan tidak tertangkap oleh crawler library ini
         * @return
         */
        public Builder setAlienTag(String[] alienTag){
            this.alienTag = alienTag;
            return this;
        }

        public Builder setOnTextConjunctionListener(OnTextConjunctionListener listener){
            this.listener = listener;
            return this;
        }

        public WidgetConjunction build(){
            return new WidgetConjunction(activity, alienTag, tagCaption, layout, listener);
        }

    }

}
