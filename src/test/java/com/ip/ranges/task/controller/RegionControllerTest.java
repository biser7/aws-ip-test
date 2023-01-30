package com.ip.ranges.task.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ip.ranges.task.service.RegionService;
import com.ip.ranges.task.service.RegionServiceImpl;

public class RegionControllerTest {
  private MockMvc mockMvc;
  private RegionService regionServiceMock;

  @BeforeEach
  void configureSystemUnderTest() {
    regionServiceMock = mock(RegionServiceImpl.class);
    mockMvc = MockMvcBuilders
      .standaloneSetup(new RegionController(regionServiceMock))
      .build();
  }

  @ParameterizedTest
  @ValueSource(strings = {"EU", "US", "AP", "CN", "SA", "AF", "CA"})
  public void whenGetRegionIpAddressesShouldReturnStatusOk(String region) throws Exception {
    String euRegionIp = "35.180.0.0/16";
    when(regionServiceMock.getRegionIpAddresses(region)).thenReturn(euRegionIp);

    mockMvc.perform(get("/api/v1/ip-range")
        .param("region", region))
      .andExpect(status().isOk())
      .andExpect(header().string("Content-Type", "text/plain;charset=UTF-8"))
      .andDo(print());

    verify(regionServiceMock).getRegionIpAddresses(region);
    verifyNoMoreInteractions(regionServiceMock);
  }

  @Test
  public void whenGetRegionIpAddressesWithNotAllowedRegionShouldReturnNotFoundStatus()
    throws Exception {

    String unAllowedRegion = "eu";
    mockMvc.perform(get("/api/v1/ip-range")
        .param("region", unAllowedRegion))
      .andExpect(status().is4xxClientError())
      .andDo(print());
  }
}
