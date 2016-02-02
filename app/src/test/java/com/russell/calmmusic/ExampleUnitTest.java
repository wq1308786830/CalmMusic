package com.russell.calmmusic;

import com.russell.calmmusic.tools.datas.NetEaseLyric;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void byteReadFile(){
        String fileName = "/Users/wq/Desktop/Lyric/60263";
        NetEaseLyric netEaseLyric = new NetEaseLyric();
        netEaseLyric.byteReadFile(fileName);
    }
    @Test
    public void charReadFile(){
        String fileName = "/Users/wq/Desktop/Lyric/60263";
        NetEaseLyric netEaseLyric = new NetEaseLyric();
        System.out.print(netEaseLyric.charReadFile(fileName));
    }
    @Test
    public void readJsons(){
        String fileName = "/Users/wq/Desktop/Lyric/60263";
        NetEaseLyric netEaseLyric = new NetEaseLyric();
        netEaseLyric.outLyric(fileName);
    }
}