package rewirteJList;

/**
 * @author 李昕
 * 渲染器，传入FileLabel来进行渲染
 */
import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import editor.FileLabel;

//参考：https://blog.csdn.net/qq_21808961/article/details/80658063
@SuppressWarnings({ "rawtypes", "serial" })
public class FriListCellRenderer extends JLabel implements ListCellRenderer {
	public FriListCellRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		FileLabel fileLabel = (FileLabel) value;// 转换为FileLabel
		String text = fileLabel.name;
		setText(text);// 获取名字并设置
		// 压缩图片并设置
		Image img = fileLabel.u.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		setIcon(new ImageIcon(img));
		setIconTextGap(30);
		//选择的话isSelected就是1，得设置背景
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			// 设置选取与取消选取的前景与背景颜色.
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		return this;
	}
}