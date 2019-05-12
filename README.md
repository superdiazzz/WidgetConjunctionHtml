# WidgetConjunctionHtml
<h2>Widget convertion library from HTML</h2> 

<h3>Gradle</h3>
<div class="highlight highlight-source-groovy">
  <pre>implementation 'com.ngerancang.textconjunctionhtml2:WidgetConjunctionHtml:0.1.0'</pre></div>

<p>This is how to use it</p>
<div class="highlight highlight-source-java">
<pre>
String articleHtml = "" //---> Your input article from HTML
String[] tagForWrapping = {"figure", "h3"}  //--> tag which is not wrapping by <p></p>
WidgetConjunction conjunction = new WidgetConjunction(this, tagForWrapping,
                new WidgetConjunction.OnTextConjunctionListener() {
                    @Override
                    public void callBackForOpenTextView(TextView tv) {
                        content.addView(tv);
                    }
                    @Override
                    public void callBackForOpenImageView(TextView cap, OpenImageView im) {
                        content.addView(im);
                        //content.addView(cap);
                    }
                    @Override
                    public void callBackForYoutubeView(OpenYoutubeView openYoutubeView, String ytID) {
                        content.addView(openYoutubeView);
                        Log.d(TAG, "callBackForYoutubeView: YoutubeID " + ytID);
                    }
                });
        conjunction.breakContent(articleHtml);
        </pre></div>
