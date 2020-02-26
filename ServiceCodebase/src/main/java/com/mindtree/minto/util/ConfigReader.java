/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * PropertiesUtil.java Created On: Feb 22, 2020 Created By: M1026329
 */
@ConfigurationProperties("minto-pay")
public class ConfigReader {

    private static String pwdEncryption;

    private static String coinContractID;

    private static String masterWalletID;

    private static String bearerToken;
    
    private static String balanceURL;
    
    private static String transactionsURL;
    
    private static String fundWalletURL;
    
    private static String authUserName;
    
    private static String authPWD;
    
    private static String microsoftFaceAPI;
    
    private static String faceAPISubscriptionKey;
    
    private static String walletCreationURL;

    /**
     * @return the pwdEncryption
     */
    public static String getPwdEncryption() {
        return pwdEncryption;
    }

    /**
     * @param pwdEncryption
     *            the pwdEncryption to set
     */
    public void setPwdEncryption(String pwdEncryption) {
        ConfigReader.pwdEncryption = pwdEncryption;
    }

    /**
     * @return the coinContractID
     */
    public static String getCoinContractID() {
        return coinContractID;
    }

    /**
     * @param coinContractID
     *            the coinContractID to set
     */
    public void setCoinContractID(String coinContractID) {
        ConfigReader.coinContractID = coinContractID;
    }

    /**
     * @return the masterWalletID
     */
    public static String getMasterWalletID() {
        return masterWalletID;
    }

    /**
     * @param masterWalletID
     *            the masterWalletID to set
     */
    public void setMasterWalletID(String masterWalletID) {
        ConfigReader.masterWalletID = masterWalletID;
    }

    /**
     * @return the authToken
     */
    public static String getBearerToken() {
        return bearerToken;
    }

    /**
     * @param authToken
     *            the authToken to set
     */
    public void setBearerToken(String bearerToken) {
        ConfigReader.bearerToken = bearerToken;
    }

    /**
     * @return the balanceURL
     */
    public static String getBalanceURL() {
        return balanceURL;
    }

    /**
     * @param balanceURL the balanceURL to set
     */
    public void setBalanceURL(String balanceURL) {
        ConfigReader.balanceURL = balanceURL;
    }

    /**
     * @return the transactionsURL
     */
    public static String getTransactionsURL() {
        return transactionsURL;
    }

    /**
     * @param transactionsURL the transactionsURL to set
     */
    public void setTransactionsURL(String transactionsURL) {
        ConfigReader.transactionsURL = transactionsURL;
    }

    /**
     * @return the fundWalletURL
     */
    public static String getFundWalletURL() {
        return fundWalletURL;
    }

    /**
     * @param fundWalletURL the fundWalletURL to set
     */
    public void setFundWalletURL(String fundWalletURL) {
        ConfigReader.fundWalletURL = fundWalletURL;
    }

    /**
     * @return the authUserName
     */
    public static String getAuthUserName() {
        return authUserName;
    }

    /**
     * @param authUserName the authUserName to set
     */
    public void setAuthUserName(String authUserName) {
        ConfigReader.authUserName = authUserName;
    }

    /**
     * @return the authPWD
     */
    public static String getAuthPWD() {
        return authPWD;
    }

    /**
     * @param authPWD the authPWD to set
     */
    public void setAuthPWD(String authPWD) {
        ConfigReader.authPWD = authPWD;
    }

    /**
     * @return the microsoftFaceAPI
     */
    public static String getMicrosoftFaceAPI() {
        return microsoftFaceAPI;
    }

    /**
     * @param microsoftFaceAPI the microsoftFaceAPI to set
     */
    public void setMicrosoftFaceAPI(String microsoftFaceAPI) {
        ConfigReader.microsoftFaceAPI = microsoftFaceAPI;
    }

    /**
     * @return the faceAPISubscriptionKey
     */
    public static String getFaceAPISubscriptionKey() {
        return faceAPISubscriptionKey;
    }

    /**
     * @param faceAPISubscriptionKey the faceAPISubscriptionKey to set
     */
    public void setFaceAPISubscriptionKey(String faceAPISubscriptionKey) {
        ConfigReader.faceAPISubscriptionKey = faceAPISubscriptionKey;
    }

    /**
     * @return the walletCreationURL
     */
    public static String getWalletCreationURL() {
        return walletCreationURL;
    }

    /**
     * @param walletCreationURL the walletCreationURL to set
     */
    public void setWalletCreationURL(String walletCreationURL) {
        ConfigReader.walletCreationURL = walletCreationURL;
    }

}
