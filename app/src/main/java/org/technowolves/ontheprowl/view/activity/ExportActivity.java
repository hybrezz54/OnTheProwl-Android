package org.technowolves.ontheprowl.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.nbsp.materialfilepicker.MaterialFilePicker;

import org.technowolves.ontheprowl.R;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ExportActivity extends AppCompatActivity {

    public static final String EVENT_KEY = "EVENT_KEY";
    public static final String FILE_PATH = "DATA_DIR";

    private String path = "/data";
    private String eventKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            eventKey = extras.getString(EVENT_KEY);
        }

        Switch btSwitch = (Switch) findViewById(R.id.bt_enabled);

        Button btnExport = (Button) findViewById(R.id.btn_export);
        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                File dir = new File(path);
                File files[] = dir.listFiles();
                ArrayList<File> filteredFiles = new ArrayList<>();

                for (File file : files) {
                    if (file.getName().startsWith(eventKey))
                        filteredFiles.add(file);
                }

                for (File file : filteredFiles) {

                }
            }
        });
    }

}
