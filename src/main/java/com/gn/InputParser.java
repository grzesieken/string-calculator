package com.gn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputParser {

  private static final String PIPE = "|";
  private static final String DEFAULT_DELIMITERS = String.join(PIPE, Arrays.asList(",", "\n"));

  private static final String INPUT_DELIMITER_MARKER = "//";
  private static final String INPUT_DELIMITER_SPLIT = "\n";

  private static final String RAW_DELIMITER_SPLIT = "]\\[";
  private static final String RAW_DELIMITER_START = "[";
  private static final String RAW_DELIMITER_END = "]";

  public List<String> parse(String input) {
    String[] rawDelimitersAndNumbers = input.startsWith(INPUT_DELIMITER_MARKER)
        ? input.substring(INPUT_DELIMITER_MARKER.length()).split(INPUT_DELIMITER_SPLIT, 2)
        : new String[]{null, input};

    String numbers = rawDelimitersAndNumbers[1];
    String delimiters = Optional.ofNullable(rawDelimitersAndNumbers[0])
        .map(this::parseDelimiters)
        .orElse(DEFAULT_DELIMITERS);

    return Arrays.asList(numbers.split(delimiters));
  }

  private String parseDelimiters(String rawDelimiters) {
    return Arrays.stream(rawDelimiters.split(RAW_DELIMITER_SPLIT))
        .map(this::trimRawDelimiter)
        .map(Pattern::quote)
        .collect(Collectors.joining(PIPE));
  }

  private String trimRawDelimiter(String raw) {
    String noStartingChar = raw.startsWith(RAW_DELIMITER_START) ? raw.substring(RAW_DELIMITER_START.length()) : raw;
    String noEndingChar = noStartingChar.endsWith(RAW_DELIMITER_END) ? noStartingChar.substring(0, noStartingChar.length() - RAW_DELIMITER_END.length()) : noStartingChar;
    return noEndingChar;
  }

}
