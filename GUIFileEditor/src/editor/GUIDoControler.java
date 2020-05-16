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
 * @author ���
 * �ܵĹ�����
 */
public class GUIDoControler implements ActionListener,ItemListener,ListSelectionListener,MouseListener{
	/**
	 * �ܴ�����Ƶ���ʾ������
	 */
	GUIShowControler guiShowControler;
	/**
	 * dir ��ǰ�ļ���
	 * fileStlected ��ǰѡ���ļ�
	 * fileCopied ��ǰ�����Ƶ��ļ�
	 */
	File dir,fileStlected,fileCopied;
	/**
	 * ��ǰĿ¼�������ļ�
	 */
	File[] fileList;
	/**
	 * ��������Ƶ���ʾ��������ʼ��
	 */
	public GUIDoControler(GUIShowControler guiShowControler) {
		this.guiShowControler=guiShowControler;
	}
	/**
	 * ˢ��ΪĿ¼s
	 */
	public void refreshPath(String s) {
		//�Ȱ���ʾ���ݸ���Ϊ��ǰĿ¼
		guiShowControler.refreshPath(s);
		//�ٰ�Ŀ¼���ģ��ļ��б���ģ��ļ��б����������������ࣩ
		dir=new File(s);
		fileList=dir.listFiles();
		guiShowControler.fileShow.addListSelectionListener(this);
		guiShowControler.fileShow.addMouseListener(this);
	}
	/**
	 * �����¼������������ť��������Ӧ�¼�
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//��ת������İ�ť
		switch(e.getActionCommand()) {
		case"��һ���ļ���":{
			if(dir.getParentFile()==null) {
				guiShowControler.addInfo("����һ���ļ���");
				return;
			}
			//����Ŀ¼����һ���ļ��в�ˢ��
			dir=dir.getParentFile();
			refreshPath(dir.getAbsolutePath());
			guiShowControler.addInfo("����"+dir.getAbsolutePath());
			return;
		}
		case"�������ļ���":{
			String inputValue = JOptionPane.showInputDialog("�������ļ�����");
			File newdir=new File(dir+File.separator+inputValue);
			if(!newdir.exists()){//����ļ��в�����
				newdir.mkdir();//�����ļ���
				refreshPath(dir.getAbsolutePath());
			}
			else {
				guiShowControler.addInfo("�ļ����Ѵ���");
			}
			return;
		}
		case"�����ļ���":{
			//����Ŀ¼Ϊ��ǰѡ��ˢ��
			dir=fileStlected;
			refreshPath(dir.getAbsolutePath());
			guiShowControler.addInfo("����"+dir.getAbsolutePath());
			return;
		}
		case"ɾ���ļ���":
		case"ɾ���ļ�":{
			Object[] options = { "��", "��" }; 
			String tString=e.getActionCommand()=="ɾ���ļ���"?"�ļ���":"�ļ�";
			int sel=JOptionPane.showOptionDialog(null, "ȷ��Ҫɾ����"+tString+"��", "ɾ��"+tString, 
			JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
			null, options, options[0]); 
			//��ѡ��ɾ���������ļ��������ɾ����������fileStlected null
			if(sel==0) {
				try {
				FileOperator.deleteFiles(fileStlected);
				fileStlected=null;
				refreshPath(dir.getAbsolutePath());
				}
				catch(IOException ex){
					guiShowControler.addInfo("ɾ��ʱ����,������Ϣ��\n"+ex.getMessage());
				}
			}
			return;
		}
		case"�����ļ���":
		case"�����ļ�":{
			fileCopied=fileStlected;
			guiShowControler.addInfo("���Ƴɹ�");
			return;
		}
		case"ճ��":{
			if(fileCopied==null||!fileCopied.exists()) {guiShowControler.addInfo("��ճ������");return;}
			//�ж��Ƿ���ͬ��
			if(fileListHasItem(fileCopied)){
				guiShowControler.addInfo("�ļ����ļ�������");
				Object[] options;
				int sel=0;
				if(fileCopied.isDirectory()) {
					options =new Object[] { "�ϲ�", "�滻","ȡ��" }; }
				else {options = new Object[]{"�滻","ȡ��" };sel++;}
				//selΪ0��1��2�Ƿֱ��ʾ�ϲ��滻ȡ������ճ����Ϊ�ļ��Զ���һ
				sel=sel+JOptionPane.showOptionDialog(null, "����ͬ���ļ�����ѡ�����", "����" ,
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
				null, options, options[0]); 
				switch(sel) {
				case 0://guiShowControler.addInfo("��ʼճ��");
				//��Ϊ�¼����еĻ��ƣ�����ճ������ܽ��ܵȹ�����ɺ�������������
				FileOperator.Paste(fileCopied,dir);break;
				case 1:try{
					//guiShowControler.addInfo("��ʼճ��");
					//�滻����ɾ����ճ��
					File fileToDel=new File(dir.getAbsolutePath()+File.separator+fileCopied.getName());
					if(fileCopied.getAbsolutePath().equals(fileToDel.getAbsolutePath())) {
						guiShowControler.addInfo("ճ������,����ԭ��ճ��");
						return;
					}
					FileOperator.deleteFiles(fileToDel);
					FileOperator.Paste(fileCopied,dir);
					break;
				}
				catch(IOException ex){
					guiShowControler.addInfo("ճ������,���滻�ļ��޷�ɾ����������Ϣ��\n"+ex.getMessage());
					return;
				}

				case 2:return;
				default:guiShowControler.addInfo("δ֪����");
				}
			}
			else {
				//guiShowControler.addInfo("��ʼճ��");
				FileOperator.Paste(fileCopied,dir);
			}
			guiShowControler.addInfo("ճ���ɹ�");
			refreshPath(dir.getAbsolutePath());
			return;
		}
		case"�����ļ�":{
			String inputValueName = JOptionPane.showInputDialog("��������ܺ���ļ���������ΪĬ�ϣ�");
			String inputValueKey = JOptionPane.showInputDialog("���������루����ΪĬ�ϣ�");
			//Ĭ��Ϊ��Դ�ļ���ǰ�ӡ����ܺ�����
			inputValueName="".equals(inputValueName)?"���ܺ�"+fileStlected.getName():inputValueName;
			String fileEncedString=dir.getAbsolutePath()+File.separator+inputValueName;
			//Ĭ���������ļ���������
			boolean useDefaultKey="".equals(inputValueKey)?true:false;
			try {
				//guiShowControler.addInfo("��ʼ����");
				FileOperator.encrypt(fileStlected.getAbsolutePath(), 
						fileEncedString, inputValueKey, useDefaultKey);
			}catch(Exception e1) {
				guiShowControler.addInfo("���ܳ���,������Ϣ:\n"+e1.getMessage());
			}
			refreshPath(dir.getAbsolutePath());
			guiShowControler.addInfo("���ܳɹ�");
			return;
		}
		case"�����ļ�":{
			String inputValueName = JOptionPane.showInputDialog("��������ܺ���ļ���������ΪĬ�ϣ�");
			String inputValueKey = JOptionPane.showInputDialog("���������루����ΪĬ�ϣ�");
			//Ĭ��ΪԴ�ļ�ǰ�ӽ��ܺ�����
			inputValueName="".equals(inputValueName)?"���ܺ�"+fileStlected.getName():inputValueName;
			String fileDecedString=dir.getAbsolutePath()+File.separator+inputValueName;
			boolean useDefaultKey="".equals(inputValueKey)?true:false;
			try {
			FileOperator.decrypt(fileStlected.getAbsolutePath(), fileDecedString, inputValueKey, useDefaultKey);
			guiShowControler.addInfo("�������");
			}catch(Exception e1) {
				//guiShowControler.addInfo("��ʼ����");
				guiShowControler.addInfo("���ܳ���,������Ϣ:\n"+e1.getMessage());
			}
			
			refreshPath(dir.getAbsolutePath());
			return;
		}
		case"ѹ���ļ�":
		case"ѹ���ļ���":{
			String inputValueName = JOptionPane.showInputDialog("������ѹ������ļ����������׺������ΪĬ�ϣ�");
			//Ĭ��Ϊ�Ӻ�׺.zip
			inputValueName="".equals(inputValueName)?fileStlected.getName()+".zip":inputValueName+".zip";
			try {
				String fileToZipString=dir.getAbsolutePath()+File.separator+inputValueName;
				//guiShowControler.addInfo("��ʼѹ��");
				FileOperator.zip(fileStlected.getAbsolutePath(),fileToZipString);
				guiShowControler.addInfo("ѹ�����");
			} catch (IOException e1) {
				guiShowControler.addInfo("ѹ��ʱ����,������Ϣ:\n"+e1.getMessage());
			} 
			
			refreshPath(dir.getAbsolutePath());
			return;
		}
		case"��ѹ���ļ�":{
			try {
				FileOperator.unZipFiles(fileStlected.getAbsolutePath(), dir.getAbsolutePath());
				guiShowControler.addInfo("��ѹ�����");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				guiShowControler.addInfo("��ѹ��ʱ����������Ϣ��\n"+e1.getMessage());
			}
			refreshPath(dir.getAbsolutePath());
			return;
		}
		default:guiShowControler.addInfo("δ֪����");
		//�ļ��С��ļ�ѹ����ѹ��
		}
	}	
	/**
	 * @param fileToOp Ѱ�ҵ��ļ�
	 * ���ҵ�ǰĿ¼������ͬ���ļ����ļ���
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
	 * ������¼������ڵ����Ҽ��˵�
	 */
	public void mouseClicked(MouseEvent e){
		//������Ҽ�
        if (e.getButton() == MouseEvent.BUTTON3){
        	//���ݵ�ǰ���λ������ѡ���ļ�����ֹѡ����ļ����Ҽ��˵�������ƥ��
        	int index = guiShowControler.fileShow.locationToIndex(e.getPoint());  
        	guiShowControler.fileShow.setSelectedIndex(index);
        	//�����ļ���ѡ�У���ֹ���ļ��г���bug
        	if(index>=0) {
        		//��Ϊ�ļ��е���menuDir������menuFile
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
	 * �̷��仯����·����Ϊ���¸�Ŀ¼
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
	 * �б�ѡ��仯��fileStlected��Ϊѡ����ļ�
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//System.out.println("ѡȡ" + guiShowControler.fileShow.getSelectedIndex());
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
