package editor;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * ��Ϣ��ʾ���
 */
class JPanel_info extends JPanel{
	GUIDoControler guiDoControler;

	private JTextArea text=new JTextArea();
	private JScrollPane jscropane=new JScrollPane();
	/**
	 * ��JTextArea��guiShowControler��������������ظ�����Ϣ
	 */
	JPanel_info(GUIDoControler guiDoControler){
		this.guiDoControler=guiDoControler;
		text.setText("��Ϣ���");
		Dimension dim=new Dimension();
		dim.setSize(800,200);
		jscropane.setPreferredSize(dim);
		jscropane.setViewportView(text);
		guiDoControler.guiShowControler.info=text;
		add(jscropane);
	}
}
