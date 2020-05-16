package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author ���
 * ����JList��JLabel������Ⱦ����ʾΪ�ļ����ļ���ͼ������ֵ���ʽ
 * ���������֡����͡�ͼ��
 */
public class FileLabel extends JLabel{
	/**
	 * ����
	 */
	public String name;
	/**
	 * ����
	 */
	public int type;
	/**
	 * ͼ��
	 */
	public ImageIcon u;
	/**
	 * @param type 1Ϊ�ļ�0Ϊ�ļ���
	 * @param name �ļ����ļ�����
	 * 
	 */
	FileLabel(int type,String name){
		this.type=type;
		//ͼ���·�����ڹ����ļ����£�����IDE���ܲ�ͬ
		String picPath = ".\\"+(type==1?"�ļ�":"�ļ���")+".jpg";
		u=new ImageIcon(picPath);
		this.name=name;
	}
	//���Դ���
	public static void main(String[] args) {
		JFrame frame = new JFrame("��JLabel��ǩ������ͼƬ");
		FileLabel f1=new FileLabel(0, "�ļ���1");
		FileLabel f2=new FileLabel(1, "�ļ�1");
		frame.add(f1);
		frame.add(f2);
		frame.getContentPane().setBackground(Color.WHITE);
        //���ô��ڿ�Ⱥ͸߶�
        Dimension dim = new Dimension();
        dim.setSize(800,600 );
        frame.setSize(dim);
        //���ô��������
        Point point = new Point(100, 100); // ��������
        frame.setLocation(point);
        frame.setVisible(true);
		frame.pack();
	}
}
