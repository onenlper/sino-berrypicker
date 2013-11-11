package mentionDetection.chinese;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import model.CoNLL.CoNLLDocument;
import model.CoNLL.CoNLLPart;
import model.CoNLL.CoNLLSentence;
import model.CoNLL.CoNLLWord;
import model.syntaxTree.MyTreeNode;
import ruleCoreference.chinese.ChDictionary;

public class ChineseNETest {
	public static void main(String args[]) throws Exception {
		if (args.length != 1) {
			System.err.println("java ~ file");
			System.exit(1);
		}
		String file = args[0];
		produceInstance(file);
	}

	public static void produceInstance(String filename) throws Exception {
		ChDictionary dict = new ChDictionary();
		FileWriter fw = new FileWriter(filename + ".crf");
		CoNLLDocument document = new CoNLLDocument(filename);
		for (CoNLLPart part : document.getParts()) {
			ArrayList<MentionInstance> instances = new ArrayList<MentionInstance>();
			int count = 0;
			for (int i = 0; i < part.getWordCount(); i++) {
				CoNLLWord word = part.getWord(i);
				String ne = word.rawNamedEntity;
				String posTag = word.posTag;
				String wordStr = word.word;
				MyTreeNode tn = part.getWord(i).getSentence().getSyntaxTree().leaves
						.get(word.indexInSentence);
				ArrayList<MyTreeNode> ancestors = tn.getAncestors();
				boolean isNP = false;
				for (MyTreeNode ancestor : ancestors) {
					if (ancestor.value.startsWith("NP")) {
						isNP = true;
					}
				}
				for (int j = 0; j < wordStr.length(); j++) {
					MentionInstance instance = new MentionInstance(
							Character.toString(wordStr.charAt(j)), count);
					instance.setWordIndex(i);
					instance.setFilePath(filename);
					instance.setPartID(part.getPartID());
					instance.setDocumentID(part.getDocument().getDocumentID());
					if (j == 0) {
						if (!ne.equals("*")) {
							if (i != 0
									&& part.getWord(i - 1).getRawNamedEntity()
											.equals(ne)) {
								instance.setLabel("I-" + ne);
							} else {
								instance.setLabel("B-" + ne);
							}
						} else {
							instance.setLabel("O");
						}
						instance.setLeftBound(1);
						instance.setPosFea("B-" + posTag);
						if (dict.allPronouns.contains(wordStr)) {
							instance.setIsPronoun1(1);
						}
						if (isNP) {
							instance.setIsInNP(1);
						}
						if (dict.surnames.contains(instance.getWord())) {
							instance.setSurname(1);
						}
					} else {
						if (!ne.equals("*")) {
							instance.setLabel("I-" + ne);
						} else {
							instance.setLabel("O");
						}
						instance.setPosFea("I-" + posTag);
						if (dict.allPronouns.contains(wordStr)) {
							instance.setIsPronoun1(1);
						}
						if (isNP) {
							instance.setIsInNP(1);
						}
					}
					if (j == wordStr.length() - 1) {
						if (dict.locationSuffix.contains(instance.getWord())) {
							instance.setLocation_suffix(1);
						}
						instance.setRightBound(1);
					}
					instances.add(instance);
					count++;
				}
			}
			HashSet<Integer> ends = new HashSet<Integer>();
			ArrayList<CoNLLSentence> sentences = part.getCoNLLSentences();
			for (CoNLLSentence sentence : sentences) {
				ends.add(sentence.getEndWordIdx());
			}
			try {
				for (int i = 0; i < instances.size(); i++) {
					MentionInstance instance = instances.get(i);
					fw.write(instance.toString() + " \n");
					if (ends.contains(instance.getWordIndex())
							&& instance.getRightBound() == 1) {
						fw.write("\n");
					}
				}
				fw.write("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		fw.close();
	}
}
