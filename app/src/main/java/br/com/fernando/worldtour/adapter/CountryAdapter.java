package br.com.fernando.worldtour.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.fernando.worldtour.R;
import br.com.fernando.worldtour.controller.CountryVisitedController;
import br.com.fernando.worldtour.controller.UserController;
import br.com.fernando.worldtour.model.domain.Country;
import br.com.fernando.worldtour.model.domain.User;
import br.com.fernando.worldtour.service.ServiceUtil;

/**
 * Created by fernando on 03/09/16.
 */
public class CountryAdapter extends BaseAdapter {

    private List<Country> paises;
    private OnItemClickListener listener;
    private Context context;

    private CountryVisitedController countryVisitedController;
    private User userLogged;

    public CountryAdapter(Context context, List<Country> paises, OnItemClickListener listener) {
        this.context = context;
        this.paises = paises;
        this.listener = listener;
        this.countryVisitedController = new CountryVisitedController(context);

        UserController userController = new UserController(context);
        this.userLogged = userController.getUserPreferences();
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

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.country_list_item, parent, false);

        viewHolder = new ViewHolder();
        viewHolder.tvPais = (TextView) view.findViewById(R.id.tvPais);
        viewHolder.ivPais = (ImageView) view.findViewById(R.id.ivPais);
        viewHolder.ivVisited = (ImageView) view.findViewById(R.id.ivVisited);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(country);
            }
        });


        String imagePaisUrl = ServiceUtil.URL_MYPUSH + "world/countries/" + country.getId() + "/flag";
        Picasso.with(context)
                .load(imagePaisUrl)
                .into(viewHolder.ivPais);

        viewHolder.tvPais.setText(country.getShortname());

        if (countryVisitedController.getCountryVisitedByIdAndIdFacebook(country.getId(), userLogged.getIdFacebook()) != null) {
            viewHolder.ivVisited.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_done_blue_700_24dp));
            country.setVisited(true);
        } else {
            viewHolder.ivVisited.setImageDrawable(null);
            country.setVisited(false);
        }

        return view;
    }


    public interface OnItemClickListener {
        void onClick(Country country);
    }

    private static class ViewHolder {
        public TextView tvPais;
        public ImageView ivPais;
        public ImageView ivVisited;
    }

}
