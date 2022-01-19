package com.relay.iot.consumer.simulator.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * @author omidp
 *
 */
public class DateUtil
{


    private static final String[] formats = new String[] {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-dd hh:mm:ss",
             "yyyy-MM-dd hh:mm", "dd MMM yyyy", "dd MMM yyyy hh:mm", "dd MMM yyyy HH:mm", "dd MMM yyyy hh:mm:ss",
             "dd MMM yyyy HH:mm:ss", "MM/yy", "dd MMM yyyy, hh:mm:ss","E MMM dd HH:mm:ss Z yyyy", "dd mmm yyyy, hh:mm a",
             "yyyy-MM-dd'T'hh:mm:ss"};

    private DateUtil()
    {
    }

    

    public static Date toDate(String dateAsString)
    {
        if(StringUtil.isEmpty(dateAsString))
            return null;
        Date result = null;
        for (String format : formats)
        {
            try
            {
                return new SimpleDateFormat(format).parse(dateAsString);
            }
            catch (ParseException e)
            {
                //DO NOTHING
            }
        }
        return result;

    }

  
}