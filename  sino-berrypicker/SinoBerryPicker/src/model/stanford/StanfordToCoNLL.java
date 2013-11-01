package model.stanford;

import java.util.ArrayList;

import model.syntaxTree.MyTreeNode;
import util.Common;

public class StanfordToCoNLL {

	public static ArrayList<String> convert(StanfordResult result) {

		ArrayList<String> lines = new ArrayList<String>();
		lines.add("#begin document " + result.documentID);

		for (StanfordSentence ss : result.sentences) {
			int from = 0;
			int to = 0;
			String parseTree = ss.parseTree.root.getPlainText(true);
			System.out.println(parseTree);
			for (int i = 0; i < ss.parseTree.leaves.size(); i++) {
				MyTreeNode leaf = ss.parseTree.leaves.get(i);
				StringBuilder sb = new StringBuilder();
				// documentID
				sb.append(result.documentID).append("\t");
				// partID
				sb.append("0").append("\t");
				// word number
				sb.append(i).append("\t");
				// word itself
				String token = leaf.value;
				sb.append(leaf.value).append("\t");
				// Part-of-Speech
				String pos = leaf.parent.value;
				if (!(pos.charAt(0) > 'A' && pos.charAt(0) < 'Z')
						&& pos.length() == 1) {
					sb.append("PU").append("\t");
				} else {
					sb.append(pos).append("\t");
				}

				// Parse bit
				String key = "(" + pos + " " + token + ")";
				to = parseTree.indexOf(key, from) + key.length();

				while (to < parseTree.length() && parseTree.charAt(to) == ')') {
					to++;
				}

				String treeEle = parseTree.substring(from, to).trim()
						.replace(key, "*").replaceAll("\\s+", "");

				sb.append(treeEle).append("\t");
				// Predicate lemma
				sb.append("-").append("\t");
				// Predicate Frameset ID
				sb.append("-").append("\t");
				// Word sense
				sb.append("-").append("\t");
				// Speaker/Author
				sb.append("-").append("\t");
				// Named Entities
				String neToken = "*";
				if (!neToken.equalsIgnoreCase("other")) {
					sb.append(neToken).append("\t");
				}
				lines.add(sb.toString());
				from = to;
			}
			lines.add("");
		}
		lines.add("#end document");

		return lines;
	}

	public static void main(String args[]) {
		if(args.length==0) {
			System.out.println("java -cp SinoBerryPicker.jar model.stanford.StanfordToCoNLL [stanford-output]");
			System.exit(1);
		}
		String filename = args[0];
		ArrayList<String> lines = convert(StanfordXMLReader
				.read(filename));
//		for (String line : lines) {
			// System.out.println(line);
//		}
		Common.outputLines(lines, filename + ".conll");
	}
}
