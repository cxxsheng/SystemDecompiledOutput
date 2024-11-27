package android.graphics.drawable;

/* loaded from: classes.dex */
public final class Icon implements android.os.Parcelable {
    private static final boolean DEBUG = false;
    public static final int MIN_ASHMEM_ICON_SIZE = 131072;
    private static final java.lang.String TAG = "Icon";
    public static final int TYPE_ADAPTIVE_BITMAP = 5;
    public static final int TYPE_BITMAP = 1;
    public static final int TYPE_DATA = 3;
    public static final int TYPE_RESOURCE = 2;
    public static final int TYPE_URI = 4;
    public static final int TYPE_URI_ADAPTIVE_BITMAP = 6;
    private static final int VERSION_STREAM_SERIALIZER = 1;
    private android.graphics.BlendMode mBlendMode;
    private boolean mCachedAshmem;
    private float mInsetScale;
    private int mInt1;
    private int mInt2;
    private java.lang.Object mObj1;
    private java.lang.String mString1;
    private android.content.res.ColorStateList mTintList;
    private final int mType;
    private boolean mUseMonochrome;
    static final android.graphics.BlendMode DEFAULT_BLEND_MODE = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
    public static final android.os.Parcelable.Creator<android.graphics.drawable.Icon> CREATOR = new android.os.Parcelable.Creator<android.graphics.drawable.Icon>() { // from class: android.graphics.drawable.Icon.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.drawable.Icon createFromParcel(android.os.Parcel parcel) {
            return new android.graphics.drawable.Icon(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.drawable.Icon[] newArray(int i) {
            return new android.graphics.drawable.Icon[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface IconType {
    }

    public interface OnDrawableLoadedListener {
        void onDrawableLoaded(android.graphics.drawable.Drawable drawable);
    }

    public int getType() {
        return this.mType;
    }

    public android.graphics.Bitmap getBitmap() {
        if (this.mType != 1 && this.mType != 5) {
            throw new java.lang.IllegalStateException("called getBitmap() on " + this);
        }
        return (android.graphics.Bitmap) this.mObj1;
    }

    private void setBitmap(android.graphics.Bitmap bitmap) {
        if (bitmap.isMutable()) {
            this.mObj1 = bitmap.copy(bitmap.getConfig(), false);
        } else {
            this.mObj1 = bitmap;
        }
        this.mCachedAshmem = false;
    }

    public int getDataLength() {
        int i;
        if (this.mType != 3) {
            throw new java.lang.IllegalStateException("called getDataLength() on " + this);
        }
        synchronized (this) {
            i = this.mInt1;
        }
        return i;
    }

    public int getDataOffset() {
        int i;
        if (this.mType != 3) {
            throw new java.lang.IllegalStateException("called getDataOffset() on " + this);
        }
        synchronized (this) {
            i = this.mInt2;
        }
        return i;
    }

    public byte[] getDataBytes() {
        byte[] bArr;
        if (this.mType != 3) {
            throw new java.lang.IllegalStateException("called getDataBytes() on " + this);
        }
        synchronized (this) {
            bArr = (byte[]) this.mObj1;
        }
        return bArr;
    }

    public android.content.res.Resources getResources() {
        if (this.mType != 2) {
            throw new java.lang.IllegalStateException("called getResources() on " + this);
        }
        return (android.content.res.Resources) this.mObj1;
    }

    public java.lang.String getResPackage() {
        if (this.mType != 2) {
            throw new java.lang.IllegalStateException("called getResPackage() on " + this);
        }
        return this.mString1;
    }

    public int getResId() {
        if (this.mType != 2) {
            throw new java.lang.IllegalStateException("called getResId() on " + this);
        }
        return this.mInt1;
    }

    public java.lang.String getUriString() {
        if (this.mType != 4 && this.mType != 6) {
            throw new java.lang.IllegalStateException("called getUriString() on " + this);
        }
        return this.mString1;
    }

    public android.net.Uri getUri() {
        return android.net.Uri.parse(getUriString());
    }

    private static final java.lang.String typeToString(int i) {
        switch (i) {
            case 1:
                return "BITMAP";
            case 2:
                return "RESOURCE";
            case 3:
                return "DATA";
            case 4:
                return "URI";
            case 5:
                return "BITMAP_MASKABLE";
            case 6:
                return "URI_MASKABLE";
            default:
                return "UNKNOWN";
        }
    }

    public void loadDrawableAsync(android.content.Context context, android.os.Message message) {
        if (message.getTarget() == null) {
            throw new java.lang.IllegalArgumentException("callback message must have a target handler");
        }
        new android.graphics.drawable.Icon.LoadDrawableTask(context, message).runAsync();
    }

    public void loadDrawableAsync(android.content.Context context, android.graphics.drawable.Icon.OnDrawableLoadedListener onDrawableLoadedListener, android.os.Handler handler) {
        new android.graphics.drawable.Icon.LoadDrawableTask(context, handler, onDrawableLoadedListener).runAsync();
    }

    public android.graphics.drawable.Drawable loadDrawable(android.content.Context context) {
        android.graphics.drawable.Drawable loadDrawableInner = loadDrawableInner(context);
        if (loadDrawableInner != null && hasTint()) {
            loadDrawableInner.mutate();
            loadDrawableInner.setTintList(this.mTintList);
            loadDrawableInner.setTintBlendMode(this.mBlendMode);
        }
        if (this.mUseMonochrome) {
            return crateMonochromeDrawable(loadDrawableInner, this.mInsetScale);
        }
        return loadDrawableInner;
    }

    private static android.graphics.drawable.Drawable crateMonochromeDrawable(android.graphics.drawable.Drawable drawable, float f) {
        android.graphics.drawable.Drawable monochrome;
        if ((drawable instanceof android.graphics.drawable.AdaptiveIconDrawable) && (monochrome = ((android.graphics.drawable.AdaptiveIconDrawable) drawable).getMonochrome()) != null) {
            return new android.graphics.drawable.InsetDrawable(monochrome, f);
        }
        return drawable;
    }

    private android.graphics.Bitmap fixMaxBitmapSize(android.graphics.Bitmap bitmap) {
        if (bitmap != null && bitmap.getByteCount() > android.graphics.RecordingCanvas.MAX_BITMAP_SIZE) {
            int rowBytes = android.graphics.RecordingCanvas.MAX_BITMAP_SIZE / (bitmap.getRowBytes() / bitmap.getWidth());
            float width = bitmap.getWidth() / bitmap.getHeight();
            int sqrt = (int) java.lang.Math.sqrt(rowBytes / width);
            return scaleDownIfNecessary(bitmap, (int) (sqrt * width), sqrt);
        }
        return bitmap;
    }

    private android.graphics.drawable.Drawable fixMaxBitmapSize(android.content.res.Resources resources, android.graphics.drawable.Drawable drawable) {
        if (drawable instanceof android.graphics.drawable.BitmapDrawable) {
            return new android.graphics.drawable.BitmapDrawable(resources, fixMaxBitmapSize(((android.graphics.drawable.BitmapDrawable) drawable).getBitmap()));
        }
        return drawable;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private android.graphics.drawable.Drawable loadDrawableInner(android.content.Context context) {
        switch (this.mType) {
            case 1:
                return new android.graphics.drawable.BitmapDrawable(context.getResources(), fixMaxBitmapSize(getBitmap()));
            case 2:
                if (getResources() == null) {
                    java.lang.String resPackage = getResPackage();
                    if (android.text.TextUtils.isEmpty(resPackage)) {
                        resPackage = context.getPackageName();
                    }
                    if ("android".equals(resPackage)) {
                        this.mObj1 = android.content.res.Resources.getSystem();
                    } else {
                        android.content.pm.PackageManager packageManager = context.getPackageManager();
                        try {
                            android.content.pm.ApplicationInfo applicationInfo = packageManager.getApplicationInfo(resPackage, 9216);
                            if (applicationInfo != null) {
                                this.mObj1 = packageManager.getResourcesForApplication(applicationInfo);
                            }
                        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                            android.util.Log.e(TAG, java.lang.String.format("Unable to find pkg=%s for icon %s", resPackage, this), e);
                        }
                        return null;
                    }
                }
                try {
                    return fixMaxBitmapSize(getResources(), getResources().getDrawable(getResId(), context.getTheme()));
                } catch (java.lang.RuntimeException e2) {
                    android.util.Log.e(TAG, java.lang.String.format("Unable to load resource 0x%08x from pkg=%s", java.lang.Integer.valueOf(getResId()), getResPackage()), e2);
                }
            case 3:
                return new android.graphics.drawable.BitmapDrawable(context.getResources(), fixMaxBitmapSize(android.graphics.BitmapFactory.decodeByteArray(getDataBytes(), getDataOffset(), getDataLength())));
            case 4:
                java.io.InputStream uriInputStream = getUriInputStream(context);
                if (uriInputStream != null) {
                    return new android.graphics.drawable.BitmapDrawable(context.getResources(), fixMaxBitmapSize(android.graphics.BitmapFactory.decodeStream(uriInputStream)));
                }
                return null;
            case 5:
                return new android.graphics.drawable.AdaptiveIconDrawable((android.graphics.drawable.Drawable) null, new android.graphics.drawable.BitmapDrawable(context.getResources(), fixMaxBitmapSize(getBitmap())));
            case 6:
                java.io.InputStream uriInputStream2 = getUriInputStream(context);
                if (uriInputStream2 != null) {
                    return new android.graphics.drawable.AdaptiveIconDrawable((android.graphics.drawable.Drawable) null, new android.graphics.drawable.BitmapDrawable(context.getResources(), fixMaxBitmapSize(android.graphics.BitmapFactory.decodeStream(uriInputStream2))));
                }
                return null;
            default:
                return null;
        }
    }

    private java.io.InputStream getUriInputStream(android.content.Context context) {
        android.net.Uri uri = getUri();
        java.lang.String scheme = uri.getScheme();
        if ("content".equals(scheme) || "file".equals(scheme)) {
            try {
                return context.getContentResolver().openInputStream(uri);
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "Unable to load image from URI: " + uri, e);
                return null;
            }
        }
        try {
            return new java.io.FileInputStream(new java.io.File(this.mString1));
        } catch (java.io.FileNotFoundException e2) {
            android.util.Log.w(TAG, "Unable to load image from path: " + uri, e2);
            return null;
        }
    }

    public android.graphics.drawable.Drawable loadDrawableAsUser(android.content.Context context, int i) {
        android.content.Context createContextAsUser;
        if (this.mType == 2) {
            java.lang.String resPackage = getResPackage();
            if (android.text.TextUtils.isEmpty(resPackage)) {
                resPackage = context.getPackageName();
            }
            if (getResources() == null && !getResPackage().equals("android")) {
                if (context.getUserId() == i) {
                    createContextAsUser = context;
                } else {
                    createContextAsUser = context.createContextAsUser(android.os.UserHandle.of(i), (android.os.UserHandle.isSameApp(context.getApplicationInfo().uid, android.os.Process.myUid()) ? 1 : 0) | 4);
                }
                try {
                    this.mObj1 = createContextAsUser.getPackageManager().getResourcesForApplication(resPackage);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Log.e(TAG, java.lang.String.format("Unable to find pkg=%s user=%d", getResPackage(), java.lang.Integer.valueOf(i)), e);
                }
            }
        }
        return loadDrawable(context);
    }

    public android.graphics.drawable.Drawable loadDrawableCheckingUriGrant(android.content.Context context, android.app.IUriGrantsManager iUriGrantsManager, int i, java.lang.String str) {
        if (getType() == 4 || getType() == 6) {
            try {
                iUriGrantsManager.checkGrantUriPermission_ignoreNonSystem(i, str, android.content.ContentProvider.getUriWithoutUserId(getUri()), 1, android.content.ContentProvider.getUserIdFromUri(getUri()));
            } catch (android.os.RemoteException | java.lang.SecurityException e) {
                android.util.Log.e(TAG, "Failed to get URI permission for: " + getUri(), e);
                return null;
            }
        }
        return loadDrawable(context);
    }

    public void convertToAshmem() {
        if ((this.mType == 1 || this.mType == 5) && getBitmap().isMutable() && getBitmap().getAllocationByteCount() >= 131072) {
            setBitmap(getBitmap().asShared());
        }
        this.mCachedAshmem = true;
    }

    public void writeToStream(java.io.OutputStream outputStream) throws java.io.IOException {
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(outputStream);
        dataOutputStream.writeInt(1);
        dataOutputStream.writeByte(this.mType);
        switch (this.mType) {
            case 1:
            case 5:
                getBitmap().compress(android.graphics.Bitmap.CompressFormat.PNG, 100, dataOutputStream);
                break;
            case 2:
                dataOutputStream.writeUTF(getResPackage());
                dataOutputStream.writeInt(getResId());
                break;
            case 3:
                dataOutputStream.writeInt(getDataLength());
                dataOutputStream.write(getDataBytes(), getDataOffset(), getDataLength());
                break;
            case 4:
            case 6:
                dataOutputStream.writeUTF(getUriString());
                break;
        }
    }

    private Icon(int i) {
        this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
        this.mCachedAshmem = false;
        this.mUseMonochrome = false;
        this.mInsetScale = 0.0f;
        this.mType = i;
    }

    public static android.graphics.drawable.Icon createFromStream(java.io.InputStream inputStream) throws java.io.IOException {
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(inputStream);
        if (dataInputStream.readInt() >= 1) {
            switch (dataInputStream.readByte()) {
                case 1:
                    return createWithBitmap(android.graphics.BitmapFactory.decodeStream(dataInputStream));
                case 2:
                    return createWithResource(dataInputStream.readUTF(), dataInputStream.readInt());
                case 3:
                    int readInt = dataInputStream.readInt();
                    byte[] bArr = new byte[readInt];
                    dataInputStream.read(bArr, 0, readInt);
                    return createWithData(bArr, 0, readInt);
                case 4:
                    return createWithContentUri(dataInputStream.readUTF());
                case 5:
                    return createWithAdaptiveBitmap(android.graphics.BitmapFactory.decodeStream(dataInputStream));
                case 6:
                    return createWithAdaptiveBitmapContentUri(dataInputStream.readUTF());
                default:
                    return null;
            }
        }
        return null;
    }

    public boolean sameAs(android.graphics.drawable.Icon icon) {
        if (icon == this) {
            return true;
        }
        if (this.mType != icon.getType()) {
            return false;
        }
        switch (this.mType) {
            case 1:
            case 5:
                return getBitmap() == icon.getBitmap();
            case 2:
                return getResId() == icon.getResId() && java.util.Objects.equals(getResPackage(), icon.getResPackage()) && this.mUseMonochrome == icon.mUseMonochrome && this.mInsetScale == icon.mInsetScale;
            case 3:
                return getDataLength() == icon.getDataLength() && getDataOffset() == icon.getDataOffset() && java.util.Arrays.equals(getDataBytes(), icon.getDataBytes());
            case 4:
            case 6:
                return java.util.Objects.equals(getUriString(), icon.getUriString());
            default:
                return false;
        }
    }

    public static android.graphics.drawable.Icon createWithResource(android.content.Context context, int i) {
        if (context == null) {
            throw new java.lang.IllegalArgumentException("Context must not be null.");
        }
        android.graphics.drawable.Icon icon = new android.graphics.drawable.Icon(2);
        icon.mInt1 = i;
        icon.mString1 = context.getPackageName();
        return icon;
    }

    public static android.graphics.drawable.Icon createWithResource(android.content.res.Resources resources, int i) {
        if (resources == null) {
            throw new java.lang.IllegalArgumentException("Resource must not be null.");
        }
        android.graphics.drawable.Icon icon = new android.graphics.drawable.Icon(2);
        icon.mInt1 = i;
        icon.mString1 = resources.getResourcePackageName(i);
        return icon;
    }

    public static android.graphics.drawable.Icon createWithResource(java.lang.String str, int i) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("Resource package name must not be null.");
        }
        android.graphics.drawable.Icon icon = new android.graphics.drawable.Icon(2);
        icon.mInt1 = i;
        icon.mString1 = str;
        return icon;
    }

    public static android.graphics.drawable.Icon createWithResourceAdaptiveDrawable(java.lang.String str, int i, boolean z, float f) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("Resource package name must not be null.");
        }
        android.graphics.drawable.Icon icon = new android.graphics.drawable.Icon(2);
        icon.mInt1 = i;
        icon.mUseMonochrome = z;
        icon.mInsetScale = f;
        icon.mString1 = str;
        return icon;
    }

    public static android.graphics.drawable.Icon createWithBitmap(android.graphics.Bitmap bitmap) {
        if (bitmap == null) {
            throw new java.lang.IllegalArgumentException("Bitmap must not be null.");
        }
        android.graphics.drawable.Icon icon = new android.graphics.drawable.Icon(1);
        icon.setBitmap(bitmap);
        return icon;
    }

    public static android.graphics.drawable.Icon createWithAdaptiveBitmap(android.graphics.Bitmap bitmap) {
        if (bitmap == null) {
            throw new java.lang.IllegalArgumentException("Bitmap must not be null.");
        }
        android.graphics.drawable.Icon icon = new android.graphics.drawable.Icon(5);
        icon.setBitmap(bitmap);
        return icon;
    }

    public static android.graphics.drawable.Icon createWithData(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new java.lang.IllegalArgumentException("Data must not be null.");
        }
        android.graphics.drawable.Icon icon = new android.graphics.drawable.Icon(3);
        icon.mObj1 = bArr;
        icon.mInt1 = i2;
        icon.mInt2 = i;
        return icon;
    }

    public static android.graphics.drawable.Icon createWithContentUri(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("Uri must not be null.");
        }
        android.graphics.drawable.Icon icon = new android.graphics.drawable.Icon(4);
        icon.mString1 = str;
        return icon;
    }

    public static android.graphics.drawable.Icon createWithContentUri(android.net.Uri uri) {
        if (uri == null) {
            throw new java.lang.IllegalArgumentException("Uri must not be null.");
        }
        return createWithContentUri(uri.toString());
    }

    public static android.graphics.drawable.Icon createWithAdaptiveBitmapContentUri(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("Uri must not be null.");
        }
        android.graphics.drawable.Icon icon = new android.graphics.drawable.Icon(6);
        icon.mString1 = str;
        return icon;
    }

    public static android.graphics.drawable.Icon createWithAdaptiveBitmapContentUri(android.net.Uri uri) {
        if (uri == null) {
            throw new java.lang.IllegalArgumentException("Uri must not be null.");
        }
        return createWithAdaptiveBitmapContentUri(uri.toString());
    }

    public android.graphics.drawable.Icon setTint(int i) {
        return setTintList(android.content.res.ColorStateList.valueOf(i));
    }

    public android.graphics.drawable.Icon setTintList(android.content.res.ColorStateList colorStateList) {
        this.mTintList = colorStateList;
        return this;
    }

    public android.content.res.ColorStateList getTintList() {
        return this.mTintList;
    }

    public android.graphics.drawable.Icon setTintMode(android.graphics.PorterDuff.Mode mode) {
        this.mBlendMode = android.graphics.BlendMode.fromValue(mode.nativeInt);
        return this;
    }

    public android.graphics.drawable.Icon setTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mBlendMode = blendMode;
        return this;
    }

    public android.graphics.BlendMode getTintBlendMode() {
        return this.mBlendMode;
    }

    public boolean hasTint() {
        return (this.mTintList == null && this.mBlendMode == DEFAULT_BLEND_MODE) ? false : true;
    }

    public static android.graphics.drawable.Icon createWithFilePath(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("Path must not be null.");
        }
        android.graphics.drawable.Icon icon = new android.graphics.drawable.Icon(4);
        icon.mString1 = str;
        return icon;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder("Icon(typ=").append(typeToString(this.mType));
        switch (this.mType) {
            case 1:
            case 5:
                append.append(" size=").append(getBitmap().getWidth()).append("x").append(getBitmap().getHeight());
                break;
            case 2:
                append.append(" pkg=").append(getResPackage()).append(" id=").append(java.lang.String.format("0x%08x", java.lang.Integer.valueOf(getResId())));
                break;
            case 3:
                append.append(" len=").append(getDataLength());
                if (getDataOffset() != 0) {
                    append.append(" off=").append(getDataOffset());
                    break;
                }
                break;
            case 4:
            case 6:
                append.append(" uri=").append(getUriString());
                break;
        }
        if (this.mTintList != null) {
            append.append(" tint=");
            int[] colors = this.mTintList.getColors();
            int length = colors.length;
            java.lang.String str = "";
            int i = 0;
            while (i < length) {
                append.append(java.lang.String.format("%s0x%08x", str, java.lang.Integer.valueOf(colors[i])));
                i++;
                str = android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER;
            }
        }
        if (this.mBlendMode != DEFAULT_BLEND_MODE) {
            append.append(" mode=").append(this.mBlendMode);
        }
        append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return append.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return (this.mType == 1 || this.mType == 5 || this.mType == 3) ? 1 : 0;
    }

    private Icon(android.os.Parcel parcel) {
        this(parcel.readInt());
        switch (this.mType) {
            case 1:
            case 5:
                this.mObj1 = android.graphics.Bitmap.CREATOR.createFromParcel(parcel);
                break;
            case 2:
                java.lang.String readString = parcel.readString();
                int readInt = parcel.readInt();
                this.mString1 = readString;
                this.mInt1 = readInt;
                this.mUseMonochrome = parcel.readBoolean();
                this.mInsetScale = parcel.readFloat();
                break;
            case 3:
                int readInt2 = parcel.readInt();
                byte[] readBlob = parcel.readBlob();
                if (readInt2 != readBlob.length) {
                    throw new java.lang.RuntimeException("internal unparceling error: blob length (" + readBlob.length + ") != expected length (" + readInt2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
                this.mInt1 = readInt2;
                this.mObj1 = readBlob;
                break;
            case 4:
            case 6:
                this.mString1 = parcel.readString();
                break;
            default:
                throw new java.lang.RuntimeException("invalid " + getClass().getSimpleName() + " type in parcel: " + this.mType);
        }
        if (parcel.readInt() == 1) {
            this.mTintList = android.content.res.ColorStateList.CREATOR.createFromParcel(parcel);
        }
        this.mBlendMode = android.graphics.BlendMode.fromValue(parcel.readInt());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        switch (this.mType) {
            case 1:
            case 5:
                if (!this.mCachedAshmem) {
                    this.mObj1 = ((android.graphics.Bitmap) this.mObj1).asShared();
                    this.mCachedAshmem = true;
                }
                getBitmap().writeToParcel(parcel, i);
                break;
            case 2:
                parcel.writeString(getResPackage());
                parcel.writeInt(getResId());
                parcel.writeBoolean(this.mUseMonochrome);
                parcel.writeFloat(this.mInsetScale);
                break;
            case 3:
                parcel.writeInt(getDataLength());
                parcel.writeBlob(getDataBytes(), getDataOffset(), getDataLength());
                break;
            case 4:
            case 6:
                parcel.writeString(getUriString());
                break;
        }
        if (this.mTintList == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mTintList.writeToParcel(parcel, i);
        }
        parcel.writeInt(android.graphics.BlendMode.toValue(this.mBlendMode));
    }

    public static android.graphics.Bitmap scaleDownIfNecessary(android.graphics.Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width > i || height > i2) {
            float f = width;
            float f2 = height;
            float min = java.lang.Math.min(i / f, i2 / f2);
            return android.graphics.Bitmap.createScaledBitmap(bitmap, java.lang.Math.max(1, (int) (f * min)), java.lang.Math.max(1, (int) (min * f2)), true);
        }
        return bitmap;
    }

    public void scaleDownIfNecessary(int i, int i2) {
        if (this.mType != 1 && this.mType != 5) {
            return;
        }
        setBitmap(scaleDownIfNecessary(getBitmap(), i, i2));
    }

    private class LoadDrawableTask implements java.lang.Runnable {
        final android.content.Context mContext;
        final android.os.Message mMessage;

        public LoadDrawableTask(android.content.Context context, android.os.Handler handler, final android.graphics.drawable.Icon.OnDrawableLoadedListener onDrawableLoadedListener) {
            this.mContext = context;
            this.mMessage = android.os.Message.obtain(handler, new java.lang.Runnable() { // from class: android.graphics.drawable.Icon.LoadDrawableTask.1
                @Override // java.lang.Runnable
                public void run() {
                    onDrawableLoadedListener.onDrawableLoaded((android.graphics.drawable.Drawable) android.graphics.drawable.Icon.LoadDrawableTask.this.mMessage.obj);
                }
            });
        }

        public LoadDrawableTask(android.content.Context context, android.os.Message message) {
            this.mContext = context;
            this.mMessage = message;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mMessage.obj = android.graphics.drawable.Icon.this.loadDrawable(this.mContext);
            this.mMessage.sendToTarget();
        }

        public void runAsync() {
            android.os.AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }
    }
}
