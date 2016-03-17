package com.example.risbo.vocabquiz16wi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE_ADD_WORD = 1234; // 0 - 65535
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playTheQuizClick(View view) {
        Intent intent = new Intent (this, QuizActivity.class);
        intent.putExtra("favoriteClass", "CS 193A");
        intent.putExtra("MartysAge",19);
        startActivity(intent);

    }




    public void addAWordClick(View view) {
        Intent intent = new Intent (this, AddWordActivity.class);
        startActivityForResult(intent,REQ_CODE_ADD_WORD);

    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == REQ_CODE_ADD_WORD &&
                resultCode == RESULT_OK){
            String word = intent.getStringExtra("word");
            String defn = intent.getStringExtra("definition");
            Toast.makeText(this, "Word is '" + word + "', defn is '" + defn + "'", Toast.LENGTH_SHORT).show();
        }


    }
}
