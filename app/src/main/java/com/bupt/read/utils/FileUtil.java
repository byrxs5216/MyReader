package com.bupt.read.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import android.graphics.Bitmap;

public class FileUtil
{
	public static String filePath = android.os.Environment.getExternalStorageDirectory() + "/Reader";

	public static String getFileName(String str)
	{
		str = str.replaceAll("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]", "");
		System.out.println("filename = " + str);
		return str + ".png";
	}


	public static boolean saveToSDcard(String fileName, Bitmap bmp)
	{
		try
		{
			File file = new File(filePath);

			if (!file.exists())
			{
				file.mkdirs();
			}
			File imgFile = new File(filePath + "/" + getFileName(fileName));
			if (imgFile.exists())
			{
				return true;
			}
			InputStream is = bitmap2InputStream(bmp);
			FileOutputStream fileOutputStream = new FileOutputStream(imgFile);
			byte[] buffer = new byte[512];
			int count = 0;
			while ((count = is.read(buffer)) > 0)
			{
				fileOutputStream.write(buffer, 0, count);
			}
			fileOutputStream.flush();
			fileOutputStream.close();
			is.close();
			System.out.println("writeToSD success");
		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("writeToSD fail");
			return false;
		}
		return true;
	}


	public static byte[] bitmap2Bytes(Bitmap bm)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}


	public static InputStream bitmap2InputStream(Bitmap bm)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// Write a compressed version of the bitmap to the specified
		// outputstream.
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	/**
	 * 复制文件
	 *
	 * @param src 源文件
	 * @param dst 目标文件
	 * @return
	 */
	public static boolean copyTo(File src, File dst) {

		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(src);
			fo = new FileOutputStream(dst);
			in = fi.getChannel();//得到对应的文件通道
			out = fo.getChannel();//得到对应的文件通道
			in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {

				if (fi != null) {
					fi.close();
				}

				if (in != null) {
					in.close();
				}

				if (fo != null) {
					fo.close();
				}

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

	}
}
