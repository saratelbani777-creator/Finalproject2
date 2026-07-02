package com.example.finalproject2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    private EditText etWord, etMeaning;
    private Button btnSave;
    private int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        etWord = findViewById(R.id.et_word);
        etMeaning = findViewById(R.id.et_meaning);
        btnSave = findViewById(R.id.btn_save);

         Intent intent = getIntent();
        if (intent.hasExtra("word")) {
            etWord.setText(intent.getStringExtra("word"));
            etMeaning.setText(intent.getStringExtra("meaning"));
            editPosition = intent.getIntExtra("position", -1);
        }

        btnSave.setOnClickListener(v -> {
            String word = etWord.getText().toString().trim();
            String meaning = etMeaning.getText().toString().trim();

            if (!word.isEmpty() && !meaning.isEmpty()) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("word", word);
                resultIntent.putExtra("meaning", meaning);
                resultIntent.putExtra("position", editPosition);

                setResult(RESULT_OK, resultIntent);
                finish(); // إغلاق الشاشة والعودة
            }
        });
    }
}
