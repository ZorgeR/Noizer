package com.zlab.noizer.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView listview;
    private List<ListViewItem> listObjects;
    private ListViewCustomAdaptor listAdaptor;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        renderList();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void renderList(){
        listview = (ListView) findViewById(R.id.listView);
        listObjects = getItemsList();

        listAdaptor = new ListViewCustomAdaptor(this, R.layout.rowlayout, listObjects);
        listview.setAdapter(listAdaptor);
    }

    private List<ListViewItem> getItemsList(){
        List<ListViewItem> list = new ArrayList<ListViewItem>();

        list.add(new ListViewItem(mContext, "Белый шум", "Успокоит вашего ребенка", R.raw.whitenoise, "raw/whitenoise.ogg", false, 25, R.drawable.whitenoise));
        list.add(new ListViewItem(mContext, "Ветер", "Меланхолия", R.raw.wind, "raw/wind.ogg", false, 25, R.drawable.wind));
        list.add(new ListViewItem(mContext, "Дождь", "Расслабляет", R.raw.rain, "raw/rain.ogg", false, 25, R.drawable.rain));
        list.add(new ListViewItem(mContext, "Гроза", "Будьте бдительны", R.raw.storm, "raw/storm.ogg", false, 25, R.drawable.storm));
        list.add(new ListViewItem(mContext, "Камин", "Истинное наслаждение", R.raw.fireplace, "raw/fireplace.ogg", false, 25, R.drawable.fireplace));
        list.add(new ListViewItem(mContext, "Птицы в парке", "Получайте удовольствие", R.raw.birds, "raw/birds.ogg", false, 25, R.drawable.birds));
        list.add(new ListViewItem(mContext, "Шумная улица", "Кого нибудь может успокоить", R.raw.street, "raw/street.ogg", false, 25, R.drawable.street));
        list.add(new ListViewItem(mContext, "Бар", "Многим помогает", R.raw.bar, "raw/bar.ogg", false, 25, R.drawable.bar));
        list.add(new ListViewItem(mContext, "Волны", "Закройте глаза, оденьте наушники", R.raw.waves, "raw/waves.ogg", false, 25, R.drawable.waves));
        list.add(new ListViewItem(mContext, "Листья на ветру", "Садитесь и слушайте", R.raw.foliage, "raw/foliage.ogg", false, 25, R.drawable.foliage));
        list.add(new ListViewItem(mContext, "Поезд", "Чу-чух-Чу-чух", R.raw.train, "raw/train.ogg", false, 25, R.drawable.train));

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
            for(int i=0;i<listAdaptor.getCount();i++){
                listAdaptor.getItem(i).stopPlaying();
            }
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}