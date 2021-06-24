import org.apache.hadoop.io.Text;

public class Extract_temp_and_humi {

    private String calendar;
    private String timestamp;   // 날짜, 시간
    private float humidity;     // 습도
    private float temperature;  // 온도

    public Extract_temp_and_humi(Text text) {

        try {
            String[] columns = text.toString().split(",");
            // csv 파일은 , 구분자로 사용하기 때문에 이를 분리해 준다.

            calendar = columns[0].substring(0, 10);
            timestamp = columns[1];
            temperature = Float.parseFloat(columns[2]);
            humidity = Float.parseFloat(columns[3]);

        } catch (Exception e) {
            System.out.println("Error parsing a record : " + e.getMessage());
        }
    }

    public String getCalendar() { return calendar; }
    public String getTimestamp() { return timestamp; }
    public double getHumidity() { return humidity; }
    public double getTemperature() { return temperature; }
}
