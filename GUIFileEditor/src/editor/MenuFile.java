package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
/**
 * @author ���
 * �ļ����Ҽ��˵�����������ɾ����ť������MenuDir������ϸ˵��
 */
public class MenuFile extends JPopupMenu{

	private JMenuItem menuItemFileCopy = new JMenuItem("�����ļ�");
	private JMenuItem menuItemFileDel = new JMenuItem("ɾ���ļ�");
	private JMenuItem menuItemFileEncry = new JMenuItem("�����ļ�");
	private JMenuItem menuItemFileDecode = new JMenuItem("�����ļ�");
	private JMenuItem menuItemFileCompress = new JMenuItem("ѹ���ļ�");
	private JMenuItem menuItemFileUncompress = new JMenuItem("��ѹ���ļ�");
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
