package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
/**
 * @author 李昕
 * 文件的右键菜单，方便地添加删除按钮，类似MenuDir，不详细说明
 */
public class MenuFile extends JPopupMenu{

	private JMenuItem menuItemFileCopy = new JMenuItem("复制文件");
	private JMenuItem menuItemFileDel = new JMenuItem("删除文件");
	private JMenuItem menuItemFileEncry = new JMenuItem("加密文件");
	private JMenuItem menuItemFileDecode = new JMenuItem("解密文件");
	private JMenuItem menuItemFileCompress = new JMenuItem("压缩文件");
	private JMenuItem menuItemFileUncompress = new JMenuItem("解压缩文件");
	public MenuFile(ActionListener actionListenerMenu) {
		// TODO Auto-generated constructor stub
		add(menuItemFileCopy);
		add(menuItemFileDel);
		add(menuItemFileCompress);
		add(menuItemFileUncompress);
		add(menuItemFileEncry);
		add(menuItemFileDecode);
		menuinit(actionListenerMenu);
	}
	private void menuinit(ActionListener actionListenerMenu) {
		// TODO Auto-generated method stub
		menuItemFileCopy.addActionListener(actionListenerMenu);
		menuItemFileEncry.addActionListener(actionListenerMenu);
		menuItemFileDecode.addActionListener(actionListenerMenu);
		menuItemFileCompress.addActionListener(actionListenerMenu);
		menuItemFileUncompress.addActionListener(actionListenerMenu);
		menuItemFileDel.addActionListener(actionListenerMenu);
	}
}
