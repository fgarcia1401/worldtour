package br.com.fernando.worldtour.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.fernando.worldtour.R;
import br.com.fernando.worldtour.model.domain.Country;
import br.com.fernando.worldtour.service.ServiceUtil;

/**
 * Created by fernando on 03/09/16.
 */
public class CountryVisitedAdapter extends BaseAdapter{

    private List<Country> paises;
    private List<Country> paisesSelecionados;
    private OnItemClickListener listener;
    private Context context;

    public CountryVisitedAdapter(Context context, List<Country> paises, OnItemClickListener listener) {
        this.context = context;
        this.paises = paises;
        this.listener = listener;
        this.paisesSelecionados = new ArrayList<Country>();
    }

    @Override
    public int getCount() {
        return paises.size();
    }

    @Override
    public Object getItem(int position) {
        return paises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        final Country country = paises.get(position);
        country.setVisited(true);

        if( view == null ) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            view = layoutInflater.inflate(R.layout.country_visited_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvPais = (TextView) view.findViewById(R.id.tvPais);
            viewHolder.ivPais = (ImageView) view.findViewById(R.id.ivPais);
            viewHolder.cbVisited = (CheckBox) view.findViewById(R.id.cbVisited);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(country);
                }
            });

            viewHolder.cbVisited.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;

                    if(cb.isChecked()) {
                        if(!paisesSelecionados.contains(country))
                            paisesSelecionados.add(country);
                    } else {
                        if(paisesSelecionados.contains(country))
                            paisesSelecionados.remove(country);
                    }

                }
            });

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String imagePaisUrl = ServiceUtil.URL_MYPUSH + "world/countries/" + country.getId() +"/flag";
        Picasso.with(context)
                .load(imagePaisUrl)
                .into(viewHolder.ivPais);

        viewHolder.tvPais.setText(country.getShortname());

        return view;
    }


    public interface OnItemClickListener {
        void onClick(Country country);
    }

    private static class ViewHolder {
        public CheckBox cbVisited;
        public TextView tvPais;
        public ImageView ivPais;
    }

    public List<Country> getPaisesSelecionados() {
        return paisesSelecionados;
    }

    public void setPaisesSelecionados(List<Country> paisesSelecionados) {
        this.paisesSelecionados = paisesSelecionados;
    }
}
