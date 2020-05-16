package rewirteJList;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import editor.FileLabel;
/**
 * @author 李昕
 * 配合同包另一个类使用，用于初始化JList
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