/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode.examples.security;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;

public class Example implements Consumer<Region<String, String>> {
  private static ClientCache adminCacheAccessPoint;
  private static ClientCache workerCacheAccessPoint;
  private static ClientCache guessCacheAccessPoint;

  public static void main(String[] args) {
    // connect to the locator using default port 10334

    adminCacheAccessPoint = new ClientCacheFactory()
        .set("security-username", "admin")
        .set("security-password", "invalid-password")
        .addPoolLocator("127.0.0.1", 10334)
        .set("log-level", "WARN")
        .setPoolMultiuserAuthentication(true)
        .create();

    // create a local region that matches the server region
    Region<String, String> region =
        adminCacheAccessPoint.<String, String>createClientRegionFactory(ClientRegionShortcut.PROXY)
            .create("example-region");

    new Example().accept(region);
    adminCacheAccessPoint.close();
  }

  @Override
  public void accept(Region<String, String> region) {
    // insert values into the region
    Random r = new Random();
    String[] names = {"Alex Able", "Bertie Bell", "Chris Call", "Dale Driver", "Frankie Forth",
        "Jamie Jive", "Morgan Minnow", "Pat Puts", "Ricky Reliable", "Taylor Tack"};
    Arrays.stream(names).forEach(name -> region.put(name, String.valueOf(r.nextInt())));

  }
}
