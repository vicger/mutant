package com.exercise.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by vgerb on 3/9/18.
 */
@Configuration
@Profile(Profiles.PROD)
public class CloudConfig {
}
