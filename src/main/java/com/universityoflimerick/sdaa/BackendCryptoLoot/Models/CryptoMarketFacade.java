package com.universityoflimerick.sdaa.BackendCryptoLoot.Models;

import com.universityoflimerick.sdaa.BackendCryptoLoot.Repositories.BankApiRepository;
import com.universityoflimerick.sdaa.BackendCryptoLoot.Repositories.FeedBackRepository;

/**
 * CryptoMarketFacade used to simplify purchasing coin
 */
public class CryptoMarketFacade {
    FeedBackRepository feedBackRepository;
    //Fake Bank API
    BankApiRepository bankApiRepository;

    /**
     * sets instance variables with autowired repositories
     * @param feedBackRepository
     * @param bankApiRepository
     */
    public CryptoMarketFacade(FeedBackRepository feedBackRepository, BankApiRepository bankApiRepository) {
        this.feedBackRepository = feedBackRepository;
        this.bankApiRepository = bankApiRepository;
    }

    /**
     * purchaseCoin
     * @param user contains user details
     * @param amount amount in USD of coin user wishes to purchase
     * @return string result depending on if user has sufficient funds and feedback to purchase coin
     */
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
