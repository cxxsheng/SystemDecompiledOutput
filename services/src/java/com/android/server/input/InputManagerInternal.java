package com.android.server.input;

/* loaded from: classes2.dex */
public abstract class InputManagerInternal {

    public interface LidSwitchCallback {
        void notifyLidSwitchChanged(long j, boolean z);
    }

    public abstract void addKeyboardLayoutAssociation(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3);

    public abstract android.view.InputChannel createInputChannel(java.lang.String str);

    public abstract void decrementKeyboardBacklight(int i);

    public abstract android.graphics.PointF getCursorPosition(int i);

    public abstract int getVirtualMousePointerDisplayId();

    public abstract void incrementKeyboardBacklight(int i);

    public abstract void notifyUserActivity();

    public abstract void onInputMethodSubtypeChangedForKeyboardLayoutMapping(int i, @android.annotation.Nullable com.android.internal.inputmethod.InputMethodSubtypeHandle inputMethodSubtypeHandle, @android.annotation.Nullable android.view.inputmethod.InputMethodSubtype inputMethodSubtype);

    public abstract void pilferPointers(android.os.IBinder iBinder);

    public abstract void registerLidSwitchCallback(@android.annotation.NonNull com.android.server.input.InputManagerInternal.LidSwitchCallback lidSwitchCallback);

    public abstract void removeKeyboardLayoutAssociation(@android.annotation.NonNull java.lang.String str);

    public abstract void setDisplayEligibilityForPointerCapture(int i, boolean z);

    public abstract void setDisplayViewports(java.util.List<android.hardware.display.DisplayViewport> list);

    public abstract void setInteractive(boolean z);

    public abstract void setMousePointerAccelerationEnabled(boolean z, int i);

    public abstract void setPointerIconVisible(boolean z, int i);

    public abstract void setPulseGestureEnabled(boolean z);

    public abstract void setStylusButtonMotionEventsEnabled(boolean z);

    public abstract void setTypeAssociation(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2);

    public abstract boolean setVirtualMousePointerDisplayId(int i);

    public abstract void toggleCapsLock(int i);

    public abstract boolean transferTouchGesture(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2);

    public abstract void unregisterLidSwitchCallback(@android.annotation.NonNull com.android.server.input.InputManagerInternal.LidSwitchCallback lidSwitchCallback);

    public abstract void unsetTypeAssociation(@android.annotation.NonNull java.lang.String str);
}
