# WidgetConjunctionHtml
<h2>Widget convertion library from HTML</h2> 

<h3>Gradle</h3>
<div class="highlight highlight-source-groovy">
  <pre>implementation 'com.ngerancang.textconjunctionhtml2:WidgetConjunctionHtml:0.1.0'</pre></div>

<p>This is how to use it</p>
<div class="highlight highlight-source-java">
<pre>
private LinearLayout content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content = findViewById(R.id.content);
        // initialize html conjunction
        String articleHtml = "your_html_article";
        String[] tagForWrapping = {"figure", "img", "h3", "iframe"};
        WidgetConjunction conjunction1 = new WidgetConjunction.Builder(this)
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
                        Log.d(TAG, "callBackForYoutubeView: YoutubeID " + ytID);
                    }
                })
                .setAlienTag(tagForWrapping)
                .setTagCaption("figcaption")
                .build();
        conjunction1.breakContent(articleHtml);
    }</pre></div>
