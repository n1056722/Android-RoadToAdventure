package ya.haojun.roadtoadventure.model;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class User extends CommonModel {
    private static final User ourInstance = new User();
    private String userId;
    private String password;
    private String userName;
    private String userPicture;
    private String email;
    private String modifyDate;
    private String verificationCode;
    private String oldPassword;
    private String newPassword;


    public static User getInstance() {
        return ourInstance;
    }

    public User() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public JSONObject getJSONObject() {
        try {
            JSONObject j = new JSONObject();
            j.put("userId", userId);
            j.put("userName", userName);
            j.put("userPicture", userPicture);
            j.put("email", email);
            j.put("modifyDate", modifyDate);
            return j;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setJSONObject(JSONObject j){
        try {
            setUserId(j.getString("userId"));
            setUserName(j.getString("userName"));
            setUserPicture(j.getString("userPicture"));
            setEmail(j.getString("email"));
            setModifyDate(j.getString("modifyDate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
