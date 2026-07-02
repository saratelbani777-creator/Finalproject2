package com.example.finalproject2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//سارة وسام التلباني
// 2320230601
    private ArrayList<WordItem> dictionaryList;
    private RecyclerView recyclerView;
    private WordAdapter adapter;

     private ActivityResultLauncher<Intent> addEditLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String word = data.getStringExtra("word");
                    String meaning = data.getStringExtra("meaning");
                    int position = data.getIntExtra("position", -1);

                    if (position == -1) {
                         dictionaryList.add(new WordItem(word, meaning));
                        adapter.notifyItemInserted(dictionaryList.size() - 1);
                        Toast.makeText(this, "A word is added", Toast.LENGTH_SHORT).show();
                    } else {
                         dictionaryList.get(position).setWord(word);
                        dictionaryList.get(position).setMeaning(meaning);
                        adapter.notifyItemChanged(position);
                        Toast.makeText(this, "A word is updated", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);

         if (savedInstanceState != null) {
            dictionaryList = (ArrayList<WordItem>) savedInstanceState.getSerializable("dict_list");
        } else {
            dictionaryList = new ArrayList<>();
        }

        buildRecyclerView();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            addEditLauncher.launch(intent);
        });
    }

    private void buildRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WordAdapter(dictionaryList, new WordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                 Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("word", dictionaryList.get(position).getWord());
                intent.putExtra("meaning", dictionaryList.get(position).getMeaning());
                intent.putExtra("position", position);
                addEditLauncher.launch(intent);
            }

            @Override
            public void onItemLongClick(int position) {
                 dictionaryList.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(MainActivity.this, "A word is deleted", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }

     @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("dict_list", dictionaryList);
    }
}