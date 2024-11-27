package android.app;

/* loaded from: classes.dex */
public class Presentation extends android.app.Dialog {
    private static final java.lang.String TAG = "Presentation";
    private final android.view.Display mDisplay;
    private final android.hardware.display.DisplayManager.DisplayListener mDisplayListener;
    private final android.hardware.display.DisplayManager mDisplayManager;
    private final android.os.Handler mHandler;

    public Presentation(android.content.Context context, android.view.Display display) {
        this(context, display, 0);
    }

    public Presentation(android.content.Context context, android.view.Display display, int i) {
        this(context, display, i, -1);
    }

    public Presentation(android.content.Context context, android.view.Display display, int i, int i2) {
        super(createPresentationContext(context, display, i, i2), i, false);
        this.mHandler = new android.os.Handler((android.os.Looper) java.util.Objects.requireNonNull(android.os.Looper.myLooper(), "Presentation must be constructed on a looper thread."));
        this.mDisplayListener = new android.hardware.display.DisplayManager.DisplayListener() { // from class: android.app.Presentation.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i3) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i3) {
                if (i3 == android.app.Presentation.this.mDisplay.getDisplayId()) {
                    android.app.Presentation.this.handleDisplayRemoved();
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i3) {
                if (i3 == android.app.Presentation.this.mDisplay.getDisplayId()) {
                    android.app.Presentation.this.handleDisplayChanged();
                }
            }
        };
        this.mDisplay = display;
        this.mDisplayManager = (android.hardware.display.DisplayManager) getContext().getSystemService(android.hardware.display.DisplayManager.class);
        android.view.Window window = getWindow();
        window.setAttributes(window.getAttributes());
        window.setGravity(119);
        window.setType(getWindowType(i2, display));
        setCanceledOnTouchOutside(false);
    }

    private static int getWindowType(int i, android.view.Display display) {
        if (i != -1) {
            return i;
        }
        return (display.getFlags() & 4) != 0 ? 2030 : 2037;
    }

    public android.view.Display getDisplay() {
        return this.mDisplay;
    }

    public android.content.res.Resources getResources() {
        return getContext().getResources();
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
        this.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mHandler);
    }

    @Override // android.app.Dialog
    protected void onStop() {
        this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
        super.onStop();
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
    }

    public void onDisplayRemoved() {
    }

    public void onDisplayChanged() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayRemoved() {
        onDisplayRemoved();
        cancel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayChanged() {
        onDisplayChanged();
    }

    private static android.content.Context createPresentationContext(android.content.Context context, android.view.Display display, int i) {
        return createPresentationContext(context, display, i, -1);
    }

    private static android.content.Context createPresentationContext(android.content.Context context, android.view.Display display, int i, int i2) {
        if (context == null) {
            throw new java.lang.IllegalArgumentException("outerContext must not be null");
        }
        if (display == null) {
            throw new java.lang.IllegalArgumentException("display must not be null");
        }
        android.content.Context createWindowContext = context.createDisplayContext(display).createWindowContext(getWindowType(i2, display), null);
        if (i == 0) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            createWindowContext.getTheme().resolveAttribute(16843712, typedValue, true);
            i = typedValue.resourceId;
        }
        return new android.view.ContextThemeWrapper(createWindowContext, i);
    }
}
