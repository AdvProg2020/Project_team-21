package Server.Model;

import Server.Model.DisAndOffStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DisAndOffService {
    protected Date startDate;
    protected Date endDate;
    protected static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static String inputDateFormat= "yyyy/MM/dd HH:mm:ss";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inputDateFormat);

    public DisAndOffService(String startDate , String endDate) throws Exception {
        Date inputStartDate = simpleDateFormat.parse(startDate);
        Date inputEndDate = simpleDateFormat.parse(endDate);
        this.startDate = inputStartDate;
        this.endDate = inputEndDate;
    }

    public DisAndOffStatus getDisAndOffStatus (){
        Date dateNow = new Date();
        if (startDate.compareTo(dateNow)>0){
            return DisAndOffStatus.Not_Started;
        }
        if (endDate.compareTo(dateNow)>=0){
            return DisAndOffStatus.Expired;
        }
        return DisAndOffStatus.Active;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
