package com.example.commonlibrary.base;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.commonlibrary.http.model.ResErrorEntity;
import org.jetbrains.annotations.NotNull;

/**
 * author : ChenShengDan
 * date   : 2021/7/26
 * desc   : vm基类
 */
public class BaseViewModel extends AndroidViewModel {

    public MutableLiveData<ResErrorEntity> getResErrorEntityMutableLiveData() {
        return resErrorEntityMutableLiveData;
    }

    /**
     * 错误的数据返回
     */
    protected MutableLiveData<ResErrorEntity> resErrorEntityMutableLiveData = new MutableLiveData<>();

    public BaseViewModel(@NonNull @NotNull Application application) {
        super(application);
    }
}
