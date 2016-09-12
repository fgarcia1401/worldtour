package br.com.fernando.worldtour.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.fernando.worldtour.model.domain.Country;
import br.com.fernando.worldtour.model.sqllite.DBHelper;
import br.com.fernando.worldtour.model.sqllite.CountryContractVisited;

/**
 * Created by fernando on 04/09/16.
 */

public class CountryVisitedDAO {

    private static CountryVisitedDAO instance;

    private SQLiteDatabase db;

    public static CountryVisitedDAO getInstance(Context context) {
        if (instance == null) {
            instance = new CountryVisitedDAO(context.getApplicationContext());
        }
        return instance;
    }

    private CountryVisitedDAO(Context context) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    public long save(Country country) {
        ContentValues values = new ContentValues();
        values.put(CountryContractVisited.Columns.ID, country.getId());
        values.put(CountryContractVisited.Columns.ISO, country.getIso());
        values.put(CountryContractVisited.Columns.SHORTNAME, country.getShortname());
        values.put(CountryContractVisited.Columns.LONGNAME, country.getLongname());
        values.put(CountryContractVisited.Columns.CALLING_CODE, country.getCallingCode());
        values.put(CountryContractVisited.Columns.STATUS, country.getStatus());
        values.put(CountryContractVisited.Columns.CULTURE, country.getCulture());
        values.put(CountryContractVisited.Columns.ID_FACEBOOK, country.getIdFacebook());
        values.put(CountryContractVisited.Columns.DATA_VISITED, country.getDataVisited());
        long _id = db.insert(CountryContractVisited.TABLE_NAME, null, values);
        country.set_id(_id);
        return _id;
    }


    public Country getCountryVisitedByIdAndIdFacebook(Integer id, String idFacebook) {

        String[] columns = {CountryContractVisited.Columns._ID, CountryContractVisited.Columns.DATA_VISITED};
        String where = CountryContractVisited.Columns.ID + "=? and " + CountryContractVisited.Columns.ID_FACEBOOK + "=?";
        String[] args = {String.valueOf(id), idFacebook};

        Cursor c = db.query(CountryContractVisited.TABLE_NAME, columns, where, args, null, null, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            Country country = new Country();
            country.set_id(c.getLong(0));
            country.setDataVisited(c.getString(1));

            return country;
        }

        return null;
    }

    public List<Country> getAllCountryVisitedByIdFacebook(String idFacebook) {

        String[] columns = {CountryContractVisited.Columns._ID, CountryContractVisited.Columns.ID, CountryContractVisited.Columns.ISO,
                CountryContractVisited.Columns.SHORTNAME, CountryContractVisited.Columns.LONGNAME, CountryContractVisited.Columns.CALLING_CODE,
                CountryContractVisited.Columns.STATUS, CountryContractVisited.Columns.CULTURE, CountryContractVisited.Columns.ID_FACEBOOK,
                CountryContractVisited.Columns.DATA_VISITED};

        String where = CountryContractVisited.Columns.ID_FACEBOOK + "=?";
        String[] args = {idFacebook};

        Cursor c = db.query(CountryContractVisited.TABLE_NAME, columns, where, args, null, null, null);
        List<Country> paises = new ArrayList<Country>();


        if (c.getCount() > 0) {

            try {
                while (c.moveToNext()) {
                    Country country = new Country();
                    country.set_id(c.getLong(0));
                    country.setId(c.getInt(1));
                    country.setIso(c.getString(2));
                    country.setShortname(c.getString(3));
                    country.setLongname(c.getString(4));
                    country.setCallingCode(c.getString(5));
                    country.setStatus(c.getString(6));
                    country.setCulture(c.getString(7));
                    country.setIdFacebook(c.getString(8));
                    country.setDataVisited(c.getString(9));

                    paises.add(country);
                }
            } finally {
                c.close();
            }

        }

        return paises;
    }


    public Integer update(Country country) {
        ContentValues values = new ContentValues();
        values.put(CountryContractVisited.Columns.ID, country.getId());
        values.put(CountryContractVisited.Columns.ISO, country.getIso());
        values.put(CountryContractVisited.Columns.SHORTNAME, country.getShortname());
        values.put(CountryContractVisited.Columns.LONGNAME, country.getLongname());
        values.put(CountryContractVisited.Columns.CALLING_CODE, country.getCallingCode());
        values.put(CountryContractVisited.Columns.STATUS, country.getStatus());
        values.put(CountryContractVisited.Columns.CULTURE, country.getCulture());
        values.put(CountryContractVisited.Columns.ID_FACEBOOK, country.getIdFacebook());
        values.put(CountryContractVisited.Columns.DATA_VISITED, country.getDataVisited());
        return db.update(CountryContractVisited.TABLE_NAME, values, CountryContractVisited.Columns._ID + " = ?", new String[]{String.valueOf(country.get_id())});
    }

    
    public Integer delete(Country country) {
        return db.delete(CountryContractVisited.TABLE_NAME, CountryContractVisited.Columns._ID + " = ?", new String[]{String.valueOf(country.get_id())});
    }

    
}