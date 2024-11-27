package com.android.server.display.color;

/* loaded from: classes.dex */
public class DisplayTransformManager {
    private static final float COLOR_SATURATION_BOOSTED = 1.1f;
    private static final float COLOR_SATURATION_NATURAL = 1.0f;
    private static final int DISPLAY_COLOR_ENHANCED = 2;
    private static final int DISPLAY_COLOR_MANAGED = 0;
    private static final int DISPLAY_COLOR_UNMANAGED = 1;
    public static final int LEVEL_COLOR_MATRIX_DISPLAY_WHITE_BALANCE = 125;
    public static final int LEVEL_COLOR_MATRIX_GRAYSCALE = 200;
    public static final int LEVEL_COLOR_MATRIX_INVERT_COLOR = 300;
    public static final int LEVEL_COLOR_MATRIX_NIGHT_DISPLAY = 100;
    public static final int LEVEL_COLOR_MATRIX_REDUCE_BRIGHT_COLORS = 250;
    public static final int LEVEL_COLOR_MATRIX_SATURATION = 150;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String PERSISTENT_PROPERTY_COMPOSITION_COLOR_MODE = "persist.sys.sf.color_mode";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String PERSISTENT_PROPERTY_DISPLAY_COLOR = "persist.sys.sf.native_mode";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String PERSISTENT_PROPERTY_SATURATION = "persist.sys.sf.color_saturation";
    private static final int SURFACE_FLINGER_TRANSACTION_COLOR_MATRIX = 1015;
    private static final int SURFACE_FLINGER_TRANSACTION_DALTONIZER = 1014;
    private static final int SURFACE_FLINGER_TRANSACTION_DISPLAY_COLOR = 1023;
    private static final int SURFACE_FLINGER_TRANSACTION_QUERY_COLOR_MANAGED = 1030;
    private static final int SURFACE_FLINGER_TRANSACTION_SATURATION = 1022;
    private static final java.lang.String TAG = "DisplayTransformManager";
    private static final java.lang.String SURFACE_FLINGER = "SurfaceFlinger";
    private static final android.os.IBinder sFlinger = android.os.ServiceManager.getService(SURFACE_FLINGER);

    @com.android.internal.annotations.GuardedBy({"mColorMatrix"})
    private final android.util.SparseArray<float[]> mColorMatrix = new android.util.SparseArray<>(6);

    @com.android.internal.annotations.GuardedBy({"mColorMatrix"})
    private final float[][] mTempColorMatrix = (float[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Float.TYPE, 2, 16);
    private final java.lang.Object mDaltonizerModeLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mDaltonizerModeLock"})
    private int mDaltonizerMode = -1;

    DisplayTransformManager() {
    }

    public float[] getColorMatrix(int i) {
        float[] copyOf;
        synchronized (this.mColorMatrix) {
            float[] fArr = this.mColorMatrix.get(i);
            copyOf = fArr == null ? null : java.util.Arrays.copyOf(fArr, fArr.length);
        }
        return copyOf;
    }

    public void setColorMatrix(int i, float[] fArr) {
        if (fArr != null && fArr.length != 16) {
            throw new java.lang.IllegalArgumentException("Expected length: 16 (4x4 matrix), actual length: " + fArr.length);
        }
        synchronized (this.mColorMatrix) {
            try {
                float[] fArr2 = this.mColorMatrix.get(i);
                if (!java.util.Arrays.equals(fArr2, fArr)) {
                    if (fArr == null) {
                        this.mColorMatrix.remove(i);
                    } else if (fArr2 == null) {
                        this.mColorMatrix.put(i, java.util.Arrays.copyOf(fArr, fArr.length));
                    } else {
                        java.lang.System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
                    }
                    applyColorMatrix(computeColorMatrixLocked());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setDaltonizerMode(int i) {
        synchronized (this.mDaltonizerModeLock) {
            try {
                if (this.mDaltonizerMode != i) {
                    this.mDaltonizerMode = i;
                    applyDaltonizerMode(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mColorMatrix"})
    private float[] computeColorMatrixLocked() {
        int size = this.mColorMatrix.size();
        if (size == 0) {
            return null;
        }
        float[][] fArr = this.mTempColorMatrix;
        int i = 0;
        android.opengl.Matrix.setIdentityM(fArr[0], 0);
        while (i < size) {
            int i2 = i + 1;
            android.opengl.Matrix.multiplyMM(fArr[i2 % 2], 0, fArr[i % 2], 0, this.mColorMatrix.valueAt(i), 0);
            i = i2;
        }
        return fArr[size % 2];
    }

    private static void applyColorMatrix(float[] fArr) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
        if (fArr != null) {
            obtain.writeInt(1);
            for (int i = 0; i < 16; i++) {
                obtain.writeFloat(fArr[i]);
            }
        } else {
            obtain.writeInt(0);
        }
        try {
            try {
                sFlinger.transact(1015, obtain, null, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to set color transform", e);
            }
        } finally {
            obtain.recycle();
        }
    }

    private static void applyDaltonizerMode(int i) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
        obtain.writeInt(i);
        try {
            try {
                sFlinger.transact(1014, obtain, null, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to set Daltonizer mode", e);
            }
        } finally {
            obtain.recycle();
        }
    }

    public boolean needsLinearColorMatrix() {
        return android.os.SystemProperties.getInt(PERSISTENT_PROPERTY_DISPLAY_COLOR, 1) != 1;
    }

    public boolean needsLinearColorMatrix(int i) {
        return i != 2;
    }

    public boolean setColorMode(int i, float[] fArr, int i2) {
        if (i == 0) {
            applySaturation(1.0f);
            setDisplayColor(0, i2);
        } else if (i == 1) {
            applySaturation(COLOR_SATURATION_BOOSTED);
            setDisplayColor(0, i2);
        } else if (i == 2) {
            applySaturation(1.0f);
            setDisplayColor(1, i2);
        } else if (i == 3) {
            applySaturation(1.0f);
            setDisplayColor(2, i2);
        } else if (i >= 256 && i <= 511) {
            applySaturation(1.0f);
            setDisplayColor(i, i2);
        }
        setColorMatrix(100, fArr);
        updateConfiguration();
        return true;
    }

    public boolean isDeviceColorManaged() {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
        try {
            sFlinger.transact(SURFACE_FLINGER_TRANSACTION_QUERY_COLOR_MANAGED, obtain, obtain2, 0);
            return obtain2.readBoolean();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to query wide color support", e);
            return false;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    private void applySaturation(float f) {
        android.os.SystemProperties.set(PERSISTENT_PROPERTY_SATURATION, java.lang.Float.toString(f));
        android.os.Parcel obtain = android.os.Parcel.obtain();
        obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
        obtain.writeFloat(f);
        try {
            try {
                sFlinger.transact(SURFACE_FLINGER_TRANSACTION_SATURATION, obtain, null, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to set saturation", e);
            }
        } finally {
            obtain.recycle();
        }
    }

    private void setDisplayColor(int i, int i2) {
        android.os.SystemProperties.set(PERSISTENT_PROPERTY_DISPLAY_COLOR, java.lang.Integer.toString(i));
        if (i2 != -1) {
            android.os.SystemProperties.set(PERSISTENT_PROPERTY_COMPOSITION_COLOR_MODE, java.lang.Integer.toString(i2));
        }
        android.os.Parcel obtain = android.os.Parcel.obtain();
        obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
        obtain.writeInt(i);
        if (i2 != -1) {
            obtain.writeInt(i2);
        }
        try {
            try {
                sFlinger.transact(SURFACE_FLINGER_TRANSACTION_DISPLAY_COLOR, obtain, null, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to set display color", e);
            }
        } finally {
            obtain.recycle();
        }
    }

    private void updateConfiguration() {
        try {
            android.app.ActivityTaskManager.getService().updateConfiguration((android.content.res.Configuration) null);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Could not update configuration", e);
        }
    }
}
