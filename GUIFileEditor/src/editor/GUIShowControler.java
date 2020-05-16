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
 * @author ���
 * ��ʾ������
 */
class GUIShowControler {
	/**
	 * ��Ӧ�Ÿ����������Ҫ�õ��Ĳ���
	 */
	JTextArea info;
	JTextField path;
	FileLabelList fileShow;
	JScrollPane jscropane;
	MenuDir menuDir;
	MenuFile menuFile;
	/**
	 * ��JTextArea info�������Ϣ
	 */
	public void addInfo(String s) {
		info.append("\n"+s);
	}
	/**
	 * JTextArea info��ղ���������
	 */
	public void changeInfo(String s) {
		info.setText(s);
	}
	/**
	 * �仯·������path������ʾΪ��ǰ·������ˢ���ļ��б�
	 */
	public void refreshPath(Object s) {
		String S=(String)s;
		path.setText(S);
		//info.append("\n�л�·����"+S);
		refreshFilesShow(S);
	}
	/**
	 * ���ߺ�������ʼ���̷�ѡ����
	 */
	static void  initJComboBox(JComboBox<String> cmb2) {
		File[] files=File.listRoots();
		for(File f:files) {
			cmb2.addItem(f.toString());
		}
	}
	/**
	 * ˢ���ļ��б�
	 */
	private void refreshFilesShow(String s) {
		File dir=new File(s);
		final File[] files=dir.listFiles();
		ArrayList<FileLabel> fArray=new ArrayList<FileLabel>();
		//��FileLabel��ArrayListװ�����ļ���ʼ����FileLabel
		for(int i=0;i<files.length;i++) {
			//ÿ���ļ���Ӧ��FileLabel
			FileLabel e=new FileLabel(files[i].isDirectory()?0:1, files[i].getName());
			fArray.add(e);
			//System.out.println(files[i].toString());
		}	
		//��fArray����FriListModel����ʼ��FileLabelList
		FriListModel friListModel=new FriListModel(fArray);
		fileShow=new FileLabelList(friListModel);
		//����������
		jscropane.setViewportView(fileShow);
		//ֵ��˵�����ǲ˵�û����ӵ�JList�������ӵ��˰�����JList������
		//�����˵��Ͳ���Ҫ����ʾ�����������ˣ�������Զ���
	}
}
