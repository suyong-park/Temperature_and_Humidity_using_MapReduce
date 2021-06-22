import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Extract_temp_and_humi_mapper extends Mapper<LongWritable, Text, Text, Text> {

    /*
    TODO 동작원리
    1. 처음 mapper에 들어오는 데이터는 input split 이다. csv 파일이 input split으로 되는 것이다.
    2. 이 때, input split의 key 값은 offset, value 값은 하나의 레코드이다.
    3. mapper 종료 이후 key 값은 해당 row 값, value 값은 1로 설정한다.
    */

    private final Text outputKey = new Text(); // 출력 키
    private final Text outputValue = new Text(); // 출력 값

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        Extract_temp_and_humi parser = new Extract_temp_and_humi(value);

        outputKey.set(parser.getDateTime()); // key 값 : 날짜와시간
        outputValue.set(parser.getHumidity() + "," + parser.getTemperature()); // value 값 : 습도,온도

        context.write(outputKey, outputValue);
    }
}
