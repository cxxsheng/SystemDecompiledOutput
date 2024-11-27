package android.window;

/* loaded from: classes4.dex */
public final class StartingWindowInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.StartingWindowInfo> CREATOR = new android.os.Parcelable.Creator<android.window.StartingWindowInfo>() { // from class: android.window.StartingWindowInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.StartingWindowInfo createFromParcel(android.os.Parcel parcel) {
            return new android.window.StartingWindowInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.StartingWindowInfo[] newArray(int i) {
            return new android.window.StartingWindowInfo[i];
        }
    };
    public static final int STARTING_WINDOW_TYPE_LEGACY_SPLASH_SCREEN = 4;
    public static final int STARTING_WINDOW_TYPE_NONE = 0;
    public static final int STARTING_WINDOW_TYPE_SNAPSHOT = 2;
    public static final int STARTING_WINDOW_TYPE_SOLID_COLOR_SPLASH_SCREEN = 3;
    public static final int STARTING_WINDOW_TYPE_SPLASH_SCREEN = 1;
    public static final int STARTING_WINDOW_TYPE_WINDOWLESS = 5;
    public static final int TYPE_PARAMETER_ACTIVITY_CREATED = 16;
    public static final int TYPE_PARAMETER_ACTIVITY_DRAWN = 64;
    public static final int TYPE_PARAMETER_ALLOW_HANDLE_SOLID_COLOR_SCREEN = 128;
    public static final int TYPE_PARAMETER_ALLOW_TASK_SNAPSHOT = 8;
    public static final int TYPE_PARAMETER_APP_PREFERS_ICON = 512;
    public static final int TYPE_PARAMETER_LEGACY_SPLASH_SCREEN = Integer.MIN_VALUE;
    public static final int TYPE_PARAMETER_NEW_TASK = 1;
    public static final int TYPE_PARAMETER_PROCESS_RUNNING = 4;
    public static final int TYPE_PARAMETER_TASK_SWITCH = 2;
    public static final int TYPE_PARAMETER_USE_SOLID_COLOR_SPLASH_SCREEN = 32;
    public static final int TYPE_PARAMETER_WINDOWLESS = 256;
    public android.os.IBinder appToken;
    public boolean isKeyguardOccluded;
    public android.view.WindowManager.LayoutParams mainWindowLayoutParams;
    public int requestedVisibleTypes;
    public android.view.SurfaceControl rootSurface;
    public int splashScreenThemeResId;
    public int startingWindowTypeParameter;
    public android.content.pm.ActivityInfo targetActivityInfo;
    public android.app.ActivityManager.RunningTaskInfo taskInfo;
    public android.window.TaskSnapshot taskSnapshot;
    public android.view.InsetsState topOpaqueWindowInsetsState;
    public android.view.WindowManager.LayoutParams topOpaqueWindowLayoutParams;
    public android.window.IWindowlessStartingSurfaceCallback windowlessStartingSurfaceCallback;

    public @interface StartingTypeParams {
    }

    public @interface StartingWindowType {
    }

    public void notifyAddComplete(android.view.SurfaceControl surfaceControl) {
        if (this.windowlessStartingSurfaceCallback != null) {
            try {
                this.windowlessStartingSurfaceCallback.onSurfaceAdded(surfaceControl);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public StartingWindowInfo() {
        this.isKeyguardOccluded = false;
        this.requestedVisibleTypes = android.view.WindowInsets.Type.defaultVisible();
    }

    private StartingWindowInfo(android.os.Parcel parcel) {
        this.isKeyguardOccluded = false;
        this.requestedVisibleTypes = android.view.WindowInsets.Type.defaultVisible();
        readFromParcel(parcel);
    }

    public boolean allowHandleSolidColorSplashScreen() {
        return (this.startingWindowTypeParameter & 128) != 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.taskInfo, i);
        parcel.writeTypedObject(this.targetActivityInfo, i);
        parcel.writeInt(this.startingWindowTypeParameter);
        parcel.writeTypedObject(this.topOpaqueWindowInsetsState, i);
        parcel.writeTypedObject(this.topOpaqueWindowLayoutParams, i);
        parcel.writeTypedObject(this.mainWindowLayoutParams, i);
        parcel.writeInt(this.splashScreenThemeResId);
        parcel.writeBoolean(this.isKeyguardOccluded);
        parcel.writeTypedObject(this.taskSnapshot, i);
        parcel.writeInt(this.requestedVisibleTypes);
        parcel.writeStrongBinder(this.appToken);
        parcel.writeStrongInterface(this.windowlessStartingSurfaceCallback);
        parcel.writeTypedObject(this.rootSurface, i);
    }

    void readFromParcel(android.os.Parcel parcel) {
        this.taskInfo = (android.app.ActivityManager.RunningTaskInfo) parcel.readTypedObject(android.app.ActivityManager.RunningTaskInfo.CREATOR);
        this.targetActivityInfo = (android.content.pm.ActivityInfo) parcel.readTypedObject(android.content.pm.ActivityInfo.CREATOR);
        this.startingWindowTypeParameter = parcel.readInt();
        this.topOpaqueWindowInsetsState = (android.view.InsetsState) parcel.readTypedObject(android.view.InsetsState.CREATOR);
        this.topOpaqueWindowLayoutParams = (android.view.WindowManager.LayoutParams) parcel.readTypedObject(android.view.WindowManager.LayoutParams.CREATOR);
        this.mainWindowLayoutParams = (android.view.WindowManager.LayoutParams) parcel.readTypedObject(android.view.WindowManager.LayoutParams.CREATOR);
        this.splashScreenThemeResId = parcel.readInt();
        this.isKeyguardOccluded = parcel.readBoolean();
        this.taskSnapshot = (android.window.TaskSnapshot) parcel.readTypedObject(android.window.TaskSnapshot.CREATOR);
        this.requestedVisibleTypes = parcel.readInt();
        this.appToken = parcel.readStrongBinder();
        this.windowlessStartingSurfaceCallback = android.window.IWindowlessStartingSurfaceCallback.Stub.asInterface(parcel.readStrongBinder());
        this.rootSurface = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
    }

    public java.lang.String toString() {
        return "StartingWindowInfo{taskId=" + this.taskInfo.taskId + " targetActivityInfo=" + this.targetActivityInfo + " displayId=" + this.taskInfo.displayId + " topActivityType=" + this.taskInfo.topActivityType + " preferredStartingWindowType=" + java.lang.Integer.toHexString(this.startingWindowTypeParameter) + " insetsState=" + this.topOpaqueWindowInsetsState + " topWindowLayoutParams=" + this.topOpaqueWindowLayoutParams + " mainWindowLayoutParams=" + this.mainWindowLayoutParams + " splashScreenThemeResId " + java.lang.Integer.toHexString(this.splashScreenThemeResId);
    }
}
