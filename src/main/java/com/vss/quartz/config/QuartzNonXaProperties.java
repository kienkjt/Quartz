package com.vss.quartz.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuartzNonXaProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
