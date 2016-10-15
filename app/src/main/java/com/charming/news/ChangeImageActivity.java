package com.charming.news;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ChangeImageActivity extends AppCompatActivity {

    private ImageView userImage;
    private static final int TAKE_PHOTO  = 1;
    private static final int PICK_IMAGE  = 2;
    private static final int CROP  = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_image);
        init();
    }

    private void init() {
        userImage = (ImageView) findViewById(R.id.user_image);
        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getExtras().get("image");
        userImage.setImageBitmap(bitmap);
    }

    public void onChangeImageClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.change_image))
                .setItems(R.array.change_image_way_selection, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent take_photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                take_photo.putExtra(MediaStore.EXTRA_OUTPUT,
                                        Uri.parse(Environment.getExternalStorageDirectory() + "/image.jpg"));
                                startActivityForResult(take_photo, TAKE_PHOTO);
                                break;
                            case 1:
                                Intent pick_image = new Intent(Intent.ACTION_GET_CONTENT);
                                pick_image.setType("image/*");
                                startActivityForResult(pick_image, PICK_IMAGE);
                                break;
                            default:
                                break;
                        }
                    }
                }).create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    Intent take_photo_crop = new Intent("com.android.camera.action.CROP");
                    take_photo_crop.setDataAndType(data.getData(), "image/*");
                    // 设置裁剪
                    take_photo_crop.putExtra("crop", "true");
                    // aspectX aspectY 是宽高的比例
                    take_photo_crop.putExtra("aspectX", 1);
                    take_photo_crop.putExtra("aspectY", 1);
                    // outputX outputY 是裁剪图片宽高
                    take_photo_crop.putExtra("outputX", 340);
                    take_photo_crop.putExtra("outputY", 340);
                    take_photo_crop.putExtra("return-data", true);
                    startActivityForResult(take_photo_crop, CROP);
                    break;
                case PICK_IMAGE:
                    Intent pick_photo_crop = new Intent("com.android.camera.action.CROP");
                    pick_photo_crop.setDataAndType(data.getData(), "image/*");
                    // 设置裁剪
                    pick_photo_crop.putExtra("crop", "true");
                    // aspectX aspectY 是宽高的比例
                    pick_photo_crop.putExtra("aspectX", 1);
                    pick_photo_crop.putExtra("aspectY", 1);
                    // outputX outputY 是裁剪图片宽高
                    pick_photo_crop.putExtra("outputX", 340);
                    pick_photo_crop.putExtra("outputY", 340);
                    pick_photo_crop.putExtra("return-data", true);
                    startActivityForResult(pick_photo_crop, CROP);
                    break;
                case CROP:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("image");
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("image" ,bitmap);
                    Intent image_result = new Intent();
                    setResult(NewsActivity.CHANGE_IMAGE, image_result);
                    finish();
                    break;
            }
        }
    }
}
