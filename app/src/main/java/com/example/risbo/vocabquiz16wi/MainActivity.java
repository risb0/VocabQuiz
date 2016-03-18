package com.example.risbo.vocabquiz16wi;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import stanford.androidlib.SimpleActivity;


public class MainActivity extends SimpleActivity {
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
        /* Previous version of the code, we called another activity to add a word, we replace it with fragment now */
//        Intent intent = new Intent (this, AddWordActivity.class);
//        startActivityForResult(intent,REQ_CODE_ADD_WORD);

        //final String[] pokemen = {"Pikachu", "Charizard", "Vulpix", "Mewtwo"};

       /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("My title");
        builder.setMessage("Hello, world!");
        AlertDialog dialog = builder.create();

        dialog.show();


        The next AlertDialog dialog is the same declaration but written in chain calls (prettier)
        The builder is implicit
        */




        /*AlertDialog dialog = new AlertDialog.Builder(this)
        .setTitle("My title")
        //.setMessage("Hello, world!")
        .setItems(pokemen, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index) {
                        Toast.makeText(MainActivity.this, "You tapped  " + pokemen[index], Toast.LENGTH_SHORT).show();
                    }
                })
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //// TODO: 18/03/16  
            }
        })        
        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //// TODO: 18/03/16
            }
        })        
        .create();

        dialog.show();


        Next method is from  Stanford Library to avoid all this code to show content of Array in Dialog
        */
        //showListInputDialog(pokemen);
        //showConfirmDialog("My Title","OK","CANCEL");

        /******** Everything above was for learning purpose, and does not have anything to do with the purpose of the app *********/

        FragmentManager fm = getFragmentManager();
        AddWordFragment frag = new AddWordFragment();
        frag.show(fm,"Anything");


        // With Stanford Library call
        //showMultiInputDialog("Your word", "Its definition"); when this is done it calls onMultiInputDialogClose() (see below)

    }

 /*   @Override
    public void onMultiInputDialogClose(AlertDialog dialog, String[] inputs) {
        String word = inputs[0];
        String defn = inputs[1];
        toast("Added word: " + word);
    }
*/
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

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause", "onPause called");
    }
}
