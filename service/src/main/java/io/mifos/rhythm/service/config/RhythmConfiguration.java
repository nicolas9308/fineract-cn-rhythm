/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.mifos.rhythm.service.config;

import io.mifos.rhythm.service.ServiceConstants;
import io.mifos.rhythm.service.internal.identity.ApplicationPermissionRequestCreator;
import org.apache.fineract.cn.anubis.config.EnableAnubis;
import org.apache.fineract.cn.api.config.EnableApiFactory;
import org.apache.fineract.cn.async.config.EnableAsync;
import org.apache.fineract.cn.cassandra.config.EnableCassandra;
import org.apache.fineract.cn.command.config.EnableCommandProcessing;
import org.apache.fineract.cn.lang.config.EnableApplicationName;
import org.apache.fineract.cn.lang.config.EnableServiceException;
import org.apache.fineract.cn.lang.config.EnableTenantContext;
import org.apache.fineract.cn.mariadb.config.EnableMariaDB;
import org.apache.fineract.cn.permittedfeignclient.config.EnablePermissionRequestingFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Myrle Krantz
 */
@SuppressWarnings("WeakerAccess")
@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableApiFactory
@EnableAsync
@EnableCassandra
@EnableMariaDB(forTenantContext = false)
@EnableCommandProcessing
@EnableAnubis
@EnableServiceException
@EnableScheduling
@EnableTenantContext
@EnablePermissionRequestingFeignClient(feignClasses = {ApplicationPermissionRequestCreator.class})
@RibbonClient(name = "rhythm-v1")
@EnableApplicationName
@EnableFeignClients(clients = {ApplicationPermissionRequestCreator.class})
@ComponentScan({
    "io.mifos.rhythm.service.rest",
    "io.mifos.rhythm.service.config",
    "io.mifos.rhythm.service.internal.service",
    "io.mifos.rhythm.service.internal.repository",
    "io.mifos.rhythm.service.internal.command.handler"
})
@EnableJpaRepositories({
    "io.mifos.rhythm.service.internal.repository"
})
public class RhythmConfiguration extends WebMvcConfigurerAdapter {

  public RhythmConfiguration() {
    super();
  }

  @Bean(name = ServiceConstants.LOGGER_NAME)
  public Logger logger() {
    return LoggerFactory.getLogger(ServiceConstants.LOGGER_NAME);
  }

  @Override
  public void configurePathMatch(final PathMatchConfigurer configurer) {
    configurer.setUseSuffixPatternMatch(Boolean.FALSE);
  }
}
