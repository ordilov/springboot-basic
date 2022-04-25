package org.prgrms.kdt.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.exception.TypedException;

class CustomerTest {

  @Nested
  @DisplayName("고객은 Email에")
  class Described_constructor {

    @Nested
    @DisplayName("null이나 빈 문자열을 전달하면")
    class Context_with_null_or_blank {

      @ParameterizedTest
      @NullAndEmptySource
      @DisplayName("예외를 발생시킨다.")
      void it_throws_exception(String email) {
        assertThatThrownBy(() -> new Customer("abc", email))
            .isInstanceOf(TypedException.class)
            .hasMessage("Error: Email cannot be blank");
      }
    }

    @Nested
    @DisplayName("50 글자를 넘으면")
    class Context_with_over_max_length {

      @Test
      @DisplayName("예외를 발생시킨다.")
      void it_throws_exception() {
        String email = "thisisoverfiftywordtomakeemailclassforrule@gmail.com";

        assertThatThrownBy(() -> new Customer("abc", email))
            .isInstanceOf(TypedException.class)
            .hasMessage("Error: Email cannot be longer than 50 characters");
      }
    }

    @Nested
    @DisplayName("잘못된 형식으로 입력하면")
    class Context_with_invalid_email {

      @ParameterizedTest
      @ValueSource(strings = {"thisisnotemail", "Abc.example.com", "A@b@example.com"})
      @DisplayName("예외를 발생시킨다.")
      void it_throws_exception(String email) {
        assertThatThrownBy(() -> new Customer("abc", email))
            .isInstanceOf(TypedException.class)
            .hasMessage("Error: Email format is invalid");
      }
    }

    @Nested
    @DisplayName("올바른 형식의 이메일을 전달하면")
    class Context_with_valid_email {

      @ParameterizedTest
      @ValueSource(strings = {"real@gmail.com", "real123@naver.re.com", "username@domain.com"})
      @DisplayName("정상적으로 생성된다.")
      void it_return_email(String email) {
        Customer customer = new Customer("abc", email);

        assertThat(customer).isNotNull();
        assertThat(customer.getEmail()).isEqualTo(email);
      }
    }
  }

  @Nested
  @DisplayName("Customer는 Name에")
  class Describe_constructor {

    @Nested
    @DisplayName("null이나 빈 값을 전달하면")
    class Context_with_null_or_blank {

      @ParameterizedTest
      @NullAndEmptySource
      @DisplayName("예외가 발생한다")
      void it_throw_exception(String name) {
        assertThatThrownBy(() -> new Customer(name, "abc@gmail.com"))
            .isInstanceOf(TypedException.class)
            .hasMessage("Error: Customer name cannot be blank");
      }
    }

    @Nested
    @DisplayName("20글자를 넘으면")
    class Context_with_over_max_length {

      @Test
      @DisplayName("예외가 발생한다")
      void it_throw_exception() {
        assertThatThrownBy(() -> new Customer("abcdefghijklmnopqrstuvwxyz", "abc@gmail.com"))
            .isInstanceOf(TypedException.class)
            .hasMessage("Error: Customer name cannot be longer than 20 characters");
      }
    }

    @Nested
    @DisplayName("알파벳이 아닌 문자가 포함되어 있으면")
    class Context_with_non_alphabet_characters {

      @Test
      @DisplayName("예외가 발생한다")
      void it_throw_exception() {
        assertThatThrownBy(() -> new Customer("asdd0213", "abc@gmail.com"))
            .isInstanceOf(TypedException.class)
            .hasMessage("Error: Customer name only accepts alphabets");
      }
    }

    @Nested
    @DisplayName("올바른 이름을 전달하면")
    class Context_with_valid_name {

      @Test
      @DisplayName("정상적으로 생성된다")
      void it_should_success() {
        Customer customer = new Customer("abcd", "abc@gmail.com");

        assertThat(customer).isNotNull();
        assertThat(customer.getName()).isEqualTo("abcd");
      }
    }
  }
}