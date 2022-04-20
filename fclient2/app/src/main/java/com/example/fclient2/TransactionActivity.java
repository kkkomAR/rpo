package com.example.fclient2;

public interface TransactionActivity {
    String enterPin(int ptc, String amount);
    void transactionResult(boolean result);
}