package com.don.testlockscreen.gesture;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.os.Environment;
public class FileHelper {
	private static String SDCardPath = null; // SD卡在Android文件系统中的路径
	public String getPath() {
		return SDCardPath;
	}
	/**
	 * 构造方法，获取SD卡在Android文件系统中的路径
	 */
	public FileHelper() {
		SDCardPath = Environment.getExternalStorageDirectory() + "/";
	}
	/**
	 * 在SD卡上创建文件
	 * 
	 * @param fileName
	 *            要创建的文件名
	 * @return 创建得到的文件
	 */
	public File createSDFile(String fileName) throws IOException {
		File file = new File(SDCardPath + fileName);
		file.createNewFile();
		return file;
	}
	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 *            要创建的目录名
	 * @return 创建得到的目录
	 */
	public File createSDDir(String dirName) {
		File dir = new File(SDCardPath + dirName);
		dir.mkdir();
		return dir;
	}
	/**
	 * 判断文件是否已经存在
	 * 
	 * @param fileName
	 *            要检查的文件名
	 * @return boolean, true表示存在，false表示不存在
	 */
	public  boolean isFileExist(String fileName) {
		File file = new File(SDCardPath + fileName);
		return file.exists();
	}
	/**
	 * 将一个输入流中的内容写入到SD卡上生成文件
	 * 
	 * @param path
	 *            文件目录
	 * @param fileName
	 *            文件名
	 * @param inputStream
	 *            字节输入流
	 * @return 得到的文件
	 */
	public File writeToSDCard(String path, String fileName,
			InputStream inputStream) {
		File file = null;
		OutputStream output = null;
		try{
			createSDDir(path);
			file = createSDFile(path + fileName);
			output = new FileOutputStream(file);
			byte buffer [] = new byte[4 * 1024];
			while((inputStream.read(buffer)) != -1){
				output.write(buffer);
			}
			output.flush();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				output.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return file;
	}
}
