package io.github.ylbfdev.wechatwallet.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import io.github.ylbfdev.wechatwallet.R;


/**
 * App 更新
 * Created by ylbf_ on 2016/9/26.
 */

public class DownloadService extends Service {
    private BroadcastReceiver receiver;
    private Uri downloadFileUri;
    private String mimeType;
    private long downLoadId;
    private DownloadManager manager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mimeType = manager.getMimeTypeForDownloadedFile(downLoadId);
                downloadFileUri = manager.getUriForDownloadedFile(downLoadId);
                if ("application/vnd.android.package-archive".equals(mimeType) && downloadFileUri != null) {
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                    install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(install);
                }
                stopSelf();
            }
        };
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        String url = intent.getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            downLoadApk(this, url);
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    /**
     * 调用系统的下载管理器
     *
     * @param context
     * @param url     下载地址
     */
    public void downLoadApk(Context context, String url) {
        /**
         * 在这里返回的 reference 变量是系统为当前的下载请求分配的一个唯一的ID，
         * 我们可以通过这个ID重新获得这个下载任务，进行一些自己想要进行的操作
         * 或者查询下载的状态以及取消下载等等
         */
        Uri uri = Uri.parse(url);        //下载连接
        manager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);  //得到系统的下载管理
        DownloadManager.Request requestApk = new DownloadManager.Request(uri);  //得到连接请求对象
        requestApk.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);   //指定在什么网络下进行下载，这里我指定了WIFI网络
        requestApk.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, String.format("%s.apk", context.getString(R.string.app_name)));//制定下载文件的保存路径
        requestApk.setMimeType("application/vnd.android.package-archive");
        requestApk.setVisibleInDownloadsUi(true);  //设置显示下载界面
        requestApk.allowScanningByMediaScanner();  //表示允许MediaScanner扫描到这个文件，默认不允许。
        requestApk.setTitle(String.format("%s", context.getString(R.string.app_name)));      //设置下载中通知栏的提示消息
        requestApk.setDescription(String.format("%s更新", context.getString(R.string.app_name)));//设置设置下载中通知栏提示的介绍
        downLoadId = manager.enqueue(requestApk);//启动下载,该方法返回系统为当前下载请求分配的一个唯一的ID
    }
}