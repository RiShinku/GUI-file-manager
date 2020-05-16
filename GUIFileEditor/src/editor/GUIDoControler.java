package editor;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * @author 李昕
 * 总的管理器
 */
public class GUIDoControler implements ActionListener,ItemListener,ListSelectionListener,MouseListener{
	/**
	 * 受此类控制的显示管理器
	 */
	GUIShowControler guiShowControler;
	/**
	 * dir 当前文件夹
	 * fileStlected 当前选中文件
	 * fileCopied 当前被复制的文件
	 */
	File dir,fileStlected,fileCopied;
	/**
	 * 当前目录下所有文件
	 */
	File[] fileList;
	/**
	 * 用受其控制的显示管理器初始化
	 */
	public GUIDoControler(GUIShowControler guiShowControler) {
		this.guiShowControler=guiShowControler;
	}
	/**
	 * 刷新为目录s
	 */
	public void refreshPath(String s) {
		//先把显示内容更新为当前目录
		guiShowControler.refreshPath(s);
		//再把目录更改，文件列表更改，文件列表加入监听器（即此类）
		dir=new File(s);
		fileList=dir.listFiles();
		guiShowControler.fileShow.addListSelectionListener(this);
		guiShowControler.fileShow.addMouseListener(this);
	}
	/**
	 * 监听事件发生所点击按钮，触发对应事件
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//跳转到点击的按钮
		switch(e.getActionCommand()) {
		case"上一级文件夹":{
			if(dir.getParentFile()==null) {
				guiShowControler.addInfo("无上一级文件夹");
				return;
			}
			//更改目录到上一级文件夹并刷新
			dir=dir.getParentFile();
			refreshPath(dir.getAbsolutePath());
			guiShowControler.addInfo("进入"+dir.getAbsolutePath());
			return;
		}
		case"创建新文件夹":{
			String inputValue = JOptionPane.showInputDialog("请输入文件夹名");
			File newdir=new File(dir+File.separator+inputValue);
			if(!newdir.exists()){//如果文件夹不存在
				newdir.mkdir();//创建文件夹
				refreshPath(dir.getAbsolutePath());
			}
			else {
				guiShowControler.addInfo("文件夹已存在");
			}
			return;
		}
		case"进入文件夹":{
			//更改目录为当前选择并刷新
			dir=fileStlected;
			refreshPath(dir.getAbsolutePath());
			guiShowControler.addInfo("进入"+dir.getAbsolutePath());
			return;
		}
		case"删除文件夹":
		case"删除文件":{
			Object[] options = { "是", "否" }; 
			String tString=e.getActionCommand()=="删除文件夹"?"文件夹":"文件";
			int sel=JOptionPane.showOptionDialog(null, "确定要删除此"+tString+"？", "删除"+tString, 
			JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
			null, options, options[0]); 
			//若选择删除，调用文件控制类的删除函数并置fileStlected null
			if(sel==0) {
				try {
				FileOperator.deleteFiles(fileStlected);
				fileStlected=null;
				refreshPath(dir.getAbsolutePath());
				}
				catch(IOException ex){
					guiShowControler.addInfo("删除时出错,错误信息：\n"+ex.getMessage());
				}
			}
			return;
		}
		case"复制文件夹":
		case"复制文件":{
			fileCopied=fileStlected;
			guiShowControler.addInfo("复制成功");
			return;
		}
		case"粘贴":{
			if(fileCopied==null||!fileCopied.exists()) {guiShowControler.addInfo("无粘贴内容");return;}
			//判断是否有同名
			if(fileListHasItem(fileCopied)){
				guiShowControler.addInfo("文件或文件夹重名");
				Object[] options;
				int sel=0;
				if(fileCopied.isDirectory()) {
					options =new Object[] { "合并", "替换","取消" }; }
				else {options = new Object[]{"替换","取消" };sel++;}
				//sel为0、1、2是分别表示合并替换取消，当粘贴的为文件自动加一
				sel=sel+JOptionPane.showOptionDialog(null, "存在同名文件，请选择操作", "重名" ,
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
				null, options, options[0]); 
				switch(sel) {
				case 0://guiShowControler.addInfo("开始粘贴");
				//因为事件队列的机制，会在粘贴或加密解密等工作完成后输出，因此无用
				FileOperator.Paste(fileCopied,dir);break;
				case 1:try{
					//guiShowControler.addInfo("开始粘贴");
					//替换即先删除再粘贴
					File fileToDel=new File(dir.getAbsolutePath()+File.separator+fileCopied.getName());
					if(fileCopied.getAbsolutePath().equals(fileToDel.getAbsolutePath())) {
						guiShowControler.addInfo("粘贴错误,不能原地粘贴");
						return;
					}
					FileOperator.deleteFiles(fileToDel);
					FileOperator.Paste(fileCopied,dir);
					break;
				}
				catch(IOException ex){
					guiShowControler.addInfo("粘贴错误,被替换文件无法删除，错误信息：\n"+ex.getMessage());
					return;
				}

				case 2:return;
				default:guiShowControler.addInfo("未知错误");
				}
			}
			else {
				//guiShowControler.addInfo("开始粘贴");
				FileOperator.Paste(fileCopied,dir);
			}
			guiShowControler.addInfo("粘贴成功");
			refreshPath(dir.getAbsolutePath());
			return;
		}
		case"加密文件":{
			String inputValueName = JOptionPane.showInputDialog("请输入加密后的文件名（不输为默认）");
			String inputValueKey = JOptionPane.showInputDialog("请输入密码（不输为默认）");
			//默认为在源文件明前加“加密后”三字
			inputValueName="".equals(inputValueName)?"加密后"+fileStlected.getName():inputValueName;
			String fileEncedString=dir.getAbsolutePath()+File.separator+inputValueName;
			//默认密码在文件控制器中
			boolean useDefaultKey="".equals(inputValueKey)?true:false;
			try {
				//guiShowControler.addInfo("开始加密");
				FileOperator.encrypt(fileStlected.getAbsolutePath(), 
						fileEncedString, inputValueKey, useDefaultKey);
			}catch(Exception e1) {
				guiShowControler.addInfo("加密出错,错误信息:\n"+e1.getMessage());
			}
			refreshPath(dir.getAbsolutePath());
			guiShowControler.addInfo("加密成功");
			return;
		}
		case"解密文件":{
			String inputValueName = JOptionPane.showInputDialog("请输入解密后的文件名（不输为默认）");
			String inputValueKey = JOptionPane.showInputDialog("请输入密码（不输为默认）");
			//默认为源文件前加解密后三字
			inputValueName="".equals(inputValueName)?"解密后"+fileStlected.getName():inputValueName;
			String fileDecedString=dir.getAbsolutePath()+File.separator+inputValueName;
			boolean useDefaultKey="".equals(inputValueKey)?true:false;
			try {
			FileOperator.decrypt(fileStlected.getAbsolutePath(), fileDecedString, inputValueKey, useDefaultKey);
			guiShowControler.addInfo("解密完成");
			}catch(Exception e1) {
				//guiShowControler.addInfo("开始解密");
				guiShowControler.addInfo("解密出错,错误信息:\n"+e1.getMessage());
			}
			
			refreshPath(dir.getAbsolutePath());
			return;
		}
		case"压缩文件":
		case"压缩文件夹":{
			String inputValueName = JOptionPane.showInputDialog("请输入压缩后的文件名（无需后缀，不输为默认）");
			//默认为加后缀.zip
			inputValueName="".equals(inputValueName)?fileStlected.getName()+".zip":inputValueName+".zip";
			try {
				String fileToZipString=dir.getAbsolutePath()+File.separator+inputValueName;
				//guiShowControler.addInfo("开始压缩");
				FileOperator.zip(fileStlected.getAbsolutePath(),fileToZipString);
				guiShowControler.addInfo("压缩完成");
			} catch (IOException e1) {
				guiShowControler.addInfo("压缩时出错,错误信息:\n"+e1.getMessage());
			} 
			
			refreshPath(dir.getAbsolutePath());
			return;
		}
		case"解压缩文件":{
			try {
				FileOperator.unZipFiles(fileStlected.getAbsolutePath(), dir.getAbsolutePath());
				guiShowControler.addInfo("解压缩完成");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				guiShowControler.addInfo("解压缩时出错，错误信息：\n"+e1.getMessage());
			}
			refreshPath(dir.getAbsolutePath());
			return;
		}
		default:guiShowControler.addInfo("未知错误");
		//文件夹、文件压缩解压缩
		}
	}	
	/**
	 * @param fileToOp 寻找的文件
	 * 找找当前目录下有无同名文件或文件夹
	 */
	private boolean fileListHasItem(File fileToOp) {
		boolean flag=false;
		for(File f:fileList) {
			String s1=f.getName();
			String s2=fileToOp.getName();
			if(s1.equals(s2)) {
				flag=true;
				break;
			}
		}
		return flag;
	}
	/**
	 * 鼠标点击事件，用于弹出右键菜单
	 */
	public void mouseClicked(MouseEvent e){
		//若点击右键
        if (e.getButton() == MouseEvent.BUTTON3){
        	//根据当前点击位置重新选择文件，防止选择的文件和右键菜单弹出不匹配
        	int index = guiShowControler.fileShow.locationToIndex(e.getPoint());  
        	guiShowControler.fileShow.setSelectedIndex(index);
        	//若有文件被选中，防止空文件夹出现bug
        	if(index>=0) {
        		//若为文件夹弹出menuDir，否则menuFile
	        	if(dir.listFiles()[index].isDirectory()) {
	        		guiShowControler.menuDir.show(e.getComponent(),e.getX(),e.getY());
	        	}
	        	else {
	        		guiShowControler.menuFile.show(e.getComponent(),e.getX(),e.getY());
	        	}
        	}
        }
    }
	/**
	 * 盘符变化，则路径变为盘下根目录
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getStateChange() == ItemEvent.SELECTED) {
			Object s=e.getItem();
			String S=(String)s;
			this.refreshPath(S);
		}
		
	}
	/**
	 * 列表选择变化，fileStlected变为选择的文件
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//System.out.println("选取" + guiShowControler.fileShow.getSelectedIndex());
		fileStlected=fileList[guiShowControler.fileShow.getSelectedIndex()];
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
