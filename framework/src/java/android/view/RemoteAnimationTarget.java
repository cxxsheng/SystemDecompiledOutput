package android.view;

/* loaded from: classes4.dex */
public class RemoteAnimationTarget implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.RemoteAnimationTarget> CREATOR = new android.os.Parcelable.Creator<android.view.RemoteAnimationTarget>() { // from class: android.view.RemoteAnimationTarget.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.RemoteAnimationTarget createFromParcel(android.os.Parcel parcel) {
            return new android.view.RemoteAnimationTarget(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.RemoteAnimationTarget[] newArray(int i) {
            return new android.view.RemoteAnimationTarget[i];
        }
    };
    public static final int MODE_CHANGING = 2;
    public static final int MODE_CLOSING = 1;
    public static final int MODE_OPENING = 0;
    public boolean allowEnterPip;
    public int backgroundColor;
    public final android.graphics.Rect clipRect;
    public final android.graphics.Rect contentInsets;
    public boolean hasAnimatingParent;
    public boolean isNotInRecents;
    public final boolean isTranslucent;
    public final android.view.SurfaceControl leash;
    public final android.graphics.Rect localBounds;
    public final int mode;

    @java.lang.Deprecated
    public final android.graphics.Point position;

    @java.lang.Deprecated
    public final int prefixOrderIndex;
    public int rotationChange;
    public final android.graphics.Rect screenSpaceBounds;
    public boolean showBackdrop;

    @java.lang.Deprecated
    public final android.graphics.Rect sourceContainerBounds;
    public final android.graphics.Rect startBounds;
    public final android.view.SurfaceControl startLeash;
    public final int taskId;
    public android.app.ActivityManager.RunningTaskInfo taskInfo;
    public boolean willShowImeOnTarget;
    public final android.app.WindowConfiguration windowConfiguration;
    public final int windowType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public RemoteAnimationTarget(int i, int i2, android.view.SurfaceControl surfaceControl, boolean z, android.graphics.Rect rect, android.graphics.Rect rect2, int i3, android.graphics.Point point, android.graphics.Rect rect3, android.graphics.Rect rect4, android.app.WindowConfiguration windowConfiguration, boolean z2, android.view.SurfaceControl surfaceControl2, android.graphics.Rect rect5, android.app.ActivityManager.RunningTaskInfo runningTaskInfo, boolean z3) {
        this(i, i2, surfaceControl, z, rect, rect2, i3, point, rect3, rect4, windowConfiguration, z2, surfaceControl2, rect5, runningTaskInfo, z3, -1);
    }

    public RemoteAnimationTarget(int i, int i2, android.view.SurfaceControl surfaceControl, boolean z, android.graphics.Rect rect, android.graphics.Rect rect2, int i3, android.graphics.Point point, android.graphics.Rect rect3, android.graphics.Rect rect4, android.app.WindowConfiguration windowConfiguration, boolean z2, android.view.SurfaceControl surfaceControl2, android.graphics.Rect rect5, android.app.ActivityManager.RunningTaskInfo runningTaskInfo, boolean z3, int i4) {
        android.graphics.Rect rect6;
        this.mode = i2;
        this.taskId = i;
        this.leash = surfaceControl;
        this.isTranslucent = z;
        this.clipRect = new android.graphics.Rect(rect);
        this.contentInsets = new android.graphics.Rect(rect2);
        this.prefixOrderIndex = i3;
        this.position = point == null ? new android.graphics.Point() : new android.graphics.Point(point);
        this.localBounds = new android.graphics.Rect(rect3);
        this.sourceContainerBounds = new android.graphics.Rect(rect4);
        this.screenSpaceBounds = new android.graphics.Rect(rect4);
        this.windowConfiguration = windowConfiguration;
        this.isNotInRecents = z2;
        this.startLeash = surfaceControl2;
        this.taskInfo = runningTaskInfo;
        this.allowEnterPip = z3;
        this.windowType = i4;
        if (rect5 == null) {
            rect6 = new android.graphics.Rect(rect4);
        } else {
            rect6 = new android.graphics.Rect(rect5);
        }
        this.startBounds = rect6;
    }

    public RemoteAnimationTarget(android.os.Parcel parcel) {
        this.taskId = parcel.readInt();
        this.mode = parcel.readInt();
        this.leash = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
        if (this.leash != null) {
            this.leash.setUnreleasedWarningCallSite("RemoteAnimationTarget[leash]");
        }
        this.isTranslucent = parcel.readBoolean();
        this.clipRect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.contentInsets = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.prefixOrderIndex = parcel.readInt();
        this.position = (android.graphics.Point) parcel.readTypedObject(android.graphics.Point.CREATOR);
        this.localBounds = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.sourceContainerBounds = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.screenSpaceBounds = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.windowConfiguration = (android.app.WindowConfiguration) parcel.readTypedObject(android.app.WindowConfiguration.CREATOR);
        this.isNotInRecents = parcel.readBoolean();
        this.startLeash = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
        if (this.startLeash != null) {
            this.startLeash.setUnreleasedWarningCallSite("RemoteAnimationTarget[startLeash]");
        }
        this.startBounds = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.taskInfo = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
        this.allowEnterPip = parcel.readBoolean();
        this.windowType = parcel.readInt();
        this.hasAnimatingParent = parcel.readBoolean();
        this.backgroundColor = parcel.readInt();
        this.showBackdrop = parcel.readBoolean();
        this.willShowImeOnTarget = parcel.readBoolean();
        this.rotationChange = parcel.readInt();
    }

    public void setShowBackdrop(boolean z) {
        this.showBackdrop = z;
    }

    public void setWillShowImeOnTarget(boolean z) {
        this.willShowImeOnTarget = z;
    }

    public boolean willShowImeOnTarget() {
        return this.willShowImeOnTarget;
    }

    public void setRotationChange(int i) {
        this.rotationChange = i;
    }

    public int getRotationChange() {
        return this.rotationChange;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.taskId);
        parcel.writeInt(this.mode);
        parcel.writeTypedObject(this.leash, 0);
        parcel.writeBoolean(this.isTranslucent);
        parcel.writeTypedObject(this.clipRect, 0);
        parcel.writeTypedObject(this.contentInsets, 0);
        parcel.writeInt(this.prefixOrderIndex);
        parcel.writeTypedObject(this.position, 0);
        parcel.writeTypedObject(this.localBounds, 0);
        parcel.writeTypedObject(this.sourceContainerBounds, 0);
        parcel.writeTypedObject(this.screenSpaceBounds, 0);
        parcel.writeTypedObject(this.windowConfiguration, 0);
        parcel.writeBoolean(this.isNotInRecents);
        parcel.writeTypedObject(this.startLeash, 0);
        parcel.writeTypedObject(this.startBounds, 0);
        parcel.writeTypedObject(this.taskInfo, 0);
        parcel.writeBoolean(this.allowEnterPip);
        parcel.writeInt(this.windowType);
        parcel.writeBoolean(this.hasAnimatingParent);
        parcel.writeInt(this.backgroundColor);
        parcel.writeBoolean(this.showBackdrop);
        parcel.writeBoolean(this.willShowImeOnTarget);
        parcel.writeInt(this.rotationChange);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("mode=");
        printWriter.print(this.mode);
        printWriter.print(" taskId=");
        printWriter.print(this.taskId);
        printWriter.print(" isTranslucent=");
        printWriter.print(this.isTranslucent);
        printWriter.print(" clipRect=");
        this.clipRect.printShortString(printWriter);
        printWriter.print(" contentInsets=");
        this.contentInsets.printShortString(printWriter);
        printWriter.print(" prefixOrderIndex=");
        printWriter.print(this.prefixOrderIndex);
        printWriter.print(" position=");
        printPoint(this.position, printWriter);
        printWriter.print(" sourceContainerBounds=");
        this.sourceContainerBounds.printShortString(printWriter);
        printWriter.print(" screenSpaceBounds=");
        this.screenSpaceBounds.printShortString(printWriter);
        printWriter.print(" localBounds=");
        this.localBounds.printShortString(printWriter);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("windowConfiguration=");
        printWriter.println(this.windowConfiguration);
        printWriter.print(str);
        printWriter.print("leash=");
        printWriter.println(this.leash);
        printWriter.print(str);
        printWriter.print("taskInfo=");
        printWriter.println(this.taskInfo);
        printWriter.print(str);
        printWriter.print("allowEnterPip=");
        printWriter.println(this.allowEnterPip);
        printWriter.print(str);
        printWriter.print("windowType=");
        printWriter.println(this.windowType);
        printWriter.print(str);
        printWriter.print("hasAnimatingParent=");
        printWriter.println(this.hasAnimatingParent);
        printWriter.print(str);
        printWriter.print("backgroundColor=");
        printWriter.println(this.backgroundColor);
        printWriter.print(str);
        printWriter.print("showBackdrop=");
        printWriter.println(this.showBackdrop);
        printWriter.print(str);
        printWriter.print("willShowImeOnTarget=");
        printWriter.println(this.willShowImeOnTarget);
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.taskId);
        protoOutputStream.write(1120986464258L, this.mode);
        this.leash.dumpDebug(protoOutputStream, 1146756268035L);
        protoOutputStream.write(1133871366148L, this.isTranslucent);
        this.clipRect.dumpDebug(protoOutputStream, 1146756268037L);
        this.contentInsets.dumpDebug(protoOutputStream, 1146756268038L);
        protoOutputStream.write(1120986464263L, this.prefixOrderIndex);
        android.graphics.GraphicsProtos.dumpPointProto(this.position, protoOutputStream, 1146756268040L);
        this.sourceContainerBounds.dumpDebug(protoOutputStream, 1146756268041L);
        this.screenSpaceBounds.dumpDebug(protoOutputStream, 1146756268046L);
        this.localBounds.dumpDebug(protoOutputStream, 1146756268045L);
        this.windowConfiguration.dumpDebug(protoOutputStream, 1146756268042L);
        if (this.startLeash != null) {
            this.startLeash.dumpDebug(protoOutputStream, 1146756268043L);
        }
        this.startBounds.dumpDebug(protoOutputStream, 1146756268044L);
        protoOutputStream.end(start);
    }

    private static void printPoint(android.graphics.Point point, java.io.PrintWriter printWriter) {
        printWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        printWriter.print(point.x);
        printWriter.print(",");
        printWriter.print(point.y);
        printWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
    }
}
