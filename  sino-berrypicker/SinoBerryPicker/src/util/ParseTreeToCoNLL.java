package util;

import java.util.ArrayList;

import model.syntaxTree.MyTree;

public class ParseTreeToCoNLL {

	public static ArrayList<String> convert(String filename) {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("#begin document conll");
		ArrayList<String> inputs = Common.getLines(filename);
		for (String input : inputs) {
			MyTree tree = Common.constructTree(input);
			StanfordToCoNLL.transform(lines, tree);
		}
		lines.add("#end document");
		return lines;
	}

	public static void main(String args[]) {
		if (args.length == 0) {
			System.out
					.println("java -cp SinoBerryPicker.jar model.stanford.ParseTreeToCoNLL [parse-tree]");
			System.exit(1);
		}
		String filename = args[0];
		ArrayList<String> lines = convert(filename);
		Common.outputLines(lines, filename + ".conll");
	}
}
