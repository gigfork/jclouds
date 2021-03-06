/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
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
package org.jclouds.aws.s3.config;

import static org.jclouds.aws.domain.Region.US_STANDARD;
import static org.jclouds.location.reference.LocationConstants.ENDPOINT;
import static org.jclouds.location.reference.LocationConstants.PROPERTY_REGION;

import java.net.URI;

import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.aws.s3.AWSS3AsyncClient;
import org.jclouds.aws.s3.AWSS3Client;
import org.jclouds.aws.s3.binders.AssignCorrectHostnameAndBindAsHostPrefixIfConfigured;
import org.jclouds.location.Region;
import org.jclouds.rest.ConfiguresRestClient;
import org.jclouds.rest.RestContext;
import org.jclouds.s3.Bucket;
import org.jclouds.s3.S3AsyncClient;
import org.jclouds.s3.S3Client;
import org.jclouds.s3.binders.BindAsHostPrefixIfConfigured;
import org.jclouds.s3.config.S3RestClientModule;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.reflect.TypeToken;
import com.google.inject.Provides;

/**
 * Configures the S3 connection.
 *
 * @author Adrian Cole
 */
@ConfiguresRestClient
public class AWSS3RestClientModule extends S3RestClientModule<AWSS3Client, AWSS3AsyncClient> {
   
   public AWSS3RestClientModule() {
      super(TypeToken.of(AWSS3Client.class), TypeToken.of(AWSS3AsyncClient.class));
   }
   
   @Override
   protected Supplier<String> defaultRegionForBucket(@Region Supplier<String> defaultRegion) {
      return Suppliers.ofInstance(US_STANDARD);
   }

   @Override
   protected void configure() {
      bind(BindAsHostPrefixIfConfigured.class).to(AssignCorrectHostnameAndBindAsHostPrefixIfConfigured.class);
      super.configure();
   }
   
   @Provides
   @Singleton
   @Bucket
   protected Supplier<URI> provideBucketURI(@Named(PROPERTY_REGION + "." + US_STANDARD + "." + ENDPOINT) String endpoint){
      return Suppliers.ofInstance(URI.create(endpoint));
   }
  
   @Singleton
   @Provides
   S3Client provide(AWSS3Client in) {
      return in;
   }

   @Singleton
   @Provides
   S3AsyncClient provide(AWSS3AsyncClient in) {
      return in;
   }

   /**
    * so that we can inject RestContext<S3Client, S3AsyncClient>
    */
   @SuppressWarnings({ "unchecked", "rawtypes" })
   @Singleton
   @Provides
   RestContext<S3Client, S3AsyncClient> provideBaseContext(RestContext<AWSS3Client, AWSS3AsyncClient> in) {
      return (RestContext) in;
   }

}
