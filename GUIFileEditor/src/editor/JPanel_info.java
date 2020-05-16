package editor;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * 信息提示面板
 */
class JPanel_info extends JPanel{
	GUIDoControler guiDoControler;

	private JTextArea text=new JTextArea();
	private JScrollPane jscropane=new JScrollPane();
	/**
	 * 将JTextArea与guiShowControler链接起来，方便地更新信息
	 */
	JPanel_info(GUIDoControler guiDoControler){
		this.guiDoControler=guiDoControler;
		text.setText("信息板块");
		Dimension dim=new Dimension();
		dim.setSize(800,200);
		jscropane.setPreferredSize(dim);
		jscropane.setViewportView(text);
		guiDoControler.guiShowControler.info=text;
		add(jscropane);
	}
}
