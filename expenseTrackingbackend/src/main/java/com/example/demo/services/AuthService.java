package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public boolean register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false; // User already exists
        }
        userRepository.save(user);
        return true;
    }

    public User login(String username, String password, HttpSession session) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Initialize lazy-loaded collection if needed
            user.getExpense().size();
            session.setAttribute("user", user);
            return user;
        }
        return null;
    }

    public void logout(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser(HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser != null) {
            return userRepository.findById(sessionUser.getId()).orElse(null);
        }
        return null;
    }
}
