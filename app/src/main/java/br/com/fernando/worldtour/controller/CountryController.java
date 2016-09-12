package br.com.fernando.worldtour.controller;

import android.content.Context;

import java.util.List;

import br.com.fernando.worldtour.model.dao.CountryDAO;
import br.com.fernando.worldtour.model.domain.Country;

/**
 * Created by fernando on 04/09/16.
 */
public class CountryController {

    private Context context;


    public CountryController(Context context) {
        this.context = context;
    }

    public boolean save(Country country) {
        CountryDAO paisVisitedDAO = CountryDAO.getInstance(context);
        Long id = paisVisitedDAO.save(country);
        return id > 0 ? true : false;
    }

    public List<Country> list() {
        CountryDAO paisVisitedDAO = CountryDAO.getInstance(context);
        return paisVisitedDAO.listAll();
    }

}





