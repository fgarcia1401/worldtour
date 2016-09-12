package br.com.fernando.worldtour.controller;

import android.content.Context;

import java.util.List;

import br.com.fernando.worldtour.model.dao.CountryVisitedDAO;
import br.com.fernando.worldtour.model.domain.Country;

/**
 * Created by fernando on 04/09/16.
 */
public class CountryVisitedController {

    private Context context;


    public CountryVisitedController(Context context) {
        this.context = context;
    }

    public boolean save(Country country) {
        CountryVisitedDAO countryVisitedDAO = CountryVisitedDAO.getInstance(context);
        Long id = countryVisitedDAO.save(country);
        return id > 0 ? true : false;
    }

    public boolean edit(Country country) {
        CountryVisitedDAO countryVisitedDAO = CountryVisitedDAO.getInstance(context);
        Integer id = countryVisitedDAO.update(country);
        return id > 0 ? true : false;
    }

    public boolean delete(Country country) {
        CountryVisitedDAO countryVisitedDAO = CountryVisitedDAO.getInstance(context);
        Integer id = countryVisitedDAO.delete(country);
        return id > 0 ? true : false;
    }

    public List<Country> getAllCountryVisitedByIdFacebook(String idFacebook){
        CountryVisitedDAO countryVisitedDAO = CountryVisitedDAO.getInstance(context);
        return countryVisitedDAO.getAllCountryVisitedByIdFacebook(idFacebook);
    }

    public Country getCountryVisitedByIdAndIdFacebook(Integer id, String idFacebook) {
        CountryVisitedDAO countryVisitedDAO = CountryVisitedDAO.getInstance(context);
        return countryVisitedDAO.getCountryVisitedByIdAndIdFacebook(id,idFacebook);
    }

}





