/**
 * Licensed to the Austrian Association for Software Tool Integration (AASTI)
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. The AASTI licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.connector.notificationsample.internal;

import static org.openengsb.connector.notificationsample.internal.Constants.PATH_ID;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.openengsb.core.api.Domain;
import org.openengsb.core.common.AbstractConnectorInstanceFactory;

public class NotifierFactory extends AbstractConnectorInstanceFactory<FileNotifier> {

    @Override
    public Domain createNewInstance(String id) {
        return new FileNotifier(id);
    }

    @Override
    public void doApplyAttributes(FileNotifier notifier, Map<String, String> attributes) {
        if (attributes.containsKey(PATH_ID)) {
            try {
                notifier.setOutputDirectory(new File(new URI(attributes.get(PATH_ID)).getPath()));
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("URI is not a correct path", e);
            }
        }
    }

}