package android.view;

/* loaded from: classes4.dex */
public class DisplayAdjustments {
    public static final android.view.DisplayAdjustments DEFAULT_DISPLAY_ADJUSTMENTS = new android.view.DisplayAdjustments();
    private volatile android.content.res.CompatibilityInfo mCompatInfo = android.content.res.CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
    private final android.content.res.Configuration mConfiguration = new android.content.res.Configuration(android.content.res.Configuration.EMPTY);

    public DisplayAdjustments() {
    }

    public DisplayAdjustments(android.content.res.Configuration configuration) {
        if (configuration != null) {
            this.mConfiguration.setTo(configuration);
        }
    }

    public DisplayAdjustments(android.view.DisplayAdjustments displayAdjustments) {
        setCompatibilityInfo(displayAdjustments.mCompatInfo);
        this.mConfiguration.setTo(displayAdjustments.getConfiguration());
    }

    public void setCompatibilityInfo(android.content.res.CompatibilityInfo compatibilityInfo) {
        if (this == DEFAULT_DISPLAY_ADJUSTMENTS) {
            throw new java.lang.IllegalArgumentException("setCompatbilityInfo: Cannot modify DEFAULT_DISPLAY_ADJUSTMENTS");
        }
        if (compatibilityInfo != null && (compatibilityInfo.isScalingRequired() || !compatibilityInfo.supportsScreen())) {
            this.mCompatInfo = compatibilityInfo;
        } else {
            this.mCompatInfo = android.content.res.CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        }
    }

    public android.content.res.CompatibilityInfo getCompatibilityInfo() {
        return this.mCompatInfo;
    }

    public void setConfiguration(android.content.res.Configuration configuration) {
        if (this == DEFAULT_DISPLAY_ADJUSTMENTS) {
            throw new java.lang.IllegalArgumentException("setConfiguration: Cannot modify DEFAULT_DISPLAY_ADJUSTMENTS");
        }
        android.content.res.Configuration configuration2 = this.mConfiguration;
        if (configuration == null) {
            configuration = android.content.res.Configuration.EMPTY;
        }
        configuration2.setTo(configuration);
    }

    public android.content.res.Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public int hashCode() {
        return ((527 + java.util.Objects.hashCode(this.mCompatInfo)) * 31) + java.util.Objects.hashCode(this.mConfiguration);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.view.DisplayAdjustments)) {
            return false;
        }
        android.view.DisplayAdjustments displayAdjustments = (android.view.DisplayAdjustments) obj;
        return java.util.Objects.equals(displayAdjustments.mCompatInfo, this.mCompatInfo) && java.util.Objects.equals(displayAdjustments.mConfiguration, this.mConfiguration);
    }
}
