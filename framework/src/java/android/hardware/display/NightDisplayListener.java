package android.hardware.display;

/* loaded from: classes2.dex */
public class NightDisplayListener {
    private android.hardware.display.NightDisplayListener.Callback mCallback;
    private final android.database.ContentObserver mContentObserver;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final android.hardware.display.ColorDisplayManager mManager;
    private final int mUserId;

    public NightDisplayListener(android.content.Context context) {
        this(context, android.app.ActivityManager.getCurrentUser(), new android.os.Handler(android.os.Looper.getMainLooper()));
    }

    public NightDisplayListener(android.content.Context context, android.os.Handler handler) {
        this(context, android.app.ActivityManager.getCurrentUser(), handler);
    }

    public NightDisplayListener(android.content.Context context, int i, android.os.Handler handler) {
        this.mContext = context.getApplicationContext();
        this.mManager = (android.hardware.display.ColorDisplayManager) this.mContext.getSystemService(android.hardware.display.ColorDisplayManager.class);
        this.mUserId = i;
        this.mHandler = handler;
        this.mContentObserver = new android.database.ContentObserver(this.mHandler) { // from class: android.hardware.display.NightDisplayListener.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.database.ContentObserver
            public void onChange(boolean z, android.net.Uri uri) {
                char c;
                super.onChange(z, uri);
                java.lang.String lastPathSegment = uri == null ? null : uri.getLastPathSegment();
                if (lastPathSegment != null && android.hardware.display.NightDisplayListener.this.mCallback != null) {
                    switch (lastPathSegment.hashCode()) {
                        case -2038150513:
                            if (lastPathSegment.equals(android.provider.Settings.Secure.NIGHT_DISPLAY_AUTO_MODE)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1761668069:
                            if (lastPathSegment.equals(android.provider.Settings.Secure.NIGHT_DISPLAY_CUSTOM_END_TIME)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case -969458956:
                            if (lastPathSegment.equals(android.provider.Settings.Secure.NIGHT_DISPLAY_COLOR_TEMPERATURE)) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 800115245:
                            if (lastPathSegment.equals(android.provider.Settings.Secure.NIGHT_DISPLAY_ACTIVATED)) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1578271348:
                            if (lastPathSegment.equals(android.provider.Settings.Secure.NIGHT_DISPLAY_CUSTOM_START_TIME)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            android.hardware.display.NightDisplayListener.this.mCallback.onActivated(android.hardware.display.NightDisplayListener.this.mManager.isNightDisplayActivated());
                            break;
                        case 1:
                            android.hardware.display.NightDisplayListener.this.mCallback.onAutoModeChanged(android.hardware.display.NightDisplayListener.this.mManager.getNightDisplayAutoMode());
                            break;
                        case 2:
                            android.hardware.display.NightDisplayListener.this.mCallback.onCustomStartTimeChanged(android.hardware.display.NightDisplayListener.this.mManager.getNightDisplayCustomStartTime());
                            break;
                        case 3:
                            android.hardware.display.NightDisplayListener.this.mCallback.onCustomEndTimeChanged(android.hardware.display.NightDisplayListener.this.mManager.getNightDisplayCustomEndTime());
                            break;
                        case 4:
                            android.hardware.display.NightDisplayListener.this.mCallback.onColorTemperatureChanged(android.hardware.display.NightDisplayListener.this.mManager.getNightDisplayColorTemperature());
                            break;
                    }
                }
            }
        };
    }

    public void setCallback(final android.hardware.display.NightDisplayListener.Callback callback) {
        if (android.os.Looper.myLooper() != this.mHandler.getLooper()) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.display.NightDisplayListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.display.NightDisplayListener.this.lambda$setCallback$0(callback);
                }
            });
        }
        lambda$setCallback$0(callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setCallbackInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$setCallback$0(android.hardware.display.NightDisplayListener.Callback callback) {
        android.hardware.display.NightDisplayListener.Callback callback2 = this.mCallback;
        if (callback2 != callback) {
            this.mCallback = callback;
            if (this.mCallback == null) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
                return;
            }
            if (callback2 == null) {
                android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
                contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.NIGHT_DISPLAY_ACTIVATED), false, this.mContentObserver, this.mUserId);
                contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.NIGHT_DISPLAY_AUTO_MODE), false, this.mContentObserver, this.mUserId);
                contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.NIGHT_DISPLAY_CUSTOM_START_TIME), false, this.mContentObserver, this.mUserId);
                contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.NIGHT_DISPLAY_CUSTOM_END_TIME), false, this.mContentObserver, this.mUserId);
                contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.NIGHT_DISPLAY_COLOR_TEMPERATURE), false, this.mContentObserver, this.mUserId);
            }
        }
    }

    public interface Callback {
        default void onActivated(boolean z) {
        }

        default void onAutoModeChanged(int i) {
        }

        default void onCustomStartTimeChanged(java.time.LocalTime localTime) {
        }

        default void onCustomEndTimeChanged(java.time.LocalTime localTime) {
        }

        default void onColorTemperatureChanged(int i) {
        }
    }
}
