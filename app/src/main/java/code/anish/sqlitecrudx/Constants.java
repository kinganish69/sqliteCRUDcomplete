package code.anish.sqlitecrudx;

public class Constants {

    //db name
    public static final String DB_NAME = "PERSON_INFO_DB";
    //db version
    public static final int DB_VERSION = 1;
    //db table
    public static final String TABLE_NAME = "PERSON_INFO_TABLE";
    //table columns
    public static final String C_ID = "ID";
    public static final String C_NAME = "NAME";
    public static final String C_AGE = "AGE";
    public static final String C_PHONE = "PHONE";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_ADD_TIMESTAMP = "ADD_TIMESTAMP";
    public static final String C_UPDATE_TIMESTAMP = "UPDATE_TIMESTAMP";
    //create query for table
    public static final String CREATE_TABLE = "CREATE TABLE" +TABLE_NAME + "("
            +C_ID + " INSERT PRIMARY KEY AUTOINCREMENT,"
            +C_NAME + " TEXT,"
            +C_AGE + " TEXT,"
            +C_PHONE + " TEXT,"
            +C_IMAGE + " TEXT,"
            +C_ADD_TIMESTAMP + " TEXT,"
            +C_UPDATE_TIMESTAMP + " TEXT,"
            +");";


}
