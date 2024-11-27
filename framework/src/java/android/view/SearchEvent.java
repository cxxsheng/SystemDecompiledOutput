package android.view;

/* loaded from: classes4.dex */
public class SearchEvent {
    private android.view.InputDevice mInputDevice;

    public SearchEvent(android.view.InputDevice inputDevice) {
        this.mInputDevice = inputDevice;
    }

    public android.view.InputDevice getInputDevice() {
        return this.mInputDevice;
    }
}
