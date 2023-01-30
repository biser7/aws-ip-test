package com.ip.ranges.task.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ip.ranges.task.exception.RegionNotFoundException;
import com.ip.ranges.task.service.RegionService;

@RestController
@RequestMapping(value = "/api/v1/ip-range", produces = "text/plain; charset=UTF-8")
@ResponseBody
public class RegionController {
  private final RegionService regionService;
  private final Set<String> allowedRegions = new HashSet<>(Arrays.asList("EU", "US", "AP", "CN",
    "SA", "AF", "CA"));

  public RegionController(RegionService regionService) {
    this.regionService = regionService;
  }

  @GetMapping
  public String getRegionIpAddresses(@RequestParam String region) throws JSONException,
    RegionNotFoundException {

    if (!allowedRegions.contains(region)) {
      throw new RegionNotFoundException("Could not find region with name: " + region);
    }
    return regionService.getRegionIpAddresses(region);
  }
}
