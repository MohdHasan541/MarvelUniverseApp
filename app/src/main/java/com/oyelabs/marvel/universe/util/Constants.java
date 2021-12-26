package com.oyelabs.marvel.universe.util;

import static com.oyelabs.marvel.universe.util.HashGenerator.generate;

import java.sql.Timestamp;

public class Constants {
    public static final String BASE_URL = "https://gateway.marvel.com/";
    public static final String PUBLIC_KEY = "c9081af23ccadcc39b56abbfeb40aa86";
    public static final String PRIVATE_KEY = "285001cfe0a522c6db300ff1f85b41548b4d7b3b";
    public static final long TIMESTAMP = new Timestamp(System.currentTimeMillis()).getTime();
    public static final String HASH = generate(TIMESTAMP, PRIVATE_KEY, PUBLIC_KEY);
}
