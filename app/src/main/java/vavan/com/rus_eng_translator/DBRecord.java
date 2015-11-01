package vavan.com.rus_eng_translator;

/**
 * Class help in working with database, it`s object has to strings: initial and translated
 */
public class DBRecord {

    private int id;
    private String langFrom;
    private String langTo;

    public DBRecord(){

    }

    public DBRecord(String _langFrom,String _langTo){
        langFrom = _langFrom;
        langTo = _langTo;
    }

    public DBRecord(int _id,String _langFrom,String _langTo){
        id = _id;
        langFrom = _langFrom;
        langTo = _langTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        id = _id;
    }

    public String getLangFrom() {
        return langFrom;
    }

    public void setLangFrom(String _langFrom) {
        langFrom = _langFrom;
    }

    public String getLangTo() {
        return langTo;
    }

    public void setLangTo(String _langTo) {
        langTo = _langTo;
    }


}
