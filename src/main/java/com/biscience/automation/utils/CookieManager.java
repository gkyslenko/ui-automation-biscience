package com.biscience.automation.utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class CookieManager {

    private static final Map<String, Set<Cookie>> COOKIES = new ConcurrentHashMap<>();
    private static final Map<String, Object> USER_LOCKS = new ConcurrentHashMap<>();

    private CookieManager() {
    }

    public static void save(String username, WebDriver driver) {
        synchronized (lockFor(username)) {
            COOKIES.put(username, new HashSet<>(driver.manage().getCookies()));
        }
    }

    public static boolean hasSavedCookies(String username) {
        Set<Cookie> cookies = COOKIES.get(username);
        return cookies != null && !cookies.isEmpty();
    }

    public static void inject(String username, WebDriver driver, String baseUrl) {
        Set<Cookie> cookies = COOKIES.getOrDefault(username, Collections.emptySet());
        driver.get(baseUrl);
        driver.manage().deleteAllCookies();
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
        driver.get(baseUrl);
    }

    public static void clear(String username) {
        synchronized (lockFor(username)) {
            COOKIES.remove(username);
        }
    }

    private static Object lockFor(String username) {
        return USER_LOCKS.computeIfAbsent(username, k -> new Object());
    }
}


