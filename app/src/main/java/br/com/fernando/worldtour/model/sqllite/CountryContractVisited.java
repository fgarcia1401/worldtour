package br.com.fernando.worldtour.model.sqllite;

/**
 * Created by fernando on 04/09/16.
 */
public class CountryContractVisited {

    public static final String TABLE_NAME = "country_visited";

    public static final class Columns {
        public static final String _ID = "_id";
        public static final String ID = "id";
        public static final String ISO = "iso";
        public static final String SHORTNAME = "shortname";
        public static final String LONGNAME = "longname";
        public static final String CALLING_CODE = "callingCode";
        public static final String STATUS = "status";
        public static final String CULTURE = "culture";
        public static final String ID_FACEBOOK = "id_facebook";
        public static final String DATA_VISITED = "data_visited";

    }
}
