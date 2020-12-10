package com.github.edgar615.resilience4j;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers("Content-Type:application/json")
public interface AccountClient {
  @RequestLine("GET /api/accounts/{id}")
  Account getAccountInfo(@Param("id") String id);
}