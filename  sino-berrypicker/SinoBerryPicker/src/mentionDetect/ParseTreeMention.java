package mentionDetect;

import java.util.ArrayList;

import mentionDetection.chinese.ChineseMention;
import model.EntityMention;
import model.CoNLL.CoNLLPart;

public class ParseTreeMention extends MentionDetect {

//	OntoCommon ontoCommon;

	@Override
	public ArrayList<EntityMention> getMentions(CoNLLPart part) {
		if (part.getDocument().getLanguage().equalsIgnoreCase("chinese")) {
			ChineseMention ch = new ChineseMention();
			return ch.getChineseMention(part);
		} else if (part.getDocument().getLanguage().equalsIgnoreCase("english")) {
//			ontoCommon = new OntoCommon("english");
//			return getEnglishMention(part);
		} else if (part.getDocument().getLanguage().equalsIgnoreCase("arabic")) {
//			ArabicMention ar = new ArabicMention();
//			return ar.getArabicMention(part);
		}
		return null;
	}

}
