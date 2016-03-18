package com.example.risbo.vocabquiz16wi;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintStream;


public class AddWordFragment extends DialogFragment {
    // you make the class extends DialogFragment and not Fragment to specify what kind of it will be.

    public static final String NEW_WORDS_FILENEAME = "newwords.txt";

    public AddWordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        // This line of code tells to read the XML file (fragment_add_word) and turn it in java objects with all the widgets
        // it returns a View that represents the whole UI
        // I have to talk to this object that is being sent back to attach event handlers

        // return inflater.inflate(R.layout.fragment_add_word, container, false); this is the line as it is auto-generated, we modify it a bit
       final View dialog = inflater.inflate(R.layout.fragment_add_word, container, false);

        Button addButton = (Button) dialog.findViewById(R.id.addbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // take the code that add word to the file
                EditText edit1 = (EditText) dialog.findViewById(R.id.word); // you have to make dialog a final
                EditText edit2 = (EditText) dialog.findViewById(R.id.defn);
                String word = edit1.getText().toString();
                String defn = edit2.getText().toString();
                addWordToFile(word,defn);
                Toast.makeText(getActivity(), "Added word: " + word, Toast.LENGTH_SHORT).show();
                dismiss(); // close dialog


            }
        });

        return dialog;


    }

    public void addWordToFile (String word, String defn){
        Activity activity = getActivity();


        try {
            //write a new word/defn to file
            PrintStream out = new PrintStream(
                    activity.openFileOutput(NEW_WORDS_FILENEAME, activity.MODE_APPEND) // MODE APPEND will not replace previous content
            );
            out.println(word + "\t" + defn);
            out.close();

            // shut down this activity and go back to Main
            Intent intent = new Intent();
            intent.putExtra("word", word);
            intent.putExtra("defn", defn);

            /* next two lines don't apply when talking to dialog */
//            setResult(activity.RESULT_OK, intent);
//            finish(); // kill meeeeeee!!

        } catch (IOException ioe){
            Log.wtf("adding a word failed", ioe);
        }

    }

    }


