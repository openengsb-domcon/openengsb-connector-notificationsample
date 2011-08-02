package org.openengsb.connector.notificationsample.internal;

import static org.openengsb.connector.notificationsample.internal.Constants.DESCRIPTION;
import static org.openengsb.connector.notificationsample.internal.Constants.NAME;
import static org.openengsb.connector.notificationsample.internal.Constants.PATH_DESCRIPTION;
import static org.openengsb.connector.notificationsample.internal.Constants.PATH_ID;
import static org.openengsb.connector.notificationsample.internal.Constants.PATH_NAME;

import java.io.File;

import org.openengsb.core.api.descriptor.ServiceDescriptor;
import org.openengsb.core.api.descriptor.ServiceDescriptor.Builder;
import org.openengsb.core.common.AbstractConnectorProvider;

public class ConnectorProvider extends AbstractConnectorProvider {

    @Override
    public ServiceDescriptor getDescriptor() {
        Builder builder = ServiceDescriptor.builder(strings);
        builder.id(id);
        builder.name(NAME).description(DESCRIPTION);
        builder
            .attribute(
            builder.newAttribute().id(PATH_ID).name(PATH_NAME)
                .description(PATH_DESCRIPTION).defaultValue(new File(".").getAbsolutePath()).required().build());
        return builder.build();
    }

}
