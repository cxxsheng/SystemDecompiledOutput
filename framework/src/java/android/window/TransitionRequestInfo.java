package android.window;

/* loaded from: classes4.dex */
public final class TransitionRequestInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.TransitionRequestInfo> CREATOR = new android.os.Parcelable.Creator<android.window.TransitionRequestInfo>() { // from class: android.window.TransitionRequestInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TransitionRequestInfo[] newArray(int i) {
            return new android.window.TransitionRequestInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TransitionRequestInfo createFromParcel(android.os.Parcel parcel) {
            return new android.window.TransitionRequestInfo(parcel);
        }
    };
    private final int mDebugId;
    private android.window.TransitionRequestInfo.DisplayChange mDisplayChange;
    private final int mFlags;
    private android.app.ActivityManager.RunningTaskInfo mPipTask;
    private android.window.RemoteTransition mRemoteTransition;
    private android.app.ActivityManager.RunningTaskInfo mTriggerTask;
    private final int mType;

    public TransitionRequestInfo(int i, android.app.ActivityManager.RunningTaskInfo runningTaskInfo, android.window.RemoteTransition remoteTransition) {
        this(i, runningTaskInfo, null, remoteTransition, null, 0, -1);
    }

    public TransitionRequestInfo(int i, android.app.ActivityManager.RunningTaskInfo runningTaskInfo, android.window.RemoteTransition remoteTransition, int i2) {
        this(i, runningTaskInfo, null, remoteTransition, null, i2, -1);
    }

    public TransitionRequestInfo(int i, android.app.ActivityManager.RunningTaskInfo runningTaskInfo, android.window.RemoteTransition remoteTransition, android.window.TransitionRequestInfo.DisplayChange displayChange, int i2) {
        this(i, runningTaskInfo, null, remoteTransition, displayChange, i2, -1);
    }

    public TransitionRequestInfo(int i, android.app.ActivityManager.RunningTaskInfo runningTaskInfo, android.app.ActivityManager.RunningTaskInfo runningTaskInfo2, android.window.RemoteTransition remoteTransition, android.window.TransitionRequestInfo.DisplayChange displayChange, int i2) {
        this(i, runningTaskInfo, runningTaskInfo2, remoteTransition, displayChange, i2, -1);
    }

    java.lang.String typeToString() {
        return android.view.WindowManager.transitTypeToString(this.mType);
    }

    public static class DisplayChange implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.window.TransitionRequestInfo.DisplayChange> CREATOR = new android.os.Parcelable.Creator<android.window.TransitionRequestInfo.DisplayChange>() { // from class: android.window.TransitionRequestInfo.DisplayChange.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.TransitionRequestInfo.DisplayChange[] newArray(int i) {
                return new android.window.TransitionRequestInfo.DisplayChange[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.TransitionRequestInfo.DisplayChange createFromParcel(android.os.Parcel parcel) {
                return new android.window.TransitionRequestInfo.DisplayChange(parcel);
            }
        };
        private final int mDisplayId;
        private android.graphics.Rect mEndAbsBounds;
        private int mEndRotation;
        private boolean mPhysicalDisplayChanged;
        private android.graphics.Rect mStartAbsBounds;
        private int mStartRotation;

        public DisplayChange(int i) {
            this.mStartAbsBounds = null;
            this.mEndAbsBounds = null;
            this.mStartRotation = -1;
            this.mEndRotation = -1;
            this.mPhysicalDisplayChanged = false;
            this.mDisplayId = i;
        }

        public DisplayChange(int i, int i2, int i3) {
            this.mStartAbsBounds = null;
            this.mEndAbsBounds = null;
            this.mStartRotation = -1;
            this.mEndRotation = -1;
            this.mPhysicalDisplayChanged = false;
            this.mDisplayId = i;
            this.mStartRotation = i2;
            this.mEndRotation = i3;
        }

        public int getDisplayId() {
            return this.mDisplayId;
        }

        public android.graphics.Rect getStartAbsBounds() {
            return this.mStartAbsBounds;
        }

        public android.graphics.Rect getEndAbsBounds() {
            return this.mEndAbsBounds;
        }

        public int getStartRotation() {
            return this.mStartRotation;
        }

        public int getEndRotation() {
            return this.mEndRotation;
        }

        public boolean isPhysicalDisplayChanged() {
            return this.mPhysicalDisplayChanged;
        }

        public android.window.TransitionRequestInfo.DisplayChange setStartAbsBounds(android.graphics.Rect rect) {
            this.mStartAbsBounds = rect;
            return this;
        }

        public android.window.TransitionRequestInfo.DisplayChange setEndAbsBounds(android.graphics.Rect rect) {
            this.mEndAbsBounds = rect;
            return this;
        }

        public android.window.TransitionRequestInfo.DisplayChange setStartRotation(int i) {
            this.mStartRotation = i;
            return this;
        }

        public android.window.TransitionRequestInfo.DisplayChange setEndRotation(int i) {
            this.mEndRotation = i;
            return this;
        }

        public android.window.TransitionRequestInfo.DisplayChange setPhysicalDisplayChanged(boolean z) {
            this.mPhysicalDisplayChanged = z;
            return this;
        }

        public java.lang.String toString() {
            return "DisplayChange { displayId = " + this.mDisplayId + ", startAbsBounds = " + this.mStartAbsBounds + ", endAbsBounds = " + this.mEndAbsBounds + ", startRotation = " + this.mStartRotation + ", endRotation = " + this.mEndRotation + ", physicalDisplayChanged = " + this.mPhysicalDisplayChanged + " }";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            byte b = this.mPhysicalDisplayChanged ? (byte) 32 : (byte) 0;
            if (this.mStartAbsBounds != null) {
                b = (byte) (b | 2);
            }
            if (this.mEndAbsBounds != null) {
                b = (byte) (b | 4);
            }
            parcel.writeByte(b);
            parcel.writeInt(this.mDisplayId);
            if (this.mStartAbsBounds != null) {
                parcel.writeTypedObject(this.mStartAbsBounds, i);
            }
            if (this.mEndAbsBounds != null) {
                parcel.writeTypedObject(this.mEndAbsBounds, i);
            }
            parcel.writeInt(this.mStartRotation);
            parcel.writeInt(this.mEndRotation);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected DisplayChange(android.os.Parcel parcel) {
            this.mStartAbsBounds = null;
            this.mEndAbsBounds = null;
            this.mStartRotation = -1;
            this.mEndRotation = -1;
            this.mPhysicalDisplayChanged = false;
            byte readByte = parcel.readByte();
            boolean z = (readByte & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) != 0;
            int readInt = parcel.readInt();
            android.graphics.Rect rect = (readByte & 2) == 0 ? null : (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
            android.graphics.Rect rect2 = (readByte & 4) != 0 ? (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR) : null;
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            this.mDisplayId = readInt;
            this.mStartAbsBounds = rect;
            this.mEndAbsBounds = rect2;
            this.mStartRotation = readInt2;
            this.mEndRotation = readInt3;
            this.mPhysicalDisplayChanged = z;
        }

        @java.lang.Deprecated
        private void __metadata() {
        }
    }

    public TransitionRequestInfo(int i, android.app.ActivityManager.RunningTaskInfo runningTaskInfo, android.app.ActivityManager.RunningTaskInfo runningTaskInfo2, android.window.RemoteTransition remoteTransition, android.window.TransitionRequestInfo.DisplayChange displayChange, int i2, int i3) {
        this.mType = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.view.WindowManager.TransitionType.class, (java.lang.annotation.Annotation) null, this.mType);
        this.mTriggerTask = runningTaskInfo;
        this.mPipTask = runningTaskInfo2;
        this.mRemoteTransition = remoteTransition;
        this.mDisplayChange = displayChange;
        this.mFlags = i2;
        this.mDebugId = i3;
    }

    public int getType() {
        return this.mType;
    }

    public android.app.ActivityManager.RunningTaskInfo getTriggerTask() {
        return this.mTriggerTask;
    }

    public android.app.ActivityManager.RunningTaskInfo getPipTask() {
        return this.mPipTask;
    }

    public android.window.RemoteTransition getRemoteTransition() {
        return this.mRemoteTransition;
    }

    public android.window.TransitionRequestInfo.DisplayChange getDisplayChange() {
        return this.mDisplayChange;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getDebugId() {
        return this.mDebugId;
    }

    public android.window.TransitionRequestInfo setTriggerTask(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.mTriggerTask = runningTaskInfo;
        return this;
    }

    public android.window.TransitionRequestInfo setPipTask(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.mPipTask = runningTaskInfo;
        return this;
    }

    public android.window.TransitionRequestInfo setRemoteTransition(android.window.RemoteTransition remoteTransition) {
        this.mRemoteTransition = remoteTransition;
        return this;
    }

    public android.window.TransitionRequestInfo setDisplayChange(android.window.TransitionRequestInfo.DisplayChange displayChange) {
        this.mDisplayChange = displayChange;
        return this;
    }

    public java.lang.String toString() {
        return "TransitionRequestInfo { type = " + typeToString() + ", triggerTask = " + this.mTriggerTask + ", pipTask = " + this.mPipTask + ", remoteTransition = " + this.mRemoteTransition + ", displayChange = " + this.mDisplayChange + ", flags = " + this.mFlags + ", debugId = " + this.mDebugId + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mTriggerTask != null ? (byte) 2 : (byte) 0;
        if (this.mPipTask != null) {
            b = (byte) (b | 4);
        }
        if (this.mRemoteTransition != null) {
            b = (byte) (b | 8);
        }
        if (this.mDisplayChange != null) {
            b = (byte) (b | 16);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mType);
        if (this.mTriggerTask != null) {
            parcel.writeTypedObject(this.mTriggerTask, i);
        }
        if (this.mPipTask != null) {
            parcel.writeTypedObject(this.mPipTask, i);
        }
        if (this.mRemoteTransition != null) {
            parcel.writeTypedObject(this.mRemoteTransition, i);
        }
        if (this.mDisplayChange != null) {
            parcel.writeTypedObject(this.mDisplayChange, i);
        }
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mDebugId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    TransitionRequestInfo(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        android.app.ActivityManager.RunningTaskInfo runningTaskInfo = (readByte & 2) == 0 ? null : (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
        android.app.ActivityManager.RunningTaskInfo runningTaskInfo2 = (readByte & 4) == 0 ? null : (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
        android.window.RemoteTransition remoteTransition = (readByte & 8) == 0 ? null : (android.window.RemoteTransition) parcel.readTypedObject(android.window.RemoteTransition.CREATOR);
        android.window.TransitionRequestInfo.DisplayChange displayChange = (readByte & 16) == 0 ? null : (android.window.TransitionRequestInfo.DisplayChange) parcel.readTypedObject(android.window.TransitionRequestInfo.DisplayChange.CREATOR);
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        this.mType = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.view.WindowManager.TransitionType.class, (java.lang.annotation.Annotation) null, this.mType);
        this.mTriggerTask = runningTaskInfo;
        this.mPipTask = runningTaskInfo2;
        this.mRemoteTransition = remoteTransition;
        this.mDisplayChange = displayChange;
        this.mFlags = readInt2;
        this.mDebugId = readInt3;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
