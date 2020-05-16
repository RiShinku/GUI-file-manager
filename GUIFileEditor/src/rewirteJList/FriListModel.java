package rewirteJList;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import editor.FileLabel;
/**
 * @author ���
 * ���ͬ����һ����ʹ�ã����ڳ�ʼ��JList
 */
public class FriListModel extends AbstractListModel{
	
	ArrayList<FileLabel> uArray;
	public FriListModel(ArrayList<FileLabel> uArray){
		 this.uArray=uArray;		
	}
	@Override
	public int getSize() {  
		return uArray.size();
	}
	@Override
	public Object getElementAt(int index) {
		return   uArray.get(index) ;
	}
}