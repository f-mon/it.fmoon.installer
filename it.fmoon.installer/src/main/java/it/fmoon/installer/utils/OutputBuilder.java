package it.fmoon.installer.utils;

public class OutputBuilder {

	private StringBuilder sb = new StringBuilder();
	private int lineWidth = 80;

	private int indent = 0;
	
	
	
	public OutputBuilder bigLineSeparator() {
		doAppend("=".repeat(lineWidth));
		return newLine();
	}

	public OutputBuilder lineSeparator() {
		doAppend("-".repeat(lineWidth));
		return newLine();
	}

	public OutputBuilder newLine() {
		doAppend("\n");
		return this;
	}

	public OutputBuilder append(Object obj) {
		doAppend(obj);
		return this;
	}

	public String build() {
		return sb.toString();
	}

	public OutputBuilder appendWithFixWidth(Object append, int width) {
		String string = String.valueOf(append);
		String fixed = string;
		if (string.length()>width) {
			fixed = string.substring(0, width);
		} else if (string.length()<width) {
			fixed = string+" ".repeat(width-string.length());
		}
		return append(fixed);
	}

	public OutputBuilder space() {
		doAppend(" ");
		return this;
	}
	
	protected void doAppend(Object obj) {
		String string = String.valueOf(obj);
		sb.append(string);
	}

}
