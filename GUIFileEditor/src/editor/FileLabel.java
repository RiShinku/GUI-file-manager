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
 * @author 李昕
 * 用于JList的JLabel，经渲染后显示为文件或文件夹图标加名字的形式
 * 包含了名字、类型、图标
 */
public class FileLabel extends JLabel{
	/**
	 * 名字
	 */
	public String name;
	/**
	 * 类型
	 */
	public int type;
	/**
	 * 图标
	 */
	public ImageIcon u;
	/**
	 * @param type 1为文件0为文件夹
	 * @param name 文件或文件夹名
	 * 
	 */
	FileLabel(int type,String name){
		this.type=type;
		//图标的路径，在工程文件夹下，根据IDE可能不同
		String picPath = ".\\"+(type==1?"文件":"文件夹")+".jpg";
		u=new ImageIcon(picPath);
		this.name=name;
	}
	//测试此类
	public static void main(String[] args) {
		JFrame frame = new JFrame("在JLabel标签上设置图片");
		FileLabel f1=new FileLabel(0, "文件夹1");
		FileLabel f2=new FileLabel(1, "文件1");
		frame.add(f1);
		frame.add(f2);
		frame.getContentPane().setBackground(Color.WHITE);
        //设置窗口宽度和高度
        Dimension dim = new Dimension();
        dim.setSize(800,600 );
        frame.setSize(dim);
        //设置窗体的坐标
        Point point = new Point(100, 100); // 设置坐标
        frame.setLocation(point);
        frame.setVisible(true);
		frame.pack();
	}
}
