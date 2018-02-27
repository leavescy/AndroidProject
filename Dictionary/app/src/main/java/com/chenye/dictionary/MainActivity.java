package com.chenye.dictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private HashMap<String, String> dictionary;
    EditText wordEditText;
    TextView resultTextView;

    public  void readAllDefinition() {
        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.gre_words));
        if(this.dictionary == null){
            this.dictionary = new HashMap<>();
        }
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            String[] pieces = line.split("\t");
            this.dictionary.put(pieces[0], pieces[1]);

        }
        scanner.close();
    }
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readAllDefinition();
        wordEditText = findViewById(R.id.wordEditText);
        resultTextView = findViewById(R.id.result);
        wordEditText.addTextChangedListener(new TextWatcher() {
            private CharSequence word;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                word = charSequence;

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                //resultTextView.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(word.length()==0) {
                    resultTextView.setText("word definition result");
                }
                else if (word.length() == 20){
                    Toast.makeText(MainActivity.this, "已到达输入的最大长度！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    public void lookup(View view) {
        EditText wordText = findViewById(R.id.wordEditText);
        String word = wordText.getText().toString();
        if(this.dictionary == null){
            readAllDefinition();
        }
       // String def = readDefinition(word);
        String def = this.dictionary.get(word);
        TextView defTextView = findViewById(R.id.result);
        if(def == null){
            defTextView.setText("word not fond!");
        }else{
            defTextView.setText(def);
        }
    }
    /*
    private String readDefinition(String word) {
        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.gre_words));
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            String[] pieces = line.split("\t");
            if(pieces[0].equalsIgnoreCase(word)){
                return pieces[1];
            }
        }
        return null;
    }
    */
}
