package android.view;

/* loaded from: classes4.dex */
public final class PointerIcon implements android.os.Parcelable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final java.lang.String TAG = "PointerIcon";
    public static final int TYPE_ALIAS = 1010;
    public static final int TYPE_ALL_SCROLL = 1013;
    public static final int TYPE_ARROW = 1000;
    public static final int TYPE_CELL = 1006;
    public static final int TYPE_CONTEXT_MENU = 1001;
    public static final int TYPE_COPY = 1011;
    public static final int TYPE_CROSSHAIR = 1007;
    public static final int TYPE_CUSTOM = -1;
    public static final int TYPE_DEFAULT = 1000;
    public static final int TYPE_GRAB = 1020;
    public static final int TYPE_GRABBING = 1021;
    public static final int TYPE_HAND = 1002;
    public static final int TYPE_HANDWRITING = 1022;
    public static final int TYPE_HELP = 1003;
    public static final int TYPE_HORIZONTAL_DOUBLE_ARROW = 1014;
    public static final int TYPE_NOT_SPECIFIED = 1;
    public static final int TYPE_NO_DROP = 1012;
    public static final int TYPE_NULL = 0;
    private static final int TYPE_OEM_FIRST = 10000;
    public static final int TYPE_SPOT_ANCHOR = 2002;
    public static final int TYPE_SPOT_HOVER = 2000;
    public static final int TYPE_SPOT_TOUCH = 2001;
    public static final int TYPE_TEXT = 1008;
    public static final int TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW = 1017;
    public static final int TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW = 1016;
    public static final int TYPE_VERTICAL_DOUBLE_ARROW = 1015;
    public static final int TYPE_VERTICAL_TEXT = 1009;
    public static final int TYPE_WAIT = 1004;
    public static final int TYPE_ZOOM_IN = 1018;
    public static final int TYPE_ZOOM_OUT = 1019;
    private android.graphics.Bitmap mBitmap;
    private android.graphics.Bitmap[] mBitmapFrames;
    private boolean mDrawNativeDropShadow;
    private int mDurationPerFrame;
    private float mHotSpotX;
    private float mHotSpotY;
    private final int mType;
    private static final android.util.SparseArray<android.view.PointerIcon> SYSTEM_ICONS = new android.util.SparseArray<>();
    public static final android.os.Parcelable.Creator<android.view.PointerIcon> CREATOR = new android.os.Parcelable.Creator<android.view.PointerIcon>() { // from class: android.view.PointerIcon.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.PointerIcon createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt != -1) {
                return android.view.PointerIcon.getSystemIcon(readInt);
            }
            android.view.PointerIcon create = android.view.PointerIcon.create(android.graphics.Bitmap.CREATOR.createFromParcel(parcel), parcel.readFloat(), parcel.readFloat());
            create.mDrawNativeDropShadow = parcel.readBoolean();
            return create;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.PointerIcon[] newArray(int i) {
            return new android.view.PointerIcon[i];
        }
    };

    private PointerIcon(int i) {
        this.mType = i;
    }

    public static android.view.PointerIcon getSystemIcon(android.content.Context context, int i) {
        if (context == null) {
            throw new java.lang.IllegalArgumentException("context must not be null");
        }
        return getSystemIcon(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.view.PointerIcon getSystemIcon(int i) {
        if (i == -1) {
            throw new java.lang.IllegalArgumentException("cannot get system icon for TYPE_CUSTOM");
        }
        android.view.PointerIcon pointerIcon = SYSTEM_ICONS.get(i);
        if (pointerIcon == null) {
            android.view.PointerIcon pointerIcon2 = new android.view.PointerIcon(i);
            SYSTEM_ICONS.put(i, pointerIcon2);
            return pointerIcon2;
        }
        return pointerIcon;
    }

    public static android.view.PointerIcon getLoadedSystemIcon(android.content.Context context, int i, boolean z) {
        int i2;
        if (i == 1) {
            throw new java.lang.IllegalStateException("Cannot load icon for type TYPE_NOT_SPECIFIED");
        }
        if (i == -1) {
            throw new java.lang.IllegalArgumentException("Custom icons must be loaded when they're created");
        }
        int systemIconTypeIndex = getSystemIconTypeIndex(i);
        if (systemIconTypeIndex == 0) {
            systemIconTypeIndex = getSystemIconTypeIndex(1000);
        }
        if (z) {
            i2 = com.android.internal.R.style.LargePointer;
        } else if (android.view.flags.Flags.enableVectorCursors()) {
            i2 = com.android.internal.R.style.VectorPointer;
        } else {
            i2 = com.android.internal.R.style.Pointer;
        }
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, com.android.internal.R.styleable.Pointer, 0, i2);
        int resourceId = obtainStyledAttributes.getResourceId(systemIconTypeIndex, -1);
        obtainStyledAttributes.recycle();
        if (resourceId == -1) {
            android.util.Log.w(TAG, "Missing theme resources for pointer icon type " + i);
            if (i == 1000) {
                return getSystemIcon(0);
            }
            return getLoadedSystemIcon(context, 1000, z);
        }
        android.view.PointerIcon pointerIcon = new android.view.PointerIcon(i);
        pointerIcon.loadResource(context.getResources(), resourceId);
        return pointerIcon;
    }

    private boolean isLoaded() {
        return this.mBitmap != null && this.mHotSpotX >= 0.0f && this.mHotSpotX < ((float) this.mBitmap.getWidth()) && this.mHotSpotY >= 0.0f && this.mHotSpotY < ((float) this.mBitmap.getHeight());
    }

    public static android.view.PointerIcon create(android.graphics.Bitmap bitmap, float f, float f2) {
        if (bitmap == null) {
            throw new java.lang.IllegalArgumentException("bitmap must not be null");
        }
        validateHotSpot(bitmap, f, f2, false);
        android.view.PointerIcon pointerIcon = new android.view.PointerIcon(-1);
        pointerIcon.mBitmap = bitmap;
        pointerIcon.mHotSpotX = f;
        pointerIcon.mHotSpotY = f2;
        return pointerIcon;
    }

    public static android.view.PointerIcon load(android.content.res.Resources resources, int i) {
        if (resources == null) {
            throw new java.lang.IllegalArgumentException("resources must not be null");
        }
        android.view.PointerIcon pointerIcon = new android.view.PointerIcon(-1);
        pointerIcon.loadResource(resources, i);
        return pointerIcon;
    }

    public int getType() {
        return this.mType;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        if (this.mType != -1) {
            return;
        }
        if (!isLoaded()) {
            throw new java.lang.IllegalStateException("Custom icon should be loaded upon creation");
        }
        this.mBitmap.writeToParcel(parcel, i);
        parcel.writeFloat(this.mHotSpotX);
        parcel.writeFloat(this.mHotSpotY);
        parcel.writeBoolean(this.mDrawNativeDropShadow);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.view.PointerIcon)) {
            return false;
        }
        android.view.PointerIcon pointerIcon = (android.view.PointerIcon) obj;
        if (this.mType == pointerIcon.mType && this.mBitmap == pointerIcon.mBitmap && this.mHotSpotX == pointerIcon.mHotSpotX && this.mHotSpotY == pointerIcon.mHotSpotY) {
            return true;
        }
        return false;
    }

    private android.graphics.Bitmap getBitmapFromDrawable(android.graphics.drawable.BitmapDrawable bitmapDrawable) {
        android.graphics.Bitmap bitmap = bitmapDrawable.getBitmap();
        int intrinsicWidth = bitmapDrawable.getIntrinsicWidth();
        int intrinsicHeight = bitmapDrawable.getIntrinsicHeight();
        if (intrinsicWidth == bitmap.getWidth() && intrinsicHeight == bitmap.getHeight()) {
            return bitmap;
        }
        android.graphics.Rect rect = new android.graphics.Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        android.graphics.RectF rectF = new android.graphics.RectF(0.0f, 0.0f, intrinsicWidth, intrinsicHeight);
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, bitmap.getConfig());
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, rect, rectF, paint);
        return createBitmap;
    }

    private android.graphics.drawable.BitmapDrawable getBitmapDrawableFromVectorDrawable(android.content.res.Resources resources, android.graphics.drawable.VectorDrawable vectorDrawable) {
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(resources.getDisplayMetrics(), vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), android.graphics.Bitmap.Config.ARGB_8888, true);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return new android.graphics.drawable.BitmapDrawable(resources, createBitmap);
    }

    private void loadResource(android.content.res.Resources resources, int i) {
        android.content.res.XmlResourceParser xml = resources.getXml(i);
        try {
            try {
                com.android.internal.util.XmlUtils.beginDocument(xml, "pointer-icon");
                android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xml, com.android.internal.R.styleable.PointerIcon);
                int resourceId = obtainAttributes.getResourceId(0, 0);
                float dimension = obtainAttributes.getDimension(1, 0.0f);
                float dimension2 = obtainAttributes.getDimension(2, 0.0f);
                obtainAttributes.recycle();
                if (resourceId == 0) {
                    throw new java.lang.IllegalArgumentException("<pointer-icon> is missing bitmap attribute.");
                }
                android.graphics.drawable.Drawable drawable = resources.getDrawable(resourceId);
                if (drawable instanceof android.graphics.drawable.AnimationDrawable) {
                    android.graphics.drawable.AnimationDrawable animationDrawable = (android.graphics.drawable.AnimationDrawable) drawable;
                    int numberOfFrames = animationDrawable.getNumberOfFrames();
                    android.graphics.drawable.Drawable frame = animationDrawable.getFrame(0);
                    if (numberOfFrames == 1) {
                        android.util.Log.w(TAG, "Animation icon with single frame -- simply treating the first frame as a normal bitmap icon.");
                    } else {
                        this.mDurationPerFrame = animationDrawable.getDuration(0);
                        this.mBitmapFrames = new android.graphics.Bitmap[numberOfFrames - 1];
                        int intrinsicWidth = frame.getIntrinsicWidth();
                        int intrinsicHeight = frame.getIntrinsicHeight();
                        boolean z = frame instanceof android.graphics.drawable.VectorDrawable;
                        this.mDrawNativeDropShadow = z;
                        for (int i2 = 1; i2 < numberOfFrames; i2++) {
                            android.graphics.drawable.Drawable frame2 = animationDrawable.getFrame(i2);
                            if (!(frame2 instanceof android.graphics.drawable.BitmapDrawable) && !(frame2 instanceof android.graphics.drawable.VectorDrawable)) {
                                throw new java.lang.IllegalArgumentException("Frame of an animated pointer icon must refer to a bitmap drawable or vector drawable.");
                            }
                            if (z != (frame2 instanceof android.graphics.drawable.VectorDrawable)) {
                                throw new java.lang.IllegalArgumentException("The drawable of the " + i2 + "-th frame is a different type from the others. All frames should be the same type.");
                            }
                            if (frame2.getIntrinsicWidth() != intrinsicWidth || frame2.getIntrinsicHeight() != intrinsicHeight) {
                                throw new java.lang.IllegalArgumentException("The bitmap size of " + i2 + "-th frame is different. All frames should have the exact same size and share the same hotspot.");
                            }
                            if (z) {
                                frame2 = getBitmapDrawableFromVectorDrawable(resources, (android.graphics.drawable.VectorDrawable) frame2);
                            }
                            this.mBitmapFrames[i2 - 1] = getBitmapFromDrawable((android.graphics.drawable.BitmapDrawable) frame2);
                        }
                    }
                    drawable = frame;
                }
                if (drawable instanceof android.graphics.drawable.VectorDrawable) {
                    this.mDrawNativeDropShadow = true;
                    drawable = getBitmapDrawableFromVectorDrawable(resources, (android.graphics.drawable.VectorDrawable) drawable);
                }
                if (!(drawable instanceof android.graphics.drawable.BitmapDrawable)) {
                    throw new java.lang.IllegalArgumentException("<pointer-icon> bitmap attribute must refer to a bitmap drawable.");
                }
                android.graphics.Bitmap bitmapFromDrawable = getBitmapFromDrawable((android.graphics.drawable.BitmapDrawable) drawable);
                validateHotSpot(bitmapFromDrawable, dimension, dimension2, true);
                this.mBitmap = bitmapFromDrawable;
                this.mHotSpotX = dimension;
                this.mHotSpotY = dimension2;
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException("Exception parsing pointer icon resource.", e);
            }
        } finally {
            xml.close();
        }
    }

    public java.lang.String toString() {
        return "PointerIcon{type=" + typeToString(this.mType) + ", hotspotX=" + this.mHotSpotX + ", hotspotY=" + this.mHotSpotY + "}";
    }

    private static void validateHotSpot(android.graphics.Bitmap bitmap, float f, float f2, boolean z) {
        if (f < 0.0f || (!z ? f >= bitmap.getWidth() : ((int) f) > bitmap.getWidth())) {
            throw new java.lang.IllegalArgumentException("x hotspot lies outside of the bitmap area");
        }
        if (f2 >= 0.0f) {
            if (z) {
                if (((int) f2) <= bitmap.getHeight()) {
                    return;
                }
            } else if (f2 < bitmap.getHeight()) {
                return;
            }
        }
        throw new java.lang.IllegalArgumentException("y hotspot lies outside of the bitmap area");
    }

    private static int getSystemIconTypeIndex(int i) {
        switch (i) {
            case 1000:
                break;
            case 1001:
                break;
            case 1002:
                break;
            case 1003:
                break;
            case 1004:
                break;
            case 1006:
                break;
            case 1007:
                break;
            case 1008:
                break;
            case 1009:
                break;
            case 1010:
                break;
            case 1011:
                break;
            case 1012:
                break;
            case 1013:
                break;
            case 1014:
                break;
            case 1015:
                break;
            case 1016:
                break;
            case 1017:
                break;
            case 1018:
                break;
            case 1019:
                break;
            case 1020:
                break;
            case 1021:
                break;
            case 1022:
                break;
            case 2000:
                break;
            case 2001:
                break;
            case 2002:
                break;
        }
        return 0;
    }

    public static java.lang.String typeToString(int i) {
        switch (i) {
            case -1:
                return "CUSTOM";
            case 0:
                return "NULL";
            case 1:
                return "NOT_SPECIFIED";
            case 1000:
                return "ARROW";
            case 1001:
                return "CONTEXT_MENU";
            case 1002:
                return "HAND";
            case 1003:
                return "HELP";
            case 1004:
                return "WAIT";
            case 1006:
                return "CELL";
            case 1007:
                return "CROSSHAIR";
            case 1008:
                return "TEXT";
            case 1009:
                return "VERTICAL_TEXT";
            case 1010:
                return "ALIAS";
            case 1011:
                return "COPY";
            case 1012:
                return "NO_DROP";
            case 1013:
                return "ALL_SCROLL";
            case 1014:
                return "HORIZONTAL_DOUBLE_ARROW";
            case 1015:
                return "VERTICAL_DOUBLE_ARROW";
            case 1016:
                return "TOP_RIGHT_DIAGONAL_DOUBLE_ARROW";
            case 1017:
                return "TOP_LEFT_DIAGONAL_DOUBLE_ARROW";
            case 1018:
                return "ZOOM_IN";
            case 1019:
                return "ZOOM_OUT";
            case 1020:
                return "GRAB";
            case 1021:
                return "GRABBING";
            case 1022:
                return "HANDWRITING";
            case 2000:
                return "SPOT_HOVER";
            case 2001:
                return "SPOT_TOUCH";
            case 2002:
                return "SPOT_ANCHOR";
            default:
                return java.lang.Integer.toString(i);
        }
    }
}
