package ru.mirea.fedotov.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText fileNameText;
    private EditText text;
    private TextView textSet;
    private SharedPreferences preferences;
    private Button saveButton;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private final String FILE_NAME = "file_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileNameText = findViewById(R.id.fileNameEdit);
        text = findViewById(R.id.textView1Edit);
        textSet = findViewById(R.id.loadText);
        saveButton = findViewById(R.id.buttSave);
        saveButton.setOnClickListener(this::setSaveButton);
        preferences = getPreferences(MODE_PRIVATE);

        try {
            fileNameText.setText(preferences.getString(FILE_NAME, "asd"));
            textSet.setText(getTextFromFile());
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setSaveButton(View view) {
        FileOutputStream outputStream;
        String string = text.getText().toString();
        String name = fileNameText.getText().toString();
        try {
            outputStream = openFileOutput(name, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
            Toast.makeText(this, "file saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FILE_NAME, name);
        editor.apply();
    }

    public String getTextFromFile() {
        FileInputStream fin = null;
        try {
            fin = openFileInput(fileNameText.getText().toString());
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            Log.d(LOG_TAG, text);
            Toast.makeText(this, "file loaded", Toast.LENGTH_SHORT).show();
            return text;
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }
}