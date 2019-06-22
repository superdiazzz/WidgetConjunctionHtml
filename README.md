# WidgetConjunctionHtml
<h2>Widget convertion library from HTML</h2> 

<h3>Gradle</h3>
<div class="highlight highlight-source-groovy">
  <pre>implementation 'com.ngerancang.textconjunctionhtml2:WidgetConjunctionHtml:0.3.0'</pre></div>

<p>This is how to use it</p>
<div class="highlight highlight-source-java">
<pre>
private LinearLayout content;
    private OpenRelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content = findViewById(R.id.content);
        layout = findViewById(R.id.openRelativeLayout);
        // initialize html conjunction
        String articleHtml = "your_html_article";
        String[] tagForWrapping = {"figure", "img", "h3", "iframe"};
        WidgetConjunction conjunction1 = new WidgetConjunction.Builder(this, layout)
                .setOnTextConjunctionListener(new WidgetConjunction.OnTextConjunctionListener() {
                    @Override
                    public void callBackForOpenTextView(TextView tv) {
                        content.addView(tv);
                    }
                    @Override
                    public void callBackForOpenImageView(TextView cap, OpenImageView im) {
                        content.addView(im);
                        //content.addView(cap); //if you need caption below of image, but make sure your caption already wrap insid
                    }
                    @Override
                    public void callBackForYoutubeView(OpenYoutubeView openYoutubeView, String ytID) {
                        content.addView(openYoutubeView);
                        openYoutubeView.setOnClickListener(v -> {
                            Toast.makeText(MainActivity.this, "ID url yt : "+ytID, Toast.LENGTH_SHORT).show();
                        });
                        //Log.d(TAG, "callBackForYoutubeView: YoutubeID " + ytID);
                    }
                })
                .setAlienTag(tagForWrapping)
                .setTagCaption("figcaption")
                .build();
        conjunction1.breakContent(articleHtml);
    }</pre></div>
    <pre><h3>Result</h3>
    <img src="https://media.giphy.com/media/IhOPNj4VHDon7wamf7/giphy.gif" alt="gambar">
    </pre>
    <h3>In your layout activity</h3>

```xml<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <LinearLayout
            android:id="@+id/content"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"/>
    </ScrollView>
    <com.ngerancang.textconjunctionhtml2.widget.OpenRelativeLayout
        android:id="@+id/openRelativeLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="gone"/>
</RelativeLayout>```

   
