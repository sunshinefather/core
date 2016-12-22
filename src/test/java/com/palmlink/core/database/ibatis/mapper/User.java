package com.palmlink.core.database.ibatis.mapper;

import java.io.Serializable;

public class User implements Serializable {

    private Integer id;

    private String name;

    private String aliaseName;

    private String passwordHash;

    private String passwordSalt;

    private Integer passwordIteration;

    private String appKey;

    private String secretKey;

    private Boolean status;

    private Boolean pwdReset;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the aliaseName
     */
    public String getAliaseName() {
        return aliaseName;
    }

    /**
     * @param aliaseName
     *            the aliaseName to set
     */
    public void setAliaseName(String aliaseName) {
        this.aliaseName = aliaseName;
    }

    /**
     * @return the passwordHash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * @param passwordHash
     *            the passwordHash to set
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * @return the passwordSalt
     */
    public String getPasswordSalt() {
        return passwordSalt;
    }

    /**
     * @param passwordSalt
     *            the passwordSalt to set
     */
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    /**
     * @return the passwordIteration
     */
    public Integer getPasswordIteration() {
        return passwordIteration;
    }

    /**
     * @param passwordIteration
     *            the passwordIteration to set
     */
    public void setPasswordIteration(Integer passwordIteration) {
        this.passwordIteration = passwordIteration;
    }

    /**
     * @return the appKey
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * @param appKey
     *            the appKey to set
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * @return the secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * @param secretKey
     *            the secretKey to set
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * @return the status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return the pwdReset
     */
    public Boolean getPwdReset() {
        return pwdReset;
    }

    /**
     * @param pwdReset
     *            the pwdReset to set
     */
    public void setPwdReset(Boolean pwdReset) {
        this.pwdReset = pwdReset;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", aliaseName=" + aliaseName + ", passwordHash=" + passwordHash + ", passwordSalt=" + passwordSalt + ", passwordIteration=" + passwordIteration + ", appKey=" + appKey + ", secretKey=" + secretKey + ", status=" + status + ", pwdReset=" + pwdReset
                + "]";
    }

}
