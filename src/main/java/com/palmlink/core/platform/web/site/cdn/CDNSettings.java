package com.palmlink.core.platform.web.site.cdn;

/**
 * @author Shihai.Fu
 */
public interface CDNSettings {
    
    String[] getCDNHosts();
    
    String[] getNFSHosts();
    
    String getLocalPath();

    boolean supportHTTPS();
    
    boolean supportS3();
    
    String getBucketName();
}
