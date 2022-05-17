package com.examples.May16;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

public class SongCacheImpl implements SongCache{

    //a map of songs with a number of plays. ConcurrentHashMap is thread-safe.
    private Map<String, Integer> map = new ConcurrentHashMap<>();


    @Override
    public void recordSongPlays(String songId, int numPlays) {
        map.put(songId, map.getOrDefault(songId, 0)+numPlays);

    }

    //return the number of plays Or -1 if songId doesn't exist.
    @Override
    public int getPlaysForSong(String songId) {
        return map.getOrDefault(songId, -1);
    }

    // Use PriorityQueue to sort songIds. Return Top N songs.
    // The Implementation of Comparator is that: compare numPlays,
    // if numPlays are equal return the comparing result of two Strings by using compareTo().
    @Override
    public List<String> getTopNSongsPlayed(int n) {
        List<String> ans = new LinkedList<>();
        PriorityQueue<String> pq = new PriorityQueue<>((a, b)-> {
            int v1 = map.get(a);
            int v2 = map.get(b);
            if(v1 == v2) {
                return a.compareTo(b);
            }
            return v1 - v2;
        });
        for(String str:map.keySet()) {
            pq.offer(str);
            if(pq.size()>n) {
                pq.poll();
            }
        }
        for(int i=0; i<n; i++) {
            ans.add(pq.poll());
        }
        return ans;
    }
}
