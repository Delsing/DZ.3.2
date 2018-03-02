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
    Button dovanload;
    EditText text;
    ProgressBar progressBar;
    TextView textd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dovanload = findViewById(R.id.dovanload);
        progressBar = findViewById(R.id.progressBar);
        textd = findViewById(R.id.textd);
        text = findViewById(R.id.text);

        dovanload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = text.getText().toString();
                new DovanloadMusick() {
                new File(Environment.getExternalStorageDirectory() +"/Music/").execute();
                };
            }
        });
    }

            public class DovanloadMusick extends AsyncTask {

                public String url;
                private File destination;

                public DovanloadMusick(String url, File destination) {
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
                    textd.setText("Задача завершена");
                }

                protected void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                    progressBar.setProgress(values[0]);
                    textd.setText(values[0]);
                }
            }
}
