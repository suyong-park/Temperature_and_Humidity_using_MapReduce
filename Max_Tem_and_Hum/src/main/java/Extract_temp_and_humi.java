import org.apache.hadoop.io.Text;

public class Extract_temp_and_humi {

    private String dateTime; // 날짜, 시간
    private float humidity;     // 습도
    private float temperature;  // 온도

    public Extract_temp_and_humi(Text text) {

        try {
            String[] columns = text.toString().split(",");
            // csv 파일은 , 구분자로 사용하기 때문에 이를 분리해 준다.

            dateTime = columns[0].substring(0, 10); // csv 파일의 첫 번째 컬럼에서 시간까지 모두 자르고 단위를 하루 단위로 하기 위함
            humidity = Float.parseFloat(columns[1]);
            temperature = Float.parseFloat(columns[2]);
        }
        catch (Exception e) {
            System.out.println("Error parsing a record : " + e.getMessage());
        }
    }

    public String getDateTime() { return dateTime; }
    public double getHumidity() { return humidity; }
    public double getTemperature() { return temperature; }
}
