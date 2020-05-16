package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
/**
 * @author 李昕
 * 文件夹的右键菜单，方便地添加删除按钮
 */
class MenuDir extends JPopupMenu{
	private JMenuItem menuItemDirCopy = new JMenuItem("复制文件夹");
	private JMenuItem menuItemDirDelete = new JMenuItem("删除文件夹");
	private JMenuItem menuItemDirIn = new JMenuItem("进入文件夹");
	//上一级文件夹不应由此菜单调用，否则没有文件夹就无法返回上级
	//private JMenuItem menuItemBack = new JMenuItem("上一级文件夹");
	private JMenuItem menuItemDirCompress = new JMenuItem("压缩文件夹");
	/**
	 * 添加按钮、添加监听器
	 */
	public MenuDir(ActionListener actionListenerMenu) {
		add(menuItemDirIn);
		add(menuItemDirDelete);
		add(menuItemDirCopy);
		add(menuItemDirCompress);
		//add(menuItemBack);
		menuinit(actionListenerMenu);
	}
	private void menuinit(ActionListener actionListenerMenu) {
		// TODO Auto-generated method stub
		menuItemDirCopy.addActionListener(actionListenerMenu);
		menuItemDirDelete.addActionListener(actionListenerMenu);
		menuItemDirIn.addActionListener(actionListenerMenu);
		menuItemDirCompress.addActionListener(actionListenerMenu);
		//menuItemBack.addActionListener(actionListenerMenu);
	}
}
