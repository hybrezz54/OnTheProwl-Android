package org.technowolves.ontheprowl.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nbsp.materialfilepicker.MaterialFilePicker;

import org.technowolves.ontheprowl.R;

import java.util.regex.Pattern;

public class ExportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(1)
                .withFilter(Pattern.compile(".*\\.json$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(true) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();
    }

}
