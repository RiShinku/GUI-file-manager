package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
/**
 * @author ���
 * �ļ��е��Ҽ��˵�����������ɾ����ť
 */
class MenuDir extends JPopupMenu{
	private JMenuItem menuItemDirCopy = new JMenuItem("�����ļ���");
	private JMenuItem menuItemDirDelete = new JMenuItem("ɾ���ļ���");
	private JMenuItem menuItemDirIn = new JMenuItem("�����ļ���");
	//��һ���ļ��в�Ӧ�ɴ˲˵����ã�����û���ļ��о��޷������ϼ�
	//private JMenuItem menuItemBack = new JMenuItem("��һ���ļ���");
	private JMenuItem menuItemDirCompress = new JMenuItem("ѹ���ļ���");
	/**
	 * ��Ӱ�ť����Ӽ�����
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
