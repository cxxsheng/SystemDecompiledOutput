package com.android.server.wm;

/* loaded from: classes3.dex */
public abstract class ConfigurationContainer<E extends com.android.server.wm.ConfigurationContainer> {
    static final int BOUNDS_CHANGE_NONE = 0;
    static final int BOUNDS_CHANGE_POSITION = 1;
    static final int BOUNDS_CHANGE_SIZE = 2;
    private boolean mHasOverrideConfiguration;
    private android.graphics.Rect mReturnBounds = new android.graphics.Rect();
    private android.content.res.Configuration mRequestedOverrideConfiguration = new android.content.res.Configuration();
    private android.content.res.Configuration mResolvedOverrideConfiguration = new android.content.res.Configuration();
    private android.content.res.Configuration mFullConfiguration = new android.content.res.Configuration();
    private android.content.res.Configuration mMergedOverrideConfiguration = new android.content.res.Configuration();
    private java.util.ArrayList<com.android.server.wm.ConfigurationContainerListener> mChangeListeners = new java.util.ArrayList<>();
    private final android.content.res.Configuration mRequestsTmpConfig = new android.content.res.Configuration();
    private final android.content.res.Configuration mResolvedTmpConfig = new android.content.res.Configuration();
    private final android.graphics.Rect mTmpRect = new android.graphics.Rect();

    protected abstract E getChildAt(int i);

    protected abstract int getChildCount();

    protected abstract com.android.server.wm.ConfigurationContainer getParent();

    @android.annotation.NonNull
    public android.content.res.Configuration getConfiguration() {
        return this.mFullConfiguration;
    }

    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        this.mResolvedTmpConfig.setTo(this.mResolvedOverrideConfiguration);
        resolveOverrideConfiguration(configuration);
        this.mFullConfiguration.setTo(configuration);
        this.mFullConfiguration.windowConfiguration.unsetAlwaysOnTop();
        this.mFullConfiguration.updateFrom(this.mResolvedOverrideConfiguration);
        onMergedOverrideConfigurationChanged();
        if (!this.mResolvedTmpConfig.equals(this.mResolvedOverrideConfiguration)) {
            for (int size = this.mChangeListeners.size() - 1; size >= 0; size--) {
                this.mChangeListeners.get(size).onRequestedOverrideConfigurationChanged(this.mResolvedOverrideConfiguration);
            }
        }
        for (int size2 = this.mChangeListeners.size() - 1; size2 >= 0; size2--) {
            this.mChangeListeners.get(size2).onMergedOverrideConfigurationChanged(this.mMergedOverrideConfiguration);
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            dispatchConfigurationToChild(getChildAt(childCount), this.mFullConfiguration);
        }
    }

    void dispatchConfigurationToChild(E e, android.content.res.Configuration configuration) {
        e.onConfigurationChanged(configuration);
    }

    void resolveOverrideConfiguration(android.content.res.Configuration configuration) {
        this.mResolvedOverrideConfiguration.setTo(this.mRequestedOverrideConfiguration);
    }

    boolean hasRequestedOverrideConfiguration() {
        return this.mHasOverrideConfiguration;
    }

    @android.annotation.NonNull
    public android.content.res.Configuration getRequestedOverrideConfiguration() {
        return this.mRequestedOverrideConfiguration;
    }

    @android.annotation.NonNull
    android.content.res.Configuration getResolvedOverrideConfiguration() {
        return this.mResolvedOverrideConfiguration;
    }

    public void onRequestedOverrideConfigurationChanged(android.content.res.Configuration configuration) {
        updateRequestedOverrideConfiguration(configuration);
        com.android.server.wm.ConfigurationContainer parent = getParent();
        onConfigurationChanged(parent != null ? parent.getConfiguration() : android.content.res.Configuration.EMPTY);
    }

    void updateRequestedOverrideConfiguration(android.content.res.Configuration configuration) {
        this.mHasOverrideConfiguration = !android.content.res.Configuration.EMPTY.equals(configuration);
        this.mRequestedOverrideConfiguration.setTo(configuration);
        android.graphics.Rect bounds = this.mRequestedOverrideConfiguration.windowConfiguration.getBounds();
        if (this.mHasOverrideConfiguration && providesMaxBounds() && diffRequestedOverrideMaxBounds(bounds) != 0) {
            this.mRequestedOverrideConfiguration.windowConfiguration.setMaxBounds(bounds);
        }
    }

    @android.annotation.NonNull
    public android.content.res.Configuration getMergedOverrideConfiguration() {
        return this.mMergedOverrideConfiguration;
    }

    void onMergedOverrideConfigurationChanged() {
        com.android.server.wm.ConfigurationContainer parent = getParent();
        if (parent != null) {
            this.mMergedOverrideConfiguration.setTo(parent.getMergedOverrideConfiguration());
            this.mMergedOverrideConfiguration.windowConfiguration.unsetAlwaysOnTop();
            this.mMergedOverrideConfiguration.updateFrom(this.mResolvedOverrideConfiguration);
            return;
        }
        this.mMergedOverrideConfiguration.setTo(this.mResolvedOverrideConfiguration);
    }

    public boolean matchParentBounds() {
        return getResolvedOverrideBounds().isEmpty();
    }

    public boolean equivalentRequestedOverrideBounds(android.graphics.Rect rect) {
        return equivalentBounds(getRequestedOverrideBounds(), rect);
    }

    public boolean equivalentRequestedOverrideMaxBounds(android.graphics.Rect rect) {
        return equivalentBounds(getRequestedOverrideMaxBounds(), rect);
    }

    public static boolean equivalentBounds(android.graphics.Rect rect, android.graphics.Rect rect2) {
        return rect == rect2 || (rect != null && (rect.equals(rect2) || (rect.isEmpty() && rect2 == null))) || (rect2 != null && rect2.isEmpty() && rect == null);
    }

    public android.graphics.Rect getBounds() {
        this.mReturnBounds.set(getConfiguration().windowConfiguration.getBounds());
        return this.mReturnBounds;
    }

    public void getBounds(android.graphics.Rect rect) {
        rect.set(getBounds());
    }

    public android.graphics.Rect getMaxBounds() {
        this.mReturnBounds.set(getConfiguration().windowConfiguration.getMaxBounds());
        return this.mReturnBounds;
    }

    public void getPosition(android.graphics.Point point) {
        android.graphics.Rect bounds = getBounds();
        point.set(bounds.left, bounds.top);
    }

    android.graphics.Rect getResolvedOverrideBounds() {
        this.mReturnBounds.set(getResolvedOverrideConfiguration().windowConfiguration.getBounds());
        return this.mReturnBounds;
    }

    public android.graphics.Rect getRequestedOverrideBounds() {
        this.mReturnBounds.set(getRequestedOverrideConfiguration().windowConfiguration.getBounds());
        return this.mReturnBounds;
    }

    public android.graphics.Rect getRequestedOverrideMaxBounds() {
        this.mReturnBounds.set(getRequestedOverrideConfiguration().windowConfiguration.getMaxBounds());
        return this.mReturnBounds;
    }

    public boolean hasOverrideBounds() {
        return !getRequestedOverrideBounds().isEmpty();
    }

    public void getRequestedOverrideBounds(android.graphics.Rect rect) {
        rect.set(getRequestedOverrideBounds());
    }

    public int setBounds(android.graphics.Rect rect) {
        int diffRequestedOverrideBounds = diffRequestedOverrideBounds(rect);
        boolean z = providesMaxBounds() && diffRequestedOverrideMaxBounds(rect) != 0;
        if (diffRequestedOverrideBounds == 0 && !z) {
            return diffRequestedOverrideBounds;
        }
        this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
        this.mRequestsTmpConfig.windowConfiguration.setBounds(rect);
        onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
        return diffRequestedOverrideBounds;
    }

    public int setBounds(int i, int i2, int i3, int i4) {
        this.mTmpRect.set(i, i2, i3, i4);
        return setBounds(this.mTmpRect);
    }

    protected boolean providesMaxBounds() {
        return false;
    }

    int diffRequestedOverrideMaxBounds(android.graphics.Rect rect) {
        if (equivalentRequestedOverrideMaxBounds(rect)) {
            return 0;
        }
        android.graphics.Rect requestedOverrideMaxBounds = getRequestedOverrideMaxBounds();
        int i = (rect != null && requestedOverrideMaxBounds.left == rect.left && requestedOverrideMaxBounds.top == rect.top) ? 0 : 1;
        if (rect == null || requestedOverrideMaxBounds.width() != rect.width() || requestedOverrideMaxBounds.height() != rect.height()) {
            return i | 2;
        }
        return i;
    }

    int diffRequestedOverrideBounds(android.graphics.Rect rect) {
        if (equivalentRequestedOverrideBounds(rect)) {
            return 0;
        }
        android.graphics.Rect requestedOverrideBounds = getRequestedOverrideBounds();
        int i = (rect != null && requestedOverrideBounds.left == rect.left && requestedOverrideBounds.top == rect.top) ? 0 : 1;
        if (rect == null || requestedOverrideBounds.width() != rect.width() || requestedOverrideBounds.height() != rect.height()) {
            return i | 2;
        }
        return i;
    }

    public android.app.WindowConfiguration getWindowConfiguration() {
        return this.mFullConfiguration.windowConfiguration;
    }

    public int getWindowingMode() {
        return this.mFullConfiguration.windowConfiguration.getWindowingMode();
    }

    public int getRequestedOverrideWindowingMode() {
        return this.mRequestedOverrideConfiguration.windowConfiguration.getWindowingMode();
    }

    public void setWindowingMode(int i) {
        this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
        this.mRequestsTmpConfig.windowConfiguration.setWindowingMode(i);
        onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
    }

    public void setAlwaysOnTop(boolean z) {
        this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
        this.mRequestsTmpConfig.windowConfiguration.setAlwaysOnTop(z);
        onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
    }

    void setDisplayWindowingMode(int i) {
        this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
        this.mRequestsTmpConfig.windowConfiguration.setDisplayWindowingMode(i);
        onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
    }

    public boolean inMultiWindowMode() {
        return android.app.WindowConfiguration.inMultiWindowMode(this.mFullConfiguration.windowConfiguration.getWindowingMode());
    }

    public boolean inPinnedWindowingMode() {
        return this.mFullConfiguration.windowConfiguration.getWindowingMode() == 2;
    }

    public boolean inFreeformWindowingMode() {
        return this.mFullConfiguration.windowConfiguration.getWindowingMode() == 5;
    }

    public int getActivityType() {
        return this.mFullConfiguration.windowConfiguration.getActivityType();
    }

    public void setActivityType(int i) {
        int activityType = getActivityType();
        if (activityType == i) {
            return;
        }
        if (activityType != 0) {
            throw new java.lang.IllegalStateException("Can't change activity type once set: " + this + " activityType=" + android.app.WindowConfiguration.activityTypeToString(i));
        }
        this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
        this.mRequestsTmpConfig.windowConfiguration.setActivityType(i);
        onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
    }

    public boolean isActivityTypeHome() {
        return getActivityType() == 2;
    }

    public boolean isActivityTypeRecents() {
        return getActivityType() == 3;
    }

    final boolean isActivityTypeHomeOrRecents() {
        int activityType = getActivityType();
        return activityType == 2 || activityType == 3;
    }

    public boolean isActivityTypeAssistant() {
        return getActivityType() == 4;
    }

    public boolean applyAppSpecificConfig(java.lang.Integer num, android.os.LocaleList localeList, java.lang.Integer num2) {
        this.mRequestsTmpConfig.setTo(getRequestedOverrideConfiguration());
        boolean z = num != null && setOverrideNightMode(this.mRequestsTmpConfig, num.intValue());
        boolean z2 = localeList != null && setOverrideLocales(this.mRequestsTmpConfig, localeList);
        boolean z3 = num2 != null && setOverrideGender(this.mRequestsTmpConfig, num2.intValue());
        if (z || z2 || z3) {
            onRequestedOverrideConfigurationChanged(this.mRequestsTmpConfig);
        }
        return z || z2 || z3;
    }

    private boolean setOverrideNightMode(android.content.res.Configuration configuration, int i) {
        int i2 = this.mRequestedOverrideConfiguration.uiMode;
        int i3 = i & 48;
        if ((i2 & 48) == i3) {
            return false;
        }
        configuration.uiMode = i3 | (i2 & (-49));
        return true;
    }

    private boolean setOverrideLocales(android.content.res.Configuration configuration, @android.annotation.NonNull android.os.LocaleList localeList) {
        if (this.mRequestedOverrideConfiguration.getLocales().equals(localeList)) {
            return false;
        }
        configuration.setLocales(localeList);
        configuration.userSetLocale = true;
        return true;
    }

    private boolean setOverrideGender(android.content.res.Configuration configuration, int i) {
        if (this.mRequestedOverrideConfiguration.getGrammaticalGender() == i) {
            return false;
        }
        configuration.setGrammaticalGender(i);
        return true;
    }

    public boolean isActivityTypeDream() {
        return getActivityType() == 5;
    }

    public boolean isActivityTypeStandard() {
        return getActivityType() == 1;
    }

    public boolean isActivityTypeStandardOrUndefined() {
        int activityType = getActivityType();
        return activityType == 1 || activityType == 0;
    }

    public static boolean isCompatibleActivityType(int i, int i2) {
        if (i == i2) {
            return true;
        }
        if (i == 4) {
            return false;
        }
        return i == 0 || i2 == 0;
    }

    public boolean isCompatible(int i, int i2) {
        int activityType = getActivityType();
        int windowingMode = getWindowingMode();
        boolean z = activityType == i2;
        boolean z2 = windowingMode == i;
        if (z && z2) {
            return true;
        }
        if ((i2 != 0 && i2 != 1) || !isActivityTypeStandardOrUndefined()) {
            return z;
        }
        return z2;
    }

    void registerConfigurationChangeListener(com.android.server.wm.ConfigurationContainerListener configurationContainerListener) {
        registerConfigurationChangeListener(configurationContainerListener, true);
    }

    void registerConfigurationChangeListener(com.android.server.wm.ConfigurationContainerListener configurationContainerListener, boolean z) {
        if (this.mChangeListeners.contains(configurationContainerListener)) {
            return;
        }
        this.mChangeListeners.add(configurationContainerListener);
        if (z) {
            configurationContainerListener.onRequestedOverrideConfigurationChanged(this.mResolvedOverrideConfiguration);
            configurationContainerListener.onMergedOverrideConfigurationChanged(this.mMergedOverrideConfiguration);
        }
    }

    void unregisterConfigurationChangeListener(com.android.server.wm.ConfigurationContainerListener configurationContainerListener) {
        this.mChangeListeners.remove(configurationContainerListener);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean containsListener(com.android.server.wm.ConfigurationContainerListener configurationContainerListener) {
        return this.mChangeListeners.contains(configurationContainerListener);
    }

    void onParentChanged(com.android.server.wm.ConfigurationContainer configurationContainer, com.android.server.wm.ConfigurationContainer configurationContainer2) {
        if (configurationContainer != null) {
            onConfigurationChanged(configurationContainer.mFullConfiguration);
        }
    }

    protected void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        long start = protoOutputStream.start(j);
        if (i == 0 || this.mHasOverrideConfiguration) {
            this.mRequestedOverrideConfiguration.dumpDebug(protoOutputStream, 1146756268033L, i == 2);
        }
        if (i == 0) {
            this.mFullConfiguration.dumpDebug(protoOutputStream, 1146756268034L, false);
            this.mMergedOverrideConfiguration.dumpDebug(protoOutputStream, 1146756268035L, false);
        }
        if (i == 1) {
            dumpDebugWindowingMode(protoOutputStream);
        }
        protoOutputStream.end(start);
    }

    private void dumpDebugWindowingMode(android.util.proto.ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268034L);
        long start2 = protoOutputStream.start(1146756268051L);
        protoOutputStream.write(1120986464258L, this.mFullConfiguration.windowConfiguration.getWindowingMode());
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    public void dumpChildrenNames(java.io.PrintWriter printWriter, java.lang.String str) {
        dumpChildrenNames(printWriter, str, true);
    }

    public void dumpChildrenNames(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        int windowingMode = getWindowingMode();
        java.lang.String windowingModeToString = android.app.WindowConfiguration.windowingModeToString(windowingMode);
        if (windowingMode != 0 && windowingMode != 1) {
            windowingModeToString = windowingModeToString.toUpperCase();
        }
        int requestedOverrideWindowingMode = getRequestedOverrideWindowingMode();
        java.lang.String windowingModeToString2 = android.app.WindowConfiguration.windowingModeToString(requestedOverrideWindowingMode);
        if (requestedOverrideWindowingMode != 0 && requestedOverrideWindowingMode != 1) {
            windowingModeToString2 = windowingModeToString2.toUpperCase();
        }
        java.lang.String activityTypeToString = android.app.WindowConfiguration.activityTypeToString(getActivityType());
        if (getActivityType() != 0 && getActivityType() != 1) {
            activityTypeToString = activityTypeToString.toUpperCase();
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append(z ? "└─ " : "├─ ");
        printWriter.print(sb.toString());
        printWriter.println(getName() + " type=" + activityTypeToString + " mode=" + windowingModeToString + " override-mode=" + windowingModeToString2 + " requested-bounds=" + getRequestedOverrideBounds().toShortString() + " bounds=" + getBounds().toShortString());
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append(str);
        sb2.append(z ? "   " : "│  ");
        java.lang.String sb3 = sb2.toString();
        int childCount = getChildCount() - 1;
        while (childCount >= 0) {
            getChildAt(childCount).dumpChildrenNames(printWriter, sb3, childCount == 0);
            childCount--;
        }
    }

    java.lang.String getName() {
        return toString();
    }

    public boolean isAlwaysOnTop() {
        return this.mFullConfiguration.windowConfiguration.isAlwaysOnTop();
    }

    boolean hasChild() {
        return getChildCount() > 0;
    }
}
