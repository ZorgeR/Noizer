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
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends Activity {

    private ListViewCustomAdaptor listAdaptor;
    private Context mContext;
    static boolean needRestart = false;
    static String theme;
    static CountDownTimer timer;
    static long timeLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme = PreferenceManager.getDefaultSharedPreferences(this).getString("theme_switch", "default_light");
        assert theme != null;
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
        ListView listview = findViewById(R.id.listView);
        List<ListViewItem> listObjects = getItemsList();

        listAdaptor = new ListViewCustomAdaptor(this, R.layout.rowlayout, listObjects);
        listview.setAdapter(listAdaptor);
    }

    private List<ListViewItem> getItemsList() {
        List<ListViewItem> list = new ArrayList<>();

        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_whitenoise), getResources().getString(R.string.sound_description_whitenoise), R.raw.whitenoise, "raw/whitenoise.ogg", false, 25, R.drawable.noise));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_brownnoise), getResources().getString(R.string.sound_description_brownnoise), R.raw.brownnoise, "raw/brownnoise.ogg", false, 25, R.drawable.noise));
        list.add(new ListViewItem(mContext, getResources().getString(R.string.sound_title_catpurpur), getResources().getString(R.string.sound_description_catpurpur), R.raw.catpurpur, "raw/catpurpur.ogg", false, 25, R.drawable.catpurpur));
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        //menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        if(timer == null){
            for (int i = 0; i<=4; i++){
                menu.getItem(0).getSubMenu().getItem(i).setVisible(true);
            }
            menu.getItem(0).getSubMenu().getItem(5).setVisible(false);
        } else {
            for (int i = 0; i<=4; i++){
                menu.getItem(0).getSubMenu().getItem(i).setVisible(false);
            }
            menu.getItem(0).getSubMenu().getItem(5).setVisible(true);
        }

        return true;
    }

    private void action_timer(int min, String timerText){
        int sec = min*60;

        if(timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(sec*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                stopAllSound();
                timer = null;
                invalidateOptionsMenu();
                ((Activity) mContext).finish();
            }
        }.start();
        Toast.makeText(this, getResources().getString(R.string.action_timer_set) + timerText, Toast.LENGTH_SHORT).show();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_timer){
            if(timer != null) {
                String time = (timeLeft /1000) + " " + getResources().getString(R.string.sec);

                if ((timeLeft /1000) > 60) {
                    time = (timeLeft /1000/60) + " " + getResources().getString(R.string.min);
                }
                Toast.makeText(this, getResources().getString(R.string.action_timer_remain) + time, Toast.LENGTH_SHORT).show();
            }
        }

        if (id == R.id.action_timer_5min){
            action_timer(5, getResources().getString(R.string.action_timer_5min));
            return true;
        } else if (id == R.id.action_timer_15min){
            action_timer(15, getResources().getString(R.string.action_timer_15min));
            return true;
        } else if (id == R.id.action_timer_30min){
            action_timer(30, getResources().getString(R.string.action_timer_30min));
            return true;
        } else if (id == R.id.action_timer_45min){
            action_timer(45, getResources().getString(R.string.action_timer_45min));
            return true;
        } else if (id == R.id.action_timer_60min) {
            action_timer(60, getResources().getString(R.string.action_timer_60min));
            return true;
        } else if (id == R.id.action_timer_cancel){
            timer.cancel();
            timer = null;
            invalidateOptionsMenu();
            Toast.makeText(this, getResources().getString(R.string.action_timer_canceled), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_exit) {
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
            Objects.requireNonNull(listAdaptor.getItem(i)).stopPlaying();
        }
        listAdaptor.notifyDataSetChanged();
    }

    private void changeTheme(String newTheme) {
        switch (newTheme) {
            case "material":
                setTheme(R.style.Theme_Material);
                break;
            case "material_light":
                setTheme(R.style.Theme_MaterialLight);
                break;
            case "material_black":
            case "material_black_color":
            case "material_black_monochrome":
                setTheme(R.style.Theme_MaterialBlack);
                break;
            case "holo":
                setTheme(R.style.Theme_Holo);
                break;
            case "holo_light":
                setTheme(R.style.Theme_HoloLight);
                break;
            default:
                setTheme(R.style.Theme_DefaultLight);
                break;
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
        Intent intentToBeNewRoot = new Intent(this, MainActivity.class);
        ComponentName cn = intentToBeNewRoot.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(cn);

        stopAllSound();
        startActivity(mainIntent);
        this.finish();
    }
}