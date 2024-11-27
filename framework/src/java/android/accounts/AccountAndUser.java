package android.accounts;

/* loaded from: classes.dex */
public class AccountAndUser {
    public android.accounts.Account account;
    public int userId;

    public AccountAndUser(android.accounts.Account account, int i) {
        this.account = account;
        this.userId = i;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.accounts.AccountAndUser)) {
            return false;
        }
        android.accounts.AccountAndUser accountAndUser = (android.accounts.AccountAndUser) obj;
        return this.account.equals(accountAndUser.account) && this.userId == accountAndUser.userId;
    }

    public int hashCode() {
        return this.account.hashCode() + this.userId;
    }

    public java.lang.String toString() {
        return this.account.toString() + " u" + this.userId;
    }
}
