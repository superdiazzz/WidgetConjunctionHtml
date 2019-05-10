package com.ngerancang.textconjunctionhtml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngerancang.textconjunctionhtml2.WidgetConjunction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize html conjunction

        String articleHtml = "<p>JUDUL ATAS</p>\n" +
                "<p>lorem lorem ipsum ipsum bla bla bla</p>\n" +
                "<figure>Bagian figure adalah bagian dilain text html</figure>\n" +
                "<p>paragraf lagi lagi lorem lorem ipsum balabala</p>\n" +
                "<id>NAMA JOEL JOEL JOEL</id>\n" +
                "<p>ini last paragraph ya ya ya ya jisjisijs </p>";

        String[] tagForWrapping = {"figure", "id"};
        WidgetConjunction conjunction = new WidgetConjunction(this, tagForWrapping,
                new WidgetConjunction.OnTextConjunctionListener() {
                    @Override
                    public void callBackForOpenTextView(TextView tv) {

                    }

                    @Override
                    public void callBackForOpenImageView(TextView cap, ImageView im) {

                    }
                });
        conjunction.breakContent(articleHtml);

    }
}
