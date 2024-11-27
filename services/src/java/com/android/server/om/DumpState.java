package com.android.server.om;

/* loaded from: classes2.dex */
public final class DumpState {

    @android.annotation.Nullable
    private java.lang.String mField;

    @android.annotation.Nullable
    private java.lang.String mOverlayName;

    @android.annotation.Nullable
    private java.lang.String mPackageName;
    private int mUserId = -1;
    private boolean mVerbose;

    public void setUserId(int i) {
        this.mUserId = i;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public void setOverlyIdentifier(java.lang.String str) {
        android.content.om.OverlayIdentifier fromString = android.content.om.OverlayIdentifier.fromString(str);
        this.mPackageName = fromString.getPackageName();
        this.mOverlayName = fromString.getOverlayName();
    }

    @android.annotation.Nullable
    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    @android.annotation.Nullable
    public java.lang.String getOverlayName() {
        return this.mOverlayName;
    }

    public void setField(java.lang.String str) {
        this.mField = str;
    }

    @android.annotation.Nullable
    public java.lang.String getField() {
        return this.mField;
    }

    public void setVerbose(boolean z) {
        this.mVerbose = z;
    }

    public boolean isVerbose() {
        return this.mVerbose;
    }
}
