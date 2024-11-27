package com.android.internal.util;

/* loaded from: classes5.dex */
public class ScreenshotRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.util.ScreenshotRequest> CREATOR = new android.os.Parcelable.Creator<com.android.internal.util.ScreenshotRequest>() { // from class: com.android.internal.util.ScreenshotRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.util.ScreenshotRequest createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.util.ScreenshotRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.util.ScreenshotRequest[] newArray(int i) {
            return new com.android.internal.util.ScreenshotRequest[i];
        }
    };
    private static final java.lang.String TAG = "ScreenshotRequest";
    private final android.graphics.Bitmap mBitmap;
    private final android.graphics.Rect mBoundsInScreen;
    private final android.graphics.Insets mInsets;
    private final int mSource;
    private final int mTaskId;
    private final android.content.ComponentName mTopComponent;
    private final int mType;
    private final int mUserId;

    private ScreenshotRequest(int i, int i2, android.content.ComponentName componentName, int i3, int i4, android.graphics.Bitmap bitmap, android.graphics.Rect rect, android.graphics.Insets insets) {
        this.mType = i;
        this.mSource = i2;
        this.mTopComponent = componentName;
        this.mTaskId = i3;
        this.mUserId = i4;
        this.mBitmap = bitmap;
        this.mBoundsInScreen = rect;
        this.mInsets = insets;
    }

    ScreenshotRequest(android.os.Parcel parcel) {
        this.mType = parcel.readInt();
        this.mSource = parcel.readInt();
        this.mTopComponent = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
        this.mTaskId = parcel.readInt();
        this.mUserId = parcel.readInt();
        this.mBitmap = com.android.internal.util.ScreenshotRequest.HardwareBitmapBundler.bundleToHardwareBitmap((android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR));
        this.mBoundsInScreen = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.mInsets = (android.graphics.Insets) parcel.readTypedObject(android.graphics.Insets.CREATOR);
    }

    public int getType() {
        return this.mType;
    }

    public int getSource() {
        return this.mSource;
    }

    public android.graphics.Bitmap getBitmap() {
        return this.mBitmap;
    }

    public android.graphics.Rect getBoundsInScreen() {
        return this.mBoundsInScreen;
    }

    public android.graphics.Insets getInsets() {
        return this.mInsets;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public android.content.ComponentName getTopComponent() {
        return this.mTopComponent;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mSource);
        parcel.writeTypedObject(this.mTopComponent, 0);
        parcel.writeInt(this.mTaskId);
        parcel.writeInt(this.mUserId);
        parcel.writeTypedObject(com.android.internal.util.ScreenshotRequest.HardwareBitmapBundler.hardwareBitmapToBundle(this.mBitmap), 0);
        parcel.writeTypedObject(this.mBoundsInScreen, 0);
        parcel.writeTypedObject(this.mInsets, 0);
    }

    public static class Builder {
        private android.graphics.Bitmap mBitmap;
        private android.graphics.Rect mBoundsInScreen;
        private final int mSource;
        private android.content.ComponentName mTopComponent;
        private final int mType;
        private android.graphics.Insets mInsets = android.graphics.Insets.NONE;
        private int mTaskId = -1;
        private int mUserId = -10000;

        public Builder(int i, int i2) {
            if (i != 1 && i != 3 && i != 2) {
                throw new java.lang.IllegalArgumentException("Invalid screenshot type requested!");
            }
            this.mType = i;
            this.mSource = i2;
        }

        public com.android.internal.util.ScreenshotRequest build() {
            if (this.mType == 1 && this.mBitmap != null) {
                android.util.Log.w(com.android.internal.util.ScreenshotRequest.TAG, "Bitmap provided, but request is fullscreen. Bitmap will be ignored.");
            }
            if (this.mType == 2 && this.mBitmap != null) {
                android.util.Log.w(com.android.internal.util.ScreenshotRequest.TAG, "Bitmap provided, but request is partial. Bitmap will be ignored.");
            }
            if (this.mType == 3 && this.mBitmap == null) {
                throw new java.lang.IllegalStateException("Request is PROVIDED_IMAGE, but no bitmap is provided!");
            }
            return new com.android.internal.util.ScreenshotRequest(this.mType, this.mSource, this.mTopComponent, this.mTaskId, this.mUserId, this.mBitmap, this.mBoundsInScreen, this.mInsets);
        }

        public com.android.internal.util.ScreenshotRequest.Builder setTopComponent(android.content.ComponentName componentName) {
            this.mTopComponent = componentName;
            return this;
        }

        public com.android.internal.util.ScreenshotRequest.Builder setTaskId(int i) {
            this.mTaskId = i;
            return this;
        }

        public com.android.internal.util.ScreenshotRequest.Builder setUserId(int i) {
            this.mUserId = i;
            return this;
        }

        public com.android.internal.util.ScreenshotRequest.Builder setBitmap(android.graphics.Bitmap bitmap) {
            this.mBitmap = bitmap;
            return this;
        }

        public com.android.internal.util.ScreenshotRequest.Builder setBoundsOnScreen(android.graphics.Rect rect) {
            this.mBoundsInScreen = rect;
            return this;
        }

        public com.android.internal.util.ScreenshotRequest.Builder setInsets(android.graphics.Insets insets) {
            this.mInsets = insets;
            return this;
        }
    }

    private static final class HardwareBitmapBundler {
        private static final java.lang.String KEY_BUFFER = "bitmap_util_buffer";
        private static final java.lang.String KEY_COLOR_SPACE = "bitmap_util_color_space";

        private HardwareBitmapBundler() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.os.Bundle hardwareBitmapToBundle(android.graphics.Bitmap bitmap) {
            android.graphics.ParcelableColorSpace parcelableColorSpace;
            if (bitmap == null) {
                return null;
            }
            if (bitmap.getConfig() != android.graphics.Bitmap.Config.HARDWARE) {
                throw new java.lang.IllegalArgumentException("Passed bitmap must have hardware config, found: " + bitmap.getConfig());
            }
            if (bitmap.getColorSpace() == null) {
                parcelableColorSpace = new android.graphics.ParcelableColorSpace(android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB));
            } else {
                parcelableColorSpace = new android.graphics.ParcelableColorSpace(bitmap.getColorSpace());
            }
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable(KEY_BUFFER, bitmap.getHardwareBuffer());
            bundle.putParcelable(KEY_COLOR_SPACE, parcelableColorSpace);
            return bundle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.graphics.Bitmap bundleToHardwareBitmap(android.os.Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            if (bundle.containsKey(KEY_BUFFER) && bundle.containsKey(KEY_COLOR_SPACE)) {
                return android.graphics.Bitmap.wrapHardwareBuffer((android.hardware.HardwareBuffer) java.util.Objects.requireNonNull((android.hardware.HardwareBuffer) bundle.getParcelable(KEY_BUFFER, android.hardware.HardwareBuffer.class)), ((android.graphics.ParcelableColorSpace) bundle.getParcelable(KEY_COLOR_SPACE, android.graphics.ParcelableColorSpace.class)).getColorSpace());
            }
            throw new java.lang.IllegalArgumentException("Bundle does not contain a hardware bitmap");
        }
    }
}
