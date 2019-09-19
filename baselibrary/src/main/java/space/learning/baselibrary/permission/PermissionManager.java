package space.learning.baselibrary.permission;

import org.jetbrains.annotations.NotNull;

public class PermissionManager {

    public static PermissionManager getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        static PermissionManager instance = new PermissionManager();
    }

    public void check(Object object) {



    }

}
