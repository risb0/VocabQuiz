package com.example.risbo.vocabquiz16wi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.PrintStream;

public class AddWordActivity extends AppCompatActivity {
    public static final String NEW_WORDS_FILENEAME = "newwords.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
    }

    public void addWordOKClick(View view) {
        EditText theNewWord = (EditText) findViewById(R.id.the_new_word);
        EditText theNewDefinition = (EditText) findViewById(R.id.the_new_definition);
        String word = theNewWord.getText().toString();
        String defn = theNewDefinition.getText().toString();


        try {
            //write a new word/defn to file
            PrintStream out = new PrintStream(
                    openFileOutput(NEW_WORDS_FILENEAME, MODE_APPEND) // MODE APPEND will not replace previous content
            );
            out.println(word + "\t" + defn);
            out.close();

            // shut down this activity and go back to Main
            Intent intent = new Intent();
            intent.putExtra("word", word);
            intent.putExtra("defn", defn);
            setResult(RESULT_OK, intent);
            finish(); // kill meeeeeee!!

        } catch (IOException ioe){
            Log.wtf("adding a word failed", ioe);
        }

    }
}
