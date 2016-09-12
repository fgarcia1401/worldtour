package br.com.fernando.worldtour.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.fernando.worldtour.R;
import br.com.fernando.worldtour.util.MenuMain;

/**
 * Created by fernando on 03/09/16.
 */
public class MenuMainAdapter extends BaseAdapter{

    private List<MenuMain> menuMainList;
    private OnItemClickListener listener;

    public MenuMainAdapter(Context context, OnItemClickListener listener) {
        this.menuMainList = MenuMain.buildMenu(context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return menuMainList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuMainList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        final MenuMain menuMain = menuMainList.get(position);

        if( view == null ) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            view = layoutInflater.inflate(R.layout.drawer_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) view.findViewById(R.id.title);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.icon);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(menuMain);
                }
            });

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.mTextView.setText(menuMain.getName());
        Drawable dwIcon = ContextCompat.getDrawable(parent.getContext(), menuMain.getImageId());
        viewHolder.imageView.setImageDrawable(dwIcon);

        return view;
    }


    public interface OnItemClickListener {
        void onClick(MenuMain menuMain);
    }

    private static class ViewHolder {
        public TextView mTextView;
        public ImageView imageView;
    }

}
