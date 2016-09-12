package br.com.fernando.worldtour.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.com.fernando.worldtour.R;
import br.com.fernando.worldtour.controller.CountryVisitedController;
import br.com.fernando.worldtour.controller.UserController;
import br.com.fernando.worldtour.model.domain.Country;
import br.com.fernando.worldtour.model.domain.User;
import br.com.fernando.worldtour.service.ServiceUtil;

/**
 * Created by fernando on 06/09/16.
 */
public class DetailPaisFragment extends Fragment {

    private EditText edtShortName;
    private EditText edtLongName;
    private EditText edtCallingCode;
    private EditText edtDataVisited;
    private TextView txtErro;
    private ImageView ivBandeira;
    private CheckBox cbVisitouPais;
    private Button btnSave;

    private UserController userController;
    private CountryVisitedController countryVisitedController;

    private Country country;
    private User userLogged;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }


        View view = inflater.inflate(R.layout.fragment_detail_pais, container, false);

        edtShortName = (EditText) view.findViewById(R.id.edtShortName);
        edtLongName = (EditText) view.findViewById(R.id.edtLongName);
        edtCallingCode = (EditText) view.findViewById(R.id.edtCallingCode);
        edtDataVisited = (EditText) view.findViewById(R.id.edtDataVisited);
        txtErro = (TextView) view.findViewById(R.id.txtErro);
        ivBandeira = (ImageView) view.findViewById(R.id.ivBandeira);
        cbVisitouPais = (CheckBox) view.findViewById(R.id.cbVisitouPais);
        btnSave = (Button) view.findViewById(R.id.btnSave);

        userController = new UserController(getActivity().getApplicationContext());
        countryVisitedController = new CountryVisitedController(getActivity().getApplicationContext());
        userLogged = userController.getUserPreferences();

        loadDetailCountry();
        listenerSaveCountry();

        edtDataVisited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDataVencimento(edtDataVisited);
            }
        });

        cbVisitouPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (((CheckBox) v).isChecked()) {
                 btnSave.setEnabled(true);
                 edtDataVisited.setVisibility(View.VISIBLE);
             } else {
                 btnSave.setEnabled(false);
                 edtDataVisited.setVisibility(View.GONE);
             }
            }
        });

        return view;
    }

    private void listenerSaveCountry() {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if (validarDados()) {
                 persistCountry();
                 getFragmentManager().popBackStack();
             }

            }
        });
    }

    private void loadDetailCountry() {
        edtShortName.setText(this.country.getShortname());
        edtLongName.setText(this.country.getLongname());
        edtCallingCode.setText(this.country.getCallingCode());

        String imageCountryUrl = ServiceUtil.URL_MYPUSH + "world/countries/" + this.country.getId() + "/flag";
        Picasso.with(getActivity()).load(imageCountryUrl).into(ivBandeira);


        if (country.isVisited()) {
            Country countryVisited = countryVisitedController.getCountryVisitedByIdAndIdFacebook(country.getId(), userLogged.getIdFacebook());
            edtDataVisited.setText(countryVisited.getDataVisited());
            country.set_id(countryVisited.get_id());

            edtDataVisited.setVisibility(View.VISIBLE);
            cbVisitouPais.setChecked(true);
            btnSave.setEnabled(true);
        }

    }


    private void showDatePickerDataVencimento(View v) {
        DatePickerFragment dateFragment = new DatePickerFragment(edtDataVisited);
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    private boolean validarDados() {
        boolean retorno = true;

        if (cbVisitouPais.isChecked() && edtDataVisited.getText().toString().length() == 0) {
            txtErro.setText(getString(R.string.informar_data_visitou_pais));
            txtErro.setVisibility(View.VISIBLE);
            retorno = false;
        } else {
            txtErro.setVisibility(View.GONE);
        }

        return retorno;
    }

    private void persistCountry() {
        this.country.setDataVisited(edtDataVisited.getText().toString());
        this.country.setIdFacebook(userLogged.getIdFacebook());

        if (country.isVisited()) {
            editCountry();
        } else {
            saveCountry();
        }
    }

    private void saveCountry() {
        if (countryVisitedController.save(country)) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.salvo_pais), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.erro_salvar_pais), Toast.LENGTH_SHORT).show();
        }
    }

    private void editCountry() {
        if (countryVisitedController.edit(country)) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.editar_pais), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.erro_salvar_pais), Toast.LENGTH_SHORT).show();
        }
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
