package be.kandoe_groepj.kandoeproject.kandoeproject.application.model;

/**
 * Created by Jan on 8/03/2016.
 */
public class Card {

    private String _id;
    private String _name;
    private String _themeId;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_themeId() {
        return _themeId;
    }

    public void set_themeId(String _themeId) {
        this._themeId = _themeId;
    }
}
