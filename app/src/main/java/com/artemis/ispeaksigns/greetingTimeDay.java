package com.artemis.ispeaksigns;

import java.util.Calendar;

public class greetingTimeDay {

    public String getGreeting()
    {
        String greeting = "";
        int currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (currentTime <=12)
        {
            if (currentTime == 0)
            {
                greeting = "Magandang gabi sa iyo";
            }else {
                greeting = "Magandang umaga sa iyo";
            }
        }else if (currentTime <=17)
        {
            greeting = "Magandang hapon sa iyo";
        }
        else {
            greeting = "Magandang gabi sa iyo";
        }

        return greeting;
    }
}
