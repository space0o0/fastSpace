package space.learning.compiler;

import javax.lang.model.element.Element;

/**
 * 每个类有多种不同权限的请求，一个权限的请求包含4种不同的注解，在此记录
 */
public class PermissionAnnoInfo {

    private Element annoCheckPermission;
    private Element annoShowRationale;
    private Element annoPermissionDenied;
    private Element annoNeverAskAgain;

    public PermissionAnnoInfo(Element annoCheckPermission, Element annoShowRationale, Element annoPermissionDenied, Element annoNeverAskAgain) {
        this.annoCheckPermission = annoCheckPermission;
        this.annoShowRationale = annoShowRationale;
        this.annoPermissionDenied = annoPermissionDenied;
        this.annoNeverAskAgain = annoNeverAskAgain;
    }

    public Element getAnnoCheckPermission() {
        return annoCheckPermission;
    }

    public void setAnnoCheckPermission(Element annoCheckPermission) {
        this.annoCheckPermission = annoCheckPermission;
    }

    public Element getAnnoShowRationale() {
        return annoShowRationale;
    }

    public void setAnnoShowRationale(Element annoShowRationale) {
        this.annoShowRationale = annoShowRationale;
    }

    public Element getAnnoPermissionDenied() {
        return annoPermissionDenied;
    }

    public void setAnnoPermissionDenied(Element annoPermissionDenied) {
        this.annoPermissionDenied = annoPermissionDenied;
    }

    public Element getAnnoNeverAskAgain() {
        return annoNeverAskAgain;
    }

    public void setAnnoNeverAskAgain(Element annoNeverAskAgain) {
        this.annoNeverAskAgain = annoNeverAskAgain;
    }
}
