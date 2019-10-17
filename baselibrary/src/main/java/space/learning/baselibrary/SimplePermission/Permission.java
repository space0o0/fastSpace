package space.learning.baselibrary.SimplePermission;

public class Permission {

    public String name;
    public boolean granted;
    public boolean shouldShowRequestPermissionRationale;//false: don't ask again

    public Permission(String name, boolean granted, boolean shouldShowRequestPermissionRationale) {
        this.name = name;
        this.granted = granted;
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale;
    }

    public Permission(String name, boolean granted) {
        this(name, granted, false);
    }

    @Override
    public String toString() {

        return "Permission { " + name +
                " /// granted = " + granted +
                " /// shouldShowRequestPermissionRtionale = " + shouldShowRequestPermissionRationale + "}";
    }
}
