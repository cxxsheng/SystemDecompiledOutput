package android.content.pm;

/* loaded from: classes.dex */
public class ResolveInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.ResolveInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.ResolveInfo>() { // from class: android.content.pm.ResolveInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ResolveInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.ResolveInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ResolveInfo[] newArray(int i) {
            return new android.content.pm.ResolveInfo[i];
        }
    };
    private static final java.lang.String INTENT_FORWARDER_ACTIVITY = "com.android.internal.app.IntentForwarderActivity";
    private static final java.lang.String TAG = "ResolveInfo";
    public android.content.pm.ActivityInfo activityInfo;
    public android.content.pm.AuxiliaryResolveInfo auxiliaryInfo;
    public android.content.IntentFilter filter;

    @android.annotation.SystemApi
    public boolean handleAllWebDataURI;
    public int icon;
    public int iconResourceId;
    public boolean isDefault;
    public boolean isInstantAppAvailable;
    public int labelRes;
    private final boolean mAutoResolutionAllowed;
    public int match;
    public boolean noResourceId;
    public java.lang.CharSequence nonLocalizedLabel;
    public int preferredOrder;
    public int priority;
    public android.content.pm.ProviderInfo providerInfo;
    public java.lang.String resolvePackageName;
    public android.content.pm.ServiceInfo serviceInfo;
    public int specificIndex;
    public boolean system;
    public int targetUserId;
    public android.os.UserHandle userHandle;

    public android.content.pm.ComponentInfo getComponentInfo() {
        if (this.activityInfo != null) {
            return this.activityInfo;
        }
        if (this.serviceInfo != null) {
            return this.serviceInfo;
        }
        if (this.providerInfo != null) {
            return this.providerInfo;
        }
        throw new java.lang.IllegalStateException("Missing ComponentInfo!");
    }

    public java.lang.CharSequence loadLabel(android.content.pm.PackageManager packageManager) {
        java.lang.CharSequence text;
        java.lang.CharSequence text2;
        if (this.nonLocalizedLabel != null) {
            return this.nonLocalizedLabel;
        }
        java.util.Objects.requireNonNull(packageManager);
        if (this.resolvePackageName != null && this.labelRes != 0 && (text2 = packageManager.getText(this.resolvePackageName, this.labelRes, null)) != null) {
            return text2.toString().trim();
        }
        android.content.pm.ComponentInfo componentInfo = getComponentInfo();
        android.content.pm.ApplicationInfo applicationInfo = componentInfo.applicationInfo;
        if (this.labelRes != 0 && (text = packageManager.getText(componentInfo.packageName, this.labelRes, applicationInfo)) != null) {
            return text.toString().trim();
        }
        java.lang.CharSequence loadLabel = componentInfo.loadLabel(packageManager);
        return loadLabel != null ? loadLabel.toString().trim() : loadLabel;
    }

    public int resolveLabelResId() {
        if (this.labelRes != 0) {
            return this.labelRes;
        }
        android.content.pm.ComponentInfo componentInfo = getComponentInfo();
        if (componentInfo.labelRes != 0) {
            return componentInfo.labelRes;
        }
        return componentInfo.applicationInfo.labelRes;
    }

    public int resolveIconResId() {
        if (this.icon != 0) {
            return this.icon;
        }
        android.content.pm.ComponentInfo componentInfo = getComponentInfo();
        if (componentInfo.icon != 0) {
            return componentInfo.icon;
        }
        return componentInfo.applicationInfo.icon;
    }

    public android.graphics.drawable.Drawable loadIcon(android.content.pm.PackageManager packageManager) {
        android.graphics.drawable.Drawable drawable = null;
        if (this.resolvePackageName != null && this.iconResourceId != 0) {
            drawable = packageManager.getDrawable(this.resolvePackageName, this.iconResourceId, null);
        }
        android.content.pm.ComponentInfo componentInfo = getComponentInfo();
        if (drawable == null && this.iconResourceId != 0) {
            drawable = packageManager.getDrawable(componentInfo.packageName, this.iconResourceId, componentInfo.applicationInfo);
        }
        if (drawable != null) {
            return packageManager.getUserBadgedIcon(drawable, new android.os.UserHandle(packageManager.getUserId()));
        }
        return componentInfo.loadIcon(packageManager);
    }

    final int getIconResourceInternal() {
        if (this.iconResourceId != 0) {
            return this.iconResourceId;
        }
        android.content.pm.ComponentInfo componentInfo = getComponentInfo();
        if (componentInfo != null) {
            return componentInfo.getIconResource();
        }
        return 0;
    }

    public final int getIconResource() {
        if (this.noResourceId) {
            return 0;
        }
        return getIconResourceInternal();
    }

    public void dump(android.util.Printer printer, java.lang.String str) {
        dump(printer, str, 3);
    }

    public void dump(android.util.Printer printer, java.lang.String str, int i) {
        if (this.filter != null) {
            printer.println(str + "Filter:");
            this.filter.dump(printer, str + "  ");
        }
        printer.println(str + "priority=" + this.priority + " preferredOrder=" + this.preferredOrder + " match=0x" + java.lang.Integer.toHexString(this.match) + " specificIndex=" + this.specificIndex + " isDefault=" + this.isDefault);
        if (this.resolvePackageName != null) {
            printer.println(str + "resolvePackageName=" + this.resolvePackageName);
        }
        if (this.labelRes != 0 || this.nonLocalizedLabel != null || this.icon != 0) {
            printer.println(str + "labelRes=0x" + java.lang.Integer.toHexString(this.labelRes) + " nonLocalizedLabel=" + ((java.lang.Object) this.nonLocalizedLabel) + " icon=0x" + java.lang.Integer.toHexString(this.icon));
        }
        if (this.activityInfo != null) {
            printer.println(str + "ActivityInfo:");
            this.activityInfo.dump(printer, str + "  ", i);
        } else if (this.serviceInfo != null) {
            printer.println(str + "ServiceInfo:");
            this.serviceInfo.dump(printer, str + "  ", i);
        } else if (this.providerInfo != null) {
            printer.println(str + "ProviderInfo:");
            this.providerInfo.dump(printer, str + "  ", i);
        }
    }

    public boolean isCrossProfileIntentForwarderActivity() {
        return this.activityInfo != null && INTENT_FORWARDER_ACTIVITY.equals(this.activityInfo.targetActivity);
    }

    public boolean isAutoResolutionAllowed() {
        return this.mAutoResolutionAllowed;
    }

    public ResolveInfo() {
        this.specificIndex = -1;
        this.targetUserId = -2;
        this.mAutoResolutionAllowed = false;
    }

    public ResolveInfo(boolean z) {
        this.specificIndex = -1;
        this.targetUserId = -2;
        this.mAutoResolutionAllowed = z;
    }

    public ResolveInfo(android.content.pm.ResolveInfo resolveInfo) {
        this.specificIndex = -1;
        this.activityInfo = resolveInfo.activityInfo;
        this.serviceInfo = resolveInfo.serviceInfo;
        this.providerInfo = resolveInfo.providerInfo;
        this.filter = resolveInfo.filter;
        this.priority = resolveInfo.priority;
        this.preferredOrder = resolveInfo.preferredOrder;
        this.match = resolveInfo.match;
        this.specificIndex = resolveInfo.specificIndex;
        this.labelRes = resolveInfo.labelRes;
        this.nonLocalizedLabel = resolveInfo.nonLocalizedLabel;
        this.icon = resolveInfo.icon;
        this.resolvePackageName = resolveInfo.resolvePackageName;
        this.noResourceId = resolveInfo.noResourceId;
        this.iconResourceId = resolveInfo.iconResourceId;
        this.system = resolveInfo.system;
        this.targetUserId = resolveInfo.targetUserId;
        this.handleAllWebDataURI = resolveInfo.handleAllWebDataURI;
        this.mAutoResolutionAllowed = resolveInfo.mAutoResolutionAllowed;
        this.isInstantAppAvailable = resolveInfo.isInstantAppAvailable;
        this.userHandle = resolveInfo.userHandle;
    }

    public java.lang.String toString() {
        android.content.pm.ComponentInfo componentInfo = getComponentInfo();
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("ResolveInfo{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        android.content.ComponentName.appendShortString(sb, componentInfo.packageName, componentInfo.name);
        if (this.priority != 0) {
            sb.append(" p=");
            sb.append(this.priority);
        }
        if (this.preferredOrder != 0) {
            sb.append(" o=");
            sb.append(this.preferredOrder);
        }
        sb.append(" m=0x");
        sb.append(java.lang.Integer.toHexString(this.match));
        if (this.targetUserId != -2) {
            sb.append(" targetUserId=");
            sb.append(this.targetUserId);
        }
        sb.append(" userHandle=");
        sb.append(this.userHandle);
        sb.append('}');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.activityInfo != null) {
            parcel.writeInt(1);
            this.activityInfo.writeToParcel(parcel, i);
        } else if (this.serviceInfo != null) {
            parcel.writeInt(2);
            this.serviceInfo.writeToParcel(parcel, i);
        } else if (this.providerInfo != null) {
            parcel.writeInt(3);
            this.providerInfo.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.filter != null) {
            parcel.writeInt(1);
            this.filter.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.priority);
        parcel.writeInt(this.preferredOrder);
        parcel.writeInt(this.match);
        parcel.writeInt(this.specificIndex);
        parcel.writeInt(this.labelRes);
        android.text.TextUtils.writeToParcel(this.nonLocalizedLabel, parcel, i);
        parcel.writeInt(this.icon);
        parcel.writeString8(this.resolvePackageName);
        parcel.writeInt(this.targetUserId);
        parcel.writeInt(this.system ? 1 : 0);
        parcel.writeInt(this.noResourceId ? 1 : 0);
        parcel.writeInt(this.iconResourceId);
        parcel.writeInt(this.handleAllWebDataURI ? 1 : 0);
        parcel.writeInt(this.mAutoResolutionAllowed ? 1 : 0);
        parcel.writeInt(this.isInstantAppAvailable ? 1 : 0);
        parcel.writeInt(this.userHandle != null ? this.userHandle.getIdentifier() : -2);
    }

    private ResolveInfo(android.os.Parcel parcel) {
        this.specificIndex = -1;
        this.activityInfo = null;
        this.serviceInfo = null;
        this.providerInfo = null;
        switch (parcel.readInt()) {
            case 1:
                this.activityInfo = android.content.pm.ActivityInfo.CREATOR.createFromParcel(parcel);
                break;
            case 2:
                this.serviceInfo = android.content.pm.ServiceInfo.CREATOR.createFromParcel(parcel);
                break;
            case 3:
                this.providerInfo = android.content.pm.ProviderInfo.CREATOR.createFromParcel(parcel);
                break;
            default:
                android.util.Slog.w(TAG, "Missing ComponentInfo!");
                break;
        }
        if (parcel.readInt() != 0) {
            this.filter = android.content.IntentFilter.CREATOR.createFromParcel(parcel);
        }
        this.priority = parcel.readInt();
        this.preferredOrder = parcel.readInt();
        this.match = parcel.readInt();
        this.specificIndex = parcel.readInt();
        this.labelRes = parcel.readInt();
        this.nonLocalizedLabel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.icon = parcel.readInt();
        this.resolvePackageName = parcel.readString8();
        this.targetUserId = parcel.readInt();
        this.system = parcel.readInt() != 0;
        this.noResourceId = parcel.readInt() != 0;
        this.iconResourceId = parcel.readInt();
        this.handleAllWebDataURI = parcel.readInt() != 0;
        this.mAutoResolutionAllowed = parcel.readInt() != 0;
        this.isInstantAppAvailable = parcel.readInt() != 0;
        int readInt = parcel.readInt();
        if (readInt != -2) {
            this.userHandle = android.os.UserHandle.of(readInt);
        }
    }

    public static class DisplayNameComparator implements java.util.Comparator<android.content.pm.ResolveInfo> {
        private final java.text.Collator mCollator = java.text.Collator.getInstance();
        private final android.content.pm.PackageManager mPM;

        public DisplayNameComparator(android.content.pm.PackageManager packageManager) {
            this.mPM = packageManager;
            this.mCollator.setStrength(0);
        }

        @Override // java.util.Comparator
        public final int compare(android.content.pm.ResolveInfo resolveInfo, android.content.pm.ResolveInfo resolveInfo2) {
            if (resolveInfo.targetUserId != -2) {
                return 1;
            }
            if (resolveInfo2.targetUserId != -2) {
                return -1;
            }
            java.lang.CharSequence loadLabel = resolveInfo.loadLabel(this.mPM);
            if (loadLabel == null) {
                loadLabel = resolveInfo.activityInfo.name;
            }
            java.lang.CharSequence loadLabel2 = resolveInfo2.loadLabel(this.mPM);
            if (loadLabel2 == null) {
                loadLabel2 = resolveInfo2.activityInfo.name;
            }
            return this.mCollator.compare(loadLabel.toString(), loadLabel2.toString());
        }
    }
}
