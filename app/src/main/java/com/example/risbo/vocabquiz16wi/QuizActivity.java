package com.example.risbo.vocabquiz16wi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class QuizActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //Create a HashMap to store the word (key) and the definition (value)
    private HashMap<String, String> dictionary;
    private String word;
    private ArrayList<String> definitions;
    private ArrayAdapter<String> adapter;

    private int pts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Passing parameter from Main activity to Quiz activity
        Intent intent = getIntent();
        String favClass = intent.getStringExtra("favoriteClass");
        int age = intent.getIntExtra("MartysAge",-1); // -1 is default in case nothing is passed and throw error

        pts = 0;

        ListView definitionsList = (ListView) findViewById(R.id.definition_list);
        // Define the method to be called when we click the ListView (see the method below)
        definitionsList.setOnItemClickListener(this); //this is the method onItemClick
        definitions = new ArrayList<>();

        // set the listviewto store 5 dynamic items (cf slide)
        adapter = new ArrayAdapter<>(
                this,
                R.layout.mydictionarylistlayout,
                R.id.the_item_text,
                definitions
        );

        definitionsList.setAdapter(adapter);


        //To avoid reading the file every time the app is loaded
        if (dictionary == null)
        {
            dictionary = new HashMap<>();
            readAllDefinitions();
        }



        pick5randomWords();



    }


    private void readAllDefinitions () {
        dictionary = new HashMap<>();

        // read the pre-existing starting words from greword.txt
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.greword));
        readWordsFromFile(scan);

        // read any added words from newwords.txt

        try {
          Scanner scan2 = new Scanner(openFileInput(AddWordActivity.NEW_WORDS_FILENEAME));
            readWordsFromFile(scan2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void readWordsFromFile(Scanner scan){


        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] pieces = line.split("\t");

            //Log.d("Help", "dictionary = " + dictionary);

            if (pieces.length >= 2) {
                // each line is put in the dictionary created above
                dictionary.put(pieces[0], pieces[1]);
            }
        }
    }


    public void pick5randomWords(){

        // when you create an ArrayList you can specify from where you take the data : dictionary for us
        ArrayList<String> chosenWords = new ArrayList<>(dictionary.keySet()); // keySet() givesthe list of all the words
        //pick 1 word out of 5 randomly picked
        Collections.shuffle(chosenWords);

        // the word that we are trying to guess is chosenWords element zero (we define it like that)
        word = chosenWords.get(0);

        TextView wordText =  (TextView) findViewById(R.id.the_word);
        wordText.setText(word);

        //put the definitions ofthe 5 rand words in an ArrayList created above
        definitions.clear();
        for(int i = 0; i <5; i++){
            String defn = dictionary.get(chosenWords.get(i));
            definitions.add(defn);
        }
        Collections.shuffle(definitions);

        // update the listview to store 5 dynamic items
        adapter.notifyDataSetChanged();


    }


    //pb with this syntax : one method per object
    @Override
    public void onItemClick(AdapterView<?> parent
            , View view
            , int index //index that got clicked on
            , long id // resource id that got clicked on
    ) {

        ListView definitionsList = (ListView) findViewById(R.id.definition_list);

        //this is the text on a list item that is being touched
        String text = definitionsList.getItemAtPosition(index).toString();

        if (text.equalsIgnoreCase(dictionary.get(word)))
        {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            pts ++;
        } else
        {
            Toast.makeText(this, "False", Toast.LENGTH_SHORT).show();
            pts --;

        }
        TextView points = (TextView) findViewById(R.id.points);
        points.setText("Points: " + pts);

        // pick 5 new words
        pick5randomWords();


    }
}

