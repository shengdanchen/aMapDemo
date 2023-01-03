package com.example.commonlibrary.units;

import android.text.TextUtils;


import com.example.commonlibrary.base.BaseApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 将Log日志写入文件中
 * 根路径:
 * 如果外部存储可用,存在/mnt/sdcard/Android/data/包名/
 * 如果外部存储不可用,存/data/data/包名/Logs
 */
public class LogToFile {
    private static final String TAG = LogToFile.class.getSimpleName();

    public static final String PER_TENMIN_FILE_NAME_FORMAT = "yyyy_MM_dd_HH_mm";
    public static final String PER_HOUR_FILE_NAME_FORMAT = "yyyy_MM_dd_HH";
    public static final String PER_DAY_FILE_NAME_FORMAT = DateUtils.Format.Y_M_d.getFormatStr();

    private static final String LOGS_PARENT_PATH =
            FileUtils.getFilePath(BaseApplication.getInstance().getApplicationContext()) + "/Logs/";

    public static final String LOG_FILE_PREFIX = "log_";

    private static final char VERBOSE = 'V';

    private static final char DEBUG = 'D';

    private static final char INFO = 'I';

    private static final char WARN = 'W';

    private static final char ERROR = 'E';

    //    private static ArrayList<String> cachedLog = new ArrayList<>();


    private LogToFile() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    public static void v(String tag, String msg) {
        writeToFile(VERBOSE, tag, msg);
    }


    public static void d(String tag, String msg) {
        writeToFile(DEBUG, tag, msg);
    }


    public static void i(String tag, String msg) {
        writeToFile(INFO, tag, msg);
    }


    public static void i(String tag, String msg, String logPath) {
        writeToFile(INFO, tag, msg, logPath, getLogFileNamePerHour());
    }


    public static void i(String tag, String msg, String logPath, String fileName) {
        writeToFile(INFO, tag, msg, logPath, fileName);
    }


    public static void w(String tag, String msg) {
        writeToFile(WARN, tag, msg);
    }


    public static void e(String tag, String msg) {
        writeToFile(ERROR, tag, msg);
    }


    public static void e(String tag, String msg, String logPath) {
        writeToFile(ERROR, tag, msg, logPath, getLogFileNamePerHour());
    }


    public static void e(String tag, String msg, String logPath, String fileName) {
        writeToFile(ERROR, tag, msg, logPath, fileName);
    }


    /**
     * 根据当前10分钟产生一个日志文件名
     */
    public static String getLogFileNamePerTenMin() {
        String perTen = DateUtils.formatDate(PER_TENMIN_FILE_NAME_FORMAT, new Date());
        perTen = perTen.substring(0, perTen.length() - 1);
        return LOG_FILE_PREFIX + perTen + ".log";
    }


    /**
     * 根据当前小时产生一个日志文件名
     */
    public static String getLogFileNamePerHour() {
        return LOG_FILE_PREFIX + DateUtils.formatDate(PER_HOUR_FILE_NAME_FORMAT, new Date()) +
                ".log";
    }


    /**
     * 根据当前日期产生一个日志文件名
     */
    public static String getLogFileNamePerDay() {
        return LOG_FILE_PREFIX + DateUtils.formatDate(PER_DAY_FILE_NAME_FORMAT, new Date()) +
                ".log";
    }


    /**
     * 根据当前时间和传入的format产生一个日志文件名
     */
    public static String getLogFileNameFormat(String format) {
        return LOG_FILE_PREFIX + DateUtils.formatDate(format, new Date()) + ".log";
    }


    /**
     * 获得文件储存路径,轮训log文件夹
     */
    public static String getLogFileLoopRQPath() {
        return LOGS_PARENT_PATH + "loopRQ/";
    }


    /**
     * 获得文件储存路径,轮训log文件夹
     */
    public static String getLogFileBankPath() {
        return LOGS_PARENT_PATH + "bank/";
    }


    /**
     * 获得文件储存路径,默认log文件夹
     */
    public static String getLogFileCommonPath() {
        return LOGS_PARENT_PATH + "common/";
    }


    /**
     * 使用默认的文件路径和文件名将log信息写入文件中
     */
    private static void writeToFile(char type, String tag, String msg) {
        if (!TextUtils.isEmpty(LOGS_PARENT_PATH)) {
            writeToFile(type, tag, msg, getLogFileCommonPath(), "exceptionLog.log");
        }
    }


    public static void writeToFile(char type, String tag, String msg, String filePath, String fileName) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }

        final String logContent = "\n" +
                DateUtils.formatDate(DateUtils.Format.H_m_s_f.getFormatStr(), new Date()) + " " +
                type + "-" + tag + ":" + msg;
        //        cachedLog.add(logContent);

        if (TextUtils.isEmpty(filePath)) {
            LogUtils.debug(TAG, "filePath == null");
            return;
        }
        if (TextUtils.isEmpty(fileName)) {
            LogUtils.debug(TAG, "fileName == null");
            return;
        }

        //如果父路径不存在
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();//创建父路径
        }

        final String fileAbsoluteName = filePath + fileName;

        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fos;//FileOutputStream会自动调用底层的close()方法，不用关闭
                BufferedWriter bw = null;
                try {
                    fos = new FileOutputStream(fileAbsoluteName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
                    bw = new BufferedWriter(new OutputStreamWriter(fos));
                    bw.write(logContent);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();//关闭缓冲流
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public static void readFromFile(final boolean readAll, String filePath, String fileName, final ReadFileFinishCallback callback) {
        readFromFile(readAll, filePath, fileName, 30_000, callback);
    }


    public static void readFromFile(final boolean readAll, String filePath, String fileName, final int endIndex, final ReadFileFinishCallback callback) {
        if (TextUtils.isEmpty(filePath)) {
            LogUtils.debug(TAG, "filePath == null");
            return;
        }

        final String fileAbsoluteName = filePath + fileName;
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                String content;
                if (readAll) {
                    content = FileUtils.readFileAll(fileAbsoluteName);
                } else {
                    content = FileUtils.readFileFromEnd(fileAbsoluteName, endIndex, "UTF-8");
                }
                observableEmitter.onNext(content);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Consumer<String>() {
                      @Override
                      public void accept(String contentStr) throws Exception {
                          callback.finishCallback(contentStr);
                      }
                  });
    }


    public interface ReadFileFinishCallback {
        void finishCallback(String content);
    }


    /**
     * 返回某文件夹下的以log_开头的文件创建时间大于当前时间 intervalTime时间间隔 的文件路径
     *
     * @param intervalTime 以小时为单位的int时间间隔
     * path 文件夹路径
     * @throws NullPointerException
     * @throws IllegalAccessException
     */
    public static List<String> matchLogFileNameByTime(long intervalTime, String filePath, String fileFormat, String fileNamePrefix) throws Exception {
        if (TextUtils.isEmpty(filePath)) {
            throw new NullPointerException("filePath 不能为空");
        }

        // get file list where the path has
        File file = new File(filePath);
        if (!file.isDirectory()) {
            throw new IllegalAccessException("没有" + filePath + "这个目录");
        }

        // get the folder list
        File[] array = file.listFiles();

        if (array == null || array.length == 0) {
            throw new NullPointerException(filePath + "目录下没有文件");
        }

        List<String> filePathList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                String[] name = array[i].getName().split(fileNamePrefix);
                if (name.length >= 2) {
                    try {
                        long formatTime = DateUtils
                                .stringFormatInt(fileFormat, name[1].replace(".log", ""));
                        if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) -
                                formatTime >= TimeUnit.HOURS.toSeconds(intervalTime)) {
                            filePathList.add(array[i].getCanonicalPath());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } else if (array[i].isDirectory()) {
                matchLogFileNameByTime(intervalTime, array[i]
                        .getParent(), fileFormat, fileNamePrefix);
            }
        }
        return filePathList;
    }


    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }


    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete(); // 删除所有文件
            } else if (file.isDirectory()) {
                deleteDirWihtFile(file); // 递规的方式删除文件夹
            }
        }
        dir.delete();// 删除目录本身
    }


    static class LogModel {
        char type;
        String tag;
        String msg;
        String filePath;
        String fileName;
    }
}
