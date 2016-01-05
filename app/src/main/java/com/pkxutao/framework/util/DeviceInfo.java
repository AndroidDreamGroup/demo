package com.pkxutao.framework.util;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.File;

/**
 * 获取设备信息
 * 
 * @author Daniel
 * 
 */
public class DeviceInfo {

	private static final String[] ANDROID_VERSIONS = { "Android", "1.0", "1.1", "1.5", "1.6", "2.0", "2.0.1", "2.1", "2.2", "2.3", "2.3.3", "3.0",
			"3.1", "3.2", "4.0", "4.0.3", "4.1", "4.2", "4.3", "4.4" };

	/**
	 * 打印设备信息
	 * @param context
	 * @param tag
	 */
	public static void printDeviceInfo(Context context, String tag) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("DeviceInfo:start=======================================\n");
		stringBuffer.append(getDeviceInfo(context));
		stringBuffer.append("DeviceInfo:end=========================================");
		LogUtil.d(tag, stringBuffer.toString());
	}

	/**
	 * 获取设备信息
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceInfo(Context context) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("deviceModel:\t\t" + getDeviceModel()).append("\n");
		stringBuffer.append("deviceId:\t\t" + getDeviceId(context)).append("\n");
		stringBuffer.append("androidID:\t\t" + getAndroidID(context)).append("\n");
		stringBuffer.append("phoneNumber:\t\t" + getPhoneNumber(context)).append("\n");
		stringBuffer.append("sdkVersion:\t\t" + getSdkVersion()).append("\n");
		stringBuffer.append("androidVersion:\t\t" + getAndroidVersion()).append("\n");
		stringBuffer.append("isSDCardAvailable:\t\t" + isSDCardAvailable()).append("\n");
		stringBuffer.append("SDCardPath:\t\t" + getSDCardPath()).append("\n");
		stringBuffer.append("Width*Height:\t\t" + getScreenWidth(context)).append("*").append(getScreenHeight(context)).append("\n");
		stringBuffer.append("density:\t\t" + getDensity(context)).append("\n");
		stringBuffer.append("vmHeapSize:\t\t" + getVmHeapSize(context) + "MB").append("\n");
		stringBuffer.append("NetworkTypeName:\t\t" + getNetworkInfoTypeName(context)).append("\n");
		return stringBuffer.toString();
	}

	/**
	 * 获取屏幕密度
	 * @param context
	 * @return
	 */
	public static float getDensity(Context context) {
		DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
		return displayMetrics.density;
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
		return displayMetrics.widthPixels;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
		return displayMetrics.heightPixels;
	}

	/**
	 * 获取设备型号
	 * 
	 * @return
	 */
	public static String getDeviceModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取设备id（GSM手机的 IMEI 和 CDMA手机的 MEID）
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = telephonyManager.getDeviceId();
		return deviceId == null ? "0" : deviceId;
	}

	/**
	 * 获取sdk版本号
	 * 
	 * @return
	 */
	public static int getSdkVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * app能够使用的最大内存
	 * 
	 * @param context
	 * @return
	 */
	public static int getVmHeapSize(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		return activityManager.getMemoryClass();
	}

	/**
	 * 获取当前使用的网络类型名称
	 * 
	 *
	 * @param context
	 * @return
	 */
	public static String getNetworkInfoTypeName(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nwInfo = connManager.getActiveNetworkInfo();
		return nwInfo == null ? "null" : nwInfo.getTypeName();
	}

	/**
	 * 获取android版本（目前最高支持4.4）
	 * 
	 * @return
	 */
	public static String getAndroidVersion() {
		int sdk = getSdkVersion();
		if (sdk < ANDROID_VERSIONS.length) {
			return ANDROID_VERSIONS[sdk];
		} else {
			return "Android-" + sdk;
		}
	}

	/**
	 * 获取手机号码
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String phoneId = telephonyManager.getLine1Number();
		return phoneId == null ? "0" : phoneId;
	}

	/**
	 * SD卡是否有效
	 * 
	 * @return
	 */
	public static boolean isSDCardAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取SD卡的路径
	 * 
	 * @return SD卡可用 返回路径 否则返回 ""
	 */
	public static String getSDCardPath() {
		if (isSDCardAvailable()) {
			return Environment.getExternalStorageDirectory().toString();
		} else {
			return "";
		}
	}

	/**
	 * 获取android_id
	 * 
	 * @return
	 */
	public static String getAndroidID(Context context) {
		return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
	}
	
	/**
	 * 获取sd卡总大小
	 * 
	 * @return
	 */
	public static long getSDCardTotalSize() {
		if (isSDCardAvailable()) {
			File sdFile = Environment.getExternalStorageDirectory();
			StatFs statfs = new StatFs(sdFile.getPath());
			long blockCount = statfs.getBlockCount();
			long blockSize = statfs.getBlockSize();
			return blockCount * blockSize;
		}
		return 0L;
	}

	/**
	 * 获取sd卡可用空间
	 * 
	 * @return
	 */
	public static long getSDCardAvailableSize() {
		if (isSDCardAvailable()) {
			File sdFile = Environment.getExternalStorageDirectory();
			StatFs statfs = new StatFs(sdFile.getPath());
			long availableBlocks = statfs.getAvailableBlocks();
			long blockSize = statfs.getBlockSize();
			return availableBlocks * blockSize;
		}
		return 0L;
	}
}
