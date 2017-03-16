package com.example.oem.notes;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContentResolverCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.example.oem.notes.MainActivity;

public class InteractiveArrayAdapter extends ArrayAdapter<NoteModel> {

    private final List<NoteModel> list;
    private final Activity context;

    public InteractiveArrayAdapter(Activity context, List<NoteModel> list) {
        super(context, R.layout.row_button_layout, list);
        this.context = context;
        this.list = list;
    }

    public List<NoteModel> getCheckedList() {
        return list;
    }

    static class ViewHolder {
        protected TextView text;
        protected TextView mail;
        protected CheckBox checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.row_button_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.label);
            viewHolder.mail = (TextView) view.findViewById(R.id.mail);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            NoteModel element = (NoteModel) viewHolder.checkbox.getTag();

                            for(int i=0;i<list.size();i++) {
                                if(list.get(i).getId() == element.getId()) {
                                    list.get(i).setSelected(true);
                                }
                            }
                        }
                    });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getName());
        holder.mail.setText(list.get(position).getEmail());
        holder.checkbox.setChecked(list.get(position).isSelected());
        return view;
    }
}