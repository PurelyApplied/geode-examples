<!--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->

# Geode `gfsh` security example

This example demonstrates basic command security and user authentication
through `gfsh`.

In this example, we start a cluster.  Users with varying permissions attempt to
view member and cluster information, to write data, and to query data.  These
attempts succeed or fail based on the user's permissions.  Because we expect authentication failures,
remember to use the `--continue-on-error` option when running each `gfsh` script.

This example assumes that Java and Geode are installed.

## Demonstration of Security
1. Set directory ```geode-examples/security``` to be the current working directory.
Each step in this example specifies paths relative to that directory.

1. Build the example

        $ ../gradlew build

2. Run a script that starts a locator and two servers with security enabled.
In this example, we use the `ExampleSecurityManager`.
This security manager reads a JSON that defines which roles are granted which permissions, as well as each user's username, password, and roles.

        $ gfsh run --continue-on-error --file=scripts/start.gfsh 

3. Run any of the user `gfsh` script files to see the effect of security.

        $ gfsh run --continue-on-error --file=scripts/superUser.gfsh
        ...

5. These scripts provide only an overview of secured commands.
Examine the users and role permissions in `src/main/resources/example_security.json`.
Refer to the security documentation for more information on each command's required permission.
Experiment in `gfsh`, connecting with

        $ gfsh
        ...
        gfsh> connect --user=[chosen user] --password=123

7. Shut down the cluster

        $ gfsh run --file=scripts/stop.gfsh

## Things to Get Right with Security

- User authentication can be handled by any class that implements `SecurityManager`.

- Specify the `SecurityManager` by the `security-manager` property of all locator and server
property files.  
