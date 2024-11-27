package com.android.server.compat;

/* loaded from: classes.dex */
public final class CompatChange extends com.android.internal.compat.CompatibilityChangeInfo {
    static final long CTS_SYSTEM_API_CHANGEID = 149391281;
    static final long CTS_SYSTEM_API_OVERRIDABLE_CHANGEID = 174043039;
    private java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Boolean> mEvaluatedOverrides;
    com.android.server.compat.CompatChange.ChangeListener mListener;
    private java.util.concurrent.ConcurrentHashMap<java.lang.String, android.app.compat.PackageOverride> mRawOverrides;

    public interface ChangeListener {
        void onCompatChange(java.lang.String str);
    }

    public CompatChange(long j) {
        this(j, null, -1, -1, false, false, null, false);
    }

    public CompatChange(com.android.server.compat.config.Change change) {
        this(change.getId(), change.getName(), change.getEnableAfterTargetSdk(), change.getEnableSinceTargetSdk(), change.getDisabled(), change.getLoggingOnly(), change.getDescription(), change.getOverridable());
    }

    public CompatChange(long j, @android.annotation.Nullable java.lang.String str, int i, int i2, boolean z, boolean z2, java.lang.String str2, boolean z3) {
        super(java.lang.Long.valueOf(j), str, i, i2, z, z2, str2, z3);
        this.mListener = null;
        this.mEvaluatedOverrides = new java.util.concurrent.ConcurrentHashMap<>();
        this.mRawOverrides = new java.util.concurrent.ConcurrentHashMap<>();
    }

    synchronized void registerListener(com.android.server.compat.CompatChange.ChangeListener changeListener) {
        if (this.mListener != null) {
            throw new java.lang.IllegalStateException("Listener for change " + toString() + " already registered.");
        }
        this.mListener = changeListener;
    }

    private void addPackageOverrideInternal(java.lang.String str, boolean z) {
        if (getLoggingOnly()) {
            throw new java.lang.IllegalArgumentException("Can't add overrides for a logging only change " + toString());
        }
        this.mEvaluatedOverrides.put(str, java.lang.Boolean.valueOf(z));
        notifyListener(str);
    }

    private void removePackageOverrideInternal(java.lang.String str) {
        if (this.mEvaluatedOverrides.remove(str) != null) {
            notifyListener(str);
        }
    }

    synchronized void addPackageOverride(java.lang.String str, android.app.compat.PackageOverride packageOverride, com.android.internal.compat.OverrideAllowedState overrideAllowedState, @android.annotation.Nullable java.lang.Long l) {
        if (getLoggingOnly()) {
            throw new java.lang.IllegalArgumentException("Can't add overrides for a logging only change " + toString());
        }
        this.mRawOverrides.put(str, packageOverride);
        recheckOverride(str, overrideAllowedState, l);
    }

    synchronized boolean recheckOverride(java.lang.String str, com.android.internal.compat.OverrideAllowedState overrideAllowedState, @android.annotation.Nullable java.lang.Long l) {
        if (str == null) {
            return false;
        }
        try {
            boolean z = overrideAllowedState.state == 0;
            if (l == null || !this.mRawOverrides.containsKey(str) || !z) {
                removePackageOverrideInternal(str);
                return false;
            }
            switch (this.mRawOverrides.get(str).evaluate(l.longValue())) {
                case 0:
                    removePackageOverrideInternal(str);
                    break;
                case 1:
                    addPackageOverrideInternal(str, true);
                    break;
                case 2:
                    addPackageOverrideInternal(str, false);
                    break;
            }
            return true;
        } finally {
        }
    }

    synchronized boolean removePackageOverride(java.lang.String str, com.android.internal.compat.OverrideAllowedState overrideAllowedState, @android.annotation.Nullable java.lang.Long l) {
        if (!this.mRawOverrides.containsKey(str)) {
            return false;
        }
        overrideAllowedState.enforce(getId(), str);
        this.mRawOverrides.remove(str);
        recheckOverride(str, overrideAllowedState, l);
        return true;
    }

    boolean isEnabled(android.content.pm.ApplicationInfo applicationInfo, com.android.internal.compat.AndroidBuildClassifier androidBuildClassifier) {
        java.lang.Boolean bool;
        if (applicationInfo == null) {
            return defaultValue();
        }
        if (applicationInfo.packageName != null && (bool = this.mEvaluatedOverrides.get(applicationInfo.packageName)) != null) {
            return bool.booleanValue();
        }
        if (getDisabled()) {
            return false;
        }
        return getEnableSinceTargetSdk() == -1 || java.lang.Math.min(applicationInfo.targetSdkVersion, androidBuildClassifier.platformTargetSdk()) >= getEnableSinceTargetSdk();
    }

    boolean willBeEnabled(java.lang.String str) {
        if (str == null) {
            return defaultValue();
        }
        android.app.compat.PackageOverride packageOverride = this.mRawOverrides.get(str);
        if (packageOverride != null) {
            switch (packageOverride.evaluateForAllVersions()) {
            }
            return defaultValue();
        }
        return defaultValue();
    }

    boolean defaultValue() {
        return !getDisabled();
    }

    synchronized void clearOverrides() {
        this.mRawOverrides.clear();
        this.mEvaluatedOverrides.clear();
    }

    synchronized void loadOverrides(com.android.server.compat.overrides.ChangeOverrides changeOverrides) {
        try {
            if (changeOverrides.getDeferred() != null) {
                for (com.android.server.compat.overrides.OverrideValue overrideValue : changeOverrides.getDeferred().getOverrideValue()) {
                    this.mRawOverrides.put(overrideValue.getPackageName(), new android.app.compat.PackageOverride.Builder().setEnabled(overrideValue.getEnabled()).build());
                }
            }
            if (changeOverrides.getValidated() != null) {
                for (com.android.server.compat.overrides.OverrideValue overrideValue2 : changeOverrides.getValidated().getOverrideValue()) {
                    this.mEvaluatedOverrides.put(overrideValue2.getPackageName(), java.lang.Boolean.valueOf(overrideValue2.getEnabled()));
                    this.mRawOverrides.put(overrideValue2.getPackageName(), new android.app.compat.PackageOverride.Builder().setEnabled(overrideValue2.getEnabled()).build());
                }
            }
            if (changeOverrides.getRaw() != null) {
                for (com.android.server.compat.overrides.RawOverrideValue rawOverrideValue : changeOverrides.getRaw().getRawOverrideValue()) {
                    this.mRawOverrides.put(rawOverrideValue.getPackageName(), new android.app.compat.PackageOverride.Builder().setMinVersionCode(rawOverrideValue.getMinVersionCode()).setMaxVersionCode(rawOverrideValue.getMaxVersionCode()).setEnabled(rawOverrideValue.getEnabled()).build());
                }
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    synchronized com.android.server.compat.overrides.ChangeOverrides saveOverrides() {
        try {
            if (this.mRawOverrides.isEmpty()) {
                return null;
            }
            com.android.server.compat.overrides.ChangeOverrides changeOverrides = new com.android.server.compat.overrides.ChangeOverrides();
            changeOverrides.setChangeId(getId());
            com.android.server.compat.overrides.ChangeOverrides.Raw raw = new com.android.server.compat.overrides.ChangeOverrides.Raw();
            java.util.List<com.android.server.compat.overrides.RawOverrideValue> rawOverrideValue = raw.getRawOverrideValue();
            for (java.util.Map.Entry<java.lang.String, android.app.compat.PackageOverride> entry : this.mRawOverrides.entrySet()) {
                com.android.server.compat.overrides.RawOverrideValue rawOverrideValue2 = new com.android.server.compat.overrides.RawOverrideValue();
                rawOverrideValue2.setPackageName(entry.getKey());
                rawOverrideValue2.setMinVersionCode(entry.getValue().getMinVersionCode());
                rawOverrideValue2.setMaxVersionCode(entry.getValue().getMaxVersionCode());
                rawOverrideValue2.setEnabled(entry.getValue().isEnabled());
                rawOverrideValue.add(rawOverrideValue2);
            }
            changeOverrides.setRaw(raw);
            com.android.server.compat.overrides.ChangeOverrides.Validated validated = new com.android.server.compat.overrides.ChangeOverrides.Validated();
            java.util.List<com.android.server.compat.overrides.OverrideValue> overrideValue = validated.getOverrideValue();
            for (java.util.Map.Entry<java.lang.String, java.lang.Boolean> entry2 : this.mEvaluatedOverrides.entrySet()) {
                com.android.server.compat.overrides.OverrideValue overrideValue2 = new com.android.server.compat.overrides.OverrideValue();
                overrideValue2.setPackageName(entry2.getKey());
                overrideValue2.setEnabled(entry2.getValue().booleanValue());
                overrideValue.add(overrideValue2);
            }
            changeOverrides.setValidated(validated);
            return changeOverrides;
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ChangeId(");
        sb.append(getId());
        if (getName() != null) {
            sb.append("; name=");
            sb.append(getName());
        }
        if (getEnableSinceTargetSdk() != -1) {
            sb.append("; enableSinceTargetSdk=");
            sb.append(getEnableSinceTargetSdk());
        }
        if (getDisabled()) {
            sb.append("; disabled");
        }
        if (getLoggingOnly()) {
            sb.append("; loggingOnly");
        }
        if (!this.mEvaluatedOverrides.isEmpty()) {
            sb.append("; packageOverrides=");
            sb.append(this.mEvaluatedOverrides);
        }
        if (!this.mRawOverrides.isEmpty()) {
            sb.append("; rawOverrides=");
            sb.append(this.mRawOverrides);
        }
        if (getOverridable()) {
            sb.append("; overridable");
        }
        sb.append(")");
        return sb.toString();
    }

    private synchronized void notifyListener(java.lang.String str) {
        if (this.mListener != null) {
            this.mListener.onCompatChange(str);
        }
    }
}
