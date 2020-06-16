package com.hwc.ablservice.config;

import com.hwc.ablservice.FlowerApplication;
import com.hwc.ablservice.R;

/**
 * @author: hwc
 * date:   On 2020/6/10
 */
public interface AppConfig {

    String APP_NAME = FlowerApplication.getInstance().getResources().getString(R.string.app_name);

}
