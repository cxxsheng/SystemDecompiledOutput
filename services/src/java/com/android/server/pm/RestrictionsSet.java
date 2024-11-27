package com.android.server.pm;

/* loaded from: classes2.dex */
public class RestrictionsSet {
    private static final java.lang.String TAG_RESTRICTIONS = "restrictions";
    private static final java.lang.String TAG_RESTRICTIONS_USER = "restrictions_user";
    private static final java.lang.String USER_ID = "user_id";
    private final android.util.SparseArray<android.os.Bundle> mUserRestrictions = new android.util.SparseArray<>(0);

    public RestrictionsSet() {
    }

    public RestrictionsSet(int i, @android.annotation.NonNull android.os.Bundle bundle) {
        if (bundle.isEmpty()) {
            throw new java.lang.IllegalArgumentException("empty restriction bundle cannot be added.");
        }
        this.mUserRestrictions.put(i, bundle);
    }

    public boolean updateRestrictions(int i, @android.annotation.Nullable android.os.Bundle bundle) {
        if (!(!com.android.server.pm.UserRestrictionsUtils.areEqual(this.mUserRestrictions.get(i), bundle))) {
            return false;
        }
        if (!com.android.server.BundleUtils.isEmpty(bundle)) {
            this.mUserRestrictions.put(i, bundle);
        } else {
            this.mUserRestrictions.delete(i);
        }
        return true;
    }

    public boolean removeRestrictionsForAllUsers(java.lang.String str) {
        boolean z = false;
        for (int i = 0; i < this.mUserRestrictions.size(); i++) {
            android.os.Bundle valueAt = this.mUserRestrictions.valueAt(i);
            if (com.android.server.pm.UserRestrictionsUtils.contains(valueAt, str)) {
                valueAt.remove(str);
                z = true;
            }
        }
        return z;
    }

    public void moveRestriction(@android.annotation.NonNull com.android.server.pm.RestrictionsSet restrictionsSet, java.lang.String str) {
        int i = 0;
        while (i < this.mUserRestrictions.size()) {
            int keyAt = this.mUserRestrictions.keyAt(i);
            android.os.Bundle valueAt = this.mUserRestrictions.valueAt(i);
            if (com.android.server.pm.UserRestrictionsUtils.contains(valueAt, str)) {
                valueAt.remove(str);
                android.os.Bundle restrictions = restrictionsSet.getRestrictions(keyAt);
                if (restrictions == null) {
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putBoolean(str, true);
                    restrictionsSet.updateRestrictions(keyAt, bundle);
                } else {
                    restrictions.putBoolean(str, true);
                }
                if (valueAt.isEmpty()) {
                    this.mUserRestrictions.removeAt(i);
                    i--;
                }
            }
            i++;
        }
    }

    public boolean isEmpty() {
        return this.mUserRestrictions.size() == 0;
    }

    @android.annotation.NonNull
    public android.os.Bundle mergeAll() {
        android.os.Bundle bundle = new android.os.Bundle();
        for (int i = 0; i < this.mUserRestrictions.size(); i++) {
            com.android.server.pm.UserRestrictionsUtils.merge(bundle, this.mUserRestrictions.valueAt(i));
        }
        return bundle;
    }

    @android.annotation.NonNull
    public java.util.List<android.os.UserManager.EnforcingUser> getEnforcingUsers(java.lang.String str, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (getRestrictionsNonNull(i).containsKey(str)) {
            arrayList.add(new android.os.UserManager.EnforcingUser(i, 4));
        }
        if (getRestrictionsNonNull(-1).containsKey(str)) {
            arrayList.add(new android.os.UserManager.EnforcingUser(-1, 2));
        }
        return arrayList;
    }

    @android.annotation.Nullable
    public android.os.Bundle getRestrictions(int i) {
        return this.mUserRestrictions.get(i);
    }

    @android.annotation.NonNull
    public android.os.Bundle getRestrictionsNonNull(int i) {
        return com.android.server.pm.UserRestrictionsUtils.nonNull(this.mUserRestrictions.get(i));
    }

    public boolean remove(int i) {
        boolean contains = this.mUserRestrictions.contains(i);
        this.mUserRestrictions.remove(i);
        return contains;
    }

    public void removeAllRestrictions() {
        this.mUserRestrictions.clear();
    }

    public void writeRestrictions(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull java.lang.String str) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, str);
        for (int i = 0; i < this.mUserRestrictions.size(); i++) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_RESTRICTIONS_USER);
            typedXmlSerializer.attributeInt((java.lang.String) null, USER_ID, this.mUserRestrictions.keyAt(i));
            com.android.server.pm.UserRestrictionsUtils.writeRestrictions(typedXmlSerializer, this.mUserRestrictions.valueAt(i), TAG_RESTRICTIONS);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_RESTRICTIONS_USER);
        }
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    public static com.android.server.pm.RestrictionsSet readRestrictions(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.pm.RestrictionsSet restrictionsSet = new com.android.server.pm.RestrictionsSet();
        int i = 0;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                java.lang.String name = typedXmlPullParser.getName();
                if (next == 3 && str.equals(name)) {
                    return restrictionsSet;
                }
                if (next == 2 && TAG_RESTRICTIONS_USER.equals(name)) {
                    i = typedXmlPullParser.getAttributeInt((java.lang.String) null, USER_ID);
                } else if (next == 2 && TAG_RESTRICTIONS.equals(name)) {
                    restrictionsSet.updateRestrictions(i, com.android.server.pm.UserRestrictionsUtils.readRestrictions(typedXmlPullParser));
                }
            } else {
                throw new org.xmlpull.v1.XmlPullParserException("restrictions cannot be read as xml is malformed.");
            }
        }
    }

    public void dumpRestrictions(java.io.PrintWriter printWriter, java.lang.String str) {
        boolean z = true;
        int i = 0;
        while (i < this.mUserRestrictions.size()) {
            printWriter.println(str + "User Id: " + this.mUserRestrictions.keyAt(i));
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(str);
            sb.append("  ");
            com.android.server.pm.UserRestrictionsUtils.dumpRestrictions(printWriter, sb.toString(), this.mUserRestrictions.valueAt(i));
            i++;
            z = false;
        }
        if (z) {
            printWriter.println(str + "none");
        }
    }

    public android.util.IntArray getUserIds() {
        android.util.IntArray intArray = new android.util.IntArray(this.mUserRestrictions.size());
        for (int i = 0; i < this.mUserRestrictions.size(); i++) {
            intArray.add(this.mUserRestrictions.keyAt(i));
        }
        return intArray;
    }

    public boolean containsKey(int i) {
        return this.mUserRestrictions.contains(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    public int size() {
        return this.mUserRestrictions.size();
    }

    @com.android.internal.annotations.VisibleForTesting
    public int keyAt(int i) {
        return this.mUserRestrictions.keyAt(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    public android.os.Bundle valueAt(int i) {
        return this.mUserRestrictions.valueAt(i);
    }
}
