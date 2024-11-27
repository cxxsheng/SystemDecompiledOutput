package android.gesture;

/* loaded from: classes.dex */
public class Gesture implements android.os.Parcelable {
    private static final boolean BITMAP_RENDERING_ANTIALIAS = true;
    private static final boolean BITMAP_RENDERING_DITHER = true;
    private static final int BITMAP_RENDERING_WIDTH = 2;
    private static final long GESTURE_ID_BASE = java.lang.System.currentTimeMillis();
    private static final java.util.concurrent.atomic.AtomicInteger sGestureCount = new java.util.concurrent.atomic.AtomicInteger(0);
    public static final android.os.Parcelable.Creator<android.gesture.Gesture> CREATOR = new android.os.Parcelable.Creator<android.gesture.Gesture>() { // from class: android.gesture.Gesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.gesture.Gesture createFromParcel(android.os.Parcel parcel) {
            android.gesture.Gesture gesture;
            long readLong = parcel.readLong();
            java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(parcel.createByteArray()));
            try {
                try {
                    gesture = android.gesture.Gesture.deserialize(dataInputStream);
                } catch (java.io.IOException e) {
                    android.util.Log.e(android.gesture.GestureConstants.LOG_TAG, "Error reading Gesture from parcel:", e);
                    android.gesture.GestureUtils.closeStream(dataInputStream);
                    gesture = null;
                }
                if (gesture != null) {
                    gesture.mGestureID = readLong;
                }
                return gesture;
            } finally {
                android.gesture.GestureUtils.closeStream(dataInputStream);
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.gesture.Gesture[] newArray(int i) {
            return new android.gesture.Gesture[i];
        }
    };
    private final android.graphics.RectF mBoundingBox = new android.graphics.RectF();
    private final java.util.ArrayList<android.gesture.GestureStroke> mStrokes = new java.util.ArrayList<>();
    private long mGestureID = GESTURE_ID_BASE + sGestureCount.incrementAndGet();

    public java.lang.Object clone() {
        android.gesture.Gesture gesture = new android.gesture.Gesture();
        gesture.mBoundingBox.set(this.mBoundingBox.left, this.mBoundingBox.top, this.mBoundingBox.right, this.mBoundingBox.bottom);
        int size = this.mStrokes.size();
        for (int i = 0; i < size; i++) {
            gesture.mStrokes.add((android.gesture.GestureStroke) this.mStrokes.get(i).clone());
        }
        return gesture;
    }

    public java.util.ArrayList<android.gesture.GestureStroke> getStrokes() {
        return this.mStrokes;
    }

    public int getStrokesCount() {
        return this.mStrokes.size();
    }

    public void addStroke(android.gesture.GestureStroke gestureStroke) {
        this.mStrokes.add(gestureStroke);
        this.mBoundingBox.union(gestureStroke.boundingBox);
    }

    public float getLength() {
        java.util.ArrayList<android.gesture.GestureStroke> arrayList = this.mStrokes;
        int size = arrayList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i = (int) (i + arrayList.get(i2).length);
        }
        return i;
    }

    public android.graphics.RectF getBoundingBox() {
        return this.mBoundingBox;
    }

    public android.graphics.Path toPath() {
        return toPath(null);
    }

    public android.graphics.Path toPath(android.graphics.Path path) {
        if (path == null) {
            path = new android.graphics.Path();
        }
        java.util.ArrayList<android.gesture.GestureStroke> arrayList = this.mStrokes;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            path.addPath(arrayList.get(i).getPath());
        }
        return path;
    }

    public android.graphics.Path toPath(int i, int i2, int i3, int i4) {
        return toPath(null, i, i2, i3, i4);
    }

    public android.graphics.Path toPath(android.graphics.Path path, int i, int i2, int i3, int i4) {
        if (path == null) {
            path = new android.graphics.Path();
        }
        java.util.ArrayList<android.gesture.GestureStroke> arrayList = this.mStrokes;
        int size = arrayList.size();
        for (int i5 = 0; i5 < size; i5++) {
            int i6 = i3 * 2;
            path.addPath(arrayList.get(i5).toPath(i - i6, i2 - i6, i4));
        }
        return path;
    }

    void setID(long j) {
        this.mGestureID = j;
    }

    public long getID() {
        return this.mGestureID;
    }

    public android.graphics.Bitmap toBitmap(int i, int i2, int i3, int i4, int i5) {
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(i, i2, android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        float f = i3;
        canvas.translate(f, f);
        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(i5);
        paint.setStyle(android.graphics.Paint.Style.STROKE);
        paint.setStrokeJoin(android.graphics.Paint.Join.ROUND);
        paint.setStrokeCap(android.graphics.Paint.Cap.ROUND);
        paint.setStrokeWidth(2.0f);
        java.util.ArrayList<android.gesture.GestureStroke> arrayList = this.mStrokes;
        int size = arrayList.size();
        for (int i6 = 0; i6 < size; i6++) {
            int i7 = i3 * 2;
            canvas.drawPath(arrayList.get(i6).toPath(i - i7, i2 - i7, i4), paint);
        }
        return createBitmap;
    }

    public android.graphics.Bitmap toBitmap(int i, int i2, int i3, int i4) {
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(i, i2, android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(i4);
        paint.setStyle(android.graphics.Paint.Style.STROKE);
        paint.setStrokeJoin(android.graphics.Paint.Join.ROUND);
        paint.setStrokeCap(android.graphics.Paint.Cap.ROUND);
        paint.setStrokeWidth(2.0f);
        android.graphics.Path path = toPath();
        android.graphics.RectF rectF = new android.graphics.RectF();
        path.computeBounds(rectF, true);
        int i5 = i3 * 2;
        float width = (i - i5) / rectF.width();
        float height = (i2 - i5) / rectF.height();
        if (width > height) {
            width = height;
        }
        paint.setStrokeWidth(2.0f / width);
        path.offset((-rectF.left) + ((i - (rectF.width() * width)) / 2.0f), (-rectF.top) + ((i2 - (rectF.height() * width)) / 2.0f));
        float f = i3;
        canvas.translate(f, f);
        canvas.scale(width, width);
        canvas.drawPath(path, paint);
        return createBitmap;
    }

    void serialize(java.io.DataOutputStream dataOutputStream) throws java.io.IOException {
        java.util.ArrayList<android.gesture.GestureStroke> arrayList = this.mStrokes;
        int size = arrayList.size();
        dataOutputStream.writeLong(this.mGestureID);
        dataOutputStream.writeInt(size);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).serialize(dataOutputStream);
        }
    }

    static android.gesture.Gesture deserialize(java.io.DataInputStream dataInputStream) throws java.io.IOException {
        android.gesture.Gesture gesture = new android.gesture.Gesture();
        gesture.mGestureID = dataInputStream.readLong();
        int readInt = dataInputStream.readInt();
        for (int i = 0; i < readInt; i++) {
            gesture.addStroke(android.gesture.GestureStroke.deserialize(dataInputStream));
        }
        return gesture;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        boolean z;
        parcel.writeLong(this.mGestureID);
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(32768);
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
        try {
            try {
                serialize(dataOutputStream);
                android.gesture.GestureUtils.closeStream(dataOutputStream);
                android.gesture.GestureUtils.closeStream(byteArrayOutputStream);
                z = true;
            } catch (java.io.IOException e) {
                android.util.Log.e(android.gesture.GestureConstants.LOG_TAG, "Error writing Gesture to parcel:", e);
                android.gesture.GestureUtils.closeStream(dataOutputStream);
                android.gesture.GestureUtils.closeStream(byteArrayOutputStream);
                z = false;
            }
            if (z) {
                parcel.writeByteArray(byteArrayOutputStream.toByteArray());
            }
        } catch (java.lang.Throwable th) {
            android.gesture.GestureUtils.closeStream(dataOutputStream);
            android.gesture.GestureUtils.closeStream(byteArrayOutputStream);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
