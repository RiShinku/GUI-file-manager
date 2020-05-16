package editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
/**
 * @author ���
 * �ܵĴ���
 */
public class GUI extends JFrame{
	/**
	 * ������ʾ���ݵĹ����������ܹ�����GUIShowControler����
	 */
	private GUIShowControler guiShowControler=new GUIShowControler();
	/**
	 * �ܵĹ�����Ҳ�Ǽ�����
	 */
	private GUIDoControler guiDoControler=new GUIDoControler(guiShowControler); 
	//���������������
	private JPanel_do jp_do = new JPanel_do(guiDoControler);
	private JPanel_show jp_show = new JPanel_show(guiDoControler);
	private JPanel_info jp_info = new JPanel_info(guiDoControler);
	GUI(){
		//���л����Ĵ������ã�������������
		this.setTitle("�ļ�����ϵͳ");
		JFrame.setDefaultLookAndFeelDecorated(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().add("North", jp_do);
        this.getContentPane().add("Center", jp_show);
        this.getContentPane().add("South", jp_info);
	}
	//�ܽ����
	public static void main(String[] args) {
		GUI gui=new GUI();
		gui.setVisible(true);
		gui.pack();
	}
}
