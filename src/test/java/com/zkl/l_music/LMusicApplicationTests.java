package com.zkl.l_music;

import com.zkl.l_music.dao.SingerDao;
import com.zkl.l_music.data.AlbumData;
import com.zkl.l_music.data.SingerData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;

@SpringBootTest
class LMusicApplicationTests {

    @Test
    void contextLoads() {
    }

//    @Test
//    void SingerData() {
//        SingerData singerData = new SingerData();
//        singerData.getSingerData("1001");
//        System.out.println("okkkk");
//    }

//    @Test
//    void AlbumData() throws ParseException {
//        AlbumData albumData = new AlbumData();
//        albumData.getAlbumData(null,1);
//        System.out.println("okkkk");
//    }
}
