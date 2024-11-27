package android.graphics;

/* loaded from: classes.dex */
public final class Rect implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.graphics.Rect> CREATOR = new android.os.Parcelable.Creator<android.graphics.Rect>() { // from class: android.graphics.Rect.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.Rect createFromParcel(android.os.Parcel parcel) {
            android.graphics.Rect rect = new android.graphics.Rect();
            rect.readFromParcel(parcel);
            return rect;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.Rect[] newArray(int i) {
            return new android.graphics.Rect[i];
        }
    };
    public int bottom;
    public int left;
    public int right;
    public int top;

    private static final class UnflattenHelper {
        private static final java.util.regex.Pattern FLATTENED_PATTERN = java.util.regex.Pattern.compile("(-?\\d+) (-?\\d+) (-?\\d+) (-?\\d+)");

        private UnflattenHelper() {
        }

        static java.util.regex.Matcher getMatcher(java.lang.String str) {
            return FLATTENED_PATTERN.matcher(str);
        }
    }

    public Rect() {
    }

    public Rect(int i, int i2, int i3, int i4) {
        this.left = i;
        this.top = i2;
        this.right = i3;
        this.bottom = i4;
    }

    public Rect(android.graphics.Rect rect) {
        if (rect == null) {
            this.bottom = 0;
            this.right = 0;
            this.top = 0;
            this.left = 0;
            return;
        }
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    public Rect(android.graphics.Insets insets) {
        if (insets == null) {
            this.bottom = 0;
            this.right = 0;
            this.top = 0;
            this.left = 0;
            return;
        }
        this.left = insets.left;
        this.top = insets.top;
        this.right = insets.right;
        this.bottom = insets.bottom;
    }

    public static android.graphics.Rect copyOrNull(android.graphics.Rect rect) {
        if (rect == null) {
            return null;
        }
        return new android.graphics.Rect(rect);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.graphics.Rect rect = (android.graphics.Rect) obj;
        if (this.left == rect.left && this.top == rect.top && this.right == rect.right && this.bottom == rect.bottom) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((this.left * 31) + this.top) * 31) + this.right) * 31) + this.bottom;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(32);
        sb.append("Rect(");
        sb.append(this.left);
        sb.append(", ");
        sb.append(this.top);
        sb.append(" - ");
        sb.append(this.right);
        sb.append(", ");
        sb.append(this.bottom);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public java.lang.String toShortString() {
        return toShortString(new java.lang.StringBuilder(32));
    }

    public java.lang.String toShortString(java.lang.StringBuilder sb) {
        sb.setLength(0);
        sb.append('[');
        sb.append(this.left);
        sb.append(',');
        sb.append(this.top);
        sb.append("][");
        sb.append(this.right);
        sb.append(',');
        sb.append(this.bottom);
        sb.append(']');
        return sb.toString();
    }

    public java.lang.String flattenToString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(32);
        sb.append(this.left);
        sb.append(' ');
        sb.append(this.top);
        sb.append(' ');
        sb.append(this.right);
        sb.append(' ');
        sb.append(this.bottom);
        return sb.toString();
    }

    public static android.graphics.Rect unflattenFromString(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        java.util.regex.Matcher matcher = android.graphics.Rect.UnflattenHelper.getMatcher(str);
        if (matcher.matches()) {
            return new android.graphics.Rect(java.lang.Integer.parseInt(matcher.group(1)), java.lang.Integer.parseInt(matcher.group(2)), java.lang.Integer.parseInt(matcher.group(3)), java.lang.Integer.parseInt(matcher.group(4)));
        }
        return null;
    }

    public void printShortString(java.io.PrintWriter printWriter) {
        printWriter.print('[');
        printWriter.print(this.left);
        printWriter.print(',');
        printWriter.print(this.top);
        printWriter.print("][");
        printWriter.print(this.right);
        printWriter.print(',');
        printWriter.print(this.bottom);
        printWriter.print(']');
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.left);
        protoOutputStream.write(1120986464258L, this.top);
        protoOutputStream.write(1120986464259L, this.right);
        protoOutputStream.write(1120986464260L, this.bottom);
        protoOutputStream.end(start);
    }

    public void readFromProto(android.util.proto.ProtoInputStream protoInputStream, long j) throws java.io.IOException, android.util.proto.WireTypeMismatchException {
        long start = protoInputStream.start(j);
        while (protoInputStream.nextField() != -1) {
            try {
                switch (protoInputStream.getFieldNumber()) {
                    case 1:
                        this.left = protoInputStream.readInt(1120986464257L);
                        break;
                    case 2:
                        this.top = protoInputStream.readInt(1120986464258L);
                        break;
                    case 3:
                        this.right = protoInputStream.readInt(1120986464259L);
                        break;
                    case 4:
                        this.bottom = protoInputStream.readInt(1120986464260L);
                        break;
                }
            } finally {
                protoInputStream.end(start);
            }
        }
    }

    public final boolean isEmpty() {
        return this.left >= this.right || this.top >= this.bottom;
    }

    public boolean isValid() {
        return this.left <= this.right && this.top <= this.bottom;
    }

    public final int width() {
        return this.right - this.left;
    }

    public final int height() {
        return this.bottom - this.top;
    }

    public final int centerX() {
        return (this.left + this.right) >> 1;
    }

    public final int centerY() {
        return (this.top + this.bottom) >> 1;
    }

    public final float exactCenterX() {
        return (this.left + this.right) * 0.5f;
    }

    public final float exactCenterY() {
        return (this.top + this.bottom) * 0.5f;
    }

    public void setEmpty() {
        this.bottom = 0;
        this.top = 0;
        this.right = 0;
        this.left = 0;
    }

    public void set(int i, int i2, int i3, int i4) {
        this.left = i;
        this.top = i2;
        this.right = i3;
        this.bottom = i4;
    }

    public void set(android.graphics.Rect rect) {
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    public void offset(int i, int i2) {
        this.left += i;
        this.top += i2;
        this.right += i;
        this.bottom += i2;
    }

    public void offsetTo(int i, int i2) {
        this.right += i - this.left;
        this.bottom += i2 - this.top;
        this.left = i;
        this.top = i2;
    }

    public void inset(int i, int i2) {
        this.left += i;
        this.top += i2;
        this.right -= i;
        this.bottom -= i2;
    }

    public void inset(android.graphics.Rect rect) {
        this.left += rect.left;
        this.top += rect.top;
        this.right -= rect.right;
        this.bottom -= rect.bottom;
    }

    public void inset(android.graphics.Insets insets) {
        this.left += insets.left;
        this.top += insets.top;
        this.right -= insets.right;
        this.bottom -= insets.bottom;
    }

    public void inset(int i, int i2, int i3, int i4) {
        this.left += i;
        this.top += i2;
        this.right -= i3;
        this.bottom -= i4;
    }

    public boolean contains(int i, int i2) {
        return this.left < this.right && this.top < this.bottom && i >= this.left && i < this.right && i2 >= this.top && i2 < this.bottom;
    }

    public boolean contains(int i, int i2, int i3, int i4) {
        return this.left < this.right && this.top < this.bottom && this.left <= i && this.top <= i2 && this.right >= i3 && this.bottom >= i4;
    }

    public boolean contains(android.graphics.Rect rect) {
        return this.left < this.right && this.top < this.bottom && this.left <= rect.left && this.top <= rect.top && this.right >= rect.right && this.bottom >= rect.bottom;
    }

    public boolean intersect(int i, int i2, int i3, int i4) {
        if (this.left < i3 && i < this.right && this.top < i4 && i2 < this.bottom) {
            if (this.left < i) {
                this.left = i;
            }
            if (this.top < i2) {
                this.top = i2;
            }
            if (this.right > i3) {
                this.right = i3;
            }
            if (this.bottom > i4) {
                this.bottom = i4;
                return true;
            }
            return true;
        }
        return false;
    }

    public boolean intersect(android.graphics.Rect rect) {
        return intersect(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void intersectUnchecked(android.graphics.Rect rect) {
        this.left = java.lang.Math.max(this.left, rect.left);
        this.top = java.lang.Math.max(this.top, rect.top);
        this.right = java.lang.Math.min(this.right, rect.right);
        this.bottom = java.lang.Math.min(this.bottom, rect.bottom);
    }

    public boolean setIntersect(android.graphics.Rect rect, android.graphics.Rect rect2) {
        if (rect.left < rect2.right && rect2.left < rect.right && rect.top < rect2.bottom && rect2.top < rect.bottom) {
            this.left = java.lang.Math.max(rect.left, rect2.left);
            this.top = java.lang.Math.max(rect.top, rect2.top);
            this.right = java.lang.Math.min(rect.right, rect2.right);
            this.bottom = java.lang.Math.min(rect.bottom, rect2.bottom);
            return true;
        }
        return false;
    }

    public boolean intersects(int i, int i2, int i3, int i4) {
        return this.left < i3 && i < this.right && this.top < i4 && i2 < this.bottom;
    }

    public static boolean intersects(android.graphics.Rect rect, android.graphics.Rect rect2) {
        return rect.left < rect2.right && rect2.left < rect.right && rect.top < rect2.bottom && rect2.top < rect.bottom;
    }

    public void union(int i, int i2, int i3, int i4) {
        if (i < i3 && i2 < i4) {
            if (this.left < this.right && this.top < this.bottom) {
                if (this.left > i) {
                    this.left = i;
                }
                if (this.top > i2) {
                    this.top = i2;
                }
                if (this.right < i3) {
                    this.right = i3;
                }
                if (this.bottom < i4) {
                    this.bottom = i4;
                    return;
                }
                return;
            }
            this.left = i;
            this.top = i2;
            this.right = i3;
            this.bottom = i4;
        }
    }

    public void union(android.graphics.Rect rect) {
        union(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void union(int i, int i2) {
        if (i < this.left) {
            this.left = i;
        } else if (i > this.right) {
            this.right = i;
        }
        if (i2 < this.top) {
            this.top = i2;
        } else if (i2 > this.bottom) {
            this.bottom = i2;
        }
    }

    public void sort() {
        if (this.left > this.right) {
            int i = this.left;
            this.left = this.right;
            this.right = i;
        }
        if (this.top > this.bottom) {
            int i2 = this.top;
            this.top = this.bottom;
            this.bottom = i2;
        }
    }

    public void splitVertically(android.graphics.Rect... rectArr) {
        int length = rectArr.length;
        int width = width() / length;
        for (int i = 0; i < length; i++) {
            android.graphics.Rect rect = rectArr[i];
            rect.left = this.left + (width * i);
            rect.top = this.top;
            rect.right = rect.left + width;
            rect.bottom = this.bottom;
        }
    }

    public void splitHorizontally(android.graphics.Rect... rectArr) {
        int length = rectArr.length;
        int height = height() / length;
        for (int i = 0; i < length; i++) {
            android.graphics.Rect rect = rectArr[i];
            rect.left = this.left;
            rect.top = this.top + (height * i);
            rect.right = this.right;
            rect.bottom = rect.top + height;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.left);
        parcel.writeInt(this.top);
        parcel.writeInt(this.right);
        parcel.writeInt(this.bottom);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.left = parcel.readInt();
        this.top = parcel.readInt();
        this.right = parcel.readInt();
        this.bottom = parcel.readInt();
    }

    public void scale(float f) {
        if (f != 1.0f) {
            this.left = (int) ((this.left * f) + 0.5f);
            this.top = (int) ((this.top * f) + 0.5f);
            this.right = (int) ((this.right * f) + 0.5f);
            this.bottom = (int) ((this.bottom * f) + 0.5f);
        }
    }
}
