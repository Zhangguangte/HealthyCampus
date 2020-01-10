package com.example.HealthyCampus.module.Find.Recipes.ClassifierCamera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.BaiduErrorVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.DishVo;
import com.example.HealthyCampus.common.network.vo.IngredientVo;
import com.example.HealthyCampus.common.utils.CameraUtils;
import com.example.HealthyCampus.common.utils.DialogUtil;
import com.example.HealthyCampus.common.utils.FunctionUtils;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.PictureUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.custom_dialog.PictureDialog;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Find.Recipes.ClassifierCamera.ClassifyResult.ClassifyResultActivity;
import com.example.HealthyCampus.module.Find.Recipes.Customization.activity.Detail.RecipesDetailActivity;
import com.example.HealthyCampus.module.Message.Chat.imageselect.ImageSelectorActivity;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static com.example.HealthyCampus.common.constants.ConstantValues.PICK_PHOTO;
import static com.example.HealthyCampus.common.utils.FunctionUtils.rotateBitmapByDegree;


public class ClassifierCameraActivity extends BaseActivity<ClassifierCameraContract.View, ClassifierCameraContract.Presenter> implements ClassifierCameraContract.View {


    //加载布局
    @BindView(R.id.classifyLayout)
    RelativeLayout classifyLayout;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;
    @BindView(R.id.contentLayout)
    RelativeLayout contentLayout;

    @BindView(R.id.cameraLayout)
    FrameLayout cameraLayout;

    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.ivReverse)
    ImageView ivReverse;
    @BindView(R.id.ivOk)
    ImageView ivOk;
    @BindView(R.id.ivPicture)
    ImageView ivPicture;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;


    public static int MATERAIL_CODE = 0;
    public static int DISH_CODE = 1;

    private int code = 0;

    private boolean change1 = false;
    private boolean change2 = false;

    private AlertDialog.Builder builder;
    private Animation takeAnimation;

    private int mOrientation;
    private CameraPreview mPreview;

    private String url;

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setFullsceen(this);  //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题

    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_recipes_cameras);
    }

    @Override
    protected ClassifierCameraContract.Presenter createPresenter() {
        return new ClassifierCameraPresenter();
    }

    @Override
    protected void initView() {
        code = getIntent().getIntExtra("CODE", 0);
        if (!FunctionUtils.checkCameraHardware(getContext())) {
            ToastUtil.show(getContext(), "不支持相机");
        } else if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            //没有权限，申请权限
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            //申请权限，其中RC_PERMISSION是权限申请码，用来标志权限申请的
            ActivityCompat.requestPermissions(this, permissions, ConstantValues.RC_PERMISSION);
        } else {
            //拥有权限
            //    private Camera mCamera;
            mPreview = new CameraPreview(this);
            cameraLayout.addView(mPreview);
            //点击屏幕进行聚焦
            cameraLayout.setOnClickListener(v -> CameraUtils.autoFocus(null));
            mOrientation = CameraUtils.calculateCameraPreviewOrientation(this);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ConstantValues.RC_PERMISSION && grantResults.length == 2
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.e("ClassifierCame:" + "123456", "权限申请成功");
        } else {
            Log.e("ClassifierCame:" + "123456", "权限申请失败");
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        takeAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.take_picture);
        ivOk.setEnabled(false);
        ivPicture.setEnabled(false);
        new Thread(() -> resetToken()).start();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tomato);
//        bitmap = rotateBitmapByDegree(bitmap, 90);
//        //缩放
//        bitmap = Bitmap.createScaledBitmap(bitmap, 720, 1280, false);
//        String params = PictureUtil.bitmapToString(bitmap);
//        mPresenter.getDishResult(params);
    }


    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void showError(Throwable throwable) {
        DialogUtil.dismissProgressDialog();
        Log.e("ClassifierCame:" + "123456", "throwable.getMessage()" + throwable.getMessage());
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                BaiduErrorVo baiduErrorVo = JsonUtil.format(httpException.errorBody().string(), BaiduErrorVo.class);
                switch (baiduErrorVo.getError_code()) {
                    case 111:
                    case 110:
                    case 100:
                        ToastUtil.show(getContext(), "请点击重试");
                        break;
                    default:
                        ToastUtil.show(getContext(), baiduErrorVo.getError_msg());
                        break;

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
//                Log.e("ClassifierCame:" + "123456", "99999999999999999999999999999999999");
            }
        } else {
            ToastUtil.show(this, "http:未知错误:" + throwable.getMessage());
        }
    }


    private void resetToken() {
        SPHelper.setString(SPHelper.BAIDU_TOKEN, AuthService.getAuth());
        Log.e("ClassifierCame:" + "123456", "SPHelper.BAIDU_TOKEN" + SPHelper.getString(SPHelper.BAIDU_TOKEN));
    }

    @Override
    public void showDishSuccess(DishVo dishVo) {
        if (null == dishVo || 0 == dishVo.getResult_num()) {
            ToastUtil.show(getContext(), "分析失败");
        } else {
            String[] names = new String[dishVo.getResult_num()];
            int i = 0;
            for (DishVo.Dish dish : dishVo.getResult()) {
                names[i++] = dish.getName();
            }
            showIngredientDialog(names);
        }
        DialogUtil.dismissProgressDialog();
    }

    @Override
    public void showIngredientSuccess(IngredientVo ingredientVo) {
        if (null == ingredientVo || 0 == ingredientVo.getResult_num()) {
            ToastUtil.show(getContext(), "分析失败");
        } else {
            String[] names = new String[ingredientVo.getResult_num()];
            int i = 0;
            for (IngredientVo.Ingredient ingredient : ingredientVo.getResult()) {
                names[i++] = ingredient.getName();
            }
            showIngredientDialog(names);
        }
        DialogUtil.dismissProgressDialog();
    }

    private void showIngredientDialog(String... names) {
        builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.find_health_recipes_result));
        builder.setItems(names, (dialog, which) -> {
            if (names != null && ("非菜".equals(names[which]) || "非果蔬食材".equals(names[which]))) {
                ToastUtil.show(getContext(), names[which]);
            } else {
                Intent intent;
                if (0 == code)
                    intent = new Intent(ClassifierCameraActivity.this, ClassifyResultActivity.class);
                else
                    intent = new Intent(ClassifierCameraActivity.this, RecipesDetailActivity.class);
                assert names != null;
                intent.putExtra("NAME", names[which]);
                startActivity(intent);
            }
        });
        builder.show();
        change2 = change1;
    }


    @Override
    public void enableClick(boolean val) {
        if (val) {
            contentLayout.setEnabled(false);
            ivOk.setEnabled(false);
            ivPhoto.setEnabled(false);
        } else {
            contentLayout.setEnabled(true);
            ivOk.setEnabled(true);
            ivPhoto.setEnabled(true);
            ivPicture.setEnabled(true);
            DialogUtil.dismissProgressDialog();
        }
    }


    /**
     * 对焦回调,对焦完成后进行拍照
     */
    private Camera.AutoFocusCallback mAutoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
                CameraUtils.takePicture(null, null, mPictureCallback);
            } else {
                CameraUtils.takePicture(null, null, mPictureCallback);
            }
        }
    };

    @Override
    // apk暂停时执行的动作：把相机关闭，避免占用导致其他应用无法使用相机
    protected void onPause() {
        super.onPause();
        CameraUtils.stopPreview();
    }


    @Override
    // 恢复apk时执行的动作
    protected void onResume() {
        super.onResume();
        CameraUtils.startPreview();
    }


    /**
     * 拍照回调
     */
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                if (bitmap == null) {
                    ToastUtil.show(getContext(), "拍照失败");
                }
                bitmap = rotateBitmapByDegree(bitmap, 90);
                //缩放
                bitmap = Bitmap.createScaledBitmap(bitmap, 720, 1280, false);
                ivPicture.setImageBitmap(bitmap);
                ivOk.setImageResource(R.drawable.find_recipes_photo_analy);
                url = "";

            } catch (Exception e) {
                e.printStackTrace();
            }
            CameraUtils.startPreview();
            progressLayout.setVisibility(View.GONE);
            ivPhoto.clearAnimation();
            enableClick(false);
        }
    };


    @OnClick(R.id.ivPhoto)
    public void ivPhoto(View view) {
        progressLayout.setVisibility(View.VISIBLE);
        ivPhoto.startAnimation(takeAnimation);
        //以change1为主，如果change1 == change2，代表分析过了，改变值;否则，不变.
        change1 = (change1 == change2) != change1;
        enableClick(true);
        CameraUtils.autoFocus(mAutoFocusCallback);
    }

    @OnClick(R.id.ivOk)
    public void ivOk(View view) {
        DialogUtil.showProgressDialog(getContext(), getString(R.string.loading_classify));
        //是否已经进行过分析
        if (change1 == change2) {       //显示分析结果
            builder.show();
            DialogUtil.dismissProgressDialog();
        } else {                        //请求分析
            enableClick(true);
            Bitmap bitmap = ((BitmapDrawable) ivPicture.getDrawable()).getBitmap();
            String params = PictureUtil.bitmapToString(bitmap);
            if (0 == code)
                mPresenter.getIngredientResult(params);
            else
                mPresenter.getDishResult(params);
        }
    }

    /**
     * 切换相机
     *
     * @param view
     */
    @OnClick(R.id.ivReverse)
    public void ivReverse(View view) {
        if (mPreview != null) {
            CameraUtils.switchCamera(1 - CameraUtils.getCameraID(), mPreview.getHolder());
            // 切换相机后需要重新计算旋转角度
            mOrientation = CameraUtils.calculateCameraPreviewOrientation(this);
        }
    }


    @OnClick(R.id.ivAlbum)
    public void ivAlbum(View view) {
        Intent intent = new Intent(this, ImageSelectorActivity.class);
        intent.putExtra("maxNum", 1);
        startActivityForResult(intent, PICK_PHOTO);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.ivPicture)
    public void ivPicture(View view) {
        initPicture();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_PHOTO:        //相册回调
                if (RESULT_OK == resultCode) {
                    List<String> images = data.getStringArrayListExtra("images");
                    url = images.get(0).replace("file://", "");
                    GlideUtils.displaySDKImage(ivPicture, url);
                    ivOk.setImageResource(R.drawable.find_recipes_photo_analy);
                    change1 = (change1 == change2) != change1;
                    enableClick(false);
                }
                break;
        }
    }

    @SuppressLint("InflateParams")
    private void initPicture() {
        ImageView imageView = (ImageView) getLayoutInflater().inflate(R.layout.dialog_picture, null);
        if ("".equals(url))
            imageView.setImageBitmap(((BitmapDrawable) ivPicture.getDrawable()).getBitmap());
        else
            GlideUtils.displaySDKImage(imageView, url);
        PictureDialog pictureDialog = new PictureDialog(this, imageView, R.style.DialogMap);
        imageView.setOnClickListener(v -> pictureDialog.dismiss());
        pictureDialog.setOnCancelListener(dialog -> pictureDialog.dismiss());
        pictureDialog.show();
    }


}
