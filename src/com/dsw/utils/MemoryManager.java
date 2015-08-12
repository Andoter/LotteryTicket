package com.dsw.utils;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

public class MemoryManager {
	private static final String TAG = "MemoryManager";
	private static final int MAXMEMORY = 36*1024*1024;
	/**
	 * 获取可用内存
	 * @return
	 */
	public static boolean hasAvaluMemory(){
		//��ȡ�ֻ��ڲ��洢�ռ�
		long size = getAvaliableInternalMemory();
		if(size < MAXMEMORY){
			//Ӧ�ô��ڵ��ڴ�
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 获取可用内部内幕承诺
	 * @return
	 */
	public static long getAvaliableInternalMemory(){
		File path = Environment.getDataDirectory();
		StatFs sta = new StatFs(path.getPath());
		long blockSize = sta.getBlockSize();
		long blocks = sta.getAvailableBlocks();
		return blockSize * blocks;
	}
	
	/**
	 * 获取外部内存可用大小
	 * @return
	 */
	public static long getAvaliableExternalMemory(){
		if(hasExternalSD()){
			File path = Environment.getExternalStorageDirectory();
			StatFs statFs = new StatFs(path.getPath());
			long blockSize = statFs.getBlockSize();
			long blocks = statFs.getBlockCount();
			return blocks * blockSize;
		}else {
			throw new RuntimeException("Don't have sdcard.");
		}
	}
	
	
	public static boolean hasExternalSD(){
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
}
