package org.prgrms.kdt.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.dto.VoucherDto;
import org.prgrms.kdt.type.VoucherType;

class PercentDiscountVoucherTest {

  @Nested
  @DisplayName("PercentAmountVoucher는")
  class Described_constructor {

    @Nested
    @DisplayName("1 ~ 100 사이 정수를 입력하면")
    class Context_with_positive {

      @ParameterizedTest
      @ValueSource(longs = {1, 50, 100})
      @DisplayName("정상적으로 생성된다.")
      void test_constructor_called(Long percent) {
        var voucherDto = new VoucherDto(UUID.randomUUID(), UUID.randomUUID(), percent,
            VoucherType.of(1),
            LocalDateTime.now());
        var fixedAmountVoucher = new PercentDiscountVoucher(voucherDto);

        assertThat(fixedAmountVoucher).isNotNull();
      }
    }

    @Nested
    @DisplayName("1 ~ 100 사이 정수를 입력하지 않으면")
    class Context_with_non_positive {

      @ParameterizedTest
      @ValueSource(longs = {-1, 0, 101})
      @DisplayName("예외가 발생한다.")
      void test_constructor_called(Long percent) {
        var voucherDto = new VoucherDto(UUID.randomUUID(), UUID.randomUUID(), percent,
            VoucherType.of(1), LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(voucherDto));
      }
    }
  }
}