package android.view.contentcapture;

/* loaded from: classes4.dex */
public final class ContentCaptureContext implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.contentcapture.ContentCaptureContext> CREATOR = new android.os.Parcelable.Creator<android.view.contentcapture.ContentCaptureContext>() { // from class: android.view.contentcapture.ContentCaptureContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.contentcapture.ContentCaptureContext createFromParcel(android.os.Parcel parcel) {
            android.view.contentcapture.ContentCaptureContext contentCaptureContext;
            if (parcel.readInt() == 1) {
                android.content.LocusId locusId = (android.content.LocusId) parcel.readParcelable(null, android.content.LocusId.class);
                android.os.Bundle readBundle = parcel.readBundle();
                android.view.contentcapture.ContentCaptureContext.Builder builder = new android.view.contentcapture.ContentCaptureContext.Builder(locusId);
                if (readBundle != null) {
                    builder.setExtras(readBundle);
                }
                contentCaptureContext = new android.view.contentcapture.ContentCaptureContext(builder);
            } else {
                contentCaptureContext = null;
            }
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readParcelable(null, android.content.ComponentName.class);
            if (componentName == null) {
                return contentCaptureContext;
            }
            return new android.view.contentcapture.ContentCaptureContext(contentCaptureContext, new android.app.assist.ActivityId(parcel), componentName, parcel.readInt(), parcel.readStrongBinder(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.contentcapture.ContentCaptureContext[] newArray(int i) {
            return new android.view.contentcapture.ContentCaptureContext[i];
        }
    };

    @android.annotation.SystemApi
    public static final int FLAG_DISABLED_BY_APP = 1;

    @android.annotation.SystemApi
    public static final int FLAG_DISABLED_BY_FLAG_SECURE = 2;
    public static final int FLAG_DISABLED_FLUSH_FOR_VIEW_TREE_APPEARING = 8;

    @android.annotation.SystemApi
    public static final int FLAG_RECONNECTED = 4;
    private final android.app.assist.ActivityId mActivityId;
    private final android.content.ComponentName mComponentName;
    private final int mDisplayId;
    private final android.os.Bundle mExtras;
    private final int mFlags;
    private final boolean mHasClientContext;
    private final android.content.LocusId mId;
    private int mParentSessionId;
    private final android.os.IBinder mWindowToken;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ContextCreationFlags {
    }

    public ContentCaptureContext(android.view.contentcapture.ContentCaptureContext contentCaptureContext, android.app.assist.ActivityId activityId, android.content.ComponentName componentName, int i, android.os.IBinder iBinder, int i2) {
        this.mParentSessionId = 0;
        if (contentCaptureContext != null) {
            this.mHasClientContext = true;
            this.mExtras = contentCaptureContext.mExtras;
            this.mId = contentCaptureContext.mId;
        } else {
            this.mHasClientContext = false;
            this.mExtras = null;
            this.mId = null;
        }
        this.mComponentName = (android.content.ComponentName) java.util.Objects.requireNonNull(componentName);
        this.mFlags = i2;
        this.mDisplayId = i;
        this.mActivityId = activityId;
        this.mWindowToken = iBinder;
    }

    private ContentCaptureContext(android.view.contentcapture.ContentCaptureContext.Builder builder) {
        this.mParentSessionId = 0;
        this.mHasClientContext = true;
        this.mExtras = builder.mExtras;
        this.mId = builder.mId;
        this.mComponentName = null;
        this.mFlags = 0;
        this.mDisplayId = -1;
        this.mActivityId = null;
        this.mWindowToken = null;
    }

    public ContentCaptureContext(android.view.contentcapture.ContentCaptureContext contentCaptureContext, int i) {
        this.mParentSessionId = 0;
        this.mHasClientContext = contentCaptureContext.mHasClientContext;
        this.mExtras = contentCaptureContext.mExtras;
        this.mId = contentCaptureContext.mId;
        this.mComponentName = contentCaptureContext.mComponentName;
        this.mFlags = i | contentCaptureContext.mFlags;
        this.mDisplayId = contentCaptureContext.mDisplayId;
        this.mActivityId = contentCaptureContext.mActivityId;
        this.mWindowToken = contentCaptureContext.mWindowToken;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public android.content.LocusId getLocusId() {
        return this.mId;
    }

    @android.annotation.SystemApi
    public int getTaskId() {
        if (this.mHasClientContext) {
            return 0;
        }
        return this.mActivityId.getTaskId();
    }

    @android.annotation.SystemApi
    public android.content.ComponentName getActivityComponent() {
        return this.mComponentName;
    }

    @android.annotation.SystemApi
    public android.app.assist.ActivityId getActivityId() {
        if (this.mHasClientContext) {
            return null;
        }
        return this.mActivityId;
    }

    @android.annotation.SystemApi
    public android.view.contentcapture.ContentCaptureSessionId getParentSessionId() {
        if (this.mParentSessionId == 0) {
            return null;
        }
        return new android.view.contentcapture.ContentCaptureSessionId(this.mParentSessionId);
    }

    public void setParentSessionId(int i) {
        this.mParentSessionId = i;
    }

    @android.annotation.SystemApi
    public int getDisplayId() {
        return this.mDisplayId;
    }

    @android.annotation.SystemApi
    public android.os.IBinder getWindowToken() {
        return this.mWindowToken;
    }

    @android.annotation.SystemApi
    public int getFlags() {
        return this.mFlags;
    }

    public static android.view.contentcapture.ContentCaptureContext forLocusId(java.lang.String str) {
        return new android.view.contentcapture.ContentCaptureContext.Builder(new android.content.LocusId(str)).build();
    }

    public static final class Builder {
        private boolean mDestroyed;
        private android.os.Bundle mExtras;
        private final android.content.LocusId mId;

        public Builder(android.content.LocusId locusId) {
            this.mId = (android.content.LocusId) java.util.Objects.requireNonNull(locusId);
        }

        public android.view.contentcapture.ContentCaptureContext.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = (android.os.Bundle) java.util.Objects.requireNonNull(bundle);
            throwIfDestroyed();
            return this;
        }

        public android.view.contentcapture.ContentCaptureContext build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new android.view.contentcapture.ContentCaptureContext(this);
        }

        private void throwIfDestroyed() {
            com.android.internal.util.Preconditions.checkState(!this.mDestroyed, "Already called #build()");
        }
    }

    public void dump(java.io.PrintWriter printWriter) {
        if (this.mComponentName != null) {
            printWriter.print("activity=");
            printWriter.print(this.mComponentName.flattenToShortString());
        }
        if (this.mId != null) {
            printWriter.print(", id=");
            this.mId.dump(printWriter);
        }
        printWriter.print(", activityId=");
        printWriter.print(this.mActivityId);
        printWriter.print(", displayId=");
        printWriter.print(this.mDisplayId);
        printWriter.print(", windowToken=");
        printWriter.print(this.mWindowToken);
        if (this.mParentSessionId != 0) {
            printWriter.print(", parentId=");
            printWriter.print(this.mParentSessionId);
        }
        if (this.mFlags > 0) {
            printWriter.print(", flags=");
            printWriter.print(this.mFlags);
        }
        if (this.mExtras != null) {
            printWriter.print(", hasExtras");
        }
    }

    private boolean fromServer() {
        return this.mComponentName != null;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("Context[");
        if (fromServer()) {
            sb.append("act=").append(android.content.ComponentName.flattenToShortString(this.mComponentName)).append(", activityId=").append(this.mActivityId).append(", displayId=").append(this.mDisplayId).append(", windowToken=").append(this.mWindowToken).append(", flags=").append(this.mFlags);
        } else {
            sb.append("id=").append(this.mId);
            if (this.mExtras != null) {
                sb.append(", hasExtras");
            }
        }
        if (this.mParentSessionId != 0) {
            sb.append(", parentId=").append(this.mParentSessionId);
        }
        return sb.append(']').toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mHasClientContext ? 1 : 0);
        if (this.mHasClientContext) {
            parcel.writeParcelable(this.mId, i);
            parcel.writeBundle(this.mExtras);
        }
        parcel.writeParcelable(this.mComponentName, i);
        if (fromServer()) {
            parcel.writeInt(this.mDisplayId);
            parcel.writeStrongBinder(this.mWindowToken);
            parcel.writeInt(this.mFlags);
            this.mActivityId.writeToParcel(parcel, i);
        }
    }
}
