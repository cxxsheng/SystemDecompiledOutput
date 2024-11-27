package android.window;

/* loaded from: classes4.dex */
public class ConfigurationHelper {
    private ConfigurationHelper() {
    }

    public static void freeTextLayoutCachesIfNeeded(int i) {
        if ((i & 4) != 0) {
            android.graphics.Canvas.freeTextLayoutCaches();
        }
    }

    public static boolean shouldUpdateResources(android.os.IBinder iBinder, android.content.res.Configuration configuration, android.content.res.Configuration configuration2, android.content.res.Configuration configuration3, boolean z, java.lang.Boolean bool) {
        if (configuration == null || z || !android.app.ResourcesManager.getInstance().isSameResourcesOverrideConfig(iBinder, configuration3) || shouldUpdateWindowMetricsBounds(configuration, configuration2) || isDisplayRotationChanged(configuration, configuration2)) {
            return true;
        }
        return bool == null ? configuration.diff(configuration2) != 0 : bool.booleanValue();
    }

    public static boolean isDifferentDisplay(int i, int i2) {
        return (i2 == -1 || i == i2) ? false : true;
    }

    private static boolean shouldUpdateWindowMetricsBounds(android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
        return (configuration.windowConfiguration.getBounds().equals(configuration2.windowConfiguration.getBounds()) && configuration.windowConfiguration.getMaxBounds().equals(configuration2.windowConfiguration.getMaxBounds())) ? false : true;
    }

    private static boolean isDisplayRotationChanged(android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
        int displayRotation = configuration.windowConfiguration.getDisplayRotation();
        int displayRotation2 = configuration2.windowConfiguration.getDisplayRotation();
        return (displayRotation2 == -1 || displayRotation == -1 || displayRotation == displayRotation2) ? false : true;
    }
}
