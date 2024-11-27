package android.content;

/* loaded from: classes.dex */
public final class ContentValues implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.ContentValues> CREATOR = new android.os.Parcelable.Creator<android.content.ContentValues>() { // from class: android.content.ContentValues.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ContentValues createFromParcel(android.os.Parcel parcel) {
            return new android.content.ContentValues(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ContentValues[] newArray(int i) {
            return new android.content.ContentValues[i];
        }
    };
    public static final java.lang.String TAG = "ContentValues";
    private final android.util.ArrayMap<java.lang.String, java.lang.Object> mMap;

    @java.lang.Deprecated
    private java.util.HashMap<java.lang.String, java.lang.Object> mValues;

    public ContentValues() {
        this.mMap = new android.util.ArrayMap<>();
    }

    public ContentValues(int i) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        this.mMap = new android.util.ArrayMap<>(i);
    }

    public ContentValues(android.content.ContentValues contentValues) {
        java.util.Objects.requireNonNull(contentValues);
        this.mMap = new android.util.ArrayMap<>(contentValues.mMap);
    }

    @java.lang.Deprecated
    private ContentValues(java.util.HashMap<java.lang.String, java.lang.Object> hashMap) {
        this.mMap = new android.util.ArrayMap<>();
        this.mMap.putAll(hashMap);
    }

    private ContentValues(android.os.Parcel parcel) {
        this.mMap = new android.util.ArrayMap<>(parcel.readInt());
        parcel.readArrayMap(this.mMap, null);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.content.ContentValues)) {
            return false;
        }
        return this.mMap.equals(((android.content.ContentValues) obj).mMap);
    }

    public android.util.ArrayMap<java.lang.String, java.lang.Object> getValues() {
        return this.mMap;
    }

    public int hashCode() {
        return this.mMap.hashCode();
    }

    public void put(java.lang.String str, java.lang.String str2) {
        this.mMap.put(str, str2);
    }

    public void putAll(android.content.ContentValues contentValues) {
        this.mMap.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends java.lang.Object>) contentValues.mMap);
    }

    public void put(java.lang.String str, java.lang.Byte b) {
        this.mMap.put(str, b);
    }

    public void put(java.lang.String str, java.lang.Short sh) {
        this.mMap.put(str, sh);
    }

    public void put(java.lang.String str, java.lang.Integer num) {
        this.mMap.put(str, num);
    }

    public void put(java.lang.String str, java.lang.Long l) {
        this.mMap.put(str, l);
    }

    public void put(java.lang.String str, java.lang.Float f) {
        this.mMap.put(str, f);
    }

    public void put(java.lang.String str, java.lang.Double d) {
        this.mMap.put(str, d);
    }

    public void put(java.lang.String str, java.lang.Boolean bool) {
        this.mMap.put(str, bool);
    }

    public void put(java.lang.String str, byte[] bArr) {
        this.mMap.put(str, bArr);
    }

    public void putNull(java.lang.String str) {
        this.mMap.put(str, null);
    }

    public void putObject(java.lang.String str, java.lang.Object obj) {
        if (obj == null) {
            putNull(str);
            return;
        }
        if (obj instanceof java.lang.String) {
            put(str, (java.lang.String) obj);
            return;
        }
        if (obj instanceof java.lang.Byte) {
            put(str, (java.lang.Byte) obj);
            return;
        }
        if (obj instanceof java.lang.Short) {
            put(str, (java.lang.Short) obj);
            return;
        }
        if (obj instanceof java.lang.Integer) {
            put(str, (java.lang.Integer) obj);
            return;
        }
        if (obj instanceof java.lang.Long) {
            put(str, (java.lang.Long) obj);
            return;
        }
        if (obj instanceof java.lang.Float) {
            put(str, (java.lang.Float) obj);
            return;
        }
        if (obj instanceof java.lang.Double) {
            put(str, (java.lang.Double) obj);
        } else if (obj instanceof java.lang.Boolean) {
            put(str, (java.lang.Boolean) obj);
        } else {
            if (obj instanceof byte[]) {
                put(str, (byte[]) obj);
                return;
            }
            throw new java.lang.IllegalArgumentException("Unsupported type " + obj.getClass());
        }
    }

    public int size() {
        return this.mMap.size();
    }

    public boolean isEmpty() {
        return this.mMap.isEmpty();
    }

    public void remove(java.lang.String str) {
        this.mMap.remove(str);
    }

    public void clear() {
        this.mMap.clear();
    }

    public boolean containsKey(java.lang.String str) {
        return this.mMap.containsKey(str);
    }

    public java.lang.Object get(java.lang.String str) {
        return this.mMap.get(str);
    }

    public java.lang.String getAsString(java.lang.String str) {
        java.lang.Object obj = this.mMap.get(str);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public java.lang.Long getAsLong(java.lang.String str) {
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return java.lang.Long.valueOf(((java.lang.Number) obj).longValue());
        } catch (java.lang.ClassCastException e) {
            if (obj instanceof java.lang.CharSequence) {
                try {
                    return java.lang.Long.valueOf(obj.toString());
                } catch (java.lang.NumberFormatException e2) {
                    android.util.Log.e(TAG, "Cannot parse Long value for " + obj + " at key " + str);
                    return null;
                }
            }
            android.util.Log.e(TAG, "Cannot cast value for " + str + " to a Long: " + obj, e);
            return null;
        }
    }

    public java.lang.Integer getAsInteger(java.lang.String str) {
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return java.lang.Integer.valueOf(((java.lang.Number) obj).intValue());
        } catch (java.lang.ClassCastException e) {
            if (obj instanceof java.lang.CharSequence) {
                try {
                    return java.lang.Integer.valueOf(obj.toString());
                } catch (java.lang.NumberFormatException e2) {
                    android.util.Log.e(TAG, "Cannot parse Integer value for " + obj + " at key " + str);
                    return null;
                }
            }
            android.util.Log.e(TAG, "Cannot cast value for " + str + " to a Integer: " + obj, e);
            return null;
        }
    }

    public java.lang.Short getAsShort(java.lang.String str) {
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return java.lang.Short.valueOf(((java.lang.Number) obj).shortValue());
        } catch (java.lang.ClassCastException e) {
            if (obj instanceof java.lang.CharSequence) {
                try {
                    return java.lang.Short.valueOf(obj.toString());
                } catch (java.lang.NumberFormatException e2) {
                    android.util.Log.e(TAG, "Cannot parse Short value for " + obj + " at key " + str);
                    return null;
                }
            }
            android.util.Log.e(TAG, "Cannot cast value for " + str + " to a Short: " + obj, e);
            return null;
        }
    }

    public java.lang.Byte getAsByte(java.lang.String str) {
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return java.lang.Byte.valueOf(((java.lang.Number) obj).byteValue());
        } catch (java.lang.ClassCastException e) {
            if (obj instanceof java.lang.CharSequence) {
                try {
                    return java.lang.Byte.valueOf(obj.toString());
                } catch (java.lang.NumberFormatException e2) {
                    android.util.Log.e(TAG, "Cannot parse Byte value for " + obj + " at key " + str);
                    return null;
                }
            }
            android.util.Log.e(TAG, "Cannot cast value for " + str + " to a Byte: " + obj, e);
            return null;
        }
    }

    public java.lang.Double getAsDouble(java.lang.String str) {
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return java.lang.Double.valueOf(((java.lang.Number) obj).doubleValue());
        } catch (java.lang.ClassCastException e) {
            if (obj instanceof java.lang.CharSequence) {
                try {
                    return java.lang.Double.valueOf(obj.toString());
                } catch (java.lang.NumberFormatException e2) {
                    android.util.Log.e(TAG, "Cannot parse Double value for " + obj + " at key " + str);
                    return null;
                }
            }
            android.util.Log.e(TAG, "Cannot cast value for " + str + " to a Double: " + obj, e);
            return null;
        }
    }

    public java.lang.Float getAsFloat(java.lang.String str) {
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return java.lang.Float.valueOf(((java.lang.Number) obj).floatValue());
        } catch (java.lang.ClassCastException e) {
            if (obj instanceof java.lang.CharSequence) {
                try {
                    return java.lang.Float.valueOf(obj.toString());
                } catch (java.lang.NumberFormatException e2) {
                    android.util.Log.e(TAG, "Cannot parse Float value for " + obj + " at key " + str);
                    return null;
                }
            }
            android.util.Log.e(TAG, "Cannot cast value for " + str + " to a Float: " + obj, e);
            return null;
        }
    }

    public java.lang.Boolean getAsBoolean(java.lang.String str) {
        java.lang.Object obj = this.mMap.get(str);
        try {
            return (java.lang.Boolean) obj;
        } catch (java.lang.ClassCastException e) {
            if (obj instanceof java.lang.CharSequence) {
                return java.lang.Boolean.valueOf(java.lang.Boolean.valueOf(obj.toString()).booleanValue() || "1".equals(obj));
            }
            if (obj instanceof java.lang.Number) {
                return java.lang.Boolean.valueOf(((java.lang.Number) obj).intValue() != 0);
            }
            android.util.Log.e(TAG, "Cannot cast value for " + str + " to a Boolean: " + obj, e);
            return null;
        }
    }

    public byte[] getAsByteArray(java.lang.String str) {
        java.lang.Object obj = this.mMap.get(str);
        if (obj instanceof byte[]) {
            return (byte[]) obj;
        }
        return null;
    }

    public java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.Object>> valueSet() {
        return this.mMap.entrySet();
    }

    public java.util.Set<java.lang.String> keySet() {
        return this.mMap.keySet();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mMap.size());
        parcel.writeArrayMap(this.mMap);
    }

    @java.lang.Deprecated
    public void putStringArrayList(java.lang.String str, java.util.ArrayList<java.lang.String> arrayList) {
        this.mMap.put(str, arrayList);
    }

    @java.lang.Deprecated
    public java.util.ArrayList<java.lang.String> getStringArrayList(java.lang.String str) {
        return (java.util.ArrayList) this.mMap.get(str);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (java.lang.String str : this.mMap.keySet()) {
            java.lang.String asString = getAsString(str);
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(str + "=" + asString);
        }
        return sb.toString();
    }

    public static boolean isSupportedValue(java.lang.Object obj) {
        if (obj == null || (obj instanceof java.lang.String) || (obj instanceof java.lang.Byte) || (obj instanceof java.lang.Short) || (obj instanceof java.lang.Integer) || (obj instanceof java.lang.Long) || (obj instanceof java.lang.Float) || (obj instanceof java.lang.Double) || (obj instanceof java.lang.Boolean) || (obj instanceof byte[])) {
            return true;
        }
        return false;
    }
}
