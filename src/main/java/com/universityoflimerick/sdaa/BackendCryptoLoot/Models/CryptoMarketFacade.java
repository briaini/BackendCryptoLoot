package com.universityoflimerick.sdaa.BackendCryptoLoot.Models;

import com.universityoflimerick.sdaa.BackendCryptoLoot.Repositories.BankApiRepository;
import com.universityoflimerick.sdaa.BackendCryptoLoot.Repositories.FeedBackRepository;

public class CryptoMarketFacade {
    FeedBackRepository feedBackRepository;
    //Fake Bank API
    BankApiRepository bankApiRepository;

    public CryptoMarketFacade(FeedBackRepository feedBackRepository, BankApiRepository bankApiRepository) {
        this.feedBackRepository = feedBackRepository;
        this.bankApiRepository = bankApiRepository;
    }

    public String purchaseCoin(UserProfile user, float amount) {
        Integer userId = user.getId();
        Feedback feedback = null;
        for (Feedback feedback1 : feedBackRepository.findAll()) {
            if(feedback1.getUser()==userId)
                feedback = feedback1;
        }
        BankAccount bankAccount = null;
        for (BankAccount bankAccount1 : bankApiRepository.findAll()) {
            if(bankAccount1.getUser()==userId)
                bankAccount = bankAccount1;
        }
        if (feedback == null)
            return "feedback null";
        if (bankAccount == null)
            return "bankAccount null";
        boolean sufficientFunds = bankAccount.hasSufficientFunds(amount);
        boolean sufficientFeedback = feedback.hasSufficientFeedbackscore(75);
        if (!sufficientFunds)
            return "Insufficient funds";
        if (!sufficientFeedback)
            return "Insufficient feedback";
        else
            return "Purchased coins for " + amount;
    }
}
