package com.intelliplatforms.bizevents.autoconfigure;

import com.dynatrace.openkit.api.OpenKit;
import com.dynatrace.openkit.api.Session;
import com.dynatrace.openkit.util.json.objects.JSONValue;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/*
*  OpenKit Implementation adapted to be used exclusively for exporting bizevents
*  @param openKitSession - the Session object created inside OpenKitAutoConfiguration
*
* */
@Component
public class BizEventAgent implements BizEventExporter, Closeable {

    private final OpenKit openKitInstance;

    private final Session beaconForwarder; //

    /* @param openKitInstance
    *  @param openKitSession
    * */
    public BizEventAgent(OpenKit openKitInstance, Session openKitSession) {
        this.beaconForwarder = openKitSession;
        this.openKitInstance = openKitInstance;
    }

    @Override
    public void sendBizEvent(String eventType, Map<String, JSONValue> attributes) {

        this.beaconForwarder.sendBizEvent(eventType, attributes);
    };

    @Override
    public void close() throws IOException {
        if (openKitInstance.isInitialized()) {
            this.openKitInstance.shutdown();
        }
    }
}

