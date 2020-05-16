package editor;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import rewirteJList.FriListCellRenderer;
import rewirteJList.FriListModel;
/**
 * @author 李昕
 * 用户体验优化的JList
 */
public class FileLabelList extends JList<FriListCellRenderer> {
	public FileLabelList() {}
	/**
	 * 用继承了AbstractListModel的模型初始化
	 */
	public FileLabelList(FriListModel fileLebelModel) {
		// TODO Auto-generated constructor stub
		super(fileLebelModel);
		this.setCellRenderer(new FriListCellRenderer());
		this.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		// fileLabelList.setPreferredSize(new Dimension(360, 350));
		
	}

}
