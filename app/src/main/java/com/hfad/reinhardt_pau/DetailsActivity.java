package com.hfad.reinhardt_pau;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String articleString = getIntent().getStringExtra("article");

        try {
            JSONObject articleObject = new JSONObject(articleString);
            displayArticle(articleObject);
        } catch (JSONException e) {}

    }

    public void displayArticle(JSONObject article) {
        try {
            TextView source = (TextView)findViewById(R.id.source);
            source.setText(article.getJSONObject("source").getString("name"));

            TextView author = (TextView)findViewById(R.id.author);
            author.setText(article.getString("author"));

            TextView title = (TextView)findViewById(R.id.title);
            title.setText(article.getString("title"));

            TextView description = (TextView)findViewById(R.id.description);
            description.setText(article.getString("description"));

            TextView url = (TextView)findViewById(R.id.url);
            url.setText(article.getString("url"));

            TextView urlToImage = (TextView)findViewById(R.id.urlToImage);
            urlToImage.setText(article.getString("urlToImage"));

            TextView publishedAt = (TextView)findViewById(R.id.publishedAt);
            publishedAt.setText(article.getString("publishedAt"));

            TextView content = (TextView)findViewById(R.id.content);
            content.setText(article.getString("content"));

            new DownloadImageTask((ImageView) findViewById(R.id.image)).execute(article.getString("urlToImage"));
        }catch (JSONException e) {}

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}