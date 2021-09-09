package org.prgrms.kdt.command;

import java.text.MessageFormat;

public class Output {
    public static void commandChoose() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type 'exit' for Exit.");
        System.out.println("Type 'create' to create a new voucher");
        System.out.println("Type 'list' to list all vouchers");
    }

    public static void inputTypeError(final String commandInput) {
        System.out.println("=== Input type error ===");
        System.out.println(MessageFormat.format("{0}은(는) 지원하지 않는 명령어입니다.", commandInput));
    }

    public static void voucherChoose() {
        System.out.println("=== Voucher Create ===");
        System.out.println("Choose the type of voucher.");
        System.out.println("- FixedAmountVoucher");
        System.out.println("- PercentDiscountVoucher");
    }

    public static void howMuchDiscount(final String voucherType) {
        if (voucherType.equals("FixedAmountVoucher")) {
            System.out.println("할인 가격을 얼마로 설정하시겠습니까?");
        } else if (voucherType.equals("PercentDiscountVoucher")) {
            System.out.println("할인율을 몇 퍼센트로 설정하시겠습니까?");
        } else {
            System.out.println("=== Input type error ===");
            System.out.println(voucherType + "은(는) 존재하지 않는 voucher type 입니다.");
            System.out.println("'FixedAmountVoucher', 'PercentDiscountVoucher'를 입력하세요.");
        }
    }
}