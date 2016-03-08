package org.ruffineo.workflowmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class PreferencesActivity extends AppCompatActivity {

    private static final String TAG = "AJUSTES_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        escribeLog("onCreate de ajustes");
    }

    private void escribeLog(String texto) {
        Log.i(TAG, texto);
    }

    private void notificaError(String mensaje) {
        Toast.makeText(PreferencesActivity.this, mensaje, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ajustes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_volver:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
