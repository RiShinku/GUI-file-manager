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
 * @author 李昕
 * 完成文件粘贴、加密解密、压缩解压缩等操作的工具类，只有静态方法。
 */
public class FileOperator {
//参考来源https://blog.csdn.net/weixin_33961829/article/details/91948087
	/**
	 * 默认密码
	 */
	static String DEFAULTKEY="defaultkey";
	/**
	 * @param fileToDel 要删除的文件
	 */
	public static Boolean deleteFiles(File fileToDel) throws IOException {
		//System.out.print("进入删除函数");
		//为文件直接删除，为文件夹删除下面所有内容再删除其
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
	 * @param fileCopied 源文件，保证其存在
	 * @param dir 目标文件夹
	 */
	public static boolean Paste(File fileCopied, File dir) {
		//为文件直接fileCopy，为文件夹先创建其，再调用dirCopy
		if (fileCopied.isDirectory()) {
			File newdir = new File(dir.getAbsoluteFile() + File.separator + fileCopied.getName());
			newdir.mkdir();
			dirCopy(fileCopied.getAbsolutePath(), newdir.getAbsolutePath());
		} else
			fileCopy(fileCopied.getAbsolutePath(), dir.getAbsolutePath() + File.separator + fileCopied.getName());
		return true;
	}
	/**
	 * @param srcPath 源文件夹，保证其存在
	 * @param destPath 目标文件夹
	 * 不复制此文件夹，只复制文件夹下的内容，配合Paste使用
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
	 * @param srcPath 源文件，保证其存在
	 * @param destPath 目标文件夹
	 * 复制源文件至目标文件夹
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
	 * @param strKey 待转换的Key
	 * 实现Key的转换
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
	 * @param file 要加密的文件
	 * @param destFile 加密后的文件
	 * @param strKey 密码
	 * @param useDefaultKey 是否使用默认密码
	 * 使用DES加密方法加密文件
	 */
    public static void encrypt(String file, String destFile, String strKey,boolean useDefaultKey) throws Exception {
    	//useDefaultKey为真使用默认密码
    	if(useDefaultKey) strKey=DEFAULTKEY;
    	//用于加密的类
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, getKey(strKey));
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(destFile);
        //加密后输入流
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
	 * @param file 要解密的文件
	 * @param dest 解密后的文件
	 * @param strKey 密码
	 * @param useDefaultKey 是否使用默认密码
	 * 使用DES加密方法解密文件
	 */
    public static void decrypt(String file, String dest, String strKey,boolean useDefaultKey) throws Exception {
        //基本同加密
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
     * 压缩函数
     * @param path 压缩文件或文件夹路径
     * @param ZipPath 压缩后文件路径
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
     *            压缩输出流
     * @param file
     *            当前需要压缩的文件
     * @param path
     *            当前文件相对于压缩文件夹的路径
     * @throws IOException
     * 配合zip函数使用
     */
    private static void zipTool(ZipOutputStream zos, File file, String path) throws IOException {
        // 判断是文件，还是文件夹，文件直接写入目录进入点，文件夹则遍历
        if (file.isDirectory()) {
            ZipEntry entry = new ZipEntry(path + File.separator);// 文件夹的目录进入点必须以名称分隔符结尾
            zos.putNextEntry(entry);
            File[] files = file.listFiles();
            for (File x : files) {
                zipTool(zos, x, path + File.separator + x.getName());
            }
        } else {
            FileInputStream fis = new FileInputStream(file);// 目录进入点的名字是文件在压缩文件中的路径
            ZipEntry entry = new ZipEntry(path);
            zos.putNextEntry(entry);// 建立一个目录进入点

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.flush();
            fis.close();
            zos.closeEntry();// 关闭当前目录进入点，将输入流移动下一个目录进入点
        }
    }
    /**
     * @param zipPath 要解压的文件
     * @param descDir 目标文件夹
     * 解压文件到指定目录
     */
    @SuppressWarnings({ "rawtypes", "resource" })
  //参考：https://blog.csdn.net/qq_17522211/java/article/details/84579883
    public static void unZipFiles(String zipPath, String descDir) throws IOException {
    		File zipFile = new File(zipPath);
    		if(!zipFile.exists()){
    			throw new IOException("需解压文件不存在.");
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
    			// 判断路径是否存在,不存在则创建文件路径
    			File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
    			if (!file.exists()) {
    				file.mkdirs();
    			}
    			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
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




