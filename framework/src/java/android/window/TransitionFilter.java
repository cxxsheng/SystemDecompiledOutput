package android.window;

/* loaded from: classes4.dex */
public final class TransitionFilter implements android.os.Parcelable {
    public static final int CONTAINER_ORDER_ANY = 0;
    public static final int CONTAINER_ORDER_TOP = 1;
    public static final android.os.Parcelable.Creator<android.window.TransitionFilter> CREATOR = new android.os.Parcelable.Creator<android.window.TransitionFilter>() { // from class: android.window.TransitionFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TransitionFilter createFromParcel(android.os.Parcel parcel) {
            return new android.window.TransitionFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TransitionFilter[] newArray(int i) {
            return new android.window.TransitionFilter[i];
        }
    };
    public int mFlags;
    public int mNotFlags;
    public android.window.TransitionFilter.Requirement[] mRequirements;
    public int[] mTypeSet;

    public @interface ContainerOrder {
    }

    public TransitionFilter() {
        this.mTypeSet = null;
        this.mFlags = 0;
        this.mNotFlags = 0;
        this.mRequirements = null;
    }

    private TransitionFilter(android.os.Parcel parcel) {
        this.mTypeSet = null;
        this.mFlags = 0;
        this.mNotFlags = 0;
        this.mRequirements = null;
        this.mTypeSet = parcel.createIntArray();
        this.mFlags = parcel.readInt();
        this.mNotFlags = parcel.readInt();
        this.mRequirements = (android.window.TransitionFilter.Requirement[]) parcel.createTypedArray(android.window.TransitionFilter.Requirement.CREATOR);
    }

    public boolean matches(android.window.TransitionInfo transitionInfo) {
        boolean z;
        if (this.mTypeSet != null) {
            int i = 0;
            while (true) {
                if (i >= this.mTypeSet.length) {
                    z = false;
                    break;
                }
                if (transitionInfo.getType() != this.mTypeSet[i]) {
                    i++;
                } else {
                    z = true;
                    break;
                }
            }
            if (!z) {
                return false;
            }
        }
        if ((transitionInfo.getFlags() & this.mFlags) != this.mFlags || (transitionInfo.getFlags() & this.mNotFlags) != 0) {
            return false;
        }
        if (this.mRequirements != null) {
            for (int i2 = 0; i2 < this.mRequirements.length; i2++) {
                if (this.mRequirements[i2].matches(transitionInfo) == this.mRequirements[i2].mNot) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeIntArray(this.mTypeSet);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mNotFlags);
        parcel.writeTypedArray(this.mRequirements, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{types=[");
        int i = 0;
        if (this.mTypeSet != null) {
            int i2 = 0;
            while (i2 < this.mTypeSet.length) {
                sb.append((i2 == 0 ? "" : ",") + android.view.WindowManager.transitTypeToString(this.mTypeSet[i2]));
                i2++;
            }
        }
        sb.append("] flags=0x" + java.lang.Integer.toHexString(this.mFlags));
        sb.append("] notFlags=0x" + java.lang.Integer.toHexString(this.mNotFlags));
        sb.append(" checks=[");
        if (this.mRequirements != null) {
            while (i < this.mRequirements.length) {
                sb.append((i == 0 ? "" : ",") + this.mRequirements[i]);
                i++;
            }
        }
        return sb.append("]}").toString();
    }

    public static final class Requirement implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.window.TransitionFilter.Requirement> CREATOR = new android.os.Parcelable.Creator<android.window.TransitionFilter.Requirement>() { // from class: android.window.TransitionFilter.Requirement.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.TransitionFilter.Requirement createFromParcel(android.os.Parcel parcel) {
                return new android.window.TransitionFilter.Requirement(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.TransitionFilter.Requirement[] newArray(int i) {
                return new android.window.TransitionFilter.Requirement[i];
            }
        };
        public int mActivityType;
        public int mFlags;
        public int[] mModes;
        public boolean mMustBeIndependent;
        public boolean mMustBeTask;
        public boolean mNot;
        public int mOrder;
        public android.content.ComponentName mTopActivity;

        public Requirement() {
            this.mActivityType = 0;
            this.mMustBeIndependent = true;
            this.mNot = false;
            this.mModes = null;
            this.mFlags = 0;
            this.mMustBeTask = false;
            this.mOrder = 0;
        }

        private Requirement(android.os.Parcel parcel) {
            this.mActivityType = 0;
            this.mMustBeIndependent = true;
            this.mNot = false;
            this.mModes = null;
            this.mFlags = 0;
            this.mMustBeTask = false;
            this.mOrder = 0;
            this.mActivityType = parcel.readInt();
            this.mMustBeIndependent = parcel.readBoolean();
            this.mNot = parcel.readBoolean();
            this.mModes = parcel.createIntArray();
            this.mFlags = parcel.readInt();
            this.mMustBeTask = parcel.readBoolean();
            this.mOrder = parcel.readInt();
            this.mTopActivity = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
        }

        boolean matches(android.window.TransitionInfo transitionInfo) {
            int size = transitionInfo.getChanges().size() - 1;
            while (true) {
                boolean z = false;
                if (size < 0) {
                    return false;
                }
                android.window.TransitionInfo.Change change = transitionInfo.getChanges().get(size);
                if ((!this.mMustBeIndependent || android.window.TransitionInfo.isIndependent(change, transitionInfo)) && ((this.mOrder != 1 || size <= 0) && ((this.mActivityType == 0 || (change.getTaskInfo() != null && change.getTaskInfo().getActivityType() == this.mActivityType)) && matchesTopActivity(change.getTaskInfo(), change.getActivityComponent())))) {
                    if (this.mModes != null) {
                        int i = 0;
                        while (true) {
                            if (i >= this.mModes.length) {
                                break;
                            }
                            if (this.mModes[i] != change.getMode()) {
                                i++;
                            } else {
                                z = true;
                                break;
                            }
                        }
                        if (!z) {
                            continue;
                        }
                    }
                    if ((change.getFlags() & this.mFlags) == this.mFlags && (!this.mMustBeTask || change.getTaskInfo() != null)) {
                        break;
                    }
                }
                size--;
            }
            return true;
        }

        private boolean matchesTopActivity(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, android.content.ComponentName componentName) {
            if (this.mTopActivity == null) {
                return true;
            }
            if (componentName != null) {
                return this.mTopActivity.equals(componentName);
            }
            if (runningTaskInfo != null) {
                return this.mTopActivity.equals(runningTaskInfo.topActivity);
            }
            return false;
        }

        boolean matches(android.window.TransitionRequestInfo transitionRequestInfo) {
            if (this.mActivityType == 0) {
                return true;
            }
            return transitionRequestInfo.getTriggerTask() != null && transitionRequestInfo.getTriggerTask().getActivityType() == this.mActivityType && matchesTopActivity(transitionRequestInfo.getTriggerTask(), null);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mActivityType);
            parcel.writeBoolean(this.mMustBeIndependent);
            parcel.writeBoolean(this.mNot);
            parcel.writeIntArray(this.mModes);
            parcel.writeInt(this.mFlags);
            parcel.writeBoolean(this.mMustBeTask);
            parcel.writeInt(this.mOrder);
            parcel.writeTypedObject(this.mTopActivity, i);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append('{');
            if (this.mNot) {
                sb.append("NOT ");
            }
            sb.append("atype=" + android.app.WindowConfiguration.activityTypeToString(this.mActivityType));
            sb.append(" independent=" + this.mMustBeIndependent);
            sb.append(" modes=[");
            if (this.mModes != null) {
                int i = 0;
                while (i < this.mModes.length) {
                    sb.append((i == 0 ? "" : ",") + android.window.TransitionInfo.modeToString(this.mModes[i]));
                    i++;
                }
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            sb.append(" flags=" + android.window.TransitionInfo.flagsToString(this.mFlags));
            sb.append(" mustBeTask=" + this.mMustBeTask);
            sb.append(" order=" + android.window.TransitionFilter.containerOrderToString(this.mOrder));
            sb.append(" topActivity=").append(this.mTopActivity);
            sb.append("}");
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String containerOrderToString(int i) {
        switch (i) {
            case 0:
                return "ANY";
            case 1:
                return "TOP";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }
}
