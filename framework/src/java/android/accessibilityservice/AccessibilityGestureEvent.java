package android.accessibilityservice;

/* loaded from: classes.dex */
public final class AccessibilityGestureEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.accessibilityservice.AccessibilityGestureEvent> CREATOR = new android.os.Parcelable.Creator<android.accessibilityservice.AccessibilityGestureEvent>() { // from class: android.accessibilityservice.AccessibilityGestureEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accessibilityservice.AccessibilityGestureEvent createFromParcel(android.os.Parcel parcel) {
            return new android.accessibilityservice.AccessibilityGestureEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accessibilityservice.AccessibilityGestureEvent[] newArray(int i) {
            return new android.accessibilityservice.AccessibilityGestureEvent[i];
        }
    };
    private final int mDisplayId;
    private final int mGestureId;
    private java.util.List<android.view.MotionEvent> mMotionEvents;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GestureId {
    }

    public AccessibilityGestureEvent(int i, int i2, java.util.List<android.view.MotionEvent> list) {
        this.mMotionEvents = new java.util.ArrayList();
        this.mGestureId = i;
        this.mDisplayId = i2;
        this.mMotionEvents.addAll(list);
    }

    public AccessibilityGestureEvent(int i, int i2) {
        this(i, i2, new java.util.ArrayList());
    }

    private AccessibilityGestureEvent(android.os.Parcel parcel) {
        this.mMotionEvents = new java.util.ArrayList();
        this.mGestureId = parcel.readInt();
        this.mDisplayId = parcel.readInt();
        this.mMotionEvents = ((android.content.pm.ParceledListSlice) parcel.readParcelable(getClass().getClassLoader(), android.content.pm.ParceledListSlice.class)).getList();
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public int getGestureId() {
        return this.mGestureId;
    }

    public java.util.List<android.view.MotionEvent> getMotionEvents() {
        return this.mMotionEvents;
    }

    public android.accessibilityservice.AccessibilityGestureEvent copyForAsync() {
        return new android.accessibilityservice.AccessibilityGestureEvent(this.mGestureId, this.mDisplayId, this.mMotionEvents.stream().map(new java.util.function.Function() { // from class: android.accessibilityservice.AccessibilityGestureEvent$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.view.MotionEvent) obj).copy();
            }
        }).toList());
    }

    public void recycle() {
        this.mMotionEvents.forEach(new java.util.function.Consumer() { // from class: android.accessibilityservice.AccessibilityGestureEvent$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.view.MotionEvent) obj).recycle();
            }
        });
        this.mMotionEvents.clear();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("AccessibilityGestureEvent[");
        sb.append("gestureId: ").append(gestureIdToString(this.mGestureId));
        sb.append(", ");
        sb.append("displayId: ").append(this.mDisplayId);
        sb.append(", ");
        sb.append("Motion Events: [");
        for (int i = 0; i < this.mMotionEvents.size(); i++) {
            sb.append(android.view.MotionEvent.actionToString(this.mMotionEvents.get(i).getActionMasked()));
            if (i < this.mMotionEvents.size() - 1) {
                sb.append(", ");
            } else {
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            }
        }
        sb.append(']');
        return sb.toString();
    }

    public static java.lang.String gestureIdToString(int i) {
        switch (i) {
            case -2:
                return "GESTURE_TOUCH_EXPLORATION";
            case -1:
                return "GESTURE_PASSTHROUGH";
            case 0:
                return "GESTURE_UNKNOWN";
            case 1:
                return "GESTURE_SWIPE_UP";
            case 2:
                return "GESTURE_SWIPE_DOWN";
            case 3:
                return "GESTURE_SWIPE_LEFT";
            case 4:
                return "GESTURE_SWIPE_RIGHT";
            case 5:
                return "GESTURE_SWIPE_LEFT_AND_RIGHT";
            case 6:
                return "GESTURE_SWIPE_RIGHT_AND_LEFT";
            case 7:
                return "GESTURE_SWIPE_UP_AND_DOWN";
            case 8:
                return "GESTURE_SWIPE_DOWN_AND_UP";
            case 9:
                return "GESTURE_SWIPE_LEFT_AND_UP";
            case 10:
                return "GESTURE_SWIPE_LEFT_AND_DOWN";
            case 11:
                return "GESTURE_SWIPE_RIGHT_AND_UP";
            case 12:
                return "GESTURE_SWIPE_RIGHT_AND_DOWN";
            case 13:
                return "GESTURE_SWIPE_UP_AND_LEFT";
            case 14:
                return "GESTURE_SWIPE_UP_AND_RIGHT";
            case 15:
                return "GESTURE_SWIPE_DOWN_AND_LEFT";
            case 16:
                return "GESTURE_SWIPE_DOWN_AND_RIGHT";
            case 17:
                return "GESTURE_DOUBLE_TAP";
            case 18:
                return "GESTURE_DOUBLE_TAP_AND_HOLD";
            case 19:
                return "GESTURE_2_FINGER_SINGLE_TAP";
            case 20:
                return "GESTURE_2_FINGER_DOUBLE_TAP";
            case 21:
                return "GESTURE_2_FINGER_TRIPLE_TAP";
            case 22:
                return "GESTURE_3_FINGER_SINGLE_TAP";
            case 23:
                return "GESTURE_3_FINGER_DOUBLE_TAP";
            case 24:
                return "GESTURE_3_FINGER_TRIPLE_TAP";
            case 25:
                return "GESTURE_2_FINGER_SWIPE_UP";
            case 26:
                return "GESTURE_2_FINGER_SWIPE_DOWN";
            case 27:
                return "GESTURE_2_FINGER_SWIPE_LEFT";
            case 28:
                return "GESTURE_2_FINGER_SWIPE_RIGHT";
            case 29:
                return "GESTURE_3_FINGER_SWIPE_UP";
            case 30:
                return "GESTURE_3_FINGER_SWIPE_DOWN";
            case 31:
                return "GESTURE_3_FINGER_SWIPE_LEFT";
            case 32:
                return "GESTURE_3_FINGER_SWIPE_RIGHT";
            case 33:
                return "GESTURE_4_FINGER_SWIPE_UP";
            case 34:
                return "GESTURE_4_FINGER_SWIPE_DOWN";
            case 35:
                return "GESTURE_4_FINGER_SWIPE_LEFT";
            case 36:
                return "GESTURE_4_FINGER_SWIPE_RIGHT";
            case 37:
                return "GESTURE_4_FINGER_SINGLE_TAP";
            case 38:
                return "GESTURE_4_FINGER_DOUBLE_TAP";
            case 39:
                return "GESTURE_4_FINGER_TRIPLE_TAP";
            case 40:
                return "GESTURE_2_FINGER_DOUBLE_TAP_AND_HOLD";
            case 41:
                return "GESTURE_3_FINGER_DOUBLE_TAP_AND_HOLD";
            case 42:
                return "GESTURE_4_FINGER_DOUBLE_TAP_AND_HOLD";
            case 43:
                return "GESTURE_2_FINGER_TRIPLE_TAP_AND_HOLD";
            case 44:
                return "GESTURE_3_FINGER_SINGLE_TAP_AND_HOLD";
            case 45:
                return "GESTURE_3_FINGER_TRIPLE_TAP_AND_HOLD";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mGestureId);
        parcel.writeInt(this.mDisplayId);
        parcel.writeParcelable(new android.content.pm.ParceledListSlice(this.mMotionEvents), 0);
    }
}
