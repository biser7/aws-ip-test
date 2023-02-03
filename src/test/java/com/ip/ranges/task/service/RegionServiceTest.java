package com.ip.ranges.task.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {
  private RegionService regionService;
  @Mock
  private RestTemplate restTemplate;
  private final ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    regionService = new RegionServiceImpl(restTemplate, mapper);
  }

  @Test
  void whenGetRegionIpAddressesShouldReturnRegionIpAddresses() {
    final String responseResult =
      "{\n" +
        "    \"syncToken\": \"1668649386\",\n" +
        "    \"createDate\": \"2022-11-17-01-43-06\",\n" +
        "    \"prefixes\": [" +
        "{\n" +
        "            \"ip_prefix\": \"35.180.0.0/16\",\n" +
        "            \"region\": \"eu-west-3\",\n" +
        "            \"service\": \"AMAZON\",\n" +
        "            \"network_border_group\": \"eu-west-3\"\n" +
        "        }" +
        "    ]\n" +
        "}";
    when(restTemplate.getForObject(anyString(), any())).thenReturn(responseResult);

    String result = regionService.getRegionIpAddresses("EU");

    Assertions.assertThat(result.equals("35.180.0.0/16")).isTrue();
  }

  @Test
  void whenGetRegionIpAddressesAWSResponseWithoutPrefixesArrayShouldThrowException() {
    final String responseResult =
      "{\n" +
        "    \"syncToken\": \"1668649386\",\n" +
        "    \"createDate\": \"2022-11-17-01-43-06\",\n" +
        "    \"sufixes\": [" +
        "{\n" +
        "            \"ip_prefix\": \"35.180.0.0/16\",\n" +
        "            \"region\": \"eu-west-3\",\n" +
        "            \"service\": \"AMAZON\",\n" +
        "            \"network_border_group\": \"eu-west-3\"\n" +
        "        }" +
        "    ]\n" +
        "}";
    when(restTemplate.getForObject(anyString(), any())).thenReturn(responseResult);

    Exception exception = assertThrows(RuntimeException.class,
      () -> regionService.getRegionIpAddresses("EU"));

    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains("Could not convert json, reason:"));
  }
}
