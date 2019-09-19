package space.learning.baselibrary.permission.listener;

/**
 * @author space
 * <p>
 * 这是在显示申请权限的ui中，监听用户是否同意再次给予权限
 * <p>
 * allow：用户看了申请理由后，同意-> 可以再次向申请权限 ，requestPermission
 * denied：用户看了理由，拒绝。
 */
public interface ShowRationalListener {

    /**
     * 用户同意申请权限的动作
     */
    void allow();

    /**
     * 用户拒绝申请权限的动作
     */
    void denied();
}
