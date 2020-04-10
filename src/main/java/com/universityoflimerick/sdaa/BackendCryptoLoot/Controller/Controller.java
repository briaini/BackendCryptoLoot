package com.universityoflimerick.sdaa.BackendCryptoLoot.Controller;

import com.google.gson.Gson;
import com.universityoflimerick.sdaa.BackendCryptoLoot.Models.CryptoMarketFacade;
import com.universityoflimerick.sdaa.BackendCryptoLoot.Repositories.BankApiRepository;
import com.universityoflimerick.sdaa.BackendCryptoLoot.Repositories.FeedBackRepository;
import com.universityoflimerick.sdaa.BackendCryptoLoot.Repositories.UserProfileRepository;
import com.universityoflimerick.sdaa.BackendCryptoLoot.Models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    FeedBackRepository feedBackRepository;
    //    //Fake Bank API
    @Autowired
    BankApiRepository bankApiRepository;

    @GetMapping(path = "/test")
    public String getComment() {
        return "hello";
    }

    @PostMapping(path = "/purchase")
    public String purchaseCoin(@RequestBody String body) {
        String result = "";
        Gson gson = new Gson();
        Map map = gson.fromJson(body, Map.class);
        float amount = Float.parseFloat(map.get("amount").toString());

        if (userProfileRepository.findBySub((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isPresent()) {
            UserProfile user = userProfileRepository.findBySub((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).get();
            CryptoMarketFacade cryptoMarket = new CryptoMarketFacade(feedBackRepository, bankApiRepository);
            result = cryptoMarket.purchaseCoin(user, amount);
        } else {
            result = "User profile not set";
        }
        return result;
    }


    @GetMapping(path = "/profile")
    public Map<String, String> getProfileInfo() {
        if (userProfileRepository.findBySub((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isPresent()) {
            UserProfile profile = userProfileRepository.findBySub((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).get();
            Map<String, String> map = new HashMap<>();
            map.put("name", profile.getName());
            return map;
        } else {
            return new HashMap<>();
        }
    }

    @PostMapping(path = "/userdetails") // Map ONLY POST Requests
    public @ResponseBody
    String saveProfileInfo(@RequestBody String body) {
        if (userProfileRepository.findBySub((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isPresent()) {

            UserProfile userProfile = userProfileRepository.findBySub((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).get();
            Gson gson = new Gson();
            UserProfile tempUserProfile = gson.fromJson(body, UserProfile.class);
            userProfile.setName(tempUserProfile.getName());

            userProfileRepository.save(userProfile);

            return ("User Profile Updated");
        } else {
            Gson gson = new Gson();
            UserProfile userProfile = gson.fromJson(body, UserProfile.class);
            userProfile.setSub((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

            userProfileRepository.save(userProfile);
            return ("User Profile Saved For First Time");
        }
    }
}
