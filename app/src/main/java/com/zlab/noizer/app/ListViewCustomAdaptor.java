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

    ListViewCustomAdaptor(Context context, int resource, List<ListViewItem> objects) {
        super(context, resource, objects);
        c = context;
        id = resource;
        items = objects;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ListViewHolder mViewHolder;

        if (convertView == null) {
            mViewHolder = new ListViewHolder();

            LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(id, parent, false);

            mViewHolder.textLayout = (LinearLayout) convertView.findViewById(R.id.textLayout);
            mViewHolder.Image = (ImageView) convertView.findViewById(R.id.imageView);
            mViewHolder.Title = (TextView) convertView.findViewById(R.id.textViewTitle);
            mViewHolder.Description = (TextView) convertView.findViewById(R.id.textViewDescription);
            mViewHolder.volumeBar = (SeekBar) convertView.findViewById(R.id.VolumeSeekBar);
            mViewHolder.OnOff = (ToggleButton) convertView.findViewById(R.id.toggleButton);

            final ListViewHolder finalMViewHolder = mViewHolder;
            mViewHolder.OnOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalMViewHolder.OnOff.isChecked()) {
                        finalMViewHolder.volumeBar.setVisibility(View.VISIBLE);
                        finalMViewHolder.textLayout.setVisibility(View.GONE);
                        finalMViewHolder.OnOff.setText(R.string.on);
                        finalMViewHolder.Item.startPlaying();
                        finalMViewHolder.volumeBar.setProgress(finalMViewHolder.Item.getVolume());
                    } else {
                        finalMViewHolder.volumeBar.setVisibility(View.GONE);
                        finalMViewHolder.textLayout.setVisibility(View.VISIBLE);
                        finalMViewHolder.OnOff.setText(R.string.off);
                        finalMViewHolder.Item.stopPlaying();
                    }
                }
            });

            mViewHolder.volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    finalMViewHolder.Item.setVolume(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ListViewHolder) convertView.getTag();
        }

        mViewHolder.Item = items.get(position);

        mViewHolder.Image.setImageResource(mViewHolder.Item.getImageResID());
        mViewHolder.Title.setText(mViewHolder.Item.getTitle());
        mViewHolder.Description.setText(mViewHolder.Item.getDescription());

        if (mViewHolder.Item.getIsPlaying()) {
            mViewHolder.volumeBar.setVisibility(View.VISIBLE);
            mViewHolder.textLayout.setVisibility(View.GONE);
            mViewHolder.OnOff.setChecked(true);
            mViewHolder.OnOff.setText(R.string.on);
            mViewHolder.volumeBar.setProgress(mViewHolder.Item.getVolume());
        } else {
            mViewHolder.volumeBar.setVisibility(View.GONE);
            mViewHolder.textLayout.setVisibility(View.VISIBLE);
            mViewHolder.OnOff.setChecked(false);
            mViewHolder.OnOff.setText(R.string.off);
        }

        return convertView;
    }
}

