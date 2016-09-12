package br.com.fernando.worldtour.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.fernando.worldtour.R;
import br.com.fernando.worldtour.adapter.CountryVisitedAdapter;
import br.com.fernando.worldtour.controller.CountryVisitedController;
import br.com.fernando.worldtour.controller.UserController;
import br.com.fernando.worldtour.model.domain.Country;
import br.com.fernando.worldtour.model.domain.User;

/**
 * Created by fernando on 06/09/16.
 */
public class ListCountryVisitedFragment extends Fragment implements CountryVisitedAdapter.OnItemClickListener {

    private ListView lvPaises;
    private Button btnDeletar;

    private List<Country> listCountry;

    private CountryVisitedController countryVisitedController;
    private UserController userController;
    private CountryVisitedAdapter countryVisitedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fragment_list_country_visited, container, false);
        lvPaises = (ListView) view.findViewById(R.id.lvPaises);
        btnDeletar = (Button) view.findViewById(R.id.btnDeletar);

        countryVisitedController = new CountryVisitedController(getActivity().getApplicationContext());
        userController = new UserController(getActivity().getApplicationContext());

        listCountry();
        listenerDeletar();

        return view;
    }

    private void listCountry() {
        User userLogged = userController.getUserPreferences();
        listCountry = countryVisitedController.getAllCountryVisitedByIdFacebook(userLogged.getIdFacebook());
        carregarPaises();

    }

    private void carregarPaises() {
        countryVisitedAdapter = new CountryVisitedAdapter(getActivity(), listCountry, this);
        lvPaises.setAdapter(countryVisitedAdapter);
    }


    private void listenerDeletar() {
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Country> paises = countryVisitedAdapter.getPaisesSelecionados();

                for(Country country :  paises) {

                    if(!countryVisitedController.delete(country)) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Falha ao deletar País " + country.getShortname(),Toast.LENGTH_SHORT).show();
                    }

                }
                listCountry();
                carregarPaises();
            }
        });
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
