package android.view;

/* loaded from: classes4.dex */
public class DragEvent implements android.os.Parcelable {
    public static final int ACTION_DRAG_ENDED = 4;
    public static final int ACTION_DRAG_ENTERED = 5;
    public static final int ACTION_DRAG_EXITED = 6;
    public static final int ACTION_DRAG_LOCATION = 2;
    public static final int ACTION_DRAG_STARTED = 1;
    public static final int ACTION_DROP = 3;
    private static final int MAX_RECYCLED = 10;
    private static final boolean TRACK_RECYCLED_LOCATION = false;
    int mAction;
    android.content.ClipData mClipData;
    android.content.ClipDescription mClipDescription;
    com.android.internal.view.IDragAndDropPermissions mDragAndDropPermissions;
    boolean mDragResult;
    private android.view.SurfaceControl mDragSurface;
    boolean mEventHandlerWasCalled;
    java.lang.Object mLocalState;
    private android.view.DragEvent mNext;
    private float mOffsetX;
    private float mOffsetY;
    private boolean mRecycled;
    private java.lang.RuntimeException mRecycledLocation;
    float mX;
    float mY;
    private static final java.lang.Object gRecyclerLock = new java.lang.Object();
    private static int gRecyclerUsed = 0;
    private static android.view.DragEvent gRecyclerTop = null;
    public static final android.os.Parcelable.Creator<android.view.DragEvent> CREATOR = new android.os.Parcelable.Creator<android.view.DragEvent>() { // from class: android.view.DragEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.DragEvent createFromParcel(android.os.Parcel parcel) {
            android.view.DragEvent obtain = android.view.DragEvent.obtain();
            obtain.mAction = parcel.readInt();
            obtain.mX = parcel.readFloat();
            obtain.mY = parcel.readFloat();
            obtain.mOffsetX = parcel.readFloat();
            obtain.mOffsetY = parcel.readFloat();
            obtain.mDragResult = parcel.readInt() != 0;
            if (parcel.readInt() != 0) {
                obtain.mClipData = android.content.ClipData.CREATOR.createFromParcel(parcel);
            }
            if (parcel.readInt() != 0) {
                obtain.mClipDescription = android.content.ClipDescription.CREATOR.createFromParcel(parcel);
            }
            if (parcel.readInt() != 0) {
                obtain.mDragSurface = android.view.SurfaceControl.CREATOR.createFromParcel(parcel);
                obtain.mDragSurface.setUnreleasedWarningCallSite("DragEvent");
            }
            if (parcel.readInt() != 0) {
                obtain.mDragAndDropPermissions = com.android.internal.view.IDragAndDropPermissions.Stub.asInterface(parcel.readStrongBinder());
            }
            return obtain;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.DragEvent[] newArray(int i) {
            return new android.view.DragEvent[i];
        }
    };

    private DragEvent() {
    }

    private void init(int i, float f, float f2, float f3, float f4, android.content.ClipDescription clipDescription, android.content.ClipData clipData, android.view.SurfaceControl surfaceControl, com.android.internal.view.IDragAndDropPermissions iDragAndDropPermissions, java.lang.Object obj, boolean z) {
        this.mAction = i;
        this.mX = f;
        this.mY = f2;
        this.mOffsetX = f3;
        this.mOffsetY = f4;
        this.mClipDescription = clipDescription;
        this.mClipData = clipData;
        this.mDragSurface = surfaceControl;
        this.mDragAndDropPermissions = iDragAndDropPermissions;
        this.mLocalState = obj;
        this.mDragResult = z;
    }

    static android.view.DragEvent obtain() {
        return obtain(0, 0.0f, 0.0f, 0.0f, 0.0f, null, null, null, null, null, false);
    }

    public static android.view.DragEvent obtain(int i, float f, float f2, float f3, float f4, java.lang.Object obj, android.content.ClipDescription clipDescription, android.content.ClipData clipData, android.view.SurfaceControl surfaceControl, com.android.internal.view.IDragAndDropPermissions iDragAndDropPermissions, boolean z) {
        synchronized (gRecyclerLock) {
            if (gRecyclerTop == null) {
                android.view.DragEvent dragEvent = new android.view.DragEvent();
                dragEvent.init(i, f, f2, f3, f4, clipDescription, clipData, surfaceControl, iDragAndDropPermissions, obj, z);
                return dragEvent;
            }
            android.view.DragEvent dragEvent2 = gRecyclerTop;
            gRecyclerTop = dragEvent2.mNext;
            gRecyclerUsed--;
            dragEvent2.mRecycledLocation = null;
            dragEvent2.mRecycled = false;
            dragEvent2.mNext = null;
            dragEvent2.init(i, f, f2, f3, f4, clipDescription, clipData, surfaceControl, iDragAndDropPermissions, obj, z);
            return dragEvent2;
        }
    }

    public static android.view.DragEvent obtain(android.view.DragEvent dragEvent) {
        return obtain(dragEvent.mAction, dragEvent.mX, dragEvent.mY, dragEvent.mOffsetX, dragEvent.mOffsetY, dragEvent.mLocalState, dragEvent.mClipDescription, dragEvent.mClipData, dragEvent.mDragSurface, dragEvent.mDragAndDropPermissions, dragEvent.mDragResult);
    }

    public int getAction() {
        return this.mAction;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public float getOffsetX() {
        return this.mOffsetX;
    }

    public float getOffsetY() {
        return this.mOffsetY;
    }

    public android.content.ClipData getClipData() {
        return this.mClipData;
    }

    public android.content.ClipDescription getClipDescription() {
        return this.mClipDescription;
    }

    public android.view.SurfaceControl getDragSurface() {
        return this.mDragSurface;
    }

    public com.android.internal.view.IDragAndDropPermissions getDragAndDropPermissions() {
        return this.mDragAndDropPermissions;
    }

    public java.lang.Object getLocalState() {
        return this.mLocalState;
    }

    public boolean getResult() {
        return this.mDragResult;
    }

    public final void recycle() {
        if (this.mRecycled) {
            throw new java.lang.RuntimeException(toString() + " recycled twice!");
        }
        this.mRecycled = true;
        this.mClipData = null;
        this.mClipDescription = null;
        this.mLocalState = null;
        this.mEventHandlerWasCalled = false;
        synchronized (gRecyclerLock) {
            if (gRecyclerUsed < 10) {
                gRecyclerUsed++;
                this.mNext = gRecyclerTop;
                gRecyclerTop = this;
            }
        }
    }

    public static java.lang.String actionToString(int i) {
        switch (i) {
            case 1:
                return "ACTION_DRAG_STARTED";
            case 2:
                return "ACTION_DRAG_LOCATION";
            case 3:
                return "ACTION_DROP";
            case 4:
                return "ACTION_DRAG_ENDED";
            case 5:
                return "ACTION_DRAG_ENTERED";
            case 6:
                return "ACTION_DRAG_EXITED";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public java.lang.String toString() {
        return "DragEvent{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " action=" + this.mAction + " @ (" + this.mX + ", " + this.mY + ") desc=" + this.mClipDescription + " data=" + this.mClipData + " local=" + this.mLocalState + " result=" + this.mDragResult + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAction);
        parcel.writeFloat(this.mX);
        parcel.writeFloat(this.mY);
        parcel.writeFloat(this.mOffsetX);
        parcel.writeFloat(this.mOffsetY);
        parcel.writeInt(this.mDragResult ? 1 : 0);
        if (this.mClipData == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mClipData.writeToParcel(parcel, i);
        }
        if (this.mClipDescription == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mClipDescription.writeToParcel(parcel, i);
        }
        if (this.mDragSurface == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mDragSurface.writeToParcel(parcel, i);
        }
        if (this.mDragAndDropPermissions == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeStrongBinder(this.mDragAndDropPermissions.asBinder());
        }
    }
}
