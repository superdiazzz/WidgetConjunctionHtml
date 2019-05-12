# WidgetConjunctionHtml
<h2>Widget convertion library from HTML</h2> 
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
        conjunction.breakContent(articleHtml);</pre></div>
