package com.intelliplatforms.bizevents.autoconfigure;


import com.dynatrace.openkit.util.json.objects.JSONValue;

import java.util.Map;

public interface BizEventExporter {
    void sendBizEvent(String eventType, Map<String, JSONValue> attributes);
}

