package gbe.demoaapi.app.Configuration;

import static gbe.demoaapi.app.CommonFunctions.*;

public class Settings {

    private String url;
    private String proxy;
    private int proxyPort;
    private String aAPIVersion;
    private int deltaCounterPrintTimeInMs;

    public Settings(String url, String proxy, int proxyPort, String aAPIVersion, int deltaCounterPrintTimeInMs) {
        this.url = url;
        this.proxy = proxy;
        this.proxyPort = proxyPort;
        this.aAPIVersion = aAPIVersion;
        this.deltaCounterPrintTimeInMs = deltaCounterPrintTimeInMs;
    }

    public String getUrl() {
        return url;
    }

    public String getProxy() {
        return proxy;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public String getaAPIVersion() {
        return aAPIVersion;
    }

    public int getDeltaCounterPrintTimeInMs() {
        return deltaCounterPrintTimeInMs;
    }

    public boolean areValid(){
        return  !isNullOrEmpty(url) &&
                !isNullOrEmpty(proxy) &&
                (proxyPort > 0) &&
                !isNullOrEmpty(aAPIVersion) &&
                (deltaCounterPrintTimeInMs > 0);
    }

    public boolean areValidWithoutProxy(){
        return  !isNullOrEmpty(url) &&
                !isNullOrEmpty(aAPIVersion) &&
                (deltaCounterPrintTimeInMs > 0);
    }


}
