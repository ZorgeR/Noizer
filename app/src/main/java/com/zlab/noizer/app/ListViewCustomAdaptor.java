package com.zlab.noizer.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

public class ListViewCustomAdaptor extends ArrayAdapter<ListViewItem> {
    private Context c;
    private int id;
    private List<ListViewItem> items;

    public ListViewCustomAdaptor(Context context, int resource, List<ListViewItem> objects) {
        super(context, resource, objects);
        c = context;
        id = resource;
        items = objects;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(id, parent, false);

        final ListViewItem o = items.get(position);

        if (o != null) {
            final LinearLayout textLayout = (LinearLayout) v.findViewById(R.id.textLayout);

            final ImageView Image = (ImageView) v.findViewById(R.id.imageView);
            Image.setImageResource(o.getImageResID());

            final TextView Title = (TextView) v.findViewById(R.id.textViewTitle);
            Title.setText(o.getTitle());

            final TextView Description = (TextView) v.findViewById(R.id.textViewDescription);
            Description.setText(o.getDescription());

            final SeekBar volumeBar = (SeekBar) v.findViewById(R.id.VolumeSeekBar);
            volumeBar.setProgress(o.getVolume());

            final ToggleButton OnOff = (ToggleButton) v.findViewById(R.id.toggleButton);
            OnOff.setText(R.string.off);

            if(o.getIsPlaying()){
                volumeBar.setVisibility(View.VISIBLE);
                textLayout.setVisibility(View.GONE);
                OnOff.setText(R.string.on);
                OnOff.setChecked(true);
            }

            OnOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(OnOff.isChecked()){
                        volumeBar.setVisibility(View.VISIBLE);
                        textLayout.setVisibility(View.GONE);
                        OnOff.setText(R.string.on);
                        o.startPlaying();
                    } else {
                        volumeBar.setVisibility(View.GONE);
                        textLayout.setVisibility(View.VISIBLE);
                        OnOff.setText(R.string.off);
                        o.stopPlaying();
                    }
                }
            });

            volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    o.setVolume(progress);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }

        return v;
    }
}
