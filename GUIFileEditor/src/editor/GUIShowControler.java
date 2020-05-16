package editor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import rewirteJList.FriListCellRenderer;
import rewirteJList.FriListModel;

/**
 * @author 李昕
 * 显示管理器
 */
class GUIShowControler {
	/**
	 * 对应着各个面板里需要用到的部件
	 */
	JTextArea info;
	JTextField path;
	FileLabelList fileShow;
	JScrollPane jscropane;
	MenuDir menuDir;
	MenuFile menuFile;
	/**
	 * 在JTextArea info里加入信息
	 */
	public void addInfo(String s) {
		info.append("\n"+s);
	}
	/**
	 * JTextArea info清空并设置内容
	 */
	public void changeInfo(String s) {
		info.setText(s);
	}
	/**
	 * 变化路径，把path框内显示为当前路径，并刷新文件列表
	 */
	public void refreshPath(Object s) {
		String S=(String)s;
		path.setText(S);
		//info.append("\n切换路径到"+S);
		refreshFilesShow(S);
	}
	/**
	 * 工具函数，初始化盘符选择器
	 */
	static void  initJComboBox(JComboBox<String> cmb2) {
		File[] files=File.listRoots();
		for(File f:files) {
			cmb2.addItem(f.toString());
		}
	}
	/**
	 * 刷新文件列表
	 */
	private void refreshFilesShow(String s) {
		File dir=new File(s);
		final File[] files=dir.listFiles();
		ArrayList<FileLabel> fArray=new ArrayList<FileLabel>();
		//将FileLabel的ArrayList装入由文件初始化的FileLabel
		for(int i=0;i<files.length;i++) {
			//每个文件对应的FileLabel
			FileLabel e=new FileLabel(files[i].isDirectory()?0:1, files[i].getName());
			fArray.add(e);
			//System.out.println(files[i].toString());
		}	
		//由fArray构建FriListModel并初始化FileLabelList
		FriListModel friListModel=new FriListModel(fArray);
		fileShow=new FileLabelList(friListModel);
		//滚动面板添加
		jscropane.setViewportView(fileShow);
		//值得说明的是菜单没有添加到JList里，而是添加到了包含此JList的面板里，
		//这样菜单就不需要由显示控制器调用了，而是相对独立
	}
}
