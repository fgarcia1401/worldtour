package br.com.fernando.worldtour.model.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fernando on 04/09/16.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "worldTourdb";
    public static final int DB_VERSION = 1;

    private static final String SQL_DROP_COUNTRY_VISITED = "DROP TABLE IF EXISTS " + CountryContractVisited.TABLE_NAME;
    private static final String SQL_CREATE_COUNTRY_VISITED = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER NOT NULL," +
                    "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL,%s TEXT NOT NULL, %s TEXT NOT NULL," +
                    "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL)",
            CountryContractVisited.TABLE_NAME,
            CountryContractVisited.Columns._ID, CountryContractVisited.Columns.ID, CountryContractVisited.Columns.ISO,
            CountryContractVisited.Columns.SHORTNAME, CountryContractVisited.Columns.LONGNAME,
            CountryContractVisited.Columns.CALLING_CODE, CountryContractVisited.Columns.STATUS, CountryContractVisited.Columns.CULTURE,
            CountryContractVisited.Columns.ID_FACEBOOK, CountryContractVisited.Columns.DATA_VISITED);


    private static final String SQL_DROP_COUNTRY = "DROP TABLE IF EXISTS " + CountryContract.TABLE_NAME;
    private static final String SQL_CREATE_COUNTRY = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER NOT NULL," +
                    "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL,%s TEXT NOT NULL, %s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            CountryContract.TABLE_NAME,
            CountryContract.Columns._ID, CountryContractVisited.Columns.ID, CountryContractVisited.Columns.ISO,
            CountryContract.Columns.SHORTNAME, CountryContractVisited.Columns.LONGNAME,
            CountryContract.Columns.CALLING_CODE, CountryContractVisited.Columns.STATUS,
            CountryContract.Columns.CULTURE);

    private static DBHelper instance;

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DROP_COUNTRY_VISITED);
        db.execSQL(SQL_CREATE_COUNTRY_VISITED);

        db.execSQL(SQL_DROP_COUNTRY);
        db.execSQL(SQL_CREATE_COUNTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}