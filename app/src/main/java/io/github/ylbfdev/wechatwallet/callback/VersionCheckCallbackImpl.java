package io.github.ylbfdev.wechatwallet.callback;

import android.content.Context;
import android.util.Log;

import im.fir.sdk.VersionCheckCallback;
import io.github.ylbfdev.wechatwallet.App;
import io.github.ylbfdev.wechatwallet.bean.VersionJson;
import io.github.ylbfdev.wechatwallet.utils.FastJsonTools;
import io.github.ylbfdev.wechatwallet.utils.Tools;

/**
 * 检测新版本接口实现
 * Created by ylbf_ on 2016/9/5.
 */
public class VersionCheckCallbackImpl extends VersionCheckCallback {
    private Context context;

    /**
     * @param context activity的 context
     */
    public VersionCheckCallbackImpl(Context context) {
        this.context = context;
    }

    @Override
    public void onSuccess(String versionJson) {
        super.onSuccess(versionJson);
        VersionJson version = FastJsonTools.getDataObject(versionJson, VersionJson.class);
        if (App.getmLocalVersionCode() < Integer.parseInt(version.getVersion())) {
            StringBuilder content = new StringBuilder();
            content.append(String.format("应用名称：%s\n", version.getName()));
            content.append(String.format("版本号：%s\n", version.getVersion()));
            content.append(String.format("版本名称：%s\n", version.getVersionShort()));
            content.append(String.format("更新时间：%s\n", Tools.TimeStamp2Date(version.getUpdated_at() + "", "")));
            content.append(String.format("安装包大小：%s\n", Tools.GetFileSize(version.getBinary().getFsize())));
            content.append(String.format("更新日志：\n%s", version.getChangelog()));
            Tools.showUpdateDialog(context, version.getDirect_install_url(), content.toString());
        }
    }

    @Override
    public void onFail(Exception exception) {
        super.onFail(exception);
        Log.i("fir", "check fir.im fail! " + "\n" + exception.getMessage());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }
}
