package br.com.fernando.worldtour.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Arrays;
import java.util.List;

import br.com.fernando.worldtour.R;
import br.com.fernando.worldtour.adapter.CountryAdapter;
import br.com.fernando.worldtour.controller.CountryController;
import br.com.fernando.worldtour.model.domain.Country;
import br.com.fernando.worldtour.service.ServiceUtil;
import br.com.fernando.worldtour.service.volley.GsonRequest;
import br.com.fernando.worldtour.service.volley.MySingleton;

/**
 * Created by fernando on 06/09/16.
 */
public class ListCountryFragment extends Fragment implements CountryAdapter.OnItemClickListener {

    private ListView lvPaises;
    private ProgressDialog progressDialog;

    private List<Country> listCountry;

    private CountryController countryController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fragment_list_country, container, false);
        lvPaises = (ListView) view.findViewById(R.id.lvPaises);
        countryController = new CountryController(getActivity().getApplicationContext());

        listCountry();

        return view;
    }

    private void listCountry() {
        listCountry = countryController.list();

        if(listCountry == null || listCountry.size() == 0 ) {
            requestListarPaises();;
        } else {
            carregarPaises();
        }
    }

    private void requestListarPaises() {
        String urlListarPaises = ServiceUtil.URL_MYPUSH + "world/countries/active";

        this.showProgressDialog();
        GsonRequest<Country[]> getCountries = new GsonRequest<Country[]>(urlListarPaises, Country[].class, null,

                new Response.Listener<Country[]>() {
                    @Override
                    public void onResponse(Country[] response) {
                        listCountry = Arrays.asList(response);
                        saveCountry();
                        carregarPaises();
                        progressDialog.dismiss();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(ListCountryFragment.class.getName(), "Erro ao listar Países, erro: " + error.getMessage());
                Toast.makeText(getActivity(),getString(R.string.erro_listar_paises),Toast.LENGTH_SHORT).show();
                ;            }
        });


        MySingleton.getInstance(getActivity()).addToRequestQueue(getCountries);
    }

    private void saveCountry() {

        for(Country country : listCountry) {
            countryController.save(country);
        }

    }

    private void carregarPaises() {
        CountryAdapter countryAdapter = new CountryAdapter(getActivity(), listCountry, this);
        lvPaises.setAdapter(countryAdapter);
    }

    private void showProgressDialog() {
        progressDialog = ProgressDialog.show(getActivity(), getString(R.string.title_aguarde), getString(R.string.title_carregando), true);
        progressDialog.show();
    }

    @Override
    public void onClick(Country country) {

        DetailPaisFragment detailPaisFragment = new DetailPaisFragment();
        detailPaisFragment.setCountry(country);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, detailPaisFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
