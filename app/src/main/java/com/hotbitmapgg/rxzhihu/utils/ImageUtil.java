package com.hotbitmapgg.rxzhihu.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ImageUtil
{

    private static final  int REQUEST_CODE_ASK_PERMISSIONS = 123;

    public static Observable<Uri> saveImageAndGetPathObservable(final Context context, final String url, final String title)
    {

        return Observable.create(new Observable.OnSubscribe<Bitmap>()
        {

            @Override
            public void call(Subscriber<? super Bitmap> subscriber)
            {

                Bitmap bitmap = null;
                try
                {

                    bitmap = Picasso.with(context).load(url).get();
                } catch (IOException e)
                {
                    subscriber.onError(e);
                }
                if (bitmap == null)
                {
                    subscriber.onError(new Exception("无法下载到图片"));
                }
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        }).flatMap(new Func1<Bitmap,Observable<Uri>>()
        {

            @Override
            public Observable<Uri> call(Bitmap bitmap)
            {

                //增加6.0保存图片动态权限 晚点再慢慢优化 这样写太渣了
                if (Build.VERSION.SDK_INT >= 23)
                {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);

                    } else
                    {
                        File appDir = new File(Environment.getExternalStorageDirectory(), "zhiliao");
                        if (!appDir.exists())
                        {
                            LogUtil.all("创建文件夹");
                            appDir.mkdir();
                        }
                        String fileName = title.replace('/', '-') + ".jpg";
                        LogUtil.all(fileName);
                        File file = new File(appDir, fileName);
                        try
                        {
                            FileOutputStream fos = new FileOutputStream(file);
                            assert bitmap != null;
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (IOException e)
                        {
                            LogUtil.all("图片保存失败" + e.getMessage());
                            e.printStackTrace();
                        }

                        Uri uri = Uri.fromFile(file);
                        // 通知图库更新
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        context.sendBroadcast(scannerIntent);
                        return Observable.just(uri);
                    }
                } else
                {
                    File appDir = new File(Environment.getExternalStorageDirectory(), "zhiliao");
                    if (!appDir.exists())
                    {
                        LogUtil.all("创建文件夹");
                        appDir.mkdir();
                    }
                    String fileName = title.replace('/', '-') + ".jpg";
                    LogUtil.all(fileName);
                    File file = new File(appDir, fileName);
                    try
                    {
                        FileOutputStream fos = new FileOutputStream(file);
                        assert bitmap != null;
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                    } catch (IOException e)
                    {
                        LogUtil.all("图片保存失败" + e.getMessage());
                        e.printStackTrace();
                    }

                    Uri uri = Uri.fromFile(file);
                    // 通知图库更新
                    Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                    context.sendBroadcast(scannerIntent);
                    return Observable.just(uri);
                }

            return null;

            }
        }).subscribeOn(Schedulers.io());
    }
}
