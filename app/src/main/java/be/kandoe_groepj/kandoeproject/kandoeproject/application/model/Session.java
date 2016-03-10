package be.kandoe_groepj.kandoeproject.kandoeproject.application.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jan on 3/03/2016.
 */
public class Session implements Serializable {
    private String _id;
    private String _groupId;
    private String[] _userIds;
    private String _name;
    private String _themeId;
    private String _creatorId;
    /* _startDate format: dd/mm/yyyy hh:mm */
    private String _startDate;
    private boolean _inProgress;
    private boolean _realTime;
    private int _endPoint;
    private boolean _allowComment;
    private int _turnTimeMin;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String get_groupId() {
        return _groupId;
    }

    public void set_groupId(String _groupId) {
        this._groupId = _groupId;
    }

    public String[] get_userIds() {
        return _userIds;
    }

    public void set_userIds(String[] _userIds) {
        this._userIds = _userIds;
    }

    public String get_themeId() {
        return _themeId;
    }

    public void set_themeId(String _themeId) {
        this._themeId = _themeId;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
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

    public int get_endPoint() {
        return _endPoint;
    }

    public void set_endPoint(int _endPoint) {
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
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public String get_id() {

        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}