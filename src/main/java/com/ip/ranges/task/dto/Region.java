package com.ip.ranges.task.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Region implements Serializable {
  @JsonProperty("ip_prefix")
  private String ipPrefix;
  @JsonProperty("region")
  private String regionName;
  private String service;
  @JsonProperty("network_border_group")
  private String networkBorderGroup;

  public Region() {
  }

  public Region(String ipPrefix, String regionName, String service, String networkBorderGroup) {
    this.ipPrefix = ipPrefix;
    this.regionName = regionName;
    this.service = service;
    this.networkBorderGroup = networkBorderGroup;
  }

  public boolean isRegionContains(String region) {
    return this.regionName.contains(region.toLowerCase());
  }
  public String getIpPrefix() {
    return ipPrefix;
  }

  public void setIpPrefix(String ipPrefix) {
    this.ipPrefix = ipPrefix;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getNetworkBorderGroup() {
    return networkBorderGroup;
  }

  public void setNetworkBorderGroup(String networkBorderGroup) {
    this.networkBorderGroup = networkBorderGroup;
  }

  @Override
  public String toString() {
    return "RegionIp{" +
      "ipPrefix='" + ipPrefix + '\'' +
      ", region='" + regionName + '\'' +
      ", service='" + service + '\'' +
      ", networkBorderGroup='" + networkBorderGroup + '\'' +
      '}';
  }
}
