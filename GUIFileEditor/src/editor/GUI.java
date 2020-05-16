package editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
/**
 * @author 李昕
 * 总的窗口
 */
public class GUI extends JFrame{
	/**
	 * 所有显示内容的管理器，由总管理器GUIShowControler控制
	 */
	private GUIShowControler guiShowControler=new GUIShowControler();
	/**
	 * 总的管理器也是监听器
	 */
	private GUIDoControler guiDoControler=new GUIDoControler(guiShowControler); 
	//各个面板加入监听器
	private JPanel_do jp_do = new JPanel_do(guiDoControler);
	private JPanel_show jp_show = new JPanel_show(guiDoControler);
	private JPanel_info jp_info = new JPanel_info(guiDoControler);
	GUI(){
		//进行基本的窗口设置，并加入各个面板
		this.setTitle("文件管理系统");
		JFrame.setDefaultLookAndFeelDecorated(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().add("North", jp_do);
        this.getContentPane().add("Center", jp_show);
        this.getContentPane().add("South", jp_info);
	}
	//总进入口
	public static void main(String[] args) {
		GUI gui=new GUI();
		gui.setVisible(true);
		gui.pack();
	}
}
