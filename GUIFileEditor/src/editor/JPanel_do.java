package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * @author 李昕
 * 最上层的面板
 */
class JPanel_do extends JPanel  {
	GUIDoControler guiDoControler;
	private JButton btnCreatDir=new JButton("创建新文件夹");
	private JButton btnBackDir=new JButton("上一级文件夹");
	private JButton btnPaste=new JButton("粘贴");
	private JComboBox<String> cmb=new JComboBox<String>();
	private JTextField textField=new JTextField("",20);
	/**
	 * 添加控制器，添加各个按钮、文本框等
	 */
	JPanel_do(GUIDoControler guiDoControler){
		this.guiDoControler=guiDoControler;
		GUIShowControler.initJComboBox(cmb);
        cmb.setSelectedIndex(-1);
        cmb.addItemListener(guiDoControler);
        btnBackDir.addActionListener(guiDoControler);
        btnCreatDir.addActionListener(guiDoControler);
        btnPaste.addActionListener(guiDoControler);
        guiDoControler.guiShowControler.path=textField;
        add(cmb);
        add(textField);
        add(btnPaste);
        add(btnBackDir);
        add(btnCreatDir);
	}
	
}
