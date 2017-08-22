package dao.cn.com.talkvip.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import dao.cn.com.talkvip.Constants;
import dao.cn.com.talkvip.R;
import dao.cn.com.talkvip.utils.DebugFlags;
import dao.cn.com.talkvip.utils.SPUtils;
import dao.cn.com.talkvip.widget.ActionSheetDialog;
import okhttp3.Call;

/**
 * @name dao.cn.com.talkvip.view.activity
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/7/12 10:27
 * @change uway
 * @chang 2017/7/12 10:27
 * @class describe
 */

public class HeadImageActivity extends Activity {

    private ImageView mIv_b;
    private TextView mIv_e;
    private TextView mIsave;
    private File tempFile;
    private static final int RESULT_CAPTURE = 100;
    private static final int RESULT_PICK = 101;
    private static final int CROP_PHOTO = 102;
    private ImageView mImageView;
    private RelativeLayout mTxBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_image_activity);
        initHead();
        initView();

        initData(savedInstanceState);
    }



    protected void initHead() {



        mIv_b = (ImageView) findViewById(R.id.iv_back);
        mIv_e = (TextView) findViewById(R.id.tv_edit);
        mIsave = (TextView) findViewById(R.id.tv_saveimg);
        mTxBack = (RelativeLayout) findViewById(R.id.rl_txback);


    }





    protected void initView() {
        mImageView = (ImageView)findViewById(R.id.iv_image);


DebugFlags.logD("图片地址"+SPUtils.getString(HeadImageActivity.this,"hurl",""));
            Glide.with(HeadImageActivity.this).load(Constants.BASE_URL+SPUtils.getString(HeadImageActivity.this,"hurl","")).error(R.mipmap.notx).fitCenter().into(mImageView);



        mTxBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       mIv_e.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        new ActionSheetDialog(HeadImageActivity.this)
                .builder()
                //这样也可以点击其他地方消失窗口
                .setTitle("请选择操作")
                .setCancelable(true)
//                  .setCanceledOnTouchOutside(false)
                .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                requestPermission();
                            }


                        })
                .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(Intent.createChooser(intent, "请选择图片"), RESULT_PICK);
                            }
                        })


                .show();

    }
});





    }
    private void initData(Bundle savedInstanceState) {


        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        }else{
            tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath()+"/clipHeaderLikeQQ/image/"),
                    System.currentTimeMillis() + ".png");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tempFile", tempFile);
    }








    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {
            case RESULT_CAPTURE:
                if (resultCode == RESULT_OK) {
                    starCropPhoto(Uri.fromFile(tempFile));
                }
                break;
            case RESULT_PICK:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();

                    starCropPhoto(uri);
                }

                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {

                    if (intent != null) {
                        setPicToView(intent);
                    }
                }
                break;

            default:
                break;
        }
    }


    /**
     * 打开截图界面
     * @param uri 原图的Uri
     */
    public void starCropPhoto(Uri uri) {

        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipHeaderActivity.class);
        intent.setData(uri);
        intent.putExtra("side_length", 200);//裁剪图片宽高
        startActivityForResult(intent, CROP_PHOTO);

        //调用系统的裁剪
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", crop);
//        intent.putExtra("outputY", crop);
//        intent.putExtra("return-data", true);
//        intent.putExtra("noFaceDetection", true);
//        startActivityForResult(intent, CROP_PHOTO);
    }

    private void setPicToView(Intent picdata) {
        Uri uri = picdata.getData();

        if (uri == null) {
            return;
        }

        mImageView .setImageURI(uri);

    }


    /**
     *
     * @param dirPath
     * @return
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }*/

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                DebugFlags.logD("检查权限已经有了");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                startActivityForResult(intent, RESULT_CAPTURE);


            } else {
                DebugFlags.logD("检查权限已经没了");
                // Permission Denied
                //  displayFrameworkBugMessageAndExit();
              //  Toast.makeText(this, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(HeadImageActivity.this);  //先得到构造器
                builder.setTitle("申请授权"); //设置标题
                builder.setMessage("启用相机需要授权,是否需要前往权限管理页面授权"); //设置内容
                builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
                builder.setPositiveButton("前往授权", new DialogInterface.OnClickListener() { //设置确定按钮
                    @Override
                    public void onClick(DialogInterface dialogs, int which) {

                        getAppDetailSettingIntent(HeadImageActivity.this);

                        dialogs.dismiss(); //关闭dialog

                    }
                });
                builder.setNegativeButton("暂不处理", new DialogInterface.OnClickListener() { //设置取消按钮
                    @Override
                    public void onClick(DialogInterface dialogs, int which) {
                        dialogs.dismiss();

                    }
                });


                //参数都设置完成了，创建并显示出来
                builder.create().show();







            }
        }
    }



    // 以下代码可以跳转到应用详情，可以通过应用详情跳转到权限界面(6.0系统测试可用)
    private void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package",HeadImageActivity.this. getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName",HeadImageActivity.this. getPackageName());
        }
        startActivity(localIntent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
            // 向用户解释为什么需要这个权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(this)
                        .setMessage("申请相机权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请相机权限
                                ActivityCompat.requestPermissions(HeadImageActivity.this,
                                        new String[]{Manifest.permission.CAMERA}, 1);
                            }
                        })
                        .show();
            } else {
                //申请相机权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, 1);
            }
        } else {
          DebugFlags.logD("有权限了");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            startActivityForResult(intent, RESULT_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               DebugFlags.logD("有权限了1");

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                startActivityForResult(intent, RESULT_CAPTURE);

            } else {
                //用户勾选了不再询问
                //提示用户手动打开权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HeadImageActivity.this);  //先得到构造器
                    builder.setTitle("申请授权"); //设置标题
                    builder.setMessage("启用相机需要授权,是否需要前往权限管理页面授权"); //设置内容
                    builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
                    builder.setPositiveButton("前往授权", new DialogInterface.OnClickListener() { //设置确定按钮
                        @Override
                        public void onClick(DialogInterface dialogs, int which) {

                            getAppDetailSettingIntent(HeadImageActivity.this);

                            dialogs.dismiss(); //关闭dialog

                        }
                    });
                    builder.setNegativeButton("暂不处理", new DialogInterface.OnClickListener() { //设置取消按钮
                        @Override
                        public void onClick(DialogInterface dialogs, int which) {
                            dialogs.dismiss();

                        }
                    });


                    //参数都设置完成了，创建并显示出来
                    builder.create().show();



                }
            }
        }
    }


    public  void saveImage(){
        String token= SPUtils.getString(HeadImageActivity.this,"token","");
        if (tempFile!=null){

            OkHttpUtils.post()
                  .addFile("userfile",  System.currentTimeMillis() + ".png", tempFile)

                    .url(Constants.BASE_URL+"/Comment/uploadHead")

                    .addHeader("Authorization", "Bearer" + " " + token)
                    .build()
            .execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {

                }
            });



        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Glide.with(HeadImageActivity.this).load(Constants.BASE_URL+SPUtils.getString(HeadImageActivity.this,"hurl","")).error(R.mipmap.notx).fitCenter().into(mImageView);
    }
}
