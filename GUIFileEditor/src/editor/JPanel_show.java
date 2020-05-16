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
 * @author ���
 * �ļ��б���ʾ��壬�������Ҽ��˵�����Ҫ��FileLabelList
 */
class JPanel_show extends JPanel {
	GUIDoControler guiDoControler;
	@SuppressWarnings("rawtypes")
	private FileLabelList fileLabelList=new FileLabelList();
	//private JList<String> jListtest=new JList<String>();
	//�Ҽ��˵���������JLabel�������Ư��һЩ��
	private MenuDir menuDir;//=new MenuDir(guiDoControler);
	private MenuFile menuFile;//=new MenuFile(guiDoControler);
	private JScrollPane jscropane=new JScrollPane();
	/**
	 * ��Ӳ˵��Ȳ����ô�С�����ԣ���Ӽ�����
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
