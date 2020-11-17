package com.gn;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;


public class StringCalculator {

  private final InputParser parser;

  public StringCalculator(InputParser parser) {
    this.parser = parser;
  }

  Integer add(String input) {

    Predicate<Integer> negatives = integer -> integer < 0;
    Predicate<Integer> lessEqual1000 = integer -> integer <= 1000;

    List<Integer> ints = parser.parse(input).stream()
        .filter(s -> !s.isEmpty())
        .map(this::parseInt)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toList());
    List<Integer> errorInts = ints.stream().filter(negatives).collect(toList());

    if (!errorInts.isEmpty()) {
      throw new IllegalArgumentException(errorInts.toString());
    }

    return ints.stream()
        .filter(negatives.negate())
        .filter(lessEqual1000)
        .reduce(Integer::sum)
        .orElse(0);
  }

  // this was not specified
  Optional<Integer> parseInt(String s) {
    try {
      return Optional.of(Integer.parseInt(s));
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }

}
