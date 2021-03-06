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
package org.jclouds.openstack.keystone.v2_0.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.domain.Resource;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.annotations.SerializedName;

/**
 * @author Adam Lowe
 */
public class ApiMetadata extends Resource {

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromApiMetadata(this);
   }

   public static abstract class Builder<T extends Builder<T>> extends Resource.Builder<T>  {
      private String status;
      private Date updated;
      private Set<MediaType> mediaTypes = Sets.newLinkedHashSet();

      /**
       * @see ApiMetadata#getStatus()
       */
      public T status(String status) {
         this.status = status;
         return self();
      }

      /**
       * @see ApiMetadata#getUpdated()
       */
      public T updated(Date updated) {
         this.updated = updated;
         return self();
      }

      /**
       * @see ApiMetadata#getMediaTypes()
       */
      public T mediaTypes(Set<MediaType> mediaTypes) {
         this.mediaTypes = mediaTypes;
         return self();
      }

      public ApiMetadata build() {
         return new ApiMetadata(this);
      }

      public T fromApiMetadata(ApiMetadata in) {
         return super.fromResource(in)
               .status(in.getStatus())
               .updated(in.getUpdated())
               .mediaTypes(in.getMediaTypes());
      }

   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   @Nullable
   private final String status;
   @Nullable
   private final Date updated;
   @SerializedName(value="media-types")
   @Nullable
   private final Set<MediaType> mediaTypes;

   protected ApiMetadata(Builder<?> builder) {
      super(builder);
      this.status = checkNotNull(builder.status, "status");
      this.updated = checkNotNull(builder.updated, "updated");
      this.mediaTypes = ImmutableSet.copyOf(checkNotNull(builder.mediaTypes, "mediaTypes"));
   }

   /**
    */
   public String getStatus() {
      return this.status;
   }

   /**
    */
   public Date getUpdated() {
      return this.updated;
   }

   /**
    */
   public Set<MediaType> getMediaTypes() {
      return Collections.unmodifiableSet(this.mediaTypes);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(status, updated, mediaTypes);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      ApiMetadata that = ApiMetadata.class.cast(obj);
      return super.equals(that) && Objects.equal(this.status, that.status)
            && Objects.equal(this.updated, that.updated)
            && Objects.equal(this.mediaTypes, that.mediaTypes);
   }

   protected ToStringHelper string() {
      return super.string()
            .add("status", status)
            .add("updated", updated)
            .add("mediaTypes", mediaTypes);
   }

}