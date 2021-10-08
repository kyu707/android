package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Word_setting extends AppCompatActivity {
    EditText editText, editText1, editText2;
    Button button4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_setting);

        editText = findViewById(R.id.editText);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        button4 = findViewById(R.id.button4);


        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                intent1.putExtra("editText", editText.getText().toString());
                intent1.putExtra("editText1", editText1.getText().toString());
                intent1.putExtra("editText2", editText2.getText().toString());
                startActivity(intent1);

            }
        });

    }
}
