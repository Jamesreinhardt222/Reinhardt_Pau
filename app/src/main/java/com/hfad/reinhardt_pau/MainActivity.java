package com.hfad.reinhardt_pau;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void submitKeyword(View view) {
        final EditText editText = (EditText) findViewById(R.id.keyword);
        Intent intent = new Intent(this, NewsListActivity.class);
        intent.putExtra("keyword", editText.getText().toString());
        startActivity(intent);
    }

}