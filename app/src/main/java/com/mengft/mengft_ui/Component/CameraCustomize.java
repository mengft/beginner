package com.mengft.mengft_ui.Component;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mengft.mengft_ui.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Arrays.asList;

/**
 * Created by mengft on 2018/6/25.
 */

@Route(path = "/home/CameraCustomize")
public class CameraCustomize extends Activity implements View.OnClickListener {

    private static final String TAG = CameraCustomize.class.getSimpleName();
    private ImageView take_image, take_image_back, take_image_cancle, take_image_confirm;
    private RelativeLayout rl_preview, rl_resultview;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private String cameraId;                                            // 摄像头ID，一般0是后视，1是前视
    private CameraDevice cameraDevice;                                  // 定义代表摄像头的成员变量，代表系统摄像头
    private CameraCharacteristics cameraCharacteristics;
    private ImageReader imageReader;
    private CaptureRequest.Builder captureRequestBuilder;               // 当程序调用setRepeatingRequest()方法进行预览时，或调用capture()进行拍照时，都需要传入CaptureRequest参数时
    // 当程序调用setRepeatingRequest()方法进行预览时，或调用capture()进行拍照时，都需要传入CaptureRequest参数时
    // captureRequest代表一次捕获请求，用于描述捕获图片的各种参数设置。比如对焦模式，曝光模式...等，程序对照片所做的各种控制，都通过CaptureRequest参数来进行设置
    // CaptureRequest.Builder 负责生成captureRequest对象
    private CameraCaptureSession session;
    private Handler handler;
    private HandlerThread handlerThread;
    private File file;                                                  // 图片存放位置
    private Image image;                                                // 存放ImageReader图片信息
    private byte[] pictureBytes;                                        // 用户临时存储拍照图片信息

    private int state;
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 1601;
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1602;
    //用SparseIntArray来代替hashMap,进行性能优化
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_camera_customize);

        bindView();
    }

    /**
     * 绑定控件
     */
    private void bindView() {
        rl_preview = findViewById(R.id.rl_preview);                         // 预览界面
        rl_resultview = findViewById(R.id.rl_resultview);                   // 拍照结果页
        surfaceView = findViewById(R.id.sv_camera_customize);               // 预览控件
        take_image = findViewById(R.id.iv_take_image);                      // 拍照按钮
        take_image_back = findViewById(R.id.iv_take_image_back);            // 拍照返回
        take_image_confirm = findViewById(R.id.iv_take_camera_confirm);     // 确认拍照效果
        take_image_cancle = findViewById(R.id.iv_take_camera_cancle);       // 取消拍照效果（重拍）

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(holderCallBack);
        take_image.setOnClickListener(this);
        take_image_back.setOnClickListener(this);
        take_image_confirm.setOnClickListener(this);
        take_image_cancle.setOnClickListener(this);
    }

    /**
     * 打开摄像头
     */
    private void openCamera() {
        Log.e(TAG, "openCamera");
        // 实例化摄像头
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        cameraId = CameraCharacteristics.LENS_FACING_FRONT + "";
        // 用户摄像头权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Doesn't Get OpenCamera Permission");
            // 请求相机调用权限，监听用户信息回调
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST_CODE);
            return;
        }
        try {
            Log.e(TAG, "Start OpenCamera");
            cameraManager.openCamera(cameraId, deviceStateCallback, handler);
        } catch (CameraAccessException e) {
            Log.e(TAG, "OpenCamera失败" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 创建预览界面
     */
    private void createCameraPreview() {
        try {
            // 请求预览
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surfaceHolder.getSurface());
            // 创建cameraCaptureSession,第一个参数是图片集合，封装了所有图片surface,第二个参数用来监听这处创建过程
            cameraDevice.createCaptureSession(asList(surfaceHolder.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    //The camera is already closed
                    if (null == cameraDevice) {
                        return;
                    }
                    // When the session is ready, we start displaying the preview.
                    session = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(CameraCustomize.this, "DeviceConfiguration change", Toast.LENGTH_SHORT).show();
                }

            }, handler);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 预览需求
     */
    protected void updatePreview() {
        if (null == cameraDevice) {
            Log.e(TAG, "updatePreview error, return");
        }
        // 设置模式为自动
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            session.setRepeatingRequest(captureRequestBuilder.build(), null, handler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照(请求其中一个预览界面)
     */
    private void takePicture() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            // 获取指定摄像头的相关特性
            cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraDevice.getId());
            // 创建一个ImageReader对象，用于获得摄像头的图像数据
            imageReader = ImageReader.newInstance(surfaceView.getWidth(), surfaceView.getHeight(), ImageFormat.JPEG, 1);
            //动态数组
//            List outputSurfaces = Arrays.asList(imageReader.getSurface());
            List<Surface> outputSurfaces = new ArrayList<Surface>(1);
            outputSurfaces.add(imageReader.getSurface());
            // 生成请求对象（TEMPLATE_STILL_CAPTURE此处请求是拍照）
            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            // 将ImageReader的surface作为captureBuilder的输出目标
            captureBuilder.addTarget(imageReader.getSurface());
            // 设置自动对焦模式
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 设置自动曝光模式
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
            // 获取设备方向
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            // 根据设置方向设置照片显示的方向
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
            // 创建File对象，设置图片的存储位置，其中包含文件所在的目录以及文件的命名
            file = new File(Environment.getExternalStorageDirectory() + "/" + (int) (Math.random() * 10000) + ".jpg");
            // ImageReader监听函数
            imageReader.setOnImageAvailableListener(onImageAvailableListener, handler);

            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    try {
                        session.capture(captureBuilder.build(), captureSessionCallback, handler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {
                }

            }, handler);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片文件
     *
     * @param bytes
     * @throws IOException
     */
    private void saveImageFile(byte[] bytes) throws IOException {
        // 写文件权限
        if (ActivityCompat.checkSelfPermission(CameraCustomize.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Doesn't Get WRITE_EXTERNAL_STORAGE Permission");
            // 请求相机调用权限，监听用户信息回调
            ActivityCompat.requestPermissions(CameraCustomize.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            pictureBytes = bytes;
            return;
        }
        // 创建FileOutputStream对象
        FileOutputStream outputStream = null;
        // 创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;
        try {
            // 如果文件存在则删除
            if (file.exists()) {
                file.delete();
            }
            // 在文件系统中根据路径创建一个新的空文件
            file.createNewFile();
            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(file);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(bytes);
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();
        } catch (Exception e) {
            // 打印异常信息
            Log.e(TAG, "照片文件写入失败的原因" + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭创建的流对象
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * 拍照完成，确认拍照图片（是否重拍）
     *
     * @param flag true 初始化预览界面 false初始化结果界面
     */
    private void changeCameraLoayout(Boolean flag) {
        if (flag) {
            CameraCustomize.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rl_preview.setVisibility(View.VISIBLE);
                    rl_resultview.setVisibility(View.GONE);
                    createCameraPreview();
                }
            });
        } else {
            CameraCustomize.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rl_resultview.setVisibility(View.VISIBLE);
                    rl_preview.setVisibility(View.GONE);
                }
            });
        }
    }

    /**
     * 关闭相机
     */
    private void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
        if (null != imageReader) {
            imageReader.close();
            imageReader = null;
        }
    }

    protected void startBackgroundThread() {
        handlerThread = new HandlerThread("Camera2");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    protected void stopBackgroundThread() {
        handlerThread.quitSafely();
        try {
            handlerThread.join();
            handlerThread = null;
            handler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void stopPreview() {

    }

    /**
     * 实例化SurfaceHolder 监听,实时的来刷新预览界面
     */
    final SurfaceHolder.Callback holderCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            Log.e(TAG, "surfaceCreated");
            // 打开摄像头
            openCamera();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            Log.e(TAG, "surfaceChanged");
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            Log.e(TAG, "surfaceDestroyed");
            closeCamera();
        }
    };

    /**
     * 实例化ImageReader 监听
     */
    final ImageReader.OnImageAvailableListener onImageAvailableListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            Log.e(TAG, "ImageReader imageAvailable");
            image = null;
            image = reader.acquireLatestImage();
        }
    };

    /**
     * 实例化CameraDevice 监听
     */
    final CameraDevice.StateCallback deviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            Log.e(TAG, "CameraDevice onOpened");
            cameraDevice = camera;
            createCameraPreview();

        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            Log.e(TAG, "CameraDevice Disconnected");
            cameraDevice.close();
            cameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            Log.e(TAG, "CameraDevice onError" + error);
            cameraDevice.close();
            cameraDevice = null;
        }

    };

    /**
     * 拍照开始或是完成时调用，用来监听CameraCaptureSession的创建过程
     */
    final CameraCaptureSession.CaptureCallback captureSessionCallback = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
            changeCameraLoayout(false);
        }

        @Override
        public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
            super.onCaptureFailed(session, request, failure);
            Log.e(TAG, "onCaptureFailed" + failure.getReason());
        }

        @Override
        public void onCaptureBufferLost(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull Surface target, long frameNumber) {
            super.onCaptureBufferLost(session, request, target, frameNumber);
            Log.e(TAG, "onCaptureBufferLost");
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 调用相机权限
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, String.format("相机权限调用%s,无法正常完成拍照", "失败"), Toast.LENGTH_SHORT).show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);

            }
        }
        // 写文件权限
        if (requestCode == PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    saveImageFile(pictureBytes);
                } catch (IOException e) {
                    Log.e(TAG, "照片文件写入失败");
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, String.format("照片保存权限调用%s,无法正常完成拍照", "失败"), Toast.LENGTH_SHORT).show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        openCamera();
                    }
                }, 1000);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_take_image:
                takePicture();
                break;
            case R.id.iv_take_image_back:
                finish();
                break;
            case R.id.iv_take_camera_cancle:
                changeCameraLoayout(true);
                createCameraPreview();
                break;
            case R.id.iv_take_camera_confirm:
                // 读取图像并保存
                changeCameraLoayout(false);
                try {
                    ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                    byte[] bytes = new byte[buffer.capacity()];
                    buffer.get(bytes);
                    saveImageFile(bytes);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (image != null) {
                        image.close();
                    }
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();
    }

    @Override
    protected void onPause() {
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }
}
