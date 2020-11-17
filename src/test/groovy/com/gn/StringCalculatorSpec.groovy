package com.gn

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class StringCalculatorSpec extends Specification {

  def parser = new InputParser()

  @Subject
  def calculator = new StringCalculator(parser)

  @Unroll
  def "should return sum = #expected for input = '#input'"() {
    expect:
      calculator.add(input) == expected

    where:
      input                     || expected
      ""                        || 0
      "2"                       || 2
      "1,2,3,4"                 || 10
      "1\n2,3"                  || 6
      "//[***][---]\n1***2---3" || 6
      "//[*]\n1*c*2"            || 3
  }

  def "should throw exception for invalid input = "() {
    given:
      def input = "1,-1,-2,4"

    when:
      calculator.add(input)

    then:
      def ex = thrown(Exception)
      ex.message.contains('-1')
      ex.message.contains('-2')
  }

}
