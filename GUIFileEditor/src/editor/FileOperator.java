package editor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
/**
 * @author ���
 * ����ļ�ճ�������ܽ��ܡ�ѹ����ѹ���Ȳ����Ĺ����ֻ࣬�о�̬������
 */
public class FileOperator {
//�ο���Դhttps://blog.csdn.net/weixin_33961829/article/details/91948087
	/**
	 * Ĭ������
	 */
	static String DEFAULTKEY="defaultkey";
	/**
	 * @param fileToDel Ҫɾ�����ļ�
	 */
	public static Boolean deleteFiles(File fileToDel) throws IOException {
		//System.out.print("����ɾ������");
		//Ϊ�ļ�ֱ��ɾ����Ϊ�ļ���ɾ����������������ɾ����
		if (fileToDel.isFile()) {
			fileToDel.delete();
		} else {
			File[] files = fileToDel.listFiles();
			if (files == null) {
				fileToDel.delete();
			} else {
				for (int i = 0; i < files.length; i++) {
					deleteFiles(files[i]);
				}
				fileToDel.delete();
			}
		}
		return true;
	}
	/**
	 * @param fileCopied Դ�ļ�����֤�����
	 * @param dir Ŀ���ļ���
	 */
	public static boolean Paste(File fileCopied, File dir) {
		//Ϊ�ļ�ֱ��fileCopy��Ϊ�ļ����ȴ����䣬�ٵ���dirCopy
		if (fileCopied.isDirectory()) {
			File newdir = new File(dir.getAbsoluteFile() + File.separator + fileCopied.getName());
			newdir.mkdir();
			dirCopy(fileCopied.getAbsolutePath(), newdir.getAbsolutePath());
		} else
			fileCopy(fileCopied.getAbsolutePath(), dir.getAbsolutePath() + File.separator + fileCopied.getName());
		return true;
	}
	/**
	 * @param srcPath Դ�ļ��У���֤�����
	 * @param destPath Ŀ���ļ���
	 * �����ƴ��ļ��У�ֻ�����ļ����µ����ݣ����Pasteʹ��
	 */
	public static void dirCopy(String srcPath, String destPath) {
		File src = new File(srcPath);
		if (!new File(destPath).exists()) {
			new File(destPath).mkdirs();
		}
		for (File s : src.listFiles()) {
			if (s.isFile()) {
				fileCopy(s.getPath(), destPath + File.separator + s.getName());
			} else {
				dirCopy(s.getPath(), destPath + File.separator + s.getName());
			}
		}
	}
	/**
	 * @param srcPath Դ�ļ�����֤�����
	 * @param destPath Ŀ���ļ���
	 * ����Դ�ļ���Ŀ���ļ���
	 */
	public static void fileCopy(String srcPath, String destPath) {
		File src = new File(srcPath);
		File dest = new File(destPath);
		try (InputStream is = new BufferedInputStream(new FileInputStream(src));
				OutputStream out = new BufferedOutputStream(new FileOutputStream(dest))) {
			byte[] flush = new byte[1024];
			int len = -1;
			while ((len = is.read(flush)) != -1) {
				out.write(flush, 0, len);
			}
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param strKey ��ת����Key
	 * ʵ��Key��ת��
	 */
    public static Key getKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(strKey.getBytes());
            _generator.init(secureRandom);
            Key key = _generator.generateKey();
            _generator = null;
            return key;
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
    }
    
    /**
	 * @param file Ҫ���ܵ��ļ�
	 * @param destFile ���ܺ���ļ�
	 * @param strKey ����
	 * @param useDefaultKey �Ƿ�ʹ��Ĭ������
	 * ʹ��DES���ܷ��������ļ�
	 */
    public static void encrypt(String file, String destFile, String strKey,boolean useDefaultKey) throws Exception {
    	//useDefaultKeyΪ��ʹ��Ĭ������
    	if(useDefaultKey) strKey=DEFAULTKEY;
    	//���ڼ��ܵ���
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, getKey(strKey));
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(destFile);
        //���ܺ�������
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }
    /**
	 * @param file Ҫ���ܵ��ļ�
	 * @param dest ���ܺ���ļ�
	 * @param strKey ����
	 * @param useDefaultKey �Ƿ�ʹ��Ĭ������
	 * ʹ��DES���ܷ��������ļ�
	 */
    public static void decrypt(String file, String dest, String strKey,boolean useDefaultKey) throws Exception {
        //����ͬ����
    	if(useDefaultKey) strKey=DEFAULTKEY;
    	Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, getKey(strKey));
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(dest);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = is.read(buffer)) >= 0) {
            cos.write(buffer, 0, r);
        }
        cos.close();
        out.close();
        is.close();
    }
    /**
     * ѹ������
     * @param path ѹ���ļ����ļ���·��
     * @param ZipPath ѹ�����ļ�·��
     * @throws IOException
     */
    public static void zip(String path,String ZipPath) throws IOException {
        File file = new File(path);
        File zipFile = new File(ZipPath);
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
        zipTool(zos, file, file.getName());
        zos.flush();
        zos.close();
    }
    /**
     * 
     * @param zos
     *            ѹ�������
     * @param file
     *            ��ǰ��Ҫѹ�����ļ�
     * @param path
     *            ��ǰ�ļ������ѹ���ļ��е�·��
     * @throws IOException
     * ���zip����ʹ��
     */
    private static void zipTool(ZipOutputStream zos, File file, String path) throws IOException {
        // �ж����ļ��������ļ��У��ļ�ֱ��д��Ŀ¼����㣬�ļ��������
        if (file.isDirectory()) {
            ZipEntry entry = new ZipEntry(path + File.separator);// �ļ��е�Ŀ¼�������������Ʒָ�����β
            zos.putNextEntry(entry);
            File[] files = file.listFiles();
            for (File x : files) {
                zipTool(zos, x, path + File.separator + x.getName());
            }
        } else {
            FileInputStream fis = new FileInputStream(file);// Ŀ¼�������������ļ���ѹ���ļ��е�·��
            ZipEntry entry = new ZipEntry(path);
            zos.putNextEntry(entry);// ����һ��Ŀ¼�����

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.flush();
            fis.close();
            zos.closeEntry();// �رյ�ǰĿ¼����㣬���������ƶ���һ��Ŀ¼�����
        }
    }
    /**
     * @param zipPath Ҫ��ѹ���ļ�
     * @param descDir Ŀ���ļ���
     * ��ѹ�ļ���ָ��Ŀ¼
     */
    @SuppressWarnings({ "rawtypes", "resource" })
  //�ο���https://blog.csdn.net/qq_17522211/java/article/details/84579883
    public static void unZipFiles(String zipPath, String descDir) throws IOException {
    		File zipFile = new File(zipPath);
    		if(!zipFile.exists()){
    			throw new IOException("���ѹ�ļ�������.");
    		}
    		File pathFile = new File(descDir);
    		if (!pathFile.exists()) {
    			pathFile.mkdirs();
    		}
    		ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
    		for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
    			ZipEntry entry = (ZipEntry) entries.nextElement();
    			String zipEntryName = entry.getName();
    			InputStream in = zip.getInputStream(entry);
    			String outPath = (descDir + File.separator + zipEntryName).replaceAll("\\*", "/");
    			// �ж�·���Ƿ����,�������򴴽��ļ�·��
    			File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
    			if (!file.exists()) {
    				file.mkdirs();
    			}
    			// �ж��ļ�ȫ·���Ƿ�Ϊ�ļ���,����������Ѿ��ϴ�,����Ҫ��ѹ
    			if (new File(outPath).isDirectory()) {
    				continue;
    			}
    			OutputStream out = new FileOutputStream(outPath);
    			byte[] buf1 = new byte[1024];
    			int len;
    			while ((len = in.read(buf1)) > 0) {
    				out.write(buf1, 0, len);
    			}
    			in.close();
    			out.close();
    		}
    }
}




