package br.com.fernando.worldtour.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.fernando.worldtour.model.domain.Country;
import br.com.fernando.worldtour.model.sqllite.DBHelper;
import br.com.fernando.worldtour.model.sqllite.CountryContract;

/**
 * Created by fernando on 04/09/16.
 */

public class CountryDAO {

    private static CountryDAO instance;

    private SQLiteDatabase db;

    public static CountryDAO getInstance(Context context) {
        if (instance == null) {
            instance = new CountryDAO(context.getApplicationContext());
        }
        return instance;
    }

    private CountryDAO(Context context) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    public long save(Country country) {
        ContentValues values = new ContentValues();
        values.put(CountryContract.Columns.ID, country.getId());
        values.put(CountryContract.Columns.ISO, country.getIso());
        values.put(CountryContract.Columns.SHORTNAME, country.getShortname());
        values.put(CountryContract.Columns.LONGNAME, country.getLongname());
        values.put(CountryContract.Columns.CALLING_CODE, country.getCallingCode());
        values.put(CountryContract.Columns.STATUS, country.getStatus());
        values.put(CountryContract.Columns.CULTURE, country.getCulture());
        long _id = db.insert(CountryContract.TABLE_NAME, null, values);
        country.set_id(_id);
        return _id;
    }


    public List<Country> listAll() {

        String[] columns = {CountryContract.Columns._ID, CountryContract.Columns.ID, CountryContract.Columns.ISO,
                CountryContract.Columns.SHORTNAME, CountryContract.Columns.LONGNAME, CountryContract.Columns.CALLING_CODE,
                CountryContract.Columns.STATUS, CountryContract.Columns.CULTURE};

        Cursor c = db.query(CountryContract.TABLE_NAME, columns, null, null, null, null, null);
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
                    paises.add(country);
                }
            } finally {
                c.close();
            }

        }

        return paises;
    }

}