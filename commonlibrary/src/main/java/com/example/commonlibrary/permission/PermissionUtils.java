package com.example.commonlibrary.permission;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * description：
 *
 * @author test
 * @date 2020/8/7
 */
public class PermissionUtils {
    private static final String TAG = PermissionUtils.class.getSimpleName();

    private PermissionFragment fragment;

    public PermissionUtils(@NonNull FragmentActivity activity) {
        fragment = getRxPermissionsFragment(activity);
    }

    private PermissionFragment getRxPermissionsFragment(FragmentActivity activity) {
        PermissionFragment fragment = (PermissionFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG);
        boolean isNewInstance = fragment == null;
        if (isNewInstance) {
            fragment = new PermissionFragment();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, TAG)
                    .commit();
            fragmentManager.executePendingTransactions();
        }

        return fragment;
    }

    /**
     * 外部使用 申请权限
     * @param permissions 申请授权的权限
     * @param listener 授权回调的监听
     */
    public void requestPermissions(String[] permissions, PermissionListener listener) {
        fragment.setListener(listener);
        fragment.requestPermissions(permissions);

    }
}
