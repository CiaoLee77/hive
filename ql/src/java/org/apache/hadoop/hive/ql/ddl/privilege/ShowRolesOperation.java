/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.ddl.privilege;

import org.apache.hadoop.hive.ql.ddl.DDLOperationContext;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hive.ql.ddl.DDLOperation;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveAuthorizer;

/**
 * Operation process of showing the roles.
 */
public class ShowRolesOperation extends DDLOperation {
  private final ShowRolesDesc desc;

  public ShowRolesOperation(DDLOperationContext context, ShowRolesDesc desc) {
    super(context);
    this.desc = desc;
  }

  @Override
  public int execute() throws HiveException, IOException {
    HiveAuthorizer authorizer = RoleUtils.getSessionAuthorizer(context.getConf());
    List<String> allRoles = authorizer.getAllRoles();
    RoleUtils.writeListToFileAfterSort(allRoles, desc.getResFile(), context);
    return 0;
  }
}
