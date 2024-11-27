package android.window;

/* loaded from: classes4.dex */
public final class TransitionInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.TransitionInfo> CREATOR = new android.os.Parcelable.Creator<android.window.TransitionInfo>() { // from class: android.window.TransitionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TransitionInfo createFromParcel(android.os.Parcel parcel) {
            return new android.window.TransitionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TransitionInfo[] newArray(int i) {
            return new android.window.TransitionInfo[i];
        }
    };
    public static final int FLAGS_IS_NON_APP_WINDOW = 65794;
    public static final int FLAGS_IS_OCCLUDED_NO_ANIMATION = 294912;
    public static final int FLAG_BACK_GESTURE_ANIMATED = 131072;
    public static final int FLAG_CONFIG_AT_END = 4194304;
    public static final int FLAG_CROSS_PROFILE_OWNER_THUMBNAIL = 4096;
    public static final int FLAG_CROSS_PROFILE_WORK_THUMBNAIL = 8192;
    public static final int FLAG_DISPLAY_HAS_ALERT_WINDOWS = 128;
    public static final int FLAG_FILLS_TASK = 1024;
    public static final int FLAG_FIRST_CUSTOM = 8388608;
    public static final int FLAG_IN_TASK_WITH_EMBEDDED_ACTIVITY = 512;
    public static final int FLAG_IS_BEHIND_STARTING_WINDOW = 16384;
    public static final int FLAG_IS_DISPLAY = 32;
    public static final int FLAG_IS_INPUT_METHOD = 256;
    public static final int FLAG_IS_OCCLUDED = 32768;
    public static final int FLAG_IS_SYSTEM_WINDOW = 65536;
    public static final int FLAG_IS_VOICE_INTERACTION = 16;
    public static final int FLAG_IS_WALLPAPER = 2;
    public static final int FLAG_MOVED_TO_TOP = 1048576;
    public static final int FLAG_NONE = 0;
    public static final int FLAG_NO_ANIMATION = 262144;
    public static final int FLAG_SHOW_WALLPAPER = 1;
    public static final int FLAG_STARTING_WINDOW_TRANSFER_RECIPIENT = 8;
    public static final int FLAG_SYNC = 2097152;
    public static final int FLAG_TASK_LAUNCHING_BEHIND = 524288;
    public static final int FLAG_TRANSLUCENT = 4;
    public static final int FLAG_WILL_IME_SHOWN = 2048;
    private static final java.lang.String TAG = "TransitionInfo";
    private final java.util.ArrayList<android.window.TransitionInfo.Change> mChanges;
    private int mDebugId;
    private int mFlags;
    private android.window.TransitionInfo.AnimationOptions mOptions;
    private final java.util.ArrayList<android.window.TransitionInfo.Root> mRoots;
    private int mTrack;
    private final int mType;

    public @interface ChangeFlags {
    }

    public @interface TransitionMode {
    }

    public TransitionInfo(int i, int i2) {
        this.mTrack = 0;
        this.mChanges = new java.util.ArrayList<>();
        this.mRoots = new java.util.ArrayList<>();
        this.mDebugId = -1;
        this.mType = i;
        this.mFlags = i2;
    }

    private TransitionInfo(android.os.Parcel parcel) {
        this.mTrack = 0;
        this.mChanges = new java.util.ArrayList<>();
        this.mRoots = new java.util.ArrayList<>();
        this.mDebugId = -1;
        this.mType = parcel.readInt();
        this.mFlags = parcel.readInt();
        parcel.readTypedList(this.mChanges, android.window.TransitionInfo.Change.CREATOR);
        parcel.readTypedList(this.mRoots, android.window.TransitionInfo.Root.CREATOR);
        this.mOptions = (android.window.TransitionInfo.AnimationOptions) parcel.readTypedObject(android.window.TransitionInfo.AnimationOptions.CREATOR);
        this.mDebugId = parcel.readInt();
        this.mTrack = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mFlags);
        parcel.writeTypedList(this.mChanges);
        parcel.writeTypedList(this.mRoots, i);
        parcel.writeTypedObject(this.mOptions, i);
        parcel.writeInt(this.mDebugId);
        parcel.writeInt(this.mTrack);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void addRootLeash(int i, android.view.SurfaceControl surfaceControl, int i2, int i3) {
        this.mRoots.add(new android.window.TransitionInfo.Root(i, surfaceControl, i2, i3));
    }

    public void addRoot(android.window.TransitionInfo.Root root) {
        this.mRoots.add(root);
    }

    public void setAnimationOptions(android.window.TransitionInfo.AnimationOptions animationOptions) {
        this.mOptions = animationOptions;
    }

    public int getType() {
        return this.mType;
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getRootCount() {
        return this.mRoots.size();
    }

    public android.window.TransitionInfo.Root getRoot(int i) {
        return this.mRoots.get(i);
    }

    public int findRootIndex(int i) {
        for (int i2 = 0; i2 < this.mRoots.size(); i2++) {
            if (this.mRoots.get(i2).mDisplayId == i) {
                return i2;
            }
        }
        return -1;
    }

    @java.lang.Deprecated
    public android.view.SurfaceControl getRootLeash() {
        if (this.mRoots.isEmpty()) {
            throw new java.lang.IllegalStateException("Trying to get a root leash from a no-op transition.");
        }
        if (this.mRoots.size() > 1) {
            android.util.Log.e(TAG, "Assuming one animation root when there are more.", new java.lang.Throwable());
        }
        return this.mRoots.get(0).mLeash;
    }

    public android.window.TransitionInfo.AnimationOptions getAnimationOptions() {
        return this.mOptions;
    }

    public java.util.List<android.window.TransitionInfo.Change> getChanges() {
        return this.mChanges;
    }

    public android.window.TransitionInfo.Change getChange(android.window.WindowContainerToken windowContainerToken) {
        for (int size = this.mChanges.size() - 1; size >= 0; size--) {
            if (windowContainerToken.equals(this.mChanges.get(size).mContainer)) {
                return this.mChanges.get(size);
            }
        }
        return null;
    }

    public void addChange(android.window.TransitionInfo.Change change) {
        this.mChanges.add(change);
    }

    public boolean hasChangesOrSideEffects() {
        return (this.mChanges.isEmpty() && !isKeyguardGoingAway() && (this.mFlags & 2048) == 0) ? false : true;
    }

    public boolean isKeyguardGoingAway() {
        return (this.mFlags & 256) != 0;
    }

    public int getTrack() {
        return this.mTrack;
    }

    public void setTrack(int i) {
        this.mTrack = i;
    }

    public void setDebugId(int i) {
        this.mDebugId = i;
    }

    public int getDebugId() {
        return this.mDebugId;
    }

    public java.lang.String toString() {
        return toString("");
    }

    public java.lang.String toString(java.lang.String str) {
        java.lang.String str2;
        boolean z = (str.isEmpty() || this.mChanges.isEmpty()) ? false : true;
        java.lang.String str3 = "";
        java.lang.String str4 = z ? str + "    " : "";
        if (!z) {
            str2 = "";
        } else {
            str2 = "\n" + str;
        }
        if (z) {
            str3 = "\n" + str4;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{id=").append(this.mDebugId).append(" t=").append(android.view.WindowManager.transitTypeToString(this.mType)).append(" f=0x").append(java.lang.Integer.toHexString(this.mFlags)).append(" trk=").append(this.mTrack);
        if (this.mOptions != null) {
            sb.append(" opt=").append(this.mOptions);
        }
        sb.append(" r=[");
        for (int i = 0; i < this.mRoots.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(this.mRoots.get(i).mDisplayId).append("@").append(this.mRoots.get(i).mOffset);
        }
        sb.append("] c=[");
        sb.append(str3);
        for (int i2 = 0; i2 < this.mChanges.size(); i2++) {
            if (i2 > 0) {
                sb.append(',');
                sb.append(str3);
            }
            sb.append(this.mChanges.get(i2));
        }
        sb.append(str2);
        sb.append("]}");
        return sb.toString();
    }

    public static java.lang.String modeToString(int i) {
        switch (i) {
            case 0:
                return android.security.keystore.KeyProperties.DIGEST_NONE;
            case 1:
                return "OPEN";
            case 2:
                return "CLOSE";
            case 3:
                return "TO_FRONT";
            case 4:
                return "TO_BACK";
            case 5:
            default:
                return "<unknown:" + i + ">";
            case 6:
                return "CHANGE";
        }
    }

    public static java.lang.String flagsToString(int i) {
        if (i == 0) {
            return android.security.keystore.KeyProperties.DIGEST_NONE;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if ((i & 1) != 0) {
            sb.append("SHOW_WALLPAPER");
        }
        if ((i & 2) != 0) {
            sb.append("IS_WALLPAPER");
        }
        if ((i & 256) != 0) {
            sb.append("IS_INPUT_METHOD");
        }
        if ((i & 4) != 0) {
            sb.append(sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append("TRANSLUCENT");
        }
        if ((i & 8) != 0) {
            sb.append(sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append("STARTING_WINDOW_TRANSFER");
        }
        if ((i & 16) != 0) {
            sb.append(sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append("IS_VOICE_INTERACTION");
        }
        if ((i & 32) != 0) {
            sb.append(sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append("IS_DISPLAY");
        }
        if ((i & 128) != 0) {
            sb.append(sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append("DISPLAY_HAS_ALERT_WINDOWS");
        }
        if ((i & 512) != 0) {
            sb.append(sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append("IN_TASK_WITH_EMBEDDED_ACTIVITY");
        }
        if ((i & 1024) != 0) {
            sb.append(sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append("FILLS_TASK");
        }
        if ((i & 16384) != 0) {
            sb.append(sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append("IS_BEHIND_STARTING_WINDOW");
        }
        if ((32768 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append("IS_OCCLUDED");
        }
        if ((65536 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append("FLAG_IS_SYSTEM_WINDOW");
        }
        if ((131072 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append("FLAG_BACK_GESTURE_ANIMATED");
        }
        if ((262144 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append("NO_ANIMATION");
        }
        if ((524288 & i) != 0) {
            sb.append((sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER) + "TASK_LAUNCHING_BEHIND");
        }
        if ((2097152 & i) != 0) {
            sb.append((sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER) + "SYNC");
        }
        if ((8388608 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).append("FIRST_CUSTOM");
        }
        if ((i & 1048576) != 0) {
            sb.append(sb.length() != 0 ? android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER : "").append("MOVE_TO_TOP");
        }
        return sb.toString();
    }

    public static boolean isIndependent(android.window.TransitionInfo.Change change, android.window.TransitionInfo transitionInfo) {
        if (change.getParent() == null) {
            return true;
        }
        if (change.getMode() == 6) {
            return false;
        }
        android.window.TransitionInfo.Change change2 = transitionInfo.getChange(change.getParent());
        while (change2 != null && change2.getMode() == 6) {
            if (change2.getParent() == null) {
                return true;
            }
            change2 = transitionInfo.getChange(change2.getParent());
        }
        return false;
    }

    public void releaseAnimSurfaces() {
        for (int size = this.mChanges.size() - 1; size >= 0; size--) {
            android.window.TransitionInfo.Change change = this.mChanges.get(size);
            if (change.mSnapshot != null) {
                change.mSnapshot.release();
                change.mSnapshot = null;
            }
        }
        for (int i = 0; i < this.mRoots.size(); i++) {
            this.mRoots.get(i).mLeash.release();
        }
    }

    public void releaseAllSurfaces() {
        releaseAnimSurfaces();
        for (int size = this.mChanges.size() - 1; size >= 0; size--) {
            this.mChanges.get(size).getLeash().release();
        }
    }

    public void setUnreleasedWarningCallSiteForAllSurfaces(java.lang.String str) {
        for (int size = this.mChanges.size() - 1; size >= 0; size--) {
            this.mChanges.get(size).getLeash().setUnreleasedWarningCallSite(str);
        }
    }

    public android.window.TransitionInfo localRemoteCopy() {
        android.window.TransitionInfo transitionInfo = new android.window.TransitionInfo(this.mType, this.mFlags);
        transitionInfo.mTrack = this.mTrack;
        transitionInfo.mDebugId = this.mDebugId;
        for (int i = 0; i < this.mChanges.size(); i++) {
            transitionInfo.mChanges.add(this.mChanges.get(i).localRemoteCopy());
        }
        for (int i2 = 0; i2 < this.mRoots.size(); i2++) {
            transitionInfo.mRoots.add(this.mRoots.get(i2).localRemoteCopy());
        }
        transitionInfo.mOptions = this.mOptions;
        return transitionInfo;
    }

    public static final class Change implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.window.TransitionInfo.Change> CREATOR = new android.os.Parcelable.Creator<android.window.TransitionInfo.Change>() { // from class: android.window.TransitionInfo.Change.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.TransitionInfo.Change createFromParcel(android.os.Parcel parcel) {
                return new android.window.TransitionInfo.Change(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.TransitionInfo.Change[] newArray(int i) {
                return new android.window.TransitionInfo.Change[i];
            }
        };
        private android.content.ComponentName mActivityComponent;
        private boolean mAllowEnterPip;
        private int mBackgroundColor;
        private final android.window.WindowContainerToken mContainer;
        private final android.graphics.Rect mEndAbsBounds;
        private int mEndDisplayId;
        private int mEndFixedRotation;
        private final android.graphics.Point mEndRelOffset;
        private int mEndRotation;
        private int mFlags;
        private android.window.WindowContainerToken mLastParent;
        private android.view.SurfaceControl mLeash;
        private int mMode;
        private android.window.WindowContainerToken mParent;
        private int mRotationAnimation;
        private android.view.SurfaceControl mSnapshot;
        private float mSnapshotLuma;
        private final android.graphics.Rect mStartAbsBounds;
        private int mStartDisplayId;
        private int mStartRotation;
        private android.app.ActivityManager.RunningTaskInfo mTaskInfo;

        public Change(android.window.WindowContainerToken windowContainerToken, android.view.SurfaceControl surfaceControl) {
            this.mMode = 0;
            this.mFlags = 0;
            this.mStartAbsBounds = new android.graphics.Rect();
            this.mEndAbsBounds = new android.graphics.Rect();
            this.mEndRelOffset = new android.graphics.Point();
            this.mTaskInfo = null;
            this.mStartDisplayId = -1;
            this.mEndDisplayId = -1;
            this.mStartRotation = -1;
            this.mEndRotation = -1;
            this.mEndFixedRotation = -1;
            this.mRotationAnimation = -1;
            this.mSnapshot = null;
            this.mActivityComponent = null;
            this.mContainer = windowContainerToken;
            this.mLeash = surfaceControl;
        }

        private Change(android.os.Parcel parcel) {
            this.mMode = 0;
            this.mFlags = 0;
            this.mStartAbsBounds = new android.graphics.Rect();
            this.mEndAbsBounds = new android.graphics.Rect();
            this.mEndRelOffset = new android.graphics.Point();
            this.mTaskInfo = null;
            this.mStartDisplayId = -1;
            this.mEndDisplayId = -1;
            this.mStartRotation = -1;
            this.mEndRotation = -1;
            this.mEndFixedRotation = -1;
            this.mRotationAnimation = -1;
            this.mSnapshot = null;
            this.mActivityComponent = null;
            this.mContainer = (android.window.WindowContainerToken) parcel.readTypedObject(android.window.WindowContainerToken.CREATOR);
            this.mParent = (android.window.WindowContainerToken) parcel.readTypedObject(android.window.WindowContainerToken.CREATOR);
            this.mLastParent = (android.window.WindowContainerToken) parcel.readTypedObject(android.window.WindowContainerToken.CREATOR);
            this.mLeash = new android.view.SurfaceControl();
            this.mLeash.readFromParcel(parcel);
            this.mMode = parcel.readInt();
            this.mFlags = parcel.readInt();
            this.mStartAbsBounds.readFromParcel(parcel);
            this.mEndAbsBounds.readFromParcel(parcel);
            this.mEndRelOffset.readFromParcel(parcel);
            this.mTaskInfo = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
            this.mAllowEnterPip = parcel.readBoolean();
            this.mStartDisplayId = parcel.readInt();
            this.mEndDisplayId = parcel.readInt();
            this.mStartRotation = parcel.readInt();
            this.mEndRotation = parcel.readInt();
            this.mEndFixedRotation = parcel.readInt();
            this.mRotationAnimation = parcel.readInt();
            this.mBackgroundColor = parcel.readInt();
            this.mSnapshot = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
            this.mSnapshotLuma = parcel.readFloat();
            this.mActivityComponent = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.window.TransitionInfo.Change localRemoteCopy() {
            android.window.TransitionInfo.Change change = new android.window.TransitionInfo.Change(this.mContainer, new android.view.SurfaceControl(this.mLeash, "localRemote"));
            change.mParent = this.mParent;
            change.mLastParent = this.mLastParent;
            change.mMode = this.mMode;
            change.mFlags = this.mFlags;
            change.mStartAbsBounds.set(this.mStartAbsBounds);
            change.mEndAbsBounds.set(this.mEndAbsBounds);
            change.mEndRelOffset.set(this.mEndRelOffset);
            change.mTaskInfo = this.mTaskInfo;
            change.mAllowEnterPip = this.mAllowEnterPip;
            change.mStartDisplayId = this.mStartDisplayId;
            change.mEndDisplayId = this.mEndDisplayId;
            change.mStartRotation = this.mStartRotation;
            change.mEndRotation = this.mEndRotation;
            change.mEndFixedRotation = this.mEndFixedRotation;
            change.mRotationAnimation = this.mRotationAnimation;
            change.mBackgroundColor = this.mBackgroundColor;
            change.mSnapshot = this.mSnapshot != null ? new android.view.SurfaceControl(this.mSnapshot, "localRemote") : null;
            change.mSnapshotLuma = this.mSnapshotLuma;
            change.mActivityComponent = this.mActivityComponent;
            return change;
        }

        public void setParent(android.window.WindowContainerToken windowContainerToken) {
            this.mParent = windowContainerToken;
        }

        public void setLastParent(android.window.WindowContainerToken windowContainerToken) {
            this.mLastParent = windowContainerToken;
        }

        public void setLeash(android.view.SurfaceControl surfaceControl) {
            this.mLeash = (android.view.SurfaceControl) java.util.Objects.requireNonNull(surfaceControl);
        }

        public void setMode(int i) {
            this.mMode = i;
        }

        public void setFlags(int i) {
            this.mFlags = i;
        }

        public void setStartAbsBounds(android.graphics.Rect rect) {
            this.mStartAbsBounds.set(rect);
        }

        public void setEndAbsBounds(android.graphics.Rect rect) {
            this.mEndAbsBounds.set(rect);
        }

        public void setEndRelOffset(int i, int i2) {
            this.mEndRelOffset.set(i, i2);
        }

        public void setTaskInfo(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
            this.mTaskInfo = runningTaskInfo;
        }

        public void setAllowEnterPip(boolean z) {
            this.mAllowEnterPip = z;
        }

        public void setDisplayId(int i, int i2) {
            this.mStartDisplayId = i;
            this.mEndDisplayId = i2;
        }

        public void setRotation(int i, int i2) {
            this.mStartRotation = i;
            this.mEndRotation = i2;
        }

        public void setEndFixedRotation(int i) {
            this.mEndFixedRotation = i;
        }

        public void setRotationAnimation(int i) {
            this.mRotationAnimation = i;
        }

        public void setBackgroundColor(int i) {
            this.mBackgroundColor = i;
        }

        public void setSnapshot(android.view.SurfaceControl surfaceControl, float f) {
            this.mSnapshot = surfaceControl;
            this.mSnapshotLuma = f;
        }

        public void setActivityComponent(android.content.ComponentName componentName) {
            this.mActivityComponent = componentName;
        }

        public android.window.WindowContainerToken getContainer() {
            return this.mContainer;
        }

        public android.window.WindowContainerToken getParent() {
            return this.mParent;
        }

        public android.window.WindowContainerToken getLastParent() {
            return this.mLastParent;
        }

        public int getMode() {
            return this.mMode;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public boolean hasFlags(int i) {
            return (i & this.mFlags) != 0;
        }

        public boolean hasAllFlags(int i) {
            return (this.mFlags & i) == i;
        }

        public android.graphics.Rect getStartAbsBounds() {
            return this.mStartAbsBounds;
        }

        public android.graphics.Rect getEndAbsBounds() {
            return this.mEndAbsBounds;
        }

        public android.graphics.Point getEndRelOffset() {
            return this.mEndRelOffset;
        }

        public android.view.SurfaceControl getLeash() {
            return this.mLeash;
        }

        public android.app.ActivityManager.RunningTaskInfo getTaskInfo() {
            return this.mTaskInfo;
        }

        public boolean getAllowEnterPip() {
            return this.mAllowEnterPip;
        }

        public int getStartDisplayId() {
            return this.mStartDisplayId;
        }

        public int getEndDisplayId() {
            return this.mEndDisplayId;
        }

        public int getStartRotation() {
            return this.mStartRotation;
        }

        public int getEndRotation() {
            return this.mEndRotation;
        }

        public int getEndFixedRotation() {
            return this.mEndFixedRotation;
        }

        public int getRotationAnimation() {
            return this.mRotationAnimation;
        }

        public int getBackgroundColor() {
            return this.mBackgroundColor;
        }

        public android.view.SurfaceControl getSnapshot() {
            return this.mSnapshot;
        }

        public float getSnapshotLuma() {
            return this.mSnapshotLuma;
        }

        public android.content.ComponentName getActivityComponent() {
            return this.mActivityComponent;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeTypedObject(this.mContainer, i);
            parcel.writeTypedObject(this.mParent, i);
            parcel.writeTypedObject(this.mLastParent, i);
            this.mLeash.writeToParcel(parcel, i);
            parcel.writeInt(this.mMode);
            parcel.writeInt(this.mFlags);
            this.mStartAbsBounds.writeToParcel(parcel, i);
            this.mEndAbsBounds.writeToParcel(parcel, i);
            this.mEndRelOffset.writeToParcel(parcel, i);
            parcel.writeTypedObject(this.mTaskInfo, i);
            parcel.writeBoolean(this.mAllowEnterPip);
            parcel.writeInt(this.mStartDisplayId);
            parcel.writeInt(this.mEndDisplayId);
            parcel.writeInt(this.mStartRotation);
            parcel.writeInt(this.mEndRotation);
            parcel.writeInt(this.mEndFixedRotation);
            parcel.writeInt(this.mRotationAnimation);
            parcel.writeInt(this.mBackgroundColor);
            parcel.writeTypedObject(this.mSnapshot, i);
            parcel.writeFloat(this.mSnapshotLuma);
            parcel.writeTypedObject(this.mActivityComponent, i);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append('{');
            sb.append(this.mContainer);
            sb.append(" m=");
            sb.append(android.window.TransitionInfo.modeToString(this.mMode));
            sb.append(" f=");
            sb.append(android.window.TransitionInfo.flagsToString(this.mFlags));
            if (this.mParent != null) {
                sb.append(" p=");
                sb.append(this.mParent);
            }
            if (this.mLeash != null) {
                sb.append(" leash=");
                sb.append(this.mLeash);
            }
            sb.append(" sb=");
            sb.append(this.mStartAbsBounds);
            sb.append(" eb=");
            sb.append(this.mEndAbsBounds);
            if (this.mEndRelOffset.x != 0 || this.mEndRelOffset.y != 0) {
                sb.append(" eo=");
                sb.append(this.mEndRelOffset);
            }
            sb.append(" d=");
            if (this.mStartDisplayId != this.mEndDisplayId) {
                sb.append(this.mStartDisplayId).append(android.telecom.Logging.Session.SUBSESSION_SEPARATION_CHAR);
            }
            sb.append(this.mEndDisplayId);
            if (this.mStartRotation != this.mEndRotation) {
                sb.append(" r=");
                sb.append(this.mStartRotation);
                sb.append(android.telecom.Logging.Session.SUBSESSION_SEPARATION_CHAR);
                sb.append(this.mEndRotation);
                sb.append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
                sb.append(this.mRotationAnimation);
            }
            if (this.mEndFixedRotation != -1) {
                sb.append(" endFixedRotation=");
                sb.append(this.mEndFixedRotation);
            }
            if (this.mSnapshot != null) {
                sb.append(" snapshot=");
                sb.append(this.mSnapshot);
            }
            if (this.mLastParent != null) {
                sb.append(" lastParent=");
                sb.append(this.mLastParent);
            }
            if (this.mActivityComponent != null) {
                sb.append(" component=");
                sb.append(this.mActivityComponent.flattenToShortString());
            }
            sb.append('}');
            return sb.toString();
        }
    }

    public static final class AnimationOptions implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.window.TransitionInfo.AnimationOptions> CREATOR = new android.os.Parcelable.Creator<android.window.TransitionInfo.AnimationOptions>() { // from class: android.window.TransitionInfo.AnimationOptions.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.TransitionInfo.AnimationOptions createFromParcel(android.os.Parcel parcel) {
                return new android.window.TransitionInfo.AnimationOptions(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.TransitionInfo.AnimationOptions[] newArray(int i) {
                return new android.window.TransitionInfo.AnimationOptions[i];
            }
        };
        private int mAnimations;
        private int mBackgroundColor;
        private android.window.TransitionInfo.AnimationOptions.CustomActivityTransition mCustomActivityCloseTransition;
        private android.window.TransitionInfo.AnimationOptions.CustomActivityTransition mCustomActivityOpenTransition;
        private int mEnterResId;
        private int mExitResId;
        private boolean mOverrideTaskTransition;
        private java.lang.String mPackageName;
        private android.hardware.HardwareBuffer mThumbnail;
        private final android.graphics.Rect mTransitionBounds = new android.graphics.Rect();
        private int mType;

        private AnimationOptions(int i) {
            this.mType = i;
        }

        public AnimationOptions(android.os.Parcel parcel) {
            this.mType = parcel.readInt();
            this.mEnterResId = parcel.readInt();
            this.mExitResId = parcel.readInt();
            this.mBackgroundColor = parcel.readInt();
            this.mOverrideTaskTransition = parcel.readBoolean();
            this.mPackageName = parcel.readString();
            this.mTransitionBounds.readFromParcel(parcel);
            this.mThumbnail = (android.hardware.HardwareBuffer) parcel.readTypedObject(android.hardware.HardwareBuffer.CREATOR);
            this.mAnimations = parcel.readInt();
            this.mCustomActivityOpenTransition = (android.window.TransitionInfo.AnimationOptions.CustomActivityTransition) parcel.readTypedObject(android.window.TransitionInfo.AnimationOptions.CustomActivityTransition.CREATOR);
            this.mCustomActivityCloseTransition = (android.window.TransitionInfo.AnimationOptions.CustomActivityTransition) parcel.readTypedObject(android.window.TransitionInfo.AnimationOptions.CustomActivityTransition.CREATOR);
        }

        public static android.window.TransitionInfo.AnimationOptions makeCommonAnimOptions(java.lang.String str) {
            android.window.TransitionInfo.AnimationOptions animationOptions = new android.window.TransitionInfo.AnimationOptions(14);
            animationOptions.mPackageName = str;
            return animationOptions;
        }

        public static android.window.TransitionInfo.AnimationOptions makeAnimOptionsFromLayoutParameters(android.view.WindowManager.LayoutParams layoutParams) {
            android.window.TransitionInfo.AnimationOptions animationOptions = new android.window.TransitionInfo.AnimationOptions(14);
            animationOptions.mPackageName = layoutParams.packageName;
            animationOptions.mAnimations = layoutParams.windowAnimations;
            return animationOptions;
        }

        public void addOptionsFromLayoutParameters(android.view.WindowManager.LayoutParams layoutParams) {
            this.mAnimations = layoutParams.windowAnimations;
        }

        public void addCustomActivityTransition(boolean z, int i, int i2, int i3) {
            android.window.TransitionInfo.AnimationOptions.CustomActivityTransition customActivityTransition = z ? this.mCustomActivityOpenTransition : this.mCustomActivityCloseTransition;
            if (customActivityTransition == null) {
                customActivityTransition = new android.window.TransitionInfo.AnimationOptions.CustomActivityTransition();
                if (z) {
                    this.mCustomActivityOpenTransition = customActivityTransition;
                } else {
                    this.mCustomActivityCloseTransition = customActivityTransition;
                }
            }
            customActivityTransition.addCustomActivityTransition(i, i2, i3);
        }

        public static android.window.TransitionInfo.AnimationOptions makeCustomAnimOptions(java.lang.String str, int i, int i2, int i3, boolean z) {
            android.window.TransitionInfo.AnimationOptions animationOptions = new android.window.TransitionInfo.AnimationOptions(1);
            animationOptions.mPackageName = str;
            animationOptions.mEnterResId = i;
            animationOptions.mExitResId = i2;
            animationOptions.mBackgroundColor = i3;
            animationOptions.mOverrideTaskTransition = z;
            return animationOptions;
        }

        public static android.window.TransitionInfo.AnimationOptions makeClipRevealAnimOptions(int i, int i2, int i3, int i4) {
            android.window.TransitionInfo.AnimationOptions animationOptions = new android.window.TransitionInfo.AnimationOptions(11);
            animationOptions.mTransitionBounds.set(i, i2, i3 + i, i4 + i2);
            return animationOptions;
        }

        public static android.window.TransitionInfo.AnimationOptions makeScaleUpAnimOptions(int i, int i2, int i3, int i4) {
            android.window.TransitionInfo.AnimationOptions animationOptions = new android.window.TransitionInfo.AnimationOptions(2);
            animationOptions.mTransitionBounds.set(i, i2, i3 + i, i4 + i2);
            return animationOptions;
        }

        public static android.window.TransitionInfo.AnimationOptions makeThumbnailAnimOptions(android.hardware.HardwareBuffer hardwareBuffer, int i, int i2, boolean z) {
            android.window.TransitionInfo.AnimationOptions animationOptions = new android.window.TransitionInfo.AnimationOptions(z ? 3 : 4);
            animationOptions.mTransitionBounds.set(i, i2, i, i2);
            animationOptions.mThumbnail = hardwareBuffer;
            return animationOptions;
        }

        public static android.window.TransitionInfo.AnimationOptions makeCrossProfileAnimOptions() {
            return new android.window.TransitionInfo.AnimationOptions(12);
        }

        public static android.window.TransitionInfo.AnimationOptions makeSceneTransitionAnimOptions() {
            return new android.window.TransitionInfo.AnimationOptions(5);
        }

        public int getType() {
            return this.mType;
        }

        public int getEnterResId() {
            return this.mEnterResId;
        }

        public int getExitResId() {
            return this.mExitResId;
        }

        public int getBackgroundColor() {
            return this.mBackgroundColor;
        }

        public boolean getOverrideTaskTransition() {
            return this.mOverrideTaskTransition;
        }

        public java.lang.String getPackageName() {
            return this.mPackageName;
        }

        public android.graphics.Rect getTransitionBounds() {
            return this.mTransitionBounds;
        }

        public android.hardware.HardwareBuffer getThumbnail() {
            return this.mThumbnail;
        }

        public int getAnimations() {
            return this.mAnimations;
        }

        public android.window.TransitionInfo.AnimationOptions.CustomActivityTransition getCustomActivityTransition(boolean z) {
            return z ? this.mCustomActivityOpenTransition : this.mCustomActivityCloseTransition;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mType);
            parcel.writeInt(this.mEnterResId);
            parcel.writeInt(this.mExitResId);
            parcel.writeInt(this.mBackgroundColor);
            parcel.writeBoolean(this.mOverrideTaskTransition);
            parcel.writeString(this.mPackageName);
            this.mTransitionBounds.writeToParcel(parcel, i);
            parcel.writeTypedObject(this.mThumbnail, i);
            parcel.writeInt(this.mAnimations);
            parcel.writeTypedObject(this.mCustomActivityOpenTransition, i);
            parcel.writeTypedObject(this.mCustomActivityCloseTransition, i);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private static java.lang.String typeToString(int i) {
            switch (i) {
                case 1:
                    return "CUSTOM";
                case 2:
                    return "SCALE_UP";
                case 3:
                    return "THUMBNAIL_SCALE_UP";
                case 4:
                    return "THUMBNAIL_SCALE_DOWN";
                case 5:
                    return "SCENE_TRANSITION";
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 13:
                default:
                    return "<" + i + ">";
                case 11:
                    return "CLIP_REVEAL";
                case 12:
                    return "OPEN_CROSS_PROFILE_APPS";
                case 14:
                    return "FROM_STYLE";
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(32);
            sb.append("{t=").append(typeToString(this.mType));
            if (this.mOverrideTaskTransition) {
                sb.append(" overrideTask=true");
            }
            if (!this.mTransitionBounds.isEmpty()) {
                sb.append(" bounds=").append(this.mTransitionBounds);
            }
            sb.append('}');
            return sb.toString();
        }

        public static class CustomActivityTransition implements android.os.Parcelable {
            public static final android.os.Parcelable.Creator<android.window.TransitionInfo.AnimationOptions.CustomActivityTransition> CREATOR = new android.os.Parcelable.Creator<android.window.TransitionInfo.AnimationOptions.CustomActivityTransition>() { // from class: android.window.TransitionInfo.AnimationOptions.CustomActivityTransition.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.window.TransitionInfo.AnimationOptions.CustomActivityTransition createFromParcel(android.os.Parcel parcel) {
                    return new android.window.TransitionInfo.AnimationOptions.CustomActivityTransition(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.window.TransitionInfo.AnimationOptions.CustomActivityTransition[] newArray(int i) {
                    return new android.window.TransitionInfo.AnimationOptions.CustomActivityTransition[i];
                }
            };
            private int mCustomBackgroundColor;
            private int mCustomEnterResId;
            private int mCustomExitResId;

            public int getCustomEnterResId() {
                return this.mCustomEnterResId;
            }

            public int getCustomExitResId() {
                return this.mCustomExitResId;
            }

            public int getCustomBackgroundColor() {
                return this.mCustomBackgroundColor;
            }

            CustomActivityTransition() {
            }

            CustomActivityTransition(android.os.Parcel parcel) {
                this.mCustomEnterResId = parcel.readInt();
                this.mCustomExitResId = parcel.readInt();
                this.mCustomBackgroundColor = parcel.readInt();
            }

            public void addCustomActivityTransition(int i, int i2, int i3) {
                this.mCustomEnterResId = i;
                this.mCustomExitResId = i2;
                this.mCustomBackgroundColor = i3;
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(android.os.Parcel parcel, int i) {
                parcel.writeInt(this.mCustomEnterResId);
                parcel.writeInt(this.mCustomExitResId);
                parcel.writeInt(this.mCustomBackgroundColor);
            }
        }
    }

    public static final class Root implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.window.TransitionInfo.Root> CREATOR = new android.os.Parcelable.Creator<android.window.TransitionInfo.Root>() { // from class: android.window.TransitionInfo.Root.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.TransitionInfo.Root createFromParcel(android.os.Parcel parcel) {
                return new android.window.TransitionInfo.Root(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.TransitionInfo.Root[] newArray(int i) {
                return new android.window.TransitionInfo.Root[i];
            }
        };
        private final int mDisplayId;
        private final android.view.SurfaceControl mLeash;
        private final android.graphics.Point mOffset;

        public Root(int i, android.view.SurfaceControl surfaceControl, int i2, int i3) {
            this.mOffset = new android.graphics.Point();
            this.mDisplayId = i;
            this.mLeash = surfaceControl;
            this.mOffset.set(i2, i3);
        }

        private Root(android.os.Parcel parcel) {
            this.mOffset = new android.graphics.Point();
            this.mDisplayId = parcel.readInt();
            this.mLeash = new android.view.SurfaceControl();
            this.mLeash.readFromParcel(parcel);
            this.mLeash.setUnreleasedWarningCallSite("TransitionInfo.Root");
            this.mOffset.readFromParcel(parcel);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.window.TransitionInfo.Root localRemoteCopy() {
            return new android.window.TransitionInfo.Root(this.mDisplayId, new android.view.SurfaceControl(this.mLeash, "localRemote"), this.mOffset.x, this.mOffset.y);
        }

        public int getDisplayId() {
            return this.mDisplayId;
        }

        public android.view.SurfaceControl getLeash() {
            return this.mLeash;
        }

        public android.graphics.Point getOffset() {
            return this.mOffset;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mDisplayId);
            this.mLeash.writeToParcel(parcel, i);
            this.mOffset.writeToParcel(parcel, i);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.String toString() {
            return this.mDisplayId + "@" + this.mOffset + ":" + this.mLeash;
        }
    }
}
