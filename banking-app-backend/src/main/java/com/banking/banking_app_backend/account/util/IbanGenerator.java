package com.banking.banking_app_backend.account.util;

import com.banking.banking_app_backend.account.exception.InvalidAccounNumberException;

import java.math.BigInteger;

public class IbanGenerator {

    private static final String COUNTRY_CODE = "TR";
    private static final String BANK_CODE = "00062";
    private static final String RESERVED_DIGIT = "0";

    public static String generate(String accountNumber) {

        if(accountNumber == null || !accountNumber.matches("\\d{15}")){
            throw new InvalidAccounNumberException("Geçersiz hesap numarası");
        }

        String accountNumber16 = "0" + accountNumber;

        String temporaryIban =
                COUNTRY_CODE
                + "00"
                + BANK_CODE
                + RESERVED_DIGIT
                + accountNumber16;

        int checkDigits = calculateCheckDigits(temporaryIban);

        return COUNTRY_CODE
                + String.format("%02d", checkDigits)
                + BANK_CODE
                + RESERVED_DIGIT
                + accountNumber16;

    }

    private static int calculateCheckDigits(String iban) {

        String rearranged =
                iban.substring(4)
                        + numericCountryCode(iban.substring(0, 2))
                        + iban.substring(2, 4);

        BigInteger number = new BigInteger(rearranged);

        int remainder = number.mod(BigInteger.valueOf(97)).intValue();

        return 98 - remainder;
    }

    private static String numericCountryCode(String countryCode) {

        StringBuilder result = new StringBuilder();

        for (char character : countryCode.toCharArray()) {
            result.append(character - 'A' + 10);
        }

        return result.toString();
    }

}
