package com.ip.ranges.task.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.buf.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ip.ranges.task.dto.Region;

@Service
public class RegionServiceImpl implements RegionService {
  private static final String AWS_IP_RANGES_URI = "https://ip-ranges.amazonaws.com/ip-ranges.json";
  private static final String REGIONS_JSON_ARRAY_NAME = "prefixes";
  private final RestTemplate restTemplate;
  private final ObjectMapper mapper;

  public RegionServiceImpl(RestTemplate restTemplate, ObjectMapper mapper) {
    this.restTemplate = restTemplate;
    this.mapper = mapper;
  }

  @Override
  public String getRegionIpAddresses(String region) {
    String responseResult = restTemplate.getForObject(AWS_IP_RANGES_URI, String.class);
    return convertResponseForRegion(responseResult, region);
  }

  private String convertResponseForRegion(String result, String region) {
    return StringUtils
      .join(mapper.convertValue(convertResultToJsonNode(result, REGIONS_JSON_ARRAY_NAME),
          new TypeReference<List<Region>>() {
          })
        .stream()
        .filter(reg -> reg.isRegionContains(region))
        .map(Region::getIpPrefix)
        .collect(Collectors.toList()), '\n');
  }

  private JsonNode convertResultToJsonNode(String result, String arrayPropertyName) {
    JsonNode jsonRegionsNode;
    try {
      JSONObject jsonResponse = new JSONObject(result);
      jsonRegionsNode = mapper.readTree(String.valueOf(jsonResponse.getJSONArray(arrayPropertyName)));
    } catch (JSONException | JsonProcessingException ex) {
      throw new RuntimeException("Could not convert json, reason: " + ex);
    }
    return jsonRegionsNode;
  }
}
