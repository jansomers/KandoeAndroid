package be.kandoe_groepj.kandoeproject.kandoeproject.session;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jan on 3/03/2016.
 */
public class Session {

    private String _id;
    private String _groupId;
    private String _themeId;
    private String _creatorId;
    private String _startDate;
    private boolean _inProgress;
    private boolean _realTime;
    private Object _endPoint;
    private boolean _allowComment;
    private int _turnTimeMin;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_groupId() {
        return _groupId;
    }

    public void set_groupId(String _groupId) {
        this._groupId = _groupId;
    }

    public String get_themeId() {
        return _themeId;
    }

    public void set_themeId(String _themeId) {
        this._themeId = _themeId;
    }

    public String get_creatorId() {
        return _creatorId;
    }

    public void set_creatorId(String _creatorId) {
        this._creatorId = _creatorId;
    }

    public String get_startDate() {
        return _startDate;
    }

    public void set_startDate(String _startDate) {
        this._startDate = _startDate;
    }

    public boolean is_inProgress() {
        return _inProgress;
    }

    public void set_inProgress(boolean _inProgress) {
        this._inProgress = _inProgress;
    }

    public boolean is_realTime() {
        return _realTime;
    }

    public void set_realTime(boolean _realTime) {
        this._realTime = _realTime;
    }

    public Object get_endPoint() {
        return _endPoint;
    }

    public void set_endPoint(Object _endPoint) {
        this._endPoint = _endPoint;
    }

    public boolean is_allowComment() {
        return _allowComment;
    }

    public void set_allowComment(boolean _allowComment) {
        this._allowComment = _allowComment;
    }

    public int get_turnTimeMin() {
        return _turnTimeMin;
    }

    public void set_turnTimeMin(int _turnTimeMin) {
        this._turnTimeMin = _turnTimeMin;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public boolean isOpenOrClosed() {
        return true;
    }

    public String getTimeRemaining() {
        return "1d2h3m";
    }
}
