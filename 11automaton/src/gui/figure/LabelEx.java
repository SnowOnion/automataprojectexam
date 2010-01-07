package gui.figure;

import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;

class LabelEx extends FlowPage {

	private TextFlow contents;

	public LabelEx() {
		this("");
	}

	public LabelEx(String text) {
		contents = new TextFlow();
		contents.setLayoutManager(new ParagraphTextLayout(contents,
				ParagraphTextLayout.WORD_WRAP_SOFT));
		contents.setText(text);
		add(contents);
	}

	public void setText(String text) {
		contents.setText(text);
	}

	public String getText() {
		return contents.getText();
	}
}
