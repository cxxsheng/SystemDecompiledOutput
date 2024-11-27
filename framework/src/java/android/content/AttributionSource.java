package android.content;

/* loaded from: classes.dex */
public final class AttributionSource implements android.os.Parcelable {
    private final android.content.AttributionSourceState mAttributionSourceState;
    private android.content.AttributionSource mNextCached;
    private java.util.Set<java.lang.String> mRenouncedPermissionsCached;
    private static final java.lang.String DESCRIPTOR = "android.content.AttributionSource";
    private static final android.os.Binder sDefaultToken = new android.os.Binder(DESCRIPTOR);
    public static final android.os.Parcelable.Creator<android.content.AttributionSource> CREATOR = new android.os.Parcelable.Creator<android.content.AttributionSource>() { // from class: android.content.AttributionSource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.AttributionSource[] newArray(int i) {
            return new android.content.AttributionSource[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.AttributionSource createFromParcel(android.os.Parcel parcel) {
            return new android.content.AttributionSource(parcel);
        }
    };

    public AttributionSource(int i, java.lang.String str, java.lang.String str2) {
        this(i, -1, str, str2, sDefaultToken);
    }

    public AttributionSource(int i, java.lang.String str, java.lang.String str2, int i2) {
        this(i, -1, str, str2, sDefaultToken, null, i2, null);
    }

    public AttributionSource(int i, int i2, java.lang.String str, java.lang.String str2) {
        this(i, i2, str, str2, sDefaultToken);
    }

    public AttributionSource(int i, java.lang.String str, java.lang.String str2, android.os.IBinder iBinder) {
        this(i, -1, str, str2, iBinder, null, 0, null);
    }

    public AttributionSource(int i, int i2, java.lang.String str, java.lang.String str2, android.os.IBinder iBinder) {
        this(i, i2, str, str2, iBinder, null, 0, null);
    }

    public AttributionSource(int i, java.lang.String str, java.lang.String str2, java.util.Set<java.lang.String> set, android.content.AttributionSource attributionSource) {
        this(i, -1, str, str2, sDefaultToken, set != null ? (java.lang.String[]) set.toArray(new java.lang.String[0]) : null, 0, attributionSource);
    }

    public AttributionSource(android.content.AttributionSource attributionSource, android.content.AttributionSource attributionSource2) {
        this(attributionSource.getUid(), attributionSource.getPid(), attributionSource.getPackageName(), attributionSource.getAttributionTag(), attributionSource.getToken(), attributionSource.mAttributionSourceState.renouncedPermissions, attributionSource.getDeviceId(), attributionSource2);
    }

    public AttributionSource(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, int i3, android.content.AttributionSource attributionSource) {
        this(i, i2, str, str2, sDefaultToken, strArr, i3, attributionSource);
    }

    public AttributionSource(int i, int i2, java.lang.String str, java.lang.String str2, android.os.IBinder iBinder, java.lang.String[] strArr, android.content.AttributionSource attributionSource) {
        this(i, i2, str, str2, iBinder, strArr, 0, attributionSource);
    }

    public AttributionSource(int i, int i2, java.lang.String str, java.lang.String str2, android.os.IBinder iBinder, java.lang.String[] strArr, int i3, android.content.AttributionSource attributionSource) {
        this.mAttributionSourceState = new android.content.AttributionSourceState();
        this.mAttributionSourceState.uid = i;
        this.mAttributionSourceState.pid = i2;
        this.mAttributionSourceState.token = iBinder;
        this.mAttributionSourceState.packageName = str;
        this.mAttributionSourceState.attributionTag = str2;
        this.mAttributionSourceState.renouncedPermissions = strArr;
        this.mAttributionSourceState.deviceId = i3;
        this.mAttributionSourceState.next = attributionSource != null ? new android.content.AttributionSourceState[]{attributionSource.mAttributionSourceState} : new android.content.AttributionSourceState[0];
    }

    AttributionSource(android.os.Parcel parcel) {
        this(android.content.AttributionSourceState.CREATOR.createFromParcel(parcel));
        if (!android.os.Binder.isDirectlyHandlingTransaction()) {
            throw new java.lang.SecurityException("AttributionSource should be unparceled during a binder transaction for proper verification.");
        }
        enforceCallingUid();
        if (android.os.Binder.getCallingPid() == 0) {
            this.mAttributionSourceState.pid = -1;
        }
        enforceCallingPid();
    }

    public AttributionSource(android.content.AttributionSourceState attributionSourceState) {
        this.mAttributionSourceState = attributionSourceState;
    }

    public android.content.AttributionSource withNextAttributionSource(android.content.AttributionSource attributionSource) {
        return new android.content.AttributionSource(getUid(), getPid(), getPackageName(), getAttributionTag(), getToken(), this.mAttributionSourceState.renouncedPermissions, getDeviceId(), attributionSource);
    }

    public android.content.AttributionSource withPackageName(java.lang.String str) {
        return new android.content.AttributionSource(getUid(), getPid(), str, getAttributionTag(), getToken(), this.mAttributionSourceState.renouncedPermissions, getDeviceId(), getNext());
    }

    public android.content.AttributionSource withToken(android.os.IBinder iBinder) {
        return new android.content.AttributionSource(getUid(), getPid(), getPackageName(), getAttributionTag(), iBinder, this.mAttributionSourceState.renouncedPermissions, getDeviceId(), getNext());
    }

    public android.content.AttributionSource withDefaultToken() {
        return withToken(sDefaultToken);
    }

    public android.content.AttributionSource withPid(int i) {
        return new android.content.AttributionSource(getUid(), i, getPackageName(), getAttributionTag(), getToken(), this.mAttributionSourceState.renouncedPermissions, getDeviceId(), getNext());
    }

    public android.content.AttributionSource withDeviceId(int i) {
        return new android.content.AttributionSource(getUid(), getPid(), getPackageName(), getAttributionTag(), getToken(), this.mAttributionSourceState.renouncedPermissions, i, getNext());
    }

    public android.content.AttributionSourceState asState() {
        return this.mAttributionSourceState;
    }

    public android.content.AttributionSource.ScopedParcelState asScopedParcelState() {
        return new android.content.AttributionSource.ScopedParcelState(this);
    }

    public static android.content.AttributionSource myAttributionSource() {
        android.content.AttributionSource currentAttributionSource = android.app.ActivityThread.currentAttributionSource();
        if (currentAttributionSource != null) {
            return currentAttributionSource;
        }
        int myUid = android.os.Process.myUid();
        if (myUid == 0) {
            myUid = 1000;
        }
        try {
            return new android.content.AttributionSource.Builder(myUid).setPid(android.os.Process.myPid()).setDeviceId(0).setPackageName(android.app.AppGlobals.getPackageManager().getPackagesForUid(myUid)[0]).build();
        } catch (java.lang.Exception e) {
            throw new java.lang.IllegalStateException("Failed to resolve AttributionSource");
        }
    }

    public static class ScopedParcelState implements java.lang.AutoCloseable {
        private final android.os.Parcel mParcel = android.os.Parcel.obtain();

        public android.os.Parcel getParcel() {
            return this.mParcel;
        }

        public ScopedParcelState(android.content.AttributionSource attributionSource) {
            attributionSource.writeToParcel(this.mParcel, 0);
            this.mParcel.setDataPosition(0);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mParcel.recycle();
        }
    }

    public void enforceCallingUid() {
        if (!checkCallingUid()) {
            throw new java.lang.SecurityException("Calling uid: " + android.os.Binder.getCallingUid() + " doesn't match source uid: " + this.mAttributionSourceState.uid);
        }
    }

    public boolean checkCallingUid() {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 0 && android.os.UserHandle.getAppId(callingUid) != 1000 && callingUid != this.mAttributionSourceState.uid) {
            return false;
        }
        return true;
    }

    public void enforceCallingPid() {
        if (!checkCallingPid()) {
            if (android.os.Binder.getCallingPid() == 0) {
                throw new java.lang.SecurityException("Calling pid unavailable due to oneway Binder call.");
            }
            throw new java.lang.SecurityException("Calling pid: " + android.os.Binder.getCallingPid() + " doesn't match source pid: " + this.mAttributionSourceState.pid);
        }
    }

    private boolean checkCallingPid() {
        int callingPid = android.os.Binder.getCallingPid();
        if (this.mAttributionSourceState.pid != -1 && callingPid != this.mAttributionSourceState.pid) {
            return false;
        }
        return true;
    }

    public java.lang.String toString() {
        if (android.os.Build.IS_DEBUGGABLE) {
            return "AttributionSource { uid = " + this.mAttributionSourceState.uid + ", packageName = " + this.mAttributionSourceState.packageName + ", attributionTag = " + this.mAttributionSourceState.attributionTag + ", token = " + this.mAttributionSourceState.token + ", deviceId = " + this.mAttributionSourceState.deviceId + ", next = " + ((this.mAttributionSourceState.next == null || this.mAttributionSourceState.next.length <= 0) ? null : new android.content.AttributionSource(this.mAttributionSourceState.next[0]).toString()) + " }";
        }
        return super.toString();
    }

    public int getNextUid() {
        if (this.mAttributionSourceState.next != null && this.mAttributionSourceState.next.length > 0) {
            return this.mAttributionSourceState.next[0].uid;
        }
        return -1;
    }

    public java.lang.String getNextPackageName() {
        if (this.mAttributionSourceState.next != null && this.mAttributionSourceState.next.length > 0) {
            return this.mAttributionSourceState.next[0].packageName;
        }
        return null;
    }

    public java.lang.String getNextAttributionTag() {
        if (this.mAttributionSourceState.next != null && this.mAttributionSourceState.next.length > 0) {
            return this.mAttributionSourceState.next[0].attributionTag;
        }
        return null;
    }

    public android.os.IBinder getNextToken() {
        if (this.mAttributionSourceState.next != null && this.mAttributionSourceState.next.length > 0) {
            return this.mAttributionSourceState.next[0].token;
        }
        return null;
    }

    public boolean isTrusted(android.content.Context context) {
        return this.mAttributionSourceState.token != null && ((android.permission.PermissionManager) context.getSystemService(android.permission.PermissionManager.class)).isRegisteredAttributionSource(this);
    }

    @android.annotation.SystemApi
    public java.util.Set<java.lang.String> getRenouncedPermissions() {
        if (this.mRenouncedPermissionsCached == null) {
            if (this.mAttributionSourceState.renouncedPermissions != null) {
                this.mRenouncedPermissionsCached = new android.util.ArraySet(this.mAttributionSourceState.renouncedPermissions);
            } else {
                this.mRenouncedPermissionsCached = java.util.Collections.emptySet();
            }
        }
        return this.mRenouncedPermissionsCached;
    }

    public int getUid() {
        return this.mAttributionSourceState.uid;
    }

    public int getPid() {
        return this.mAttributionSourceState.pid;
    }

    public java.lang.String getPackageName() {
        return this.mAttributionSourceState.packageName;
    }

    public java.lang.String getAttributionTag() {
        return this.mAttributionSourceState.attributionTag;
    }

    public int getDeviceId() {
        return this.mAttributionSourceState.deviceId;
    }

    public android.os.IBinder getToken() {
        return this.mAttributionSourceState.token;
    }

    public android.content.AttributionSource getNext() {
        if (this.mNextCached == null && this.mAttributionSourceState.next != null && this.mAttributionSourceState.next.length > 0) {
            this.mNextCached = new android.content.AttributionSource(this.mAttributionSourceState.next[0]);
        }
        return this.mNextCached;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.AttributionSource attributionSource = (android.content.AttributionSource) obj;
        if (equalsExceptToken(attributionSource) && java.util.Objects.equals(this.mAttributionSourceState.token, attributionSource.mAttributionSourceState.token)) {
            return true;
        }
        return false;
    }

    public boolean equalsExceptToken(android.content.AttributionSource attributionSource) {
        return attributionSource != null && this.mAttributionSourceState.uid == attributionSource.mAttributionSourceState.uid && java.util.Objects.equals(this.mAttributionSourceState.packageName, attributionSource.mAttributionSourceState.packageName) && java.util.Objects.equals(this.mAttributionSourceState.attributionTag, attributionSource.mAttributionSourceState.attributionTag) && java.util.Arrays.equals(this.mAttributionSourceState.renouncedPermissions, attributionSource.mAttributionSourceState.renouncedPermissions) && java.util.Objects.equals(getNext(), attributionSource.getNext());
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mAttributionSourceState.uid), this.mAttributionSourceState.packageName, this.mAttributionSourceState.attributionTag, this.mAttributionSourceState.token, java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mAttributionSourceState.renouncedPermissions)), getNext());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mAttributionSourceState.writeToParcel(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private final android.content.AttributionSourceState mAttributionSourceState = new android.content.AttributionSourceState();
        private long mBuilderFieldsSet = 0;

        public Builder(int i) {
            this.mAttributionSourceState.uid = i;
        }

        public Builder(android.content.AttributionSource attributionSource) {
            if (attributionSource == null) {
                throw new java.lang.IllegalArgumentException("current AttributionSource can not be null");
            }
            this.mAttributionSourceState.uid = attributionSource.getUid();
            this.mAttributionSourceState.pid = attributionSource.getPid();
            this.mAttributionSourceState.packageName = attributionSource.getPackageName();
            this.mAttributionSourceState.attributionTag = attributionSource.getAttributionTag();
            this.mAttributionSourceState.token = attributionSource.getToken();
            this.mAttributionSourceState.renouncedPermissions = attributionSource.mAttributionSourceState.renouncedPermissions;
        }

        public android.content.AttributionSource.Builder setPid(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mAttributionSourceState.pid = i;
            return this;
        }

        public android.content.AttributionSource.Builder setPackageName(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mAttributionSourceState.packageName = str;
            return this;
        }

        public android.content.AttributionSource.Builder setAttributionTag(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mAttributionSourceState.attributionTag = str;
            return this;
        }

        @android.annotation.SystemApi
        public android.content.AttributionSource.Builder setRenouncedPermissions(java.util.Set<java.lang.String> set) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mAttributionSourceState.renouncedPermissions = set != null ? (java.lang.String[]) set.toArray(new java.lang.String[0]) : null;
            return this;
        }

        public android.content.AttributionSource.Builder setDeviceId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 18;
            this.mAttributionSourceState.deviceId = i;
            return this;
        }

        public android.content.AttributionSource.Builder setNext(android.content.AttributionSource attributionSource) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mAttributionSourceState.next = attributionSource != null ? new android.content.AttributionSourceState[]{attributionSource.mAttributionSourceState} : this.mAttributionSourceState.next;
            return this;
        }

        public android.content.AttributionSource.Builder setNextAttributionSource(android.content.AttributionSource attributionSource) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mAttributionSourceState.next = new android.content.AttributionSourceState[]{attributionSource.mAttributionSourceState};
            return this;
        }

        public android.content.AttributionSource build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mAttributionSourceState.pid = -1;
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mAttributionSourceState.packageName = null;
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mAttributionSourceState.attributionTag = null;
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mAttributionSourceState.renouncedPermissions = null;
            }
            if ((this.mBuilderFieldsSet & 18) == 0) {
                this.mAttributionSourceState.deviceId = 0;
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mAttributionSourceState.next = null;
            }
            this.mAttributionSourceState.token = android.content.AttributionSource.sDefaultToken;
            if (this.mAttributionSourceState.next == null) {
                this.mAttributionSourceState.next = new android.content.AttributionSourceState[0];
            }
            return new android.content.AttributionSource(this.mAttributionSourceState);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 64) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
