package api.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	static public SimpleDateFormat getSimpleDateFormat() {
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}
	
	// 時間を加える
    static public Date add_hours( Date d1, int delta ) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        cal1.add(Calendar.HOUR_OF_DAY, delta );
        return cal1.getTime();
    }
	// 時間を加える
    static public Date add_minutes( Date d1, int delta ) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        cal1.add(Calendar.MINUTE, delta );
        return cal1.getTime();
    }
    
    
}
