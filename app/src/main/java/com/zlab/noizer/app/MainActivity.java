package com.zlab.noizer.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.IntentCompat;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListViewCustomAdaptor listAdaptor;
    private Context mContext;
    static boolean needRestart = false;
    static String theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme = PreferenceManager.getDefaultSharedPreferences(this).getString("theme_switch", "default_light");
        changeTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        renderList();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (needRestart) {
            needRestart = false;
            restartActivity();
        }
    }

    private void renderList() {
        ListView listview = (ListView) findViewById(R.id.listView);
        List<ListViewItem> listObjects = getItemsList();

        listAdaptor = new ListViewCustomAdaptor(this, R.layout.rowlayout, listObjects);
        listview.setAdapter(listAdaptor);
    }

    private List<ListViewItem> getItemsList() {
        List<ListViewItem> list = new ArrayList<ListViewItem>();

        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_whitenoise), getResources().getString(R.string.sound_description_whitenoise), R.raw.whitenoise, "raw/whitenoise.ogg", false, 25, R.drawable.noise));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_brownnoise), getResources().getString(R.string.sound_description_brownnoise), R.raw.brownnoise, "raw/brownnoise.ogg", false, 25, R.drawable.noise));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_intrauterine), getResources().getString(R.string.sound_description_intrauterine), R.raw.intrauterine, "raw/intrauterine.ogg", false, 25, R.drawable.intrauterine));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_wind), getResources().getString(R.string.sound_description_wind), R.raw.wind, "raw/wind.ogg", false, 25, R.drawable.wind));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_rain), getResources().getString(R.string.sound_description_rain), R.raw.rain, "raw/rain.ogg", false, 25, R.drawable.rain));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_storm), getResources().getString(R.string.sound_description_storm), R.raw.storm, "raw/storm.ogg", false, 25, R.drawable.storm));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_fireplace), getResources().getString(R.string.sound_description_fireplace), R.raw.fireplace, "raw/fireplace.ogg", false, 25, R.drawable.fireplace));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_birds), getResources().getString(R.string.sound_description_birds), R.raw.birds, "raw/birds.ogg", false, 25, R.drawable.birds));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_street), getResources().getString(R.string.sound_description_street), R.raw.street, "raw/street.ogg", false, 25, R.drawable.street));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_bar), getResources().getString(R.string.sound_description_bar), R.raw.bar, "raw/bar.ogg", false, 25, R.drawable.bar));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_waves), getResources().getString(R.string.sound_description_waves), R.raw.waves, "raw/waves.ogg", false, 25, R.drawable.waves));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_foliage), getResources().getString(R.string.sound_description_foliage), R.raw.foliage, "raw/foliage.ogg", false, 25, R.drawable.foliage));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_train), getResources().getString(R.string.sound_description_train), R.raw.train, "raw/train.ogg", false, 25, R.drawable.train));

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_exit) {
            stopAllSound();
            this.finish();
            return true;
        } else if (id == R.id.action_contact) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"zorg.rhrd@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Noizer app request");
            intent.putExtra(Intent.EXTRA_TEXT, "Your text here");
            startActivity(Intent.createChooser(intent, ""));
            return true;
        } else if (id == R.id.action_about) {
            PackageManager manager = getPackageManager();
            PackageInfo info = null;
            String version = "Can't identify";
            try {
                info = manager.getPackageInfo(getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (info != null) {
                version = info.versionName;
            }

            final SpannableString msg = new SpannableString(getResources().getString(R.string.action_about_description) + "Build: " + version);
            Linkify.addLinks(msg, Linkify.ALL);

            final AlertDialog aboutDialog = new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.action_about_title))
                    .setMessage(msg)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .create();
            aboutDialog.show();

            ((TextView) aboutDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        } else if (id == R.id.action_settings) {
            startActivity(new Intent(this, Preferences.class));
        } else if (id == R.id.action_turn_off_all) {
            stopAllSound();
        }
        return super.onOptionsItemSelected(item);
    }

    private void stopAllSound() {
        for (int i = 0; i < listAdaptor.getCount(); i++) {
            listAdaptor.getItem(i).stopPlaying();
        }
        listAdaptor.notifyDataSetChanged();
    }

    private void changeTheme(String newTheme) {
        if (newTheme.equals("material")) {
            setTheme(R.style.Theme_Material);
        } else if (newTheme.equals("material_light")) {
            setTheme(R.style.Theme_MaterialLight);
        } else if (newTheme.equals("material_black") ||
                   newTheme.equals("material_black_color") ||
                   newTheme.equals("material_black_monochrome")) {
            setTheme(R.style.Theme_MaterialBlack);
        } else if (newTheme.equals("holo")) {
            setTheme(R.style.Theme_Holo);
        } else if (newTheme.equals("holo_light")) {
            setTheme(R.style.Theme_HoloLight);
        } else {
            setTheme(R.style.Theme_DefaultLight);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void restartActivity() {
        ComponentName componentName;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
            componentName = getPackageManager().getLaunchIntentForPackage("com.zlab.noizer.app").getComponent();
            Intent intent = IntentCompat.makeRestartActivityTask(componentName);
            stopAllSound();
            startActivity(intent);
            this.finish();
        }
    }
}