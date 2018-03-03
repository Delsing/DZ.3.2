package com.example.user.dz;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button download;
    EditText text;
    ProgressBar progressBar;
    TextView textD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        download = findViewById(R.id.dovanload);
        progressBar = findViewById(R.id.progressBar);
        textD = findViewById(R.id.textd);
        text = findViewById(R.id.text);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = text.getText().toString();
                File destination = new File(Environment.getExternalStorageDirectory() +"/Music/music.mp3");
                new DownloadMusic(url, destination).execute();
            }
        });
    }

            public class DownloadMusic extends AsyncTask {

                public String url;
                public File destination;

                public DownloadMusic(String url, File destination) {
                    this.url = url;
                    this.destination = destination;

                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        FileUtil.copyURLToFile(new URL(url), destination);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < 100; i += 1) {
                        try {
                            publishProgress(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    publishProgress(100);
                    return null;
                }

                public String onPostExecute() {
                    textD.setText("Задача завершена");
                }

                protected void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                    progressBar.setProgress(values[0]);
                    textD.setText(values[0]);
                }
            }
}
