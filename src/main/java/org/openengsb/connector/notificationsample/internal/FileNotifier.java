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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.openengsb.core.api.AliveState;
import org.openengsb.core.common.AbstractOpenEngSBConnectorService;
import org.openengsb.domain.notification.Notification;
import org.openengsb.domain.notification.NotificationDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileNotifier extends AbstractOpenEngSBConnectorService implements NotificationDomain {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileNotifier.class);

    private File outputDirectory;

    public FileNotifier(String instanceId) {
        super(instanceId);
    }

    @Override
    public void notify(Notification notification) {
        String finalFile = "";
        StringBuilder builder = new StringBuilder();
        try {
            finalFile = new URI(outputDirectory.getPath()).getPath() + "/" + new Date().getTime() + ".notification";
        } catch (URISyntaxException e) {
            // log and ignore
            LOGGER.error("File url is not correct", e);
            return;
        }
        createMessage(notification, finalFile, builder);
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(finalFile));
            out.write(builder.toString());
            out.close();
        } catch (IOException e) {
            // log and ignore
            LOGGER.error("The file couldn't be written", e);
            return;
        }
        LOGGER.info("file has been written");
    }

    private void createMessage(Notification notification, String finalFile, StringBuilder builder) {
        LOGGER.info("notifying {} via file {}...", notification.getRecipient(), finalFile);
        builder.append("notifying ").append(notification.getRecipient()).append(" by writing in this file")
            .append("\n");
        LOGGER.info("Subject: {}", notification.getSubject());
        builder.append("Subject: ").append(notification.getSubject()).append("\n");
        LOGGER.info("Message: {}", StringUtils.abbreviate(notification.getMessage(), 200));
        builder.append("Message: ").append(notification.getMessage()).append("\n");
    }

    @Override
    public AliveState getAliveState() {
        return AliveState.ONLINE;
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
    }

}
