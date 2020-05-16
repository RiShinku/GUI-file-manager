package editor;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionListener;

import rewirteJList.FriListCellRenderer;
import rewirteJList.FriListModel;

import javax.swing.event.ListSelectionEvent;
/**
 * @author 李昕
 * 文件列表显示面板，包含了右键菜单和主要的FileLabelList
 */
class JPanel_show extends JPanel {
	GUIDoControler guiDoControler;
	@SuppressWarnings("rawtypes")
	private FileLabelList fileLabelList=new FileLabelList();
	//private JList<String> jListtest=new JList<String>();
	//右键菜单（可以用JLabel让他变更漂亮一些）
	private MenuDir menuDir;//=new MenuDir(guiDoControler);
	private MenuFile menuFile;//=new MenuFile(guiDoControler);
	private JScrollPane jscropane=new JScrollPane();
	/**
	 * 添加菜单等并设置大小等属性，添加监听器
	 */
	JPanel_show(GUIDoControler guiDoControler){
		this.guiDoControler=guiDoControler;
		menuDir=new MenuDir(guiDoControler);
		menuFile=new MenuFile(guiDoControler);
		setLayout(new FlowLayout());
		Dimension dim=new Dimension();
		dim.setSize(800,600);
		jscropane.setPreferredSize(dim);
		add(jscropane);
		guiDoControler.guiShowControler.menuDir=this.menuDir;
		guiDoControler.guiShowControler.menuFile=this.menuFile;
		guiDoControler.guiShowControler.fileShow=fileLabelList;
		guiDoControler.guiShowControler.jscropane=this.jscropane;
	}
}
