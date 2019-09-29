package it.fmoon.installer.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.shell.Input;
import org.springframework.shell.InputProvider;
import org.springframework.util.StringUtils;

public class StringInputProvider implements InputProvider {

	private final List<String> words;

	private boolean done;

	public StringInputProvider(List<String> words) {
		this.words = words;
	}
	public StringInputProvider(String... words) {
		this.words = Arrays.asList(words);
	}

	@Override
	public Input readInput() {
		if (!done) {
			done = true;
			return new Input() {
				@Override
				public List<String> words() {
					return words;
				}

				@Override
				public String rawText() {
					return StringUtils.collectionToDelimitedString(words, " ");
				}
			};
		}
		else {
			return null;
		}
	}
}