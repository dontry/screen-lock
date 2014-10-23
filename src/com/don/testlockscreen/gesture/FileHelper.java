package com.don.testlockscreen.gesture;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.os.Environment;
public class FileHelper {
	private static String SDCardPath = null; // SD����Android�ļ�ϵͳ�е�·��
	public String getPath() {
		return SDCardPath;
	}
	/**
	 * ���췽������ȡSD����Android�ļ�ϵͳ�е�·��
	 */
	public FileHelper() {
		SDCardPath = Environment.getExternalStorageDirectory() + "/";
	}
	/**
	 * ��SD���ϴ����ļ�
	 * 
	 * @param fileName
	 *            Ҫ�������ļ���
	 * @return �����õ����ļ�
	 */
	public File createSDFile(String fileName) throws IOException {
		File file = new File(SDCardPath + fileName);
		file.createNewFile();
		return file;
	}
	/**
	 * ��SD���ϴ���Ŀ¼
	 * 
	 * @param dirName
	 *            Ҫ������Ŀ¼��
	 * @return �����õ���Ŀ¼
	 */
	public File createSDDir(String dirName) {
		File dir = new File(SDCardPath + dirName);
		dir.mkdir();
		return dir;
	}
	/**
	 * �ж��ļ��Ƿ��Ѿ�����
	 * 
	 * @param fileName
	 *            Ҫ�����ļ���
	 * @return boolean, true��ʾ���ڣ�false��ʾ������
	 */
	public  boolean isFileExist(String fileName) {
		File file = new File(SDCardPath + fileName);
		return file.exists();
	}
	/**
	 * ��һ���������е�����д�뵽SD���������ļ�
	 * 
	 * @param path
	 *            �ļ�Ŀ¼
	 * @param fileName
	 *            �ļ���
	 * @param inputStream
	 *            �ֽ�������
	 * @return �õ����ļ�
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
