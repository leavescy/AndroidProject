package com.chenye.dictionarychange;

import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private HashMap<String, String> dictionary;  // 存放单词-单词含义的字典
    private ArrayList<String> chosenWords; // 将所有单词存放到chosenwords
    private String word;   //单词
    private ArrayList<String> definations; // 含义列表
    private ArrayAdapter<String> adapter;
    private int score = 0;
    private static final String LIST_STATE = "listState";
    private Parcelable mListState = null;
    private ListView defnListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readAllDefination();

        this.chosenWords = new ArrayList<>(this.dictionary.keySet());  // 获取到所有单词并存在在ArrayList中
        this.definations = new ArrayList<>();
        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.definations);  // 初始化ArrayAdapter
        // 随机选择4个单词含义
        pick4Definations();
        // 将列表添加到adapter中
        defnListView = findViewById(R.id.definitions_ListView);
        defnListView.setAdapter(this.adapter);
        defnListView.setOnItemClickListener(this);
        /*
        // 监听这个列表， 用到了匿名内部类
        defnListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListView defnListView = findViewById(R.id.definitions_ListView);
                String chooseDefnText = defnListView.getItemAtPosition(i).toString();  // 获取点击位置的字符串
                String correctDefn = MainActivity.this.dictionary.get(word);   // 获取对应单词的解释
                // 如果选择和正确结果一致，则：
                if(correctDefn.equals(chooseDefnText)){
                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                    score ++;
                }else{
                    Toast.makeText(MainActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                    score --;
                }
                // 选择之后在重新刷新一次列表,即再次挑选一个单词和5个单词的含义
                TextView scoreTextView = findViewById(R.id.scoreTextView);
                scoreTextView.setText("得分: " + score);
                pick4Definations();

            }
        });
        */
    }
    public void pick4Definations(){

        Collections.shuffle(this.chosenWords);  // 打乱单词的存放位置
        this.word = this.chosenWords.get(0);  // 获取存放单词的list中的第一个单词
        TextView wordText = findViewById(R.id.wordTextView);
        wordText.setText(this.word);  // 显示单词
        this.definations.clear();  // 防止结果有脏数据，清空一下单词含义的list
        for(int i = 0; i < 4; i++){
            String defn = this.dictionary.get(this.chosenWords.get(i));  // 获取到单词对应的意思
            this.definations.add(defn);
        }
        Collections.shuffle(this.definations);  // 打乱结果
        this.adapter.notifyDataSetChanged();  // 通知adpter改变
    }
    // 将所有单词及其含义放在dictionary这个字典中
    private void readAllDefination(){
        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.gre_words));
        if(this.dictionary == null){
            this.dictionary = new HashMap<>();
        }
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            String[] spiece = line.split("\t");
            this.dictionary.put(spiece[0], spiece[1]);
        }
    }
    /*
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("score", score);
        outState.putString("word", this.word);
        defnListView = findViewById(R.id.definitions_ListView);
        mListState = defnListView.onSaveInstanceState();
        outState.putParcelable(LIST_STATE, mListState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        score = savedInstanceState.getInt("score");
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("得分: " + score);
        this.word = savedInstanceState.getString("word");
        TextView wordTextView = findViewById(R.id.wordTextView);
        wordTextView.setText(this.word);  // 显示单词
        mListState = savedInstanceState.getParcelable(LIST_STATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mListState != null){
            defnListView = findViewById(R.id.definitions_ListView);
            defnListView.onRestoreInstanceState(mListState);
            mListState = null;
        }
    }
*/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.i("LANDSCAPE = ", String.valueOf(Configuration.ORIENTATION_LANDSCAPE));
        }
        else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.i("LANDSCAPE = ", String.valueOf(Configuration.ORIENTATION_PORTRAIT));

        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ListView defnListView = findViewById(R.id.definitions_ListView);
        String chooseDefnText = defnListView.getItemAtPosition(i).toString();  // 获取点击位置的字符串
        String correctDefn = MainActivity.this.dictionary.get(word);   // 获取对应单词的解释
        // 如果选择和正确结果一致，则：
        if(correctDefn.equals(chooseDefnText)){
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            score ++;
        }else{
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
            score --;
        }
        // 选择之后在重新刷新一次列表,即再次挑选一个单词和5个单词的含义
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("score: " + score);
        pick4Definations();

    }

}
