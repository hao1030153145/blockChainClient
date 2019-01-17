package com.smartdatachain.api.integration.bo;

public class WorkGroupRegulationSettingsBO {


    private boolean loadImages;
    private boolean loadAds;
    private boolean userAgent;
    private String reborn;
    private String basePath;
    private boolean proxy;
    private int timeoutAjax;
    private boolean downloadImage;
    private String cron;
    private boolean startCron;
    private boolean publishAutomatic;
    private String savedConfigName;

    public boolean isLoadImages() {
        return loadImages;
    }

    public void setLoadImages(boolean loadImages) {
        this.loadImages = loadImages;
    }

    public boolean isLoadAds() {
        return loadAds;
    }

    public void setLoadAds(boolean loadAds) {
        this.loadAds = loadAds;
    }

    public boolean isUserAgent() {
        return userAgent;
    }

    public void setUserAgent(boolean userAgent) {
        this.userAgent = userAgent;
    }

    public String getReborn() {
        return reborn;
    }

    public void setReborn(String reborn) {
        this.reborn = reborn;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public boolean isProxy() {
        return proxy;
    }

    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }

    public int getTimeoutAjax() {
        return timeoutAjax;
    }

    public void setTimeoutAjax(int timeoutAjax) {
        this.timeoutAjax = timeoutAjax;
    }

    public boolean isDownloadImage() {
        return downloadImage;
    }

    public void setDownloadImage(boolean downloadImage) {
        this.downloadImage = downloadImage;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public boolean isStartCron() {
        return startCron;
    }

    public void setStartCron(boolean startCron) {
        this.startCron = startCron;
    }

    public boolean isPublishAutomatic() {
        return publishAutomatic;
    }

    public void setPublishAutomatic(boolean publishAutomatic) {
        this.publishAutomatic = publishAutomatic;
    }

    public String getSavedConfigName() {
        return savedConfigName;
    }

    public void setSavedConfigName(String savedConfigName) {
        this.savedConfigName = savedConfigName;
    }
}
