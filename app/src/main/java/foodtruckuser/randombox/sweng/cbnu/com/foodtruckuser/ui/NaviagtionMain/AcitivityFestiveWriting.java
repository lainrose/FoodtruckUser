package foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.ui.NaviagtionMain;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.R;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.FileUtils;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.Utill.ServiceGenerator;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.model.UserModel;
import foodtruckuser.randombox.sweng.cbnu.com.foodtruckuser.service.ApiService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AcitivityFestiveWriting extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private EditText writingTextView;
    private ImageView uproadPhotoImage;
    private ImageView uproadPhoto;
    private Toolbar toolbar;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;
    private Uri mImageCaptureUri;
    private String absoultePath;
    private TextView inputStartDateAndTime;
    private TextView inputEndDateAndTime;
    private TextView startRcruitingPeriod;
    private TextView endRcruitingPeriod;
    private String temp;
    private String year;
    private String monthOfYear;
    private String dayOfMonth;
    private String hourOfDay;
    private String minute;
    private String Flag;
    private long backKeyPressedTime = 0;
    private Toast toast;
    private String StartDateAndTime;
    private String festivetitle_Textview;
    private String EndDateAndTime;
    private String StartRcruitingPeriod;
    private String EndRcruitingPeriod;
    private TextView inputLocation;
    private TextView numOfTruck;
    private TextView isElectric;
    private File image;
    private MultipartBody.Part imageFileBody;
    private EditText festive_title;
    private CheckBox electricbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festive_writing);
        setupToolbar();
        uproadPhoto = (ImageView)findViewById(R.id.uproadPhoto);
        uproadPhotoImage = (ImageView)findViewById(R.id.uproadPhotoImage);
        inputStartDateAndTime = (TextView)findViewById(R.id.inputStartDateAndTime);
        inputEndDateAndTime = (TextView)findViewById(R.id.inputEndDateAndTime);
        startRcruitingPeriod = (TextView)findViewById(R.id.startRcruitingPeriod);
        endRcruitingPeriod = (TextView)findViewById(R.id.endRcruitingPeriod);
        inputLocation = (TextView)findViewById(R.id.inputLocation);
        numOfTruck = (TextView)findViewById(R.id.numOfTruck);
        writingTextView = (EditText)findViewById(R.id.writingText);
        festive_title = (EditText)findViewById(R.id.festive_title);
        electricbtn = (CheckBox)findViewById(R.id.electricbtn);

        checkPermission();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void doTakePhotoAction() // 카메라 촬영 후 이미지 가져오기
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }
    public void doTakeAlbumAction() // 앨범에서 이미지 가져오기
    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode != RESULT_OK)
            return;

        switch(requestCode)
        {
            case PICK_FROM_ALBUM:
            {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel",mImageCaptureUri.getPath().toString());
                image = FileUtils.getFile(this, mImageCaptureUri);
            }

            case PICK_FROM_CAMERA:
            {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                // CROP할 이미지를 200*200 크기로 저장
                intent.putExtra("outputX", 300); // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 300); // CROP한 이미지의 y축 크기
                intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
                intent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_iMAGE); // CROP_FROM_CAMERA case문 이동
                break;
            }
            case CROP_FROM_iMAGE:
            {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                if(resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                // CROP된 이미지를 저장하기 위한 FILE 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/SmartWheel/"+System.currentTimeMillis()+".jpg";

                if(extras != null)
                {
                    //uproadPhoto.bringToFront();
                    Bitmap photo = extras.getParcelable("data"); // CROP된 BITMAP
                    uproadPhotoImage.setImageBitmap(photo); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌
                    uproadPhotoImage.bringToFront();
                    storeCropImage(photo, filePath); // CROP된 이미지를 외부저장소, 앨범에 저장한다.
                    absoultePath = filePath;
                    break;

                }
                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }
            }
        }
    }
    /*
   * Bitmap을 저장하는 부분
   */
    private void storeCropImage(Bitmap bitmap, String filePath) {
        // SmartWheel 폴더를 생성하여 이미지를 저장하는 방식이다.
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_SmartWheel = new File(dirPath);

        if(!directory_SmartWheel.exists()) // SmartWheel 디렉터리에 폴더가 없다면 (새로 이미지를 저장할 경우에 속한다.)
            directory_SmartWheel.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {

            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            // sendBroadcast를 통해 Crop된 사진을 앨범에 보이도록 갱신한다.
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.uproadFestiveBtn) {
            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("작성한 행사를 올리시겠습니까?")
                    //.setContentText("확인 버튼을 누르면 취소할 수 없습니다.")
                    .setCancelText("취소")
                    .setConfirmText("확인")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(final SweetAlertDialog sDialog) {
                            Log.d("TEST", writingTextView.getText().toString());

                            String writingText = writingTextView.getText().toString();
                            String inputLocationText = inputLocation.getText().toString();
                            String numOfTruckText = numOfTruck.getText().toString();
                            festivetitle_Textview = festive_title.getText().toString();
                            // TODO: 2016-12-02 서버로 보낼 날짜 및 기타 변수
                            //StartDateAndTime // 행사 시작일
                            //EndDateAndTime // 행사 종료일
                            //StartRcruitingPeriod // 모집기간 시작일
                            //EndRcruitingPeriod // 모집기간 종료일
                            //writingText // 작성한 내용
                            //inputLocationText // 장소설정 내용
                            //numOfTruckText // 트럭대수
                            //isElectricText // 전기여부 -> true, false로 반환되게 해줭
                            JsonObject ob = new JsonObject();
                            ob.addProperty("title", festivetitle_Textview);
                            ob.addProperty("condition", writingText);
                            ob.addProperty("place", inputLocationText);
                            ob.addProperty("start_date", StartDateAndTime);
                            ob.addProperty("end_date", EndDateAndTime);
                            ob.addProperty("applicant_start", StartRcruitingPeriod);
                            ob.addProperty("applicant_end", EndRcruitingPeriod);
                            ob.addProperty("support_type", electricbtn.isChecked());
                            ob.addProperty("limit_num_of_application", numOfTruckText);
                            ob.addProperty("client_id", UserModel.getInstance().getUserId());


                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), image);
                            imageFileBody = MultipartBody.Part.createFormData("image", image.getName(), requestBody);

                            ApiService service = ServiceGenerator.createService(ApiService.class);
                            Call<Integer> call = service.save_festival(imageFileBody, ob);
                            call.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    int activeCheck = response.body();
                                    switch (activeCheck) {
                                        case 1: {
                                            Toast.makeText(getApplicationContext(), "행사 입점 공고 신청완료", Toast.LENGTH_SHORT).show();
                                            sDialog.dismiss();
                                            finish();
                                            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                                            break;
                                        }
                                        case 2: {
                                            Toast.makeText(getApplicationContext(), "저장 실패", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                        case 3: {
                                            Toast.makeText(getApplicationContext(), "행사 날짜를 체크하세요.", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                        case 4: {
                                            Toast.makeText(getApplicationContext(), "잘못된 소비자 아이디", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                        case 5: {
                                            Toast.makeText(getApplicationContext(), "이름이 똑같은 행사가 입력되어 있습니다.", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Log.d("FESTIVAL", t.toString());
                                }
                            });
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    }).show();

        } else if (view.getId() == R.id.uproadPhotoBtn) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakePhotoAction();
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };

            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
        } else if (view.getId() == R.id.inputStartDateAndTime) {
            Flag = "inputStartDateAndTime";
            CalendarDate();
        }
        else if (view.getId() == R.id.inputEndDateAndTime) {
            Flag = "inputEndDateAndTime";
            CalendarDate();
        }
        else if (view.getId() == R.id.startRcruitingPeriod) {
            Flag = "startRcruitingPeriod";
            CalendarDate();
        }
        else if (view.getId() == R.id.endRcruitingPeriod) {
            Flag = "endRcruitingPeriod";
            CalendarDate();
        }
    }
    public void CalendarDate(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                AcitivityFestiveWriting.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(false);
        dpd.vibrate(true);
        dpd.dismissOnPause(false);
        dpd.showYearPickerFirst(false);
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(Color.parseColor("#ff5722"));
        dpd.show(getFragmentManager(), "행사 날짜 설정");
    }
    public void CalendarTime(){
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                AcitivityFestiveWriting.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.vibrate(true);
        tpd.dismissOnPause(false);
        tpd.enableSeconds(false);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.setAccentColor(Color.parseColor("#ff5722"));
        tpd.setTitle("행사 시간 설정");
        tpd.setTimeInterval(1, 10, 10);
    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("행사 날짜 설정");
        if(dpd != null) dpd.setOnDateSetListener(this);
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("행사 시간 설정");
        if(tpd != null) tpd.setOnTimeSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        this.year = String.valueOf(year);
        this.monthOfYear = String.valueOf(++monthOfYear);
        this.dayOfMonth = String.valueOf(dayOfMonth);

        temp = year + "년 " + monthOfYear + "월 " + dayOfMonth + "일 ";
        if(Flag.equals("inputStartDateAndTime")){
            StartDateAndTime = year + "-" + monthOfYear + "-" + dayOfMonth;
            inputStartDateAndTime.setText(temp);
            Log.d("DATE", StartDateAndTime);
        }
        else if(Flag.equals("inputEndDateAndTime")){
            EndDateAndTime =year + "-" + monthOfYear + "-" + dayOfMonth;
            inputEndDateAndTime.setText(temp);
        }
        else if(Flag.equals("startRcruitingPeriod")){
            StartRcruitingPeriod = year + "-" + monthOfYear + "-" + dayOfMonth;
            startRcruitingPeriod.setText(temp);
        }
        else if(Flag.equals("endRcruitingPeriod")){
            EndRcruitingPeriod = year + "-" + monthOfYear + "-" + dayOfMonth;
            endRcruitingPeriod.setText(temp);
        }
    }
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        this.hourOfDay = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        this.minute = minute < 10 ? "0"+minute : ""+minute;
        //String secondString = second < 10 ? "0"+second : ""+second;

        temp += hourOfDay+"시 "+minute+"분 ";
        if(Flag.equals("inputStartDateAndTime")){
            inputStartDateAndTime.setText(temp);
        }
        else if(Flag.equals("inputEndDateAndTime")){
            inputEndDateAndTime.setText(temp);
        }
        else if(Flag.equals("startRcruitingPeriod")){
            startRcruitingPeriod.setText(temp);
        }
        else if(Flag.equals("endRcruitingPeriod")){
            endRcruitingPeriod.setText(temp);
        }
    }

    @Override
    public void onBackPressed(){

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            toast.cancel();
        }
    }
    public void showGuide() {
        toast = Toast.makeText(getApplication(), "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
