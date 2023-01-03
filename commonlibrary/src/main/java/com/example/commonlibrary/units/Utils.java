package com.example.commonlibrary.units;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by youxh on 2018/5/5.
 */

public class Utils {
    private static String IDENTIFY = "mk";
    private static SimpleDateFormat formatter;
    private static File file;
    static File directory;
    private static ArrayList list;
    /**
     * 地球半径
     **/
    private static final double R = 6371e3;
    /**
     * 180°
     **/
    private static final DecimalFormat df = new DecimalFormat("0.000000");

//    /**
//     * carType : 1
//     * carNum : 鄂AH132D
//     */
//
//    private int carType;
//    private String carNum;
//
//    private static void sendFrameHead(int messageLength) {
//        sendTcpData[0] = (byte) 0xa5;
//        sendTcpData[1] = (byte) 0xa5;
//        sendTcpData[2] = (byte) 0x00;
//        sendTcpData[3] = (byte) 0x01;
//        sendTcpData[4] = (byte) 0x00;
//        sendTcpData[5] = (byte) 0x01;
//        sendTcpData[6] = (byte) 0x00;
//        sendTcpData[7] = (byte) 0x00;
//        sendTcpData[8] = (byte) 0x00;
//        sendTcpData[9] = (byte) 0x00;
//        sendTcpData[10] = (byte) 0x00;
//        sendTcpData[11] = (byte) 0x00;
//        sendTcpData[12] = (byte) 0x00;
//        sendTcpData[13] = (byte) 0x00;
//        sendTcpData[14] = (byte) 0x00;
//        sendTcpData[15] = (byte) 0xa0;
//
//        sendTcpData[16] = (byte) ((messageLength & 0xFF000000) >> 24);
//        sendTcpData[17] = (byte) ((messageLength & 0x00FF0000) >> 16);
//        sendTcpData[18] = (byte) ((messageLength & 0x0000FF00) >> 8);
//        sendTcpData[19] = (byte) (messageLength & 0x000000FF);
//
//    }


    /**
     * 判断是否是车牌号
     */

    public static boolean isCarNo(String carnumber) {
                  /*
3          车牌号格式：汉字 + A-Z + 5位A-Z或0-9
4         （只包括了普通车牌号，教练车和部分部队车等车牌号不包括在内）
5          */
        String carnumRegex = "[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
        if (TextUtils.isEmpty(carnumber)) return false;
        else
            return carnumber.matches(carnumRegex);
    }


    public static void putIp(String key, String value, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(IDENTIFY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void putPort(String key, String value, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(IDENTIFY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void putCarId(String key, String value, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(IDENTIFY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void putCarType(String key, String value, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(IDENTIFY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(IDENTIFY, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    public static void putServiceIP(String key, String value, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(IDENTIFY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static void setData(String data) {
        if (list == null) {
            list = new ArrayList();
        }
        list.add(data);
        if (list.size() > 30) {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {

                String Crash_File_Directory_Path = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/MokerData/data/";
                if (formatter == null) {
                    formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                }
                long timestamp = System.currentTimeMillis();
                String time = formatter.format(new Date());
                String fileName = Crash_File_Directory_Path + "data.txt";
                file = new File(fileName);
                directory = file.getParentFile();
                if (!directory.exists() && !directory.mkdirs()) {
                    File folder = new File(Crash_File_Directory_Path);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }
                }
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // FileOutputStream fos = null;
                try {
                    // fos = new FileOutputStream(fileName);
                    OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName), "gbk");
                    for (int a = 0; a < list.size(); a++) {
                        //  fos.write(list.get(a).toString().getBytes());
                        oStreamWriter.write(list.get(a).toString());
                    }
                    // fos.close();
                    oStreamWriter.close();
                    list.remove(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

//            File[] op = directory.listFiles();
//            if (op.length > 10) {
//                directory.delete();
//            }
            }

        }
    }

    public static void setData2(String log) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {

            String Crash_File_Directory_Path = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/MokerData/data2/";
            if (formatter == null) {
                formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            }
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = Crash_File_Directory_Path + "dataLog.txt";
            file = new File(fileName);
            directory = file.getParentFile();
            if (!directory.exists() && !directory.mkdirs()) {
                File folder = new File(Crash_File_Directory_Path);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(fileName);
                fos.write(log.toString().getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


//    public static void postMsg(String msg, MsgType msgType){
//        BaseJson baseJson = new BaseJson();
//        baseJson.setCode(msgType.getValue());
//        baseJson.setMsg(msg);
//        RxBus.getInstance().post(baseJson);
//    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }

    public static ByteBuf byteBufSend(String object) {
        int length = object.getBytes().length;
        byte[] newBuf = new byte[length + 21];
        ByteBuf byteBuf = Unpooled.copiedBuffer(newBuf);
        byteBuf.resetWriterIndex();
        //startFlag
        byteBuf.writeByte(0x5a);
        byteBuf.writeByte(0xa5);
        //Versuon
        byteBuf.writeByte(0x00);
        byteBuf.writeByte(0x01);
        //type
        byteBuf.writeByte(0x00);
        byteBuf.writeByte(0x01);
        //id
        byteBuf.writeByte(0x00);
        byteBuf.writeByte(0x00);
        byteBuf.writeByte(0x00);
        byteBuf.writeByte(0x00);
        //dataType
        byteBuf.writeByte(0x00);
        byteBuf.writeByte(0x00);
        //byRes
        byteBuf.writeByte(0x00);
        byteBuf.writeByte(0x00);
        byteBuf.writeByte(0x00);
        byteBuf.writeByte(0xa0);
        //length
        byteBuf.writeByte((length & 0xFF000000) >> 24);
        byteBuf.writeByte((length & 0x00FF0000) >> 16);
        byteBuf.writeByte((length & 0x0000FF00) >> 8);
        byteBuf.writeByte((length & 0x000000FF));
        //data
        byte[] bytes = object.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            byteBuf.writeByte(bytes[i]);
        }
        //CheckNum
        byteBuf.writeByte(0x01);
        return byteBuf;

    }


    public static byte[] sendFrameHead(int messageLength, String s) {
        byte[] sendTcpData = new byte[512];
        sendTcpData[0] = (byte) 0x5a;
        sendTcpData[1] = (byte) 0xa5;
        sendTcpData[2] = (byte) 0x00;
        sendTcpData[3] = (byte) 0x01;
        sendTcpData[4] = (byte) 0x00;
        sendTcpData[5] = (byte) 0x01;
        sendTcpData[6] = (byte) 0x00;
        sendTcpData[7] = (byte) 0x00;
        sendTcpData[8] = (byte) 0x00;
        sendTcpData[9] = (byte) 0x00;
        sendTcpData[10] = (byte) 0x00;
        sendTcpData[11] = (byte) 0x00;
        sendTcpData[12] = (byte) 0x00;
        sendTcpData[13] = (byte) 0x00;
        sendTcpData[14] = (byte) 0x00;
        sendTcpData[15] = (byte) 0xa0;

        sendTcpData[16] = (byte) ((messageLength & 0xFF000000) >> 24);
        sendTcpData[17] = (byte) ((messageLength & 0x00FF0000) >> 16);
        sendTcpData[18] = (byte) ((messageLength & 0x0000FF00) >> 8);
        sendTcpData[19] = (byte) (messageLength & 0x000000FF);
        try {
            byte[] bytes = s.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                sendTcpData[20 + i] = bytes[i];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendTcpData;
    }


    private static String speakContentNow = ""; //上一个播放的语音文字
    private static Date speakdate = new Date();

//    public static  void speak(String levelist0, String speakContent, boolean flush){
//        Date speakdateNow= new Date();
//           if(flush) //特殊场景，需要打断操作,如特殊车辆信号优先应用语音可打断车速引导和闯红灯预警的语音
//            {
//                NewMainActivity.textToSpeech.speak(speakContent, TextToSpeech.QUEUE_FLUSH, null);
//                Log.e("Utils","特殊场景，需要打断操作:"+speakContent);
//            } else{
//              if(NewMainActivity.textToSpeech.isSpeaking()){
//                  return;
//              }
//                NewMainActivity.textToSpeech.speak(speakContent, TextToSpeech.QUEUE_FLUSH,null);
//                //如果相同场景，2秒内再要求播放，刷新播放时间并且消除
////                if(NewMainActivity.warm2.equals(NewMainActivity.warm)&&speakdateNow.getTime()-speakdate.getTime()<2*1000)
////                {
////                    speakdate=speakdateNow;
////                    return;
////                }
////                 //新规则;如果正在读场景不一致忽略否则强制覆盖原有操作，10秒内不再重复相同场景的语音
////                 if((NewMainActivity.textToSpeech.isSpeaking()||speakdateNow.getTime()-speakdate.getTime()<10*1000) && NewMainActivity.warm2.equals(NewMainActivity.warm))
////                {
////                    return;
////                }
////                else
////                {
////                    NewMainActivity.textToSpeech.speak(speakContent, TextToSpeech.QUEUE_FLUSH, null);
////                    //speakContentNow=speakContent;
////                    speakdate=speakdateNow;
////                }
////
////            NewMainActivity.warm2 = NewMainActivity.warm;
//        }
//    }


    public static HashMap<String, Integer[]> laneDistribute = new HashMap<String, Integer[]>() {
        {
            put("6_1", new Integer[]{1, 4});
            put("6_5", new Integer[]{1, 4});
            put("7_5", new Integer[]{1, 4});

            put("9_3", new Integer[]{2, 3});

            put("1_3", new Integer[]{1, 3, 4});
            put("1_7", new Integer[]{1, 3, 4});
            put("6_3", new Integer[]{1, 3, 4});
            put("6_7", new Integer[]{1, 3, 4});
            put("7_1", new Integer[]{1, 3, 4});
            put("8_1", new Integer[]{1, 3, 4});
            put("8_5", new Integer[]{1, 3, 4});
            put("10_1", new Integer[]{1, 3, 4});
            put("10_3", new Integer[]{1, 3, 4});
            put("10_5", new Integer[]{1, 3, 4});
            put("10_7", new Integer[]{1, 3, 4});
            put("11_1", new Integer[]{1, 3, 4});
            put("11_5", new Integer[]{1, 3, 4});
            put("12_1", new Integer[]{1, 3, 4});
            put("12_3", new Integer[]{1, 3, 4});
            put("12_5", new Integer[]{1, 3, 4});
            put("12_7", new Integer[]{1, 3, 4});
            put("13_1", new Integer[]{1, 3, 4});
            put("13_5", new Integer[]{1, 3, 4});
            put("14_1", new Integer[]{1, 3, 4});
            put("14_3", new Integer[]{1, 3, 4});
            put("14_5", new Integer[]{1, 3, 4});
            put("14_7", new Integer[]{1, 3, 4});
            put("15_3", new Integer[]{1, 3, 4});
            put("15_7", new Integer[]{1, 3, 4});

            put("5_3", new Integer[]{1, 1, 5});
            put("9_5", new Integer[]{1, 1, 5});

            put("9_7", new Integer[]{3, 3, 5});

            put("15_5", new Integer[]{1, 3, 5});

            put("2_3", new Integer[]{1, 3, 3, 5});
            put("2_7", new Integer[]{1, 3, 3, 5});
            put("3_3", new Integer[]{1, 3, 3, 5});
            put("3_7", new Integer[]{1, 3, 3, 5});
            put("4_7", new Integer[]{1, 3, 3, 5});
            put("7_7", new Integer[]{1, 3, 3, 5});
            put("8_7", new Integer[]{1, 3, 3, 5});
            put("4_3", new Integer[]{1, 3, 3, 5});

            put("5_1", new Integer[]{1, 3, 3, 3});

            put("1_1", new Integer[]{1, 3, 3, 3, 5});
            put("1_5", new Integer[]{1, 3, 3, 3, 5});
            put("2_1", new Integer[]{1, 3, 3, 3, 5});
            put("2_5", new Integer[]{1, 3, 3, 3, 5});
            put("3_1", new Integer[]{1, 3, 3, 3, 5});
            put("3_5", new Integer[]{1, 3, 3, 3, 5});
            put("4_1", new Integer[]{1, 3, 3, 3, 5});
            put("4_5", new Integer[]{1, 3, 3, 3, 5});
            put("11_3", new Integer[]{1, 3, 3, 3, 5});
            put("13_3", new Integer[]{1, 3, 3, 3, 5});

            put("5_5", new Integer[]{3, 3, 3, 5, 5});

            put("7_3", new Integer[]{1, 1, 3, 3, 5});
            put("8_3", new Integer[]{1, 1, 3, 3, 5});
            put("11_7", new Integer[]{1, 1, 3, 3, 5});
            put("13_7", new Integer[]{1, 1, 3, 3, 5});


        }
    };


//    public static void apngAnimate(Context context, ImageView imageView, int resourImage, int times){
//        // Load form Resource
//        ResourceStreamLoader resourceLoader = new ResourceStreamLoader(context, resourImage);
//        //AssetStreamLoader assetLoader = new AssetStreamLoader(this.getContext(), "avatar_talk_1.png");
//        // Create APNG Drawable
//        APNGDrawable apngDrawable = new APNGDrawable(resourceLoader);
//        // Auto play
//        imageView.setImageDrawable(apngDrawable);
//        // Not needed.default controlled by content
//        apngDrawable.setLoopLimit(times); //-1代表无线循环
////        String uri = "assets://apng/avatar_talk_1.png";
////        ApngImageLoader.getInstance().displayImage(uri, middle_icon);
////        ApngImageLoader.getInstance()
////                .displayApng(uri, middle_icon,
////                        new ApngImageLoader.ApngConfig(10000, true));
//
//
//    }

//    public static int dp2px(float dpValue) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                dpValue, MyApplication.mApplicationContext.getResources().getDisplayMetrics());
//    }

    /**
     * 长半径a=6378137 米
     */
    public static double EARTH_RADIUS = 6378137;


    /**
     * 度换成弧度
     *
     * @param d 度
     * @return 弧度
     */
    public static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 判断是否缺少权限
     *
     * @param mContexts  上下文
     * @param permission 权限名称
     * @return
     */
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    /**
     * 查看服务是否开启
     */
    public static Boolean isServiceRunning(Context context, String serviceName) {
        //获取服务方法  参数 必须用大写的Context！！！
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : infos) {
            String className = info.service.getClassName();
            Log.d("etewtw", "isServiceRunning: " + className);
            if (serviceName.equals(className))
                return true;
        }
        return false;
    }

    //view 转bitmap
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    //    public static LatLng gps2Gaode(double lat, double lon) {
//        double[] latAndLon = LngLonUtil.gps84_To_Gcj02(lat, lon);
//        lat = latAndLon[0];
//        lon = latAndLon[1];
//        LatLng latLng = new LatLng(lat, lon);
//        return latLng;
//    }
    public static String getNumberFromString(String des) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(des);
        return m.replaceAll("").trim();
    }

    public static String readLocalJson(Context context, String fileName) {
        String jsonString = "";
        String resultString = "";
        try {
            InputStream inputStream = context.getResources().getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            resultString = new String(buffer, "GB2312");
        } catch (Exception e) {
            // TODO: handle exception
        }
        return resultString;
    }

    public static JSONObject getJSONObject(String fileName, Context context) {
        try {
            return new JSONObject(getJson(fileName, context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getJson(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String getSn(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return StringgetSerialNumber(context);
        } else {
            return getSerialNumber();
        }
    }
    public static String StringgetSerialNumber(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            try {
                return Build.getSerial();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Build.SERIAL;
    }
    private static String getSerialNumber() {

        String serial = null;

        try {

            Class<?> c = Class.forName("android.os.SystemProperties");

            Method get = c.getMethod("get", String.class);

            serial = (String) get.invoke(c, "ro.serialno");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return serial;

    }
    public static double getDistance(double lng1, double lat1, double lng2, double lat2){
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
    public static String getNowTimeDay() {
        long time = new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        if (time == 0) {
            time = Calendar.getInstance().getTimeInMillis();
        }
        return sdf.format(time);
    }

    public static String getTimeDay(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        if (time == 0) {
            time = Calendar.getInstance().getTimeInMillis();
        }
        return sdf.format(time);
    }

    /**
     * 获取昨天的时间戳
     * @return
     */
    public static long getYesterdayTimestamp() {
        Calendar calendar = Calendar.getInstance();     //当前时间
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        return calendar.getTime().getTime();
    }
}
