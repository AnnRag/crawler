package org.example.crawler.web;

import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

public class Counter {

    @Getter
    private ConcurrentHashMap<String, Integer> wordsCount = new ConcurrentHashMap<>(100);

    public final static Counter getInstance = new Counter();

    public ConcurrentHashMap<String, Integer> count (String text) {
        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
            wordsCount.compute(words[i], (k, v) -> v == null ? 1 : v + 1);
        }
        return wordsCount;
    }
}
