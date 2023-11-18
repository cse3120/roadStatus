package com.api.road.status;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RoadStatusBDDTest {

    @Karate.Test
    Karate testGetRoadStatus(){
        return Karate.run("roadStatus").relativeTo(getClass());
    }

}
