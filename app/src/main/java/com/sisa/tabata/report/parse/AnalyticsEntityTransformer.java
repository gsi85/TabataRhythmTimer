package com.sisa.tabata.report.parse;

import com.google.inject.Singleton;
import com.parse.ParseObject;
import com.sisa.tabata.report.domain.AnalyticsEntity;

/**
 * Transformer for creating a {@link ParseObject} for an {@link AnalyticsEntity}.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class AnalyticsEntityTransformer {

    private static final String ANALYTICS_EVENTS_CLASS_NAME = "AnalyticsEvents";

    /**
     * Transforms an {@link AnalyticsEntity} to a {@link ParseObject}.
     *
     * @param analyticsEntity {@link AnalyticsEntity}
     * @return {@link ParseObject}
     */
    public ParseObject transform(final AnalyticsEntity analyticsEntity) {
        ParseObject parseObject = createParseObject();
        parseObject.put("eventName", analyticsEntity.getEventName());
        parseObject.put("dimensions", analyticsEntity.getDimensions());
        parseObject.put("installationId", analyticsEntity.getInstallationId());
        parseObject.put("operatingSystemVersion", analyticsEntity.getOperatingSystemVersion());
        parseObject.put("appBuildVersion", analyticsEntity.getAppBuildVersion());
        parseObject.put("appDisplayVersion", analyticsEntity.getAppDisplayVersion());
        return parseObject;
    }

    private ParseObject createParseObject() {
        return new ParseObject(ANALYTICS_EVENTS_CLASS_NAME);
    }

}
