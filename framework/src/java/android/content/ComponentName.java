package android.content;

/* loaded from: classes.dex */
public final class ComponentName implements android.os.Parcelable, java.lang.Cloneable, java.lang.Comparable<android.content.ComponentName> {
    public static final android.os.Parcelable.Creator<android.content.ComponentName> CREATOR = new android.os.Parcelable.Creator<android.content.ComponentName>() { // from class: android.content.ComponentName.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ComponentName createFromParcel(android.os.Parcel parcel) {
            return new android.content.ComponentName(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ComponentName[] newArray(int i) {
            return new android.content.ComponentName[i];
        }
    };
    private final java.lang.String mClass;
    private final java.lang.String mPackage;

    @java.lang.FunctionalInterface
    public interface WithComponentName {
        android.content.ComponentName getComponentName();
    }

    public static android.content.ComponentName createRelative(java.lang.String str, java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str2)) {
            throw new java.lang.IllegalArgumentException("class name cannot be empty");
        }
        if (str2.charAt(0) == '.') {
            str2 = str + str2;
        }
        return new android.content.ComponentName(str, str2);
    }

    public static android.content.ComponentName createRelative(android.content.Context context, java.lang.String str) {
        return createRelative(context.getPackageName(), str);
    }

    public ComponentName(java.lang.String str, java.lang.String str2) {
        if (str == null) {
            throw new java.lang.NullPointerException("package name is null");
        }
        if (str2 == null) {
            throw new java.lang.NullPointerException("class name is null");
        }
        this.mPackage = str;
        this.mClass = str2;
    }

    public ComponentName(android.content.Context context, java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("class name is null");
        }
        this.mPackage = context.getPackageName();
        this.mClass = str;
    }

    public ComponentName(android.content.Context context, java.lang.Class<?> cls) {
        this.mPackage = context.getPackageName();
        this.mClass = cls.getName();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.content.ComponentName m778clone() {
        return new android.content.ComponentName(this.mPackage, this.mClass);
    }

    public java.lang.String getPackageName() {
        return this.mPackage;
    }

    public java.lang.String getClassName() {
        return this.mClass;
    }

    public java.lang.String getShortClassName() {
        int length;
        int length2;
        if (this.mClass.startsWith(this.mPackage) && (length2 = this.mClass.length()) > (length = this.mPackage.length()) && this.mClass.charAt(length) == '.') {
            return this.mClass.substring(length, length2);
        }
        return this.mClass;
    }

    private static void appendShortClassName(java.lang.StringBuilder sb, java.lang.String str, java.lang.String str2) {
        int length;
        int length2;
        if (str2.startsWith(str) && (length2 = str2.length()) > (length = str.length()) && str2.charAt(length) == '.') {
            sb.append((java.lang.CharSequence) str2, length, length2);
        } else {
            sb.append(str2);
        }
    }

    private static void printShortClassName(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2) {
        int length;
        int length2;
        if (str2.startsWith(str) && (length2 = str2.length()) > (length = str.length()) && str2.charAt(length) == '.') {
            printWriter.write(str2, length, length2 - length);
        } else {
            printWriter.print(str2);
        }
    }

    public static java.lang.String flattenToShortString(android.content.ComponentName componentName) {
        if (componentName == null) {
            return null;
        }
        return componentName.flattenToShortString();
    }

    public java.lang.String flattenToString() {
        return this.mPackage + "/" + this.mClass;
    }

    public java.lang.String flattenToShortString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(this.mPackage.length() + this.mClass.length());
        appendShortString(sb, this.mPackage, this.mClass);
        return sb.toString();
    }

    public void appendShortString(java.lang.StringBuilder sb) {
        appendShortString(sb, this.mPackage, this.mClass);
    }

    public static void appendShortString(java.lang.StringBuilder sb, java.lang.String str, java.lang.String str2) {
        sb.append(str).append('/');
        appendShortClassName(sb, str, str2);
    }

    public static void printShortString(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2) {
        printWriter.print(str);
        printWriter.print('/');
        printShortClassName(printWriter, str, str2);
    }

    public static android.content.ComponentName unflattenFromString(java.lang.String str) {
        int i;
        int indexOf = str.indexOf(47);
        if (indexOf < 0 || (i = indexOf + 1) >= str.length()) {
            return null;
        }
        java.lang.String substring = str.substring(0, indexOf);
        java.lang.String substring2 = str.substring(i);
        if (substring2.length() > 0 && substring2.charAt(0) == '.') {
            substring2 = substring + substring2;
        }
        return new android.content.ComponentName(substring, substring2);
    }

    public java.lang.String toShortString() {
        return "{" + this.mPackage + "/" + this.mClass + "}";
    }

    public java.lang.String toString() {
        return "ComponentInfo{" + this.mPackage + "/" + this.mClass + "}";
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mPackage);
        protoOutputStream.write(1138166333442L, this.mClass);
        protoOutputStream.end(start);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.content.ComponentName)) {
            return false;
        }
        android.content.ComponentName componentName = (android.content.ComponentName) obj;
        return this.mPackage.equals(componentName.mPackage) && this.mClass.equals(componentName.mClass);
    }

    public int hashCode() {
        return this.mPackage.hashCode() + this.mClass.hashCode();
    }

    @Override // java.lang.Comparable
    public int compareTo(android.content.ComponentName componentName) {
        int compareTo = this.mPackage.compareTo(componentName.mPackage);
        if (compareTo != 0) {
            return compareTo;
        }
        return this.mClass.compareTo(componentName.mClass);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPackage);
        parcel.writeString(this.mClass);
    }

    public static void writeToParcel(android.content.ComponentName componentName, android.os.Parcel parcel) {
        if (componentName != null) {
            componentName.writeToParcel(parcel, 0);
        } else {
            parcel.writeString(null);
        }
    }

    public static android.content.ComponentName readFromParcel(android.os.Parcel parcel) {
        java.lang.String readString = parcel.readString();
        if (readString != null) {
            return new android.content.ComponentName(readString, parcel);
        }
        return null;
    }

    public ComponentName(android.os.Parcel parcel) {
        this.mPackage = parcel.readString();
        if (this.mPackage == null) {
            throw new java.lang.NullPointerException("package name is null");
        }
        this.mClass = parcel.readString();
        if (this.mClass == null) {
            throw new java.lang.NullPointerException("class name is null");
        }
    }

    private ComponentName(java.lang.String str, android.os.Parcel parcel) {
        this.mPackage = str;
        this.mClass = parcel.readString();
    }
}
