package com.banking.onlinebankingwebapi.payload.auth;


import lombok.ToString;

@ToString
public class LoginResponse {

  private String type;

  private boolean success;

  private String accessToken;

  public LoginResponse(boolean success, String accessToken) {

    this.success = success;
    this.accessToken = accessToken;
    this.type = "Bearer";
    
  }

  public LoginResponse() {
    this.type = "Bearer";
  }

  public LoginResponse(String type, boolean success, String accessToken) {
    this.type = type;
    this.success = success;
    this.accessToken = accessToken;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
